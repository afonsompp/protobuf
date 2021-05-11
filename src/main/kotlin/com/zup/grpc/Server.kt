package com.zup.grpc

import io.grpc.ServerBuilder

fun main() {
	val server = ServerBuilder
			.forPort(8084)
			.addService(ShippingService())
			.build()
	server.start()
	server.awaitTermination()
}
