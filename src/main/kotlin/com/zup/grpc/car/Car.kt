package com.zup.grpc.car

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Car(
	@Column(nullable = false)
	val model: String,
	@Column(nullable = false)
	val plate: String,
	@Id
	@GeneratedValue
	val id: Long? = null
)
