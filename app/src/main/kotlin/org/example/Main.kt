package org.example

import java.util.Scanner
import java.time.format.DateTimeFormatter
fun main() {

    val reader = Scanner(System.`in`)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    val usuario1 = Usuario(1, "Juani", "Cruzado")
    val cuenta1 = Cuenta("123456", 1000.0, usuario1)


    println("Bienvenido al sistema de gestión de cuentas bancarias.")
    println("Usuario: ${usuario1.nombre} ${usuario1.apellido}")
    println("Cuenta Nº: ${cuenta1.numeroCuenta}")
    println("Saldo inicial: ${cuenta1.obtenerSaldo()}")

    var opcion: Int
    do {
        println(
            """
            === Menú Principal ===
            1. Depositar
            2. Extraer
            3. Ver saldo
            4. Ver historial de transacciones
            0. Salir
            """.trimIndent()
        )
        print("Seleccione una opción: ")
        opcion = reader.nextInt()
        
        when (opcion) {
            1 -> {
                print("Ingrese monto a depositar: ")
                val monto = reader.nextDouble()
                if (monto > 0) {
                    cuenta1.depositar(monto)
                    println("Depósito realizado. Saldo actual: ${cuenta1.obtenerSaldo()}")
                } else {
                    println("Monto inválido")
                }
            }

            2 -> {
                print("Ingrese monto a extraer: ")
                val monto = reader.nextDouble()
                if (monto > 0) {
                    val exito = cuenta1.extraer(monto)
                    if (exito) {
                        println("Extracción realizada. Saldo actual: ${cuenta1.obtenerSaldo()}")
                    } else {
                        println("Fondos insuficientes.")
                    }
                } else {
                    println("Monto inválido")
                }
            }

            3 -> {
                println("Saldo disponible: ${cuenta1.obtenerSaldo()}")
            }

            4 -> {
                println("=== Historial de transacciones ===")
                if (cuenta1.obtenerTransacciones().isEmpty()) {
                    println("No hay transacciones registradas.")
                } else {
                    cuenta1.obtenerTransacciones().forEach {
                        println(" - ${it::class.simpleName} de ${it.monto} el ${it.fecha.format(formatter)}")
                    }
                }
            }

            0 -> println("Gracias por usar el sistema. ¡Hasta luego!")
            else -> println("Opción inválida, intente de nuevo.")
        }
    } while (opcion != 0)
}