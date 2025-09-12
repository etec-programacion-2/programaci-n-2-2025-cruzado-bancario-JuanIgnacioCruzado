package org.example

import java.time.LocalDateTime

class Cuenta(
    val numeroCuenta: String,
    private var saldo: Double,
    val usuario: Usuario
) {
    private val transacciones = mutableListOf<Transaccion>()

    fun obtenerSaldo(): Double {
        return saldo
    }

    fun depositar(monto: Double) {
        saldo += monto
        transacciones.add(
            Deposito(
                transacciones.size + 1,
                monto,
                LocalDateTime.now(),
                this
            )
        )
    }

    fun extraer(monto: Double): Boolean {
        return if (saldo >= monto) {
            saldo -= monto
            transacciones.add(
                Extraccion(
                    transacciones.size + 1,
                    monto,
                    LocalDateTime.now(),
                    this
                )
            )
            true
        } else {
            false
        }
    }

    fun transferir(monto: Double, destino: Cuenta): Boolean {
        return if (saldo >= monto) {
            saldo -= monto
            destino.saldo += monto

            val transferencia = Transferencia(
                transacciones.size + 1,
                this,
                destino,
                monto,
                LocalDateTime.now()
            )
            transacciones.add(transferencia)
            destino.transacciones.add(transferencia)
            true
        } else {
            false
        }
    }

    fun obtenerTransacciones(): List<Transaccion> {
        return transacciones
    }
}
