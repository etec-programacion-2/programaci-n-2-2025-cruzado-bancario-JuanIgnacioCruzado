package org.example

import javax.swing.JFrame

class AplicacionBancaria {
    private val gestorPersistencia = GestorPersistencia()
    private val banco = Banco()
    private val servicio = ServicioBancario(banco, gestorPersistencia)
    private var ventanaActual: JFrame? = null

    init {
        cargarDatos()
    }

    fun iniciar() {
        mostrarLogin()
    }

    private fun cargarDatos() {
        gestorPersistencia.cargarUsuarios().forEach { datosUsuario ->
            servicio.crearCuentaDesdeGuardado(
                datosUsuario.id,
                datosUsuario.nombre,
                datosUsuario.apellido,
                datosUsuario.dni,
                datosUsuario.numeroCuenta,
                datosUsuario.saldo,
                datosUsuario.fechaCreacion
            )
        }
    }

    fun mostrarLogin() {
        ventanaActual?.dispose()
        ventanaActual = VentanaLogin(this)
        ventanaActual?.isVisible = true
    }

    fun mostrarRegistro() {
        ventanaActual?.dispose()
        ventanaActual = VentanaRegistro(this)
        ventanaActual?.isVisible = true
    }

    fun mostrarDashboard(cuenta: Cuenta) {
        ventanaActual?.dispose()
        ventanaActual = VentanaDashboard(this, cuenta, servicio)
        ventanaActual?.isVisible = true
    }

    fun autenticar(dni: String): Cuenta? {
        return banco.obtenerCuentas().find { it.usuario.dni == dni }
    }

    fun registrarUsuario(nombre: String, apellido: String, dni: String, saldoInicial: Double): Cuenta {
        val cuenta = servicio.crearNuevaCuenta(
            banco.obtenerCuentas().size + 1,
            nombre,
            apellido,
            dni,
            saldoInicial
        )
        gestorPersistencia.guardarUsuario(cuenta)
        return cuenta
    }
}