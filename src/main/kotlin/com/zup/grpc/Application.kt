package com.zup.grpc

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
	build()
			.args(*args)
			.packages("com.zup.grpc")
			.start()
}
