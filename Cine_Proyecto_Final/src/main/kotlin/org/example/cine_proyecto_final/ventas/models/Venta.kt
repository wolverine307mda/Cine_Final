package org.example.cine_proyecto_final.ventas.models

import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import java.time.LocalDateTime
import java.util.UUID

/**
 * Clase que representa una venta.
 * @property id El ID único de la venta.
 * @property cliente El cliente asociado con la venta.
 * @property lineasVenta La lista de líneas de venta asociadas con la venta.
 * @property createdAt La fecha y hora de creación de la venta.
 * @property updatedAt La fecha y hora de la última actualización de la venta.
 * @property isDeleted Indica si la venta ha sido eliminada.
 */
data class Venta(
    var id : String = UUID.randomUUID().toString(),
    var cliente : Cuenta,
    var butacas : List<Butaca>,
    var lineasVenta : List<LineaVenta>,
    var createdAt : LocalDateTime = LocalDateTime.now(),
    var updatedAt : LocalDateTime = LocalDateTime.now(),
    var isDeleted : Boolean = false
) {
}