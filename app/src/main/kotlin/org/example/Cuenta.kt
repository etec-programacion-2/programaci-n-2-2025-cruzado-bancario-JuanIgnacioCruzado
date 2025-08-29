package org.example

class Cuenta(
    val numeroCuenta: String,
    private var saldo: Double,
    val propietario: Usuario
) {
    fun obtenerSaldo(): Double {
        return saldo
    }
}
