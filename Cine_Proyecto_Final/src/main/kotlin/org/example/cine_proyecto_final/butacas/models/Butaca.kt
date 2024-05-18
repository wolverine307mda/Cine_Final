package org.example.cine_proyecto_final.butacas.models

import java.time.LocalDateTime

/**
 * Clase que representa una butaca con sus atributos.
 *
 * @property id El identificador único de la butaca.
 * @property tipo El tipo de la butaca, puede ser "NORMAL" o "VIP".
 * @property estado El estado actual de la butaca, puede ser "LIBRE", "OCUPADA" o "FUERA_DE_SERVICIO".
 * @property ocupamiento El estado de ocupación de la butaca.
 * @property idVenta El identificador de la venta asociada a la butaca.
 * @property precio El precio asociado a la butaca.
 * @property createdAt La fecha y hora de creación de la butaca, se establece en el momento de la creación por defecto.
 * @property updatedAt La fecha y hora de la última actualización de la butaca, se establece en el momento de la creación por defecto.
 * @property isDeleted Indica si la butaca ha sido marcada como eliminada, por defecto es false.
 */
data class Butaca(
    var id: String,
    var estado: org.example.cine_proyecto_final.butacas.models.Estado?,
    var ocupamiento: String,
    var tipo: org.example.cine_proyecto_final.butacas.models.Tipo?,
    var idVenta: String?, // Agregamos la propiedad idVenta
    var precio: Int, // Agregamos la propiedad precio
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    var isDeleted: Boolean = false
){

enum class Tipo {
    NORMAL, VIP
}

enum class Estado {
    LIBRE, OCUPADA, FUERA_DE_SERVICIO
}


}

enum class Estado{
    ACTIVA, EN_MANTENIMIENTO, FUERA_DE_SERVICIO
}

enum class Ocupamiento{
    LIBRE, RESERVADA, OCUPADA
}

enum class Tipo(val precio : Int){
    VIP(8), NORMAL(5)
}