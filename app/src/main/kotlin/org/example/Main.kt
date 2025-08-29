package org.example

fun main() {
    val usuario1 = Usuario(1, "Juani", "Cruzado")
    val usuario2 = Usuario(2, "Joaquin", "Ardevol")
    val cuenta1 = Cuenta("123456", 1000.0, usuario1)
    val cuenta2 = Cuenta("654321", 2000.0, usuario2)

    println("Usuario: ${usuario1.nombre} ${usuario1.apellido}")
    println("Número de cuenta: ${cuenta1.numeroCuenta}")
    println("Saldo inicial: ${cuenta1.obtenerSaldo()}")
    println("Usuario: ${usuario2.nombre} ${usuario2.apellido}")
    println("Número de cuenta: ${cuenta2.numeroCuenta}")
    println("Saldo inicial: ${cuenta2.obtenerSaldo()}")
}
