package org.example

import java.awt.*
import java.time.format.DateTimeFormatter
import javax.swing.*

class VentanaDashboard(
    private val app: AplicacionBancaria,
    private val cuenta: Cuenta,
    private val servicio: ServicioBancario
) : JFrame("Panel Principal") {

    private val labelSaldo = JLabel()
    private val panelContenido = JPanel()

    init {
        configurarVentana()
        crearInterfaz()
        mostrarInicio()
    }

    private fun configurarVentana() {
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(900, 600)
        setLocationRelativeTo(null)
    }

    private fun crearInterfaz() {
        layout = BorderLayout()

        // Header con logo del banco
        val header = JPanel().apply {
            background = Color(26, 35, 126)
            preferredSize = Dimension(width, 70)
            layout = FlowLayout(FlowLayout.CENTER, 0, 18)
        }

        val logoLabel = JLabel("🏦 JUANIBANCO").apply {
            font = Font("Arial", Font.BOLD, 28)
            foreground = Color.WHITE
        }
        header.add(logoLabel)
        add(header, BorderLayout.NORTH)

        val panelLateral = crearPanelLateral()
        add(panelLateral, BorderLayout.WEST)

        panelContenido.layout = BorderLayout()
        panelContenido.background = Color(245, 247, 250)
        add(panelContenido, BorderLayout.CENTER)
    }

    private fun crearPanelLateral(): JPanel {
        return JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            background = Color(44, 62, 80)
            preferredSize = Dimension(280, height)
            border = BorderFactory.createEmptyBorder(25, 20, 25, 20)

            val labelUsuario = JLabel("${cuenta.usuario.nombre} ${cuenta.usuario.apellido}").apply {
                font = Font("Segoe UI", Font.BOLD, 19)
                foreground = Color.WHITE
                alignmentX = Component.CENTER_ALIGNMENT
            }

            labelSaldo.apply {
                text = "Saldo: ${String.format("%.2f", cuenta.obtenerSaldo())}"
                font = Font("Segoe UI", Font.PLAIN, 15)
                foreground = Color(189, 195, 199)
                alignmentX = Component.CENTER_ALIGNMENT
            }

            add(labelUsuario)
            add(Box.createRigidArea(Dimension(0, 5)))
            add(labelSaldo)
            add(Box.createRigidArea(Dimension(0, 35)))

            add(crearBotonMenu("💰 Depositar", Color(46, 204, 113)) { mostrarDepositar() })
            add(Box.createRigidArea(Dimension(0, 12)))
            add(crearBotonMenu("💸 Extraer", Color(231, 76, 60)) { mostrarExtraer() })
            add(Box.createRigidArea(Dimension(0, 12)))
            add(crearBotonMenu("🔄 Transferir", Color(52, 152, 219)) { mostrarTransferir() })
            add(Box.createRigidArea(Dimension(0, 12)))
            add(crearBotonMenu("📋 Historial", Color(155, 89, 182)) { mostrarHistorial() })

            add(Box.createVerticalGlue())

            val menuCuenta = JPopupMenu()
            val itemInfo = JMenuItem("👤 Ver mi información").apply {
                font = Font("Segoe UI", Font.PLAIN, 13)
                addActionListener { mostrarInformacion() }
            }
            val itemCerrar = JMenuItem("🚪 Cerrar sesión").apply {
                font = Font("Segoe UI", Font.PLAIN, 13)
                addActionListener { app.mostrarLogin() }
            }
            menuCuenta.add(itemInfo)
            menuCuenta.add(itemCerrar)

            val botonCuenta = JButton("Mi Cuenta ▼").apply {
                maximumSize = Dimension(240, 45)
                background = Color(52, 73, 94)
                foreground = Color.WHITE
                font = Font("Segoe UI", Font.BOLD, 14)
                isFocusPainted = false
                cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                addActionListener { menuCuenta.show(this, 0, height) }
            }

            add(botonCuenta)
        }
    }

    private fun crearBotonMenu(texto: String, color: Color, accion: () -> Unit): JButton {
        return JButton(texto).apply {
            maximumSize = Dimension(240, 50)
            background = color
            foreground = Color.WHITE
            font = Font("SansSerif", Font.BOLD, 14)
            isFocusPainted = false
            cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            horizontalAlignment = SwingConstants.LEFT
            border = BorderFactory.createEmptyBorder(10, 20, 10, 20)
            addActionListener { accion() }
        }
    }

    private fun actualizarSaldo() {
        labelSaldo.text = "Saldo: $${String.format("%.2f", cuenta.obtenerSaldo())}"
    }

    private fun mostrarInicio() {
        panelContenido.removeAll()
        val panel = JPanel().apply {
            layout = BorderLayout()
            background = Color.WHITE
            border = BorderFactory.createEmptyBorder(40, 40, 40, 40)
        }

        val mensaje = JLabel("<html><div style='text-align: center;'>" +
                "<h1>Bienvenido/a ${cuenta.usuario.nombre}</h1>" +
                "<p style='font-size: 14px; color: #6c757d;'>Selecciona una opción del menú lateral</p>" +
                "</div></html>").apply {
            horizontalAlignment = SwingConstants.CENTER
        }

        panel.add(mensaje, BorderLayout.CENTER)
        panelContenido.add(panel)
        panelContenido.revalidate()
        panelContenido.repaint()
    }

    private fun mostrarDepositar() {
        panelContenido.removeAll()
        val panel = crearPanelOperacion("💰 Depositar Dinero")

        val campoMonto = JTextField(15).apply {
            font = Font("SansSerif", Font.PLAIN, 14)
        }
        val boton = JButton("DEPOSITAR").apply {
            background = Color(40, 167, 69)
            foreground = Color.WHITE
            font = Font("SansSerif", Font.BOLD, 14)
            addActionListener {
                val monto = campoMonto.text.toDoubleOrNull()
                if (monto != null && monto > 0) {
                    cuenta.depositar(monto)
                    actualizarSaldo()
                    JOptionPane.showMessageDialog(this@VentanaDashboard, "Depósito exitoso")
                    campoMonto.text = ""
                } else {
                    JOptionPane.showMessageDialog(this@VentanaDashboard, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE)
                }
            }
        }

        panel.add(JLabel("Monto:").apply { font = Font("SansSerif", Font.BOLD, 14) })
        panel.add(campoMonto)
        panel.add(boton)
        panelContenido.add(panel)
        panelContenido.revalidate()
        panelContenido.repaint()
    }

    private fun mostrarExtraer() {
        panelContenido.removeAll()
        val panel = crearPanelOperacion("💸 Extraer Dinero")

        val campoMonto = JTextField(15).apply {
            font = Font("SansSerif", Font.PLAIN, 14)
        }
        val boton = JButton("EXTRAER").apply {
            background = Color(220, 53, 69)
            foreground = Color.WHITE
            font = Font("SansSerif", Font.BOLD, 14)
            addActionListener {
                val monto = campoMonto.text.toDoubleOrNull()
                if (monto != null && monto > 0) {
                    if (cuenta.extraer(monto)) {
                        actualizarSaldo()
                        JOptionPane.showMessageDialog(this@VentanaDashboard, "Extracción exitosa")
                        campoMonto.text = ""
                    } else {
                        JOptionPane.showMessageDialog(this@VentanaDashboard, "Fondos insuficientes", "Error", JOptionPane.ERROR_MESSAGE)
                    }
                } else {
                    JOptionPane.showMessageDialog(this@VentanaDashboard, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE)
                }
            }
        }

        panel.add(JLabel("Monto:").apply { font = Font("SansSerif", Font.BOLD, 14) })
        panel.add(campoMonto)
        panel.add(boton)
        panelContenido.add(panel)
        panelContenido.revalidate()
        panelContenido.repaint()
    }

    private fun mostrarTransferir() {
        panelContenido.removeAll()
        val panel = crearPanelOperacion("🔄 Transferir Dinero")

        val cuentasDisponibles = servicio.obtenerCuentas().filter { it != cuenta }
        val comboCuentas = JComboBox(cuentasDisponibles.map { "${it.usuario.nombre} ${it.usuario.apellido} (${it.numeroCuenta})" }.toTypedArray()).apply {
            font = Font("SansSerif", Font.PLAIN, 14)
        }
        val campoMonto = JTextField(15).apply {
            font = Font("SansSerif", Font.PLAIN, 14)
        }

        val boton = JButton("TRANSFERIR").apply {
            background = Color(0, 123, 255)
            foreground = Color.WHITE
            font = Font("SansSerif", Font.BOLD, 14)
            addActionListener {
                val monto = campoMonto.text.toDoubleOrNull()
                val cuentaDestino = cuentasDisponibles.getOrNull(comboCuentas.selectedIndex)

                if (monto != null && monto > 0 && cuentaDestino != null) {
                    if (servicio.transferir(cuenta, cuentaDestino, monto)) {
                        actualizarSaldo()
                        JOptionPane.showMessageDialog(this@VentanaDashboard, "Transferencia exitosa")
                        campoMonto.text = ""
                    } else {
                        JOptionPane.showMessageDialog(this@VentanaDashboard, "Fondos insuficientes", "Error", JOptionPane.ERROR_MESSAGE)
                    }
                } else {
                    JOptionPane.showMessageDialog(this@VentanaDashboard, "Datos inválidos", "Error", JOptionPane.ERROR_MESSAGE)
                }
            }
        }

        panel.add(JLabel("Cuenta destino:").apply { font = Font("SansSerif", Font.BOLD, 14) })
        panel.add(comboCuentas)
        panel.add(JLabel("Monto:").apply { font = Font("SansSerif", Font.BOLD, 14) })
        panel.add(campoMonto)
        panel.add(boton)
        panelContenido.add(panel)
        panelContenido.revalidate()
        panelContenido.repaint()
    }

    private fun mostrarHistorial() {
        panelContenido.removeAll()
        val panel = JPanel().apply {
            layout = BorderLayout()
            background = Color.WHITE
            border = BorderFactory.createEmptyBorder(30, 30, 30, 30)
        }

        val titulo = JLabel("📋 Historial de Transacciones").apply {
            font = Font("SansSerif", Font.BOLD, 26)
            border = BorderFactory.createEmptyBorder(0, 0, 20, 0)
        }

        val modelo = javax.swing.table.DefaultTableModel(
            arrayOf("Tipo", "Monto", "Fecha/Hora", "Detalles"),
            0
        )

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        cuenta.obtenerTransacciones().forEach { trans ->
            val tipo = when (trans) {
                is Deposito -> "Depósito"
                is Extraccion -> "Extracción"
                is Transferencia -> if (trans.cuentaOrigen == cuenta) "Transferencia Enviada" else "Transferencia Recibida"
                else -> "Otro"
            }

            val detalles = when (trans) {
                is Transferencia -> {
                    if (trans.cuentaOrigen == cuenta) {
                        "A: ${trans.cuentaDestino.usuario.nombre} ${trans.cuentaDestino.usuario.apellido}"
                    } else {
                        "De: ${trans.cuentaOrigen.usuario.nombre} ${trans.cuentaOrigen.usuario.apellido}"
                    }
                }
                else -> "-"
            }

            modelo.addRow(arrayOf(
                tipo,
                "${String.format("%.2f", trans.monto)}",
                trans.fecha.format(formatter),
                detalles
            ))
        }

        val tabla = JTable(modelo).apply {
            rowHeight = 35
            font = Font("SansSerif", Font.PLAIN, 13)
            tableHeader.font = Font("SansSerif", Font.BOLD, 14)
        }

        val scroll = JScrollPane(tabla)

        panel.add(titulo, BorderLayout.NORTH)
        panel.add(scroll, BorderLayout.CENTER)
        panelContenido.add(panel)
        panelContenido.revalidate()
        panelContenido.repaint()
    }

    private fun mostrarInformacion() {
        panelContenido.removeAll()
        val panel = JPanel().apply {
            layout = BorderLayout()
            background = Color.WHITE
            border = BorderFactory.createEmptyBorder(40, 40, 40, 40)
        }

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val info = """
            <html>
            <div style='font-family: SansSerif; padding: 20px;'>
                <h2>👤 Información de la Cuenta</h2>
                <hr>
                <p style='font-size: 14px;'><b>Nombre completo:</b> ${cuenta.usuario.nombre} ${cuenta.usuario.apellido}</p>
                <p style='font-size: 14px;'><b>DNI:</b> ${cuenta.usuario.dni}</p>
                <p style='font-size: 14px;'><b>Número de cuenta:</b> ${cuenta.numeroCuenta}</p>
                <p style='font-size: 14px;'><b>Fecha de creación:</b> ${cuenta.usuario.fechaCreacion.format(formatter)}</p>
                <p style='font-size: 14px;'><b>Saldo actual:</b> ${String.format("%.2f", cuenta.obtenerSaldo())}</p>
            </div>
            </html>
        """.trimIndent()

        val label = JLabel(info)
        panel.add(label, BorderLayout.CENTER)
        panelContenido.add(panel)
        panelContenido.revalidate()
        panelContenido.repaint()
    }

    private fun crearPanelOperacion(titulo: String): JPanel {
        return JPanel().apply {
            layout = GridBagLayout()
            background = Color.WHITE
            border = BorderFactory.createEmptyBorder(40, 40, 40, 40)

            val gbc = GridBagConstraints().apply {
                gridx = 0
                gridy = GridBagConstraints.RELATIVE
                insets = Insets(10, 10, 10, 10)
                fill = GridBagConstraints.HORIZONTAL
            }

            val labelTitulo = JLabel(titulo).apply {
                font = Font("SansSerif", Font.BOLD, 26)
            }
            add(labelTitulo, gbc)
        }
    }
}