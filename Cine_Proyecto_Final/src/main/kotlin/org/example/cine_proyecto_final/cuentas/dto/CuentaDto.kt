package org.example.cine_proyecto_final.cuentas.dto

data class CuentaDTO(
    var email: String,
    var nombre: String,
    var apellido: String,
    var imagen: String,
    var password: String,
    var tipo: String,
    var createdAt: String,
    var updatedAt: String,
    var isDeleted: Int
)
