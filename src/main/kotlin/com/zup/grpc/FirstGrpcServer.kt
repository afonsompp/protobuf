package com.zup.grpc

import com.google.protobuf.Timestamp
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import java.time.LocalDateTime
import java.time.ZoneId

fun main() {
	val server = ServerBuilder
			.forPort(8082)
			.addService(FirstGrpcServer())
			.build()
	server.start()
	server.awaitTermination()
}

class FirstGrpcServer : ProtobufServiceGrpc.ProtobufServiceImplBase() {
	override fun send(request: EmployeeRequest?, responseObserver: StreamObserver<EmployeeResponse>?) {
		val instant = LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant()
		val createAt = Timestamp.newBuilder()
				.setNanos(instant.nano)
				.setSeconds(instant.epochSecond)

		val name = if (request?.name.isNullOrBlank()) request?.name else "[???]"
		val response = EmployeeResponse.newBuilder()
				.setName(name)
				.setCreateAt(createAt)
				.build()

		println(response)

		responseObserver?.onNext(response)
		responseObserver?.onCompleted()
	}
}
