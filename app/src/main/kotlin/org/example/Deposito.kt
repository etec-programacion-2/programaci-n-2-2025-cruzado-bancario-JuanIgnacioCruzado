package org.example

import java.time.LocalDateTime

class Deposito(
    id: Int,
    monto: Double,
    fecha: LocalDateTime,
    val cuenta: Cuenta
) : Transaccion(id, monto, fecha)
