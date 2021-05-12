package com.zup.grpc

import com.zup.grpc.frete.ShippingRequest
import com.zup.grpc.frete.ShippingResponse
import com.zup.grpc.frete.ShippingServiceGrpc
import io.grpc.Status
import io.grpc.stub.StreamObserver
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class ShippingService : ShippingServiceGrpc.ShippingServiceImplBase() {

	override fun calcShipping(
		request: ShippingRequest?,
		responseObserver: StreamObserver<ShippingResponse>?
	) {
		val cep = request!!.cep

		if (cep == null || cep.isBlank()) {
			val error = Status.INVALID_ARGUMENT
					.withDescription("cep cannot be null or blank")
					.asRuntimeException()
			responseObserver?.onError(error)
			return
		}
		if (!cep.matches("[0-9]{5}-[\\d]{3}".toRegex())) {

			val error = Status.INVALID_ARGUMENT
					.withDescription("invalid cep format")
					.augmentDescription("provide a cep in format 99999-999")
					.asRuntimeException()
			responseObserver?.onError(error)
			return
		}
		if (cep.endsWith("333")) {

			val error = Status.PERMISSION_DENIED
					.withDescription("Access denied")
					.augmentDescription("token expired")
					.asRuntimeException()
			responseObserver?.onError(error)
			return
		}

		val response = ShippingResponse.newBuilder()
				.setCep(cep)
				.setValue(Random.nextDouble(10.0, 200.0))
				.build()

		responseObserver?.onNext(response)
		responseObserver?.onCompleted()
	}
}
