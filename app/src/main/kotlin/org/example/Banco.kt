package org.example

class Banco {
    private val cuentas = mutableListOf<Cuenta>()

    fun agregarCuenta(cuenta: Cuenta) {
        cuentas.add(cuenta)
    }

    fun buscarCuenta(numeroCuenta: String): Cuenta? {
        return cuentas.find { it.numeroCuenta == numeroCuenta }
    }

    fun obtenerCuentas(): List<Cuenta> {
        return cuentas
    }
}
