package com.zup.grpc

import io.grpc.health.v1.HealthCheckRequest
import io.grpc.health.v1.HealthCheckResponse
import io.grpc.health.v1.HealthGrpc
import io.grpc.stub.StreamObserver

class HealthCheckService : HealthGrpc.HealthImplBase() {

	override fun check(
		request: HealthCheckRequest?,
		responseObserver: StreamObserver<HealthCheckResponse>?
	) {
		responseObserver?.onNext(
			HealthCheckResponse
					.newBuilder()
					.setStatus(HealthCheckResponse.ServingStatus.SERVING)
					.build()
		)
	}

	override fun watch(
		request: HealthCheckRequest?,
		responseObserver: StreamObserver<HealthCheckResponse>?
	) {
		responseObserver?.onNext(
			HealthCheckResponse
					.newBuilder()
					.setStatus(HealthCheckResponse.ServingStatus.SERVING)
					.build()
		)
	}
}
