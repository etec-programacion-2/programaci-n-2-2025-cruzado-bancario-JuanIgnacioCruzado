package org.example

fun main() {
    val usuario = Usuario(1, "Juan", "Pérez")
    val cuenta = Cuenta("123456", 1000.0, usuario)

    println("Usuario: ${usuario.nombre} ${usuario.apellido}")
    println("Número de cuenta: ${cuenta.numeroCuenta}")
    println("Saldo inicial: ${cuenta.obtenerSaldo()}")
}
