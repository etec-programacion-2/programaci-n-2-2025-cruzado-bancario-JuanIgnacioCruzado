package org.example

class ServicioBancario(private val banco: Banco) {

    fun crearNuevaCuenta(id: Int, nombre: String, apellido: String, dni: String, saldoInicial: Double): Cuenta {
        val usuario = Usuario(id, nombre, apellido, dni)
        val numeroCuenta = generarNumeroCuenta()
        val cuenta = Cuenta(numeroCuenta, saldoInicial, usuario)
        banco.agregarCuenta(cuenta)
        return cuenta
    }

    private fun generarNumeroCuenta(): String {
        return (100000..999999).random().toString()
    }

    fun transferir(cuentaOrigen: Cuenta, cuentaDestino: Cuenta, monto: Double): Boolean {
        return cuentaOrigen.transferir(monto, cuentaDestino)
    }
}
