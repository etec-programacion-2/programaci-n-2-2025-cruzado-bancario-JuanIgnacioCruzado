package org.example

import java.io.Serializable
import java.time.LocalDateTime

data class Usuario(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val dni: String,
    val fechaCreacion: LocalDateTime = LocalDateTime.now()
) : Serializable