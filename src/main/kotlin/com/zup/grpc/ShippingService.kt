package com.zup.grpc

import com.zup.grpc.frete.ShippingRequest
import com.zup.grpc.frete.ShippingResponse
import com.zup.grpc.frete.ShippingServiceGrpc
import io.grpc.stub.StreamObserver
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class ShippingService : ShippingServiceGrpc.ShippingServiceImplBase() {

	override fun calcShipping(
		request: ShippingRequest?,
		responseObserver: StreamObserver<ShippingResponse>?
	) {
		val response = ShippingResponse.newBuilder()
				.setCep(request?.cep)
				.setValue(Random.nextDouble(10.0, 200.0))
				.build()

		responseObserver?.onNext(response)
		responseObserver?.onCompleted()
	}
}
