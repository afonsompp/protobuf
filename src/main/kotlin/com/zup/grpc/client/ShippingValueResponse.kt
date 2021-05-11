package com.zup.grpc.client

import java.math.BigDecimal

data class ShippingValueResponse(val cep: String, val value: BigDecimal)
