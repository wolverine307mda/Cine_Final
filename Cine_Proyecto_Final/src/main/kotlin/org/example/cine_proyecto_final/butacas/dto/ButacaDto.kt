package org.example.cine_proyecto_final.butacas.dto

import java.time.LocalDateTime

/**
 * DTO (Data Transfer Object) para la clase Butaca.
 *
 * @property id El identificador único de la butaca.
 * @property tipo El tipo de la butaca, puede ser "NORMAL" o "VIP".
 * @property estado El estado actual de la butaca, puede ser "LIBRE", "OCUPADA" o "FUERA_DE_SERVICIO".
 * @property ocupamiento El estado de ocupación de la butaca.
 * @property idVenta El identificador de la venta asociada a la butaca.
 * @property precio El precio asociado a la butaca.
 * @property createdAt La fecha y hora de creación de la butaca.
 * @property updatedAt La fecha y hora de la última actualización de la butaca.
 * @property isDeleted Indica si la butaca ha sido marcada como eliminada.
 */
data class ButacaDTO (
    val id : String,
    val tipo : String,
    val estado : String,
    val ocupamiento: String,
    val idVenta: String?,
    val precio: Int,
    val createdAt : LocalDateTime,
    val updatedAt : LocalDateTime,
    val isDeleted : Boolean
)