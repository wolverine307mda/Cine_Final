package org.example.cine_proyecto_final.butacas.models

import org.example.cine_proyecto_final.ventas.models.Venta
import java.time.LocalDateTime

/**
 * Clase que representa una butaca con sus atributos.
 *
 * @property id El identificador único de la butaca.
 * @property tipo El tipo de la butaca, puede ser "NORMAL" o "VIP".
 * @property estado El estado actual de la butaca, puede ser "LIBRE", "OCUPADA" o "FUERA_DE_SERVICIO".
 * @property venta La venta asociada a la butaca si tiene alguna venta asociada con ella.
 * @property precio El precio asociado a la butaca.
 * @property createdAt La fecha y hora de creación de la butaca, se establece en el momento de la creación por defecto.
 * @property updatedAt La fecha y hora de la última actualización de la butaca, se establece en el momento de la creación por defecto.
 * @property isDeleted Indica si la butaca ha sido marcada como eliminada, por defecto es false.
 */
data class Butaca(
    var id: String,
    var estado: org.example.cine_proyecto_final.butacas.models.Estado?,
    var tipo: org.example.cine_proyecto_final.butacas.models.Tipo?,
    var venta: Venta?, // Agregamos la propiedad idVenta
    var precio: Double, // Agregamos la propiedad precio
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    var isDeleted: Boolean = false
){}

/**
 * Enumeración que representa los tipos de butacas que puede haber
 *
*/
enum class Tipo {
    NORMAL, VIP
}

/**
 * Enumeración que representa el estado en el que se puede encontrar la butaca
 */
enum class Estado {
    LIBRE, OCUPADA, FUERA_DE_SERVICIO
}