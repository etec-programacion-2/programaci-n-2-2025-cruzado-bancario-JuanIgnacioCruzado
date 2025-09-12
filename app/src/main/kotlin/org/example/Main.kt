package org.example

import java.util.Scanner
import java.time.format.DateTimeFormatter

fun main() {
    val reader = Scanner(System.`in`)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    val usuario1 = Usuario(1, "Juani", "Cruzado")
    val cuenta1 = Cuenta("123456", 10000.0, usuario1)
    val usuario2 = Usuario(2, "Joaquin", "Ardevol")
    val cuenta2 = Cuenta("654321", 10000.0, usuario2)

    println("Bienvenido al sistema de gestión de cuentas bancarias.")

    var cuentaSeleccionada: Cuenta? = null
    var opcionUsuario: Int

    // Selección de usuario
    do {
        println(
            """
            === Elección de usuario ===
            1. Juani
            2. Joaquín 
            0. Salir
            """.trimIndent()
        )
        print("Seleccione un usuario: ")
        opcionUsuario = reader.nextInt()

        when (opcionUsuario) {
            1 -> {
                cuentaSeleccionada = cuenta1
                println("Usuario: ${usuario1.nombre} ${usuario1.apellido}")
                println("Cuenta Nº: ${cuenta1.numeroCuenta}")
                println("Saldo inicial: ${cuenta1.obtenerSaldo()}")
            }
            2 -> {
                cuentaSeleccionada = cuenta2
                println("Usuario: ${usuario2.nombre} ${usuario2.apellido}")
                println("Cuenta Nº: ${cuenta2.numeroCuenta}")
                println("Saldo inicial: ${cuenta2.obtenerSaldo()}")
            }
            0 -> println("Saliendo...")
            else -> println("Opción inválida")
        }
    } while (opcionUsuario !in listOf(0, 1, 2))

    if (cuentaSeleccionada == null) return

    // Menú principal
    var opcionAccion: Int
    do {
        println(
            """
            === Menú Principal ===
            1. Depositar
            2. Extraer
            3. Ver saldo
            4. Ver historial de transacciones
            5. Transferir
            0. Salir
            """.trimIndent()
        )
        print("Seleccione una opción: ")
        opcionAccion = reader.nextInt()

        when (opcionAccion) {
            1 -> {
                print("Ingrese monto a depositar: ")
                val monto = reader.nextDouble()
                if (monto > 0) {
                    cuentaSeleccionada.depositar(monto)
                    println("Depósito realizado. Saldo actual: ${cuentaSeleccionada.obtenerSaldo()}")
                } else {
                    println("Monto inválido")
                }
            }

            2 -> {
                print("Ingrese monto a extraer: ")
                val monto = reader.nextDouble()
                if (monto > 0) {
                    val exito = cuentaSeleccionada.extraer(monto)
                    if (exito) {
                        println("Extracción realizada. Saldo actual: ${cuentaSeleccionada.obtenerSaldo()}")
                    } else {
                        println("Fondos insuficientes.")
                    }
                } else {
                    println("Monto inválido")
                }
            }

            3 -> {
                println("Saldo disponible: ${cuentaSeleccionada.obtenerSaldo()}")
            }

            4 -> {
                println("=== Historial de transacciones ===")
                if (cuentaSeleccionada.obtenerTransacciones().isEmpty()) {
                    println("No hay transacciones registradas.")
                } else {
                    cuentaSeleccionada.obtenerTransacciones().forEach {
                        println(" - ${it::class.simpleName} de ${it.monto} el ${it.fecha.format(formatter)}")
                    }
                }
            }

            5 -> {
                println("Transferencia")
                println("Seleccione cuenta destino:")
                println("1. Juani (${cuenta1.numeroCuenta})")
                println("2. Joaquín (${cuenta2.numeroCuenta})")

                val destinoOpcion = reader.nextInt()
                val cuentaDestino = when (destinoOpcion) {
                    1 -> cuenta1
                    2 -> cuenta2
                    else -> null
                }

                if (cuentaDestino != null && cuentaDestino != cuentaSeleccionada) {
                    print("Ingrese monto a transferir: ")
                    val monto = reader.nextDouble()
                    if (monto > 0) {
                        val exito = cuentaSeleccionada.transferir(monto, cuentaDestino)
                        if (exito) {
                            println("Transferencia realizada.")
                            println("Saldo actual de origen: ${cuentaSeleccionada.obtenerSaldo()}")
                            println("Saldo actual de destino: ${cuentaDestino.obtenerSaldo()}")
                        } else {
                            println("Fondos insuficientes.")
                        }
                    } else {
                        println("Monto inválido")
                    }
                } else {
                    println("Cuenta destino inválida.")
                }
            }

            0 -> println("Gracias por usar el sistema. ¡Hasta luego!")
            else -> println("Opción inválida, intente de nuevo.")
        }
    } while (opcionAccion != 0)
}

