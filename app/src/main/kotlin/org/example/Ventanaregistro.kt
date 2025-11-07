package org.example

import java.awt.*
import javax.swing.*

class VentanaRegistro(private val app: AplicacionBancaria) : JFrame("Registro de Usuario") {
    private val campoNombre = JTextField(20)
    private val campoApellido = JTextField(20)
    private val campoDni = JTextField(20)
    private val campoSaldo = JTextField(20)

    init {
        configurarVentana()
        crearInterfaz()
    }

    private fun configurarVentana() {
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(550, 700)
        setLocationRelativeTo(null)
        contentPane.background = Color(245, 247, 250)
    }

    private fun crearInterfaz() {
        layout = BorderLayout()

        // Header con logo del banco
        val header = JPanel().apply {
            background = Color(26, 35, 126)
            preferredSize = Dimension(width, 80)
            layout = FlowLayout(FlowLayout.CENTER, 0, 20)
        }

        val logoLabel = JLabel("🏦 JUANIBANCO").apply {
            font = Font("Arial", Font.BOLD, 32)
            foreground = Color.WHITE
        }
        header.add(logoLabel)

        val panelPrincipal = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            background = Color(245, 247, 250)
            border = BorderFactory.createEmptyBorder(40, 50, 40, 50)
        }

        val titulo = JLabel("Crear Cuenta").apply {
            font = Font("Segoe UI", Font.BOLD, 28)
            foreground = Color(26, 35, 126)
            alignmentX = Component.CENTER_ALIGNMENT
        }

        panelPrincipal.add(titulo)
        panelPrincipal.add(Box.createRigidArea(Dimension(0, 30)))

        agregarCampo(panelPrincipal, "Nombre", campoNombre)
        agregarCampo(panelPrincipal, "Apellido", campoApellido)
        agregarCampo(panelPrincipal, "DNI", campoDni)
        agregarCampo(panelPrincipal, "Saldo Inicial", campoSaldo)

        val botonRegistrar = JButton("REGISTRAR").apply {
            maximumSize = Dimension(350, 50)
            background = Color(67, 160, 71)
            foreground = Color.WHITE
            font = Font("Segoe UI", Font.BOLD, 15)
            isFocusPainted = false
            cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            addActionListener { registrar() }
        }

        val botonVolver = JButton("VOLVER").apply {
            maximumSize = Dimension(350, 50)
            background = Color(120, 120, 120)
            foreground = Color.WHITE
            font = Font("Segoe UI", Font.PLAIN, 14)
            isFocusPainted = false
            cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            addActionListener { app.mostrarLogin() }
        }

        panelPrincipal.add(Box.createRigidArea(Dimension(0, 20)))
        panelPrincipal.add(botonRegistrar)
        panelPrincipal.add(Box.createRigidArea(Dimension(0, 10)))
        panelPrincipal.add(botonVolver)

        add(header, BorderLayout.NORTH)
        add(panelPrincipal, BorderLayout.CENTER)
    }

    private fun agregarCampo(panel: JPanel, label: String, campo: JTextField) {
        val etiqueta = JLabel(label).apply {
            font = Font("Segoe UI", Font.BOLD, 14)
            foreground = Color(60, 60, 60)
            alignmentX = Component.LEFT_ALIGNMENT
        }

        campo.apply {
            maximumSize = Dimension(350, 45)
            font = Font("Segoe UI", Font.PLAIN, 15)
            border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
            )
        }

        panel.add(etiqueta)
        panel.add(Box.createRigidArea(Dimension(0, 5)))
        panel.add(campo)
        panel.add(Box.createRigidArea(Dimension(0, 15)))
    }

    private fun registrar() {
        val nombre = campoNombre.text.trim()
        val apellido = campoApellido.text.trim()
        val dni = campoDni.text.trim()
        val saldoTexto = campoSaldo.text.trim()

        if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || saldoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE)
            return
        }

        val saldo = saldoTexto.toDoubleOrNull()
        if (saldo == null || saldo < 0) {
            JOptionPane.showMessageDialog(this, "Saldo inválido", "Error", JOptionPane.ERROR_MESSAGE)
            return
        }

        val cuenta = app.registrarUsuario(nombre, apellido, dni, saldo)
        JOptionPane.showMessageDialog(this, "¡Cuenta creada exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE)
        app.mostrarDashboard(cuenta)
    }
}