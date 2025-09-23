package org.example

class Banco {
    private val cuentas = mutableListOf<Cuenta>()

    fun agregarCuenta(usuario: Usuario, saldoInicial: Double): Cuenta {
        val numeroCuenta = (cuentas.size + 1).toString().padStart(6, '0')
        val cuenta = Cuenta(numeroCuenta, saldoInicial, usuario)
        cuentas.add(cuenta)
        return cuenta
    }

    fun buscarCuenta(numeroCuenta: String): Cuenta? {
        return cuentas.find { it.numeroCuenta == numeroCuenta }
    }

    fun listarCuentas(): List<Cuenta> {
        return cuentas
    }
}
