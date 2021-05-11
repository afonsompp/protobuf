package com.zup.grpc

fun main() {
	val request =
		EmployeeRequest.newBuilder()
				.setName("Afonso")
				.setEmail("afonso@email.com")
				.setCpf("026.542.202.73")
				.setAge(18)
				.setRole(Role.DEV).addAddress(
					Address.newBuilder()
							.setStreet("João Paulo I")
							.setCode("76920-000")
							.setNumber("1645").build()
				).build()
}
