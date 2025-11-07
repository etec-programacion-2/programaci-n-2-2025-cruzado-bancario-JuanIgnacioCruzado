package org.example

import java.time.LocalDateTime

class ServicioBancario(
    private val banco: Banco,
    private val gestorPersistencia: GestorPersistencia
) {

    fun crearNuevaCuenta(id: Int, nombre: String, apellido: String, dni: String, saldoInicial: Double): Cuenta {
        val usuario = Usuario(id, nombre, apellido, dni)
        val numeroCuenta = generarNumeroCuenta()
        val cuenta = Cuenta(numeroCuenta, saldoInicial, usuario)
        banco.agregarCuenta(cuenta)
        return cuenta
    }

    fun crearCuentaDesdeGuardado(
        id: Int,
        nombre: String,
        apellido: String,
        dni: String,
        numeroCuenta: String,
        saldo: Double,
        fechaCreacion: LocalDateTime
    ): Cuenta {
        val usuario = Usuario(id, nombre, apellido, dni, fechaCreacion)
        val cuenta = Cuenta(numeroCuenta, saldo, usuario)
        banco.agregarCuenta(cuenta)
        return cuenta
    }

    private fun generarNumeroCuenta(): String {
        return (100000..999999).random().toString()
    }

    fun transferir(cuentaOrigen: Cuenta, cuentaDestino: Cuenta, monto: Double): Boolean {
        val resultado = cuentaOrigen.transferir(monto, cuentaDestino)
        if (resultado) {
            gestorPersistencia.guardarUsuario(cuentaOrigen)
            gestorPersistencia.guardarUsuario(cuentaDestino)
        }
        return resultado
    }

    fun obtenerCuentas(): List<Cuenta> {
        return banco.obtenerCuentas()
    }
}