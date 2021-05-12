package com.zup.grpc.car

import io.grpc.ManagedChannel
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.inject.Inject

@MicronautTest(transactional = false)
internal class CarServiceTest {

	@Inject
	lateinit var grpc: CarServiceGrpc.CarServiceBlockingStub

	@Inject
	lateinit var repository: CarRepository

	@Test
	fun `should return a valid response`() {

		val request = CarRequest.newBuilder()
				.setModel("Corsa")
				.setPlate("PEW1235")
				.build()
		val response = grpc.register(request)

		assertEquals(request.plate, response.plate)
		assertEquals(1L, response.id)
	}

	@Test
	fun `should return a RuntimeException because plate already exists`() {

		repository.save(Car("Corsa", "PEW1235"))

		val err = assertThrows<StatusRuntimeException> {
			grpc.register(
				CarRequest.newBuilder()
						.setModel("Corsa")
						.setPlate("PEW1235")
						.build()
			)

		}
		assertEquals(Status.ALREADY_EXISTS.code, err.status.code)
		assertEquals("Plate already exists", err.status.description)
	}
}

@Factory
internal class Clients {
	@Bean
	fun blockingStub(
		@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel?
	): CarServiceGrpc.CarServiceBlockingStub {
		return CarServiceGrpc.newBlockingStub(channel)
	}
}
