package org.example.cine_proyecto_final.cuentas.models

import java.time.LocalDateTime

/**
 * La clase Cuenta representa una cuenta de usuario.
 * @property email El identificador único de la cuenta.
 * @property nombre El nombre del usuario.
 * @property apellido El apellido del usuario.
 * @property imagen La imagen del usuario.
 * @property password La contraseña del usuario.
 * @property tipo El tipo de cuenta (ADMINISTRADOR, USUARIO, etc.).
 * @property createdAt La fecha y hora de creación de la cuenta, por defecto es el momento actual.
 * @property updatedAt La fecha y hora de la última actualización de la cuenta, por defecto es el momento actual.
 */
data class Cuenta (
    var email: String,
    var nombre: String,
    var apellido: String,
    var imagen: String,
    var password: String,
    var tipo: TipoCuenta?,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()
)

enum class TipoCuenta {
    ADMINISTRADOR, USUARIO
}