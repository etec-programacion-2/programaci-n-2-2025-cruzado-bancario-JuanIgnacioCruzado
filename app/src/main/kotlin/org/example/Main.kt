package org.example
import java.time.format.DateTimeFormatter
import java.util.Scanner

fun main() {
    val reader = Scanner(System.`in`)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    val banco = Banco()
    val servicio = ServicioBancario(banco) // ✅ nueva capa de servicio

    println("=== Sistema Bancario ===")
    print("¿Cuántos usuarios desea registrar? ")
    val cantidadUsuarios = reader.nextInt()
    reader.nextLine()

    // 🔁 ahora usamos el servicio para crear cada cuenta
    repeat(cantidadUsuarios) { i ->
        println("=== Registro de usuario ${i + 1} ===")
        print("Ingrese ID: ")
        val id = reader.nextInt()
        reader.nextLine()

        print("Ingrese nombre: ")
        val nombre = reader.nextLine()

        print("Ingrese apellido: ")
        val apellido = reader.nextLine()

        print("Ingrese DNI: ")
        val dni = reader.nextLine()

        print("Ingrese saldo inicial: ")
        val saldo = reader.nextDouble()
        reader.nextLine()

        servicio.crearNuevaCuenta(id, nombre, apellido, dni, saldo)
    }

    println("=== Todas las cuentas registradas ===")
    banco.obtenerCuentas().forEach {
        println("Usuario: ${it.usuario.nombre} ${it.usuario.apellido} | Cuenta: ${it.numeroCuenta} | Saldo: ${it.obtenerSaldo()}")
    }

    println("Bienvenido al sistema de gestión de cuentas bancarias.")
    var opcionUsuario: Int
    var cuentaSeleccionada: Cuenta? = null

    do {
        println("Seleccione un usuario:")
        banco.obtenerCuentas().forEachIndexed { index, cuenta ->
            println("${index + 1}. ${cuenta.usuario.nombre} ${cuenta.usuario.apellido}")
        }
        println("0. Salir")
        print("Opción: ")
        opcionUsuario = reader.nextInt()

        cuentaSeleccionada = if (opcionUsuario in 1..banco.obtenerCuentas().size) {
            banco.obtenerCuentas()[opcionUsuario - 1]
        } else null

        if (opcionUsuario != 0 && cuentaSeleccionada == null) {
            println("Opción inválida")
        }
    } while (opcionUsuario != 0 && cuentaSeleccionada == null)

    if (cuentaSeleccionada == null) return

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
                } else println("Monto inválido")
            }
            2 -> {
                print("Ingrese monto a extraer: ")
                val monto = reader.nextDouble()
                if (!cuentaSeleccionada.extraer(monto)) {
                    println("Fondos insuficientes.")
                }
            }
            3 -> println("Saldo disponible: ${cuentaSeleccionada.obtenerSaldo()}")
            4 -> {
                println("=== Historial de transacciones ===")
                cuentaSeleccionada.obtenerTransacciones().forEach {
                    println(" - ${it::class.simpleName} de ${it.monto} el ${it.fecha.format(formatter)}")
                }
            }
            5 -> {
                println("Seleccione cuenta destino:")
                banco.obtenerCuentas().forEachIndexed { index, cuenta ->
                    if (cuenta != cuentaSeleccionada) {
                        println("${index + 1}. ${cuenta.usuario.nombre} ${cuenta.usuario.apellido}")
                    }
                }
                val destino = reader.nextInt()
                val cuentaDestino = banco.obtenerCuentas().getOrNull(destino - 1)
                if (cuentaDestino != null && cuentaDestino != cuentaSeleccionada) {
                    print("Ingrese monto a transferir: ")
                    val monto = reader.nextDouble()
                    if (servicio.transferir(cuentaSeleccionada, cuentaDestino, monto)) { // ✅ ahora usa ServicioBancario
                        println("Transferencia realizada con éxito.")
                    } else println("Fondos insuficientes.")
                } else println("Cuenta destino inválida")
            }
            0 -> println("Gracias por usar el sistema. ¡Hasta luego!")
            else -> println("Opción inválida")
        }
    } while (opcionAccion != 0)
}
