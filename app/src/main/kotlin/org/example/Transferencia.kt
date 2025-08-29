package org.example

import java.time.LocalDateTime

class Transferencia(
    id: Int,
    val cuentaOrigen: Cuenta,
    val cuentaDestino: Cuenta,
    monto: Double,
    fecha: LocalDateTime,
) : Transaccion(id, monto, fecha)
