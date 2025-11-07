package org.example

import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater {
        val app = AplicacionBancaria()
        app.iniciar()
    }
}

