package com.zup.grpc.car

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface CarRepository : JpaRepository<Car, Long> {
	fun existsByPlate(plate: String): Boolean
}
