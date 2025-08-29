package org.example

import java.time.LocalDateTime

abstract class Transaccion(
    val id: Int,
    val monto: Double,
    val fecha: LocalDateTime
) 