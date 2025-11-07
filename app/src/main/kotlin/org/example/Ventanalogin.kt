package org.example

import java.awt.*
import javax.swing.*

class VentanaLogin(private val app: AplicacionBancaria) : JFrame("Inicio de Sesión") {
    private val campoDni = JTextField(20)
    
    init {
        configurarVentana()
        crearInterfaz()
    }

    private fun configurarVentana() {
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(900, 650)
        setLocationRelativeTo(null)
        contentPane.background = Color(245, 247, 250)
    }

    private fun crearInterfaz() {
        layout = BorderLayout()

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
            layout = GridLayout(1, 2, 40, 0)
            background = Color(245, 247, 250)
            border = BorderFactory.createEmptyBorder(60, 80, 60, 80)
        }

        val panelLogin = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            background = Color.WHITE
            border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color(220, 224, 230), 2),
                BorderFactory.createEmptyBorder(40, 30, 40, 30)
            )
        }

        val tituloLogin = JLabel("Iniciar Sesión").apply {
            font = Font("Segoe UI", Font.BOLD, 24)
            foreground = Color(26, 35, 126)
            alignmentX = Component.CENTER_ALIGNMENT
        }

        val labelDni = JLabel("DNI").apply {
            font = Font("Segoe UI", Font.BOLD, 14)
            foreground = Color(60, 60, 60)
            alignmentX = Component.LEFT_ALIGNMENT
        }

        campoDni.apply {
            maximumSize = Dimension(300, 45)
            font = Font("Segoe UI", Font.PLAIN, 15)
            border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
            )
        }

        val botonLogin = JButton("INGRESAR").apply {
            maximumSize = Dimension(300, 50)
            background = Color(26, 35, 126)
            foreground = Color.WHITE
            font = Font("Segoe UI", Font.BOLD, 15)
            isFocusPainted = false
            border = BorderFactory.createEmptyBorder(12, 20, 12, 20)
            cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            addActionListener { intentarLogin() }
        }

        panelLogin.add(tituloLogin)
        panelLogin.add(Box.createRigidArea(Dimension(0, 40)))
        panelLogin.add(labelDni)
        panelLogin.add(Box.createRigidArea(Dimension(0, 8)))
        panelLogin.add(campoDni)
        panelLogin.add(Box.createRigidArea(Dimension(0, 30)))
        panelLogin.add(botonLogin)

        val panelRegistro = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            background = Color.WHITE
            border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color(220, 224, 230), 2),
                BorderFactory.createEmptyBorder(40, 30, 40, 30)
            )
        }

        val tituloRegistro = JLabel("Registrarse").apply {
            font = Font("Segoe UI", Font.BOLD, 24)
            foreground = Color(67, 160, 71)
            alignmentX = Component.CENTER_ALIGNMENT
        }

        val mensajeRegistro = JLabel("<html><center>¿No tienes cuenta?<br>Créala ahora</center></html>").apply {
            font = Font("Segoe UI", Font.PLAIN, 14)
            foreground = Color(100, 100, 100)
            alignmentX = Component.CENTER_ALIGNMENT
        }

        val botonRegistro = JButton("CREAR CUENTA").apply {
            maximumSize = Dimension(300, 50)
            background = Color(67, 160, 71)
            foreground = Color.WHITE
            font = Font("Segoe UI", Font.BOLD, 15)
            isFocusPainted = false
            border = BorderFactory.createEmptyBorder(12, 20, 12, 20)
            cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            addActionListener { app.mostrarRegistro() }
        }

        panelRegistro.add(tituloRegistro)
        panelRegistro.add(Box.createRigidArea(Dimension(0, 30)))
        panelRegistro.add(mensajeRegistro)
        panelRegistro.add(Box.createRigidArea(Dimension(0, 40)))
        panelRegistro.add(botonRegistro)
        panelRegistro.add(Box.createVerticalGlue())

        panelPrincipal.add(panelLogin)
        panelPrincipal.add(panelRegistro)

        add(header, BorderLayout.NORTH)
        add(panelPrincipal, BorderLayout.CENTER)
    }

    private fun intentarLogin() {
        val dni = campoDni.text.trim()
        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese su DNI", "Error", JOptionPane.ERROR_MESSAGE)
            return
        }

        val cuenta = app.autenticar(dni)
        if (cuenta != null) {
            app.mostrarDashboard(cuenta)
        } else {
            JOptionPane.showMessageDialog(this, "DNI no encontrado", "Error", JOptionPane.ERROR_MESSAGE)
        }
    }
}
