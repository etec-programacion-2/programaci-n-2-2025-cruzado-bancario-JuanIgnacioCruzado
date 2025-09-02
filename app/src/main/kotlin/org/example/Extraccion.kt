package org.example

import java.time.LocalDateTime

class Extraccion(
    id: Int,
    monto: Double,
    fecha: LocalDateTime,
    val cuenta: Cuenta
) : Transaccion(id, monto, fecha)
