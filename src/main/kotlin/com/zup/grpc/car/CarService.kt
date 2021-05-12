package com.zup.grpc.car

import io.grpc.Status
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
class CarService(@Inject val repository: CarRepository) : CarServiceGrpc.CarServiceImplBase() {

	override fun register(request: CarRequest, responseObserver: StreamObserver<CarResponse>?) {
		if (repository.existsByPlate(request.plate)) {
			responseObserver?.onError(
				Status.ALREADY_EXISTS
						.withDescription("Plate already exists")
						.asRuntimeException()
			)
			return
		}
		var car = Car(request.model, request.plate)

		try {
			car = repository.save(car)
		} catch (e: ConstraintViolationException) {
			responseObserver?.onError(
				Status.INVALID_ARGUMENT
						.withDescription("Invalid arguments ")
						.asRuntimeException()
			)
			return
		}
		responseObserver?.onNext(car.id?.let {
			CarResponse.newBuilder().setId(it).setPlate(car.plate).build()
		})
		responseObserver?.onCompleted()
	}
}
