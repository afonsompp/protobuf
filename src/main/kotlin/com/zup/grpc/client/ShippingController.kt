package com.zup.grpc.client

import com.zup.grpc.frete.ShippingRequest
import com.zup.grpc.frete.ShippingServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.math.RoundingMode
import javax.inject.Inject

@Controller("/shipping")
class ShippingController(@Inject val grpcClient: ShippingServiceGrpc.ShippingServiceBlockingStub) {
	@Get
	fun getValue(): HttpResponse<ShippingValueResponse> {

		val request = ShippingRequest.newBuilder().setCep("76920-000").build()
		val response = grpcClient.calcShipping(request)

		val value = response.value.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
		return HttpResponse.ok(ShippingValueResponse(response.cep, value))
	}
}
