package org.example.cine_proyecto_final.ventas.mappers

import database.LineaVentaEntity
import database.VentaEntity
import org.example.cine_final.productos.models.Producto
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.ventas.models.LineaVenta
import org.example.cine_proyecto_final.ventas.models.Venta
import java.time.LocalDateTime

/**
 * Convierte una entidad de línea de venta a un objeto LineaVenta.
 * @param producto El producto asociado con la línea de venta.
 * @return Un objeto LineaVenta.
 */
fun LineaVentaEntity.toLineaVenta(producto: Producto) : LineaVenta {
    return LineaVenta(
        id = this.id,
        cantidad = cantidad.toInt(),
        precio = precio,
        producto = producto,
        createdAt = LocalDateTime.parse(this.createdAt),
        updatedAt = LocalDateTime.parse(this.createdAt),
        isDeleted = this.isDeleted.toInt() == 1
    )
}

/**
 * Convierte una entidad de venta a un objeto Venta.
 * @param lineas La lista de líneas de venta asociadas con la venta.
 * @param cliente El cliente asociado con la venta.
 * @return Un objeto Venta.
 */
fun VentaEntity.toVenta(
    lineas : List<LineaVenta>,
    cliente : Cuenta,
    butacas : List<Butaca>
) : Venta {
    return Venta(
        id = this.id,
        lineasVenta = lineas,
        cliente = cliente,
        butacas = butacas,
        createdAt = LocalDateTime.parse(this.createdAt),
        updatedAt = LocalDateTime.parse(this.createdAt),
        isDeleted = this.isDeleted.toInt() == 1
    )
}