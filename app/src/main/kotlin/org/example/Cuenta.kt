package org.example

class Cuenta(
    val numeroCuenta: String,
    private var saldo: Double,
    val usuario: Usuario
) {
    fun obtenerSaldo(): Double {
        return saldo
    }
}

