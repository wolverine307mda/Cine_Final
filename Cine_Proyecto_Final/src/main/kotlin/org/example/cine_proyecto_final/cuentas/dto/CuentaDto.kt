package org.example.cine_final.cuentas.dto

import kotlinx.serialization.Serializable

/**
 * @property email El correo electrónico del usuario.
 * @property nombre El nombre del usuario.
 * @property apellido El apellido del usuario.
 * @property imagen La URL de la imagen del usuario.
 * @property password La contraseña del usuario.
 * @property tipo El tipo de usuario (por ejemplo, admin, usuario).
 * @property createdAt La fecha y hora en que se creó el usuario.
 * @property updatedAt La fecha y hora en que se actualizó el usuario por última vez..
 */
@Serializable
data class CuentaDTO(
    var email: String,
    var nombre: String,
    var apellido: String,
    var imagen: String,
    var password: String,
    var tipo: String,
    var createdAt: String,
    var updatedAt: String,
)
