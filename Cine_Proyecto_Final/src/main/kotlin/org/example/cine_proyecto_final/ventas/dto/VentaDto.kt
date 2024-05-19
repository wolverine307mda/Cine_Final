package org.example.cine_proyecto_final.ventas.dto

import kotlinx.serialization.Serializable
import org.example.cine_final.cuentas.dto.CuentaDTO
import org.example.cine_proyecto_final.butacas.dto.ButacaDto

/**
 * Clase que representa una venta.
 * @property id El ID único de la venta.
 * @property cliente El cliente asociado con la venta.
 * @property butacas las butacas que forman parte de la venta
 * @property lineasVenta La lista de líneas de venta asociadas con la venta.
 * @property createdAt La fecha y hora de creación de la venta.
 * @property updatedAt La fecha y hora de la última actualización de la venta.
 * @property isDeleted Indica si la venta ha sido eliminada.
 */
@Serializable
class VentaDto(
    var id : String,
    var cliente : CuentaDTO,
    var butacas : List<ButacaDto>,
    var lineasVenta : List<LineaVentaDto>,
    var createdAt : String,
    var updatedAt : String,
    var isDeleted : Boolean = false
)