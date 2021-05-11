package com.zup.grpc.client

import com.zup.grpc.frete.ShippingServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class ShippingGrpcClient {

	@Singleton
	fun shippingClient(@GrpcChannel("shipping") channel: ManagedChannel): ShippingServiceGrpc
	.ShippingServiceBlockingStub {
		return ShippingServiceGrpc.newBlockingStub(channel)
	}
}
