package org.example.cine_proyecto_final.ventas.dto

import kotlinx.serialization.Serializable
import org.example.cine_proyecto_final.productos.dto.ProductoDto

/**
 * Clase que representa una línea de venta.
 * @property id El ID único de la línea de venta.
 * @property producto El producto asociado con la línea de venta.
 * @property cantidad La cantidad del producto vendido.
 * @property precio El precio unitario del producto.
 * @property createdAt La fecha y hora de creación de la línea de venta.
 * @property updatedAt La fecha y hora de la última actualización de la línea de venta.
 * @property isDeleted Indica si la línea de venta ha sido eliminada.
 */
@Serializable
data class LineaVentaDto(
    var id : String,
    var producto: ProductoDto,
    var cantidad : Int,
    var precio : Double,
    var createdAt : String,
    var updatedAt : String,
    var isDeleted : Boolean = false
) {
}