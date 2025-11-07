package org.example

import java.io.*
import java.time.LocalDateTime

class GestorPersistencia {
    private val archivoUsuarios = File("usuarios_banco.dat")

    data class DatosUsuarioGuardado(
        val id: Int,
        val nombre: String,
        val apellido: String,
        val dni: String,
        val numeroCuenta: String,
        val saldo: Double,
        val fechaCreacion: LocalDateTime
    ) : Serializable

    fun guardarUsuario(cuenta: Cuenta) {
        val usuarios = cargarUsuarios().toMutableList()
        usuarios.removeIf { it.dni == cuenta.usuario.dni }
        usuarios.add(
            DatosUsuarioGuardado(
                cuenta.usuario.id,
                cuenta.usuario.nombre,
                cuenta.usuario.apellido,
                cuenta.usuario.dni,
                cuenta.numeroCuenta,
                cuenta.obtenerSaldo(),
                cuenta.usuario.fechaCreacion
            )
        )
        guardarTodos(usuarios)
    }

    fun cargarUsuarios(): List<DatosUsuarioGuardado> {
        if (!archivoUsuarios.exists()) return emptyList()
        return try {
            ObjectInputStream(FileInputStream(archivoUsuarios)).use { it.readObject() as List<DatosUsuarioGuardado> }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun guardarTodos(usuarios: List<DatosUsuarioGuardado>) {
        ObjectOutputStream(FileOutputStream(archivoUsuarios)).use { it.writeObject(usuarios) }
    }
}