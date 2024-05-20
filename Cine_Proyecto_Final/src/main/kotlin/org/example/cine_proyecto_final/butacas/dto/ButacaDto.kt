package org.example.cine_proyecto_final.butacas.dto

import kotlinx.serialization.Serializable

/**
 * DTO (Data Transfer Object) para la clase Butaca.
 *
 * @property id El identificador único de la butaca.
 * @property tipo El tipo de la butaca, puede ser "NORMAL" o "VIP".
 * @property estado El estado actual de la butaca, puede ser "LIBRE", "OCUPADA" o "FUERA_DE_SERVICIO".
 * @property precio El precio asociado a la butaca.
 * @property createdAt La fecha y hora de creación de la butaca.
 * @property updatedAt La fecha y hora de la última actualización de la butaca.
 */
@Serializable
data class ButacaDto (
    val id : String,
    val tipo : String,
    val estado : String,
    val precio: Double,
    val createdAt : String,
    val updatedAt : String,
)