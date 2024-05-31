package org.example.cine_proyecto_final.ventas.mappers

import database.LineaVentaEntity
import database.VentaEntity
import org.example.cine_final.productos.mappers.toDto
import org.example.cine_final.productos.mappers.toProducto
import org.example.cine_proyecto_final.productos.models.Producto
import org.example.cine_proyecto_final.butacas.mappers.toButaca
import org.example.cine_proyecto_final.butacas.mappers.toDto
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.ventas.dto.LineaVentaDto
import org.example.cine_proyecto_final.ventas.dto.VentaDto
import org.example.cine_proyecto_final.ventas.models.LineaVenta
import org.example.cine_proyecto_final.ventas.models.Venta
import org.example.cine_proyecto_final.cuentas.mappers.toCuenta
import org.example.cine_proyecto_final.cuentas.mappers.toDto
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

fun LineaVenta.toDto() : LineaVentaDto{
    return LineaVentaDto(
        id = this.id,
        cantidad = this.cantidad,
        precio = this.precio,
        producto = this.producto.toDto(),
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString(),
        isDeleted = this.isDeleted
    )
}
fun Venta.toDto () : VentaDto{
    val butacas = this.butacas.map {
        it.toDto()
    }
    val lineas = this.lineasVenta.map {
        it.toDto()
    }
    return VentaDto(
        id = this.id,
        cliente = this.cliente.toDto(),
        butacas = butacas,
        lineasVenta = lineas,
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString(),
        isDeleted = this.isDeleted
    )
}

fun LineaVentaDto.toLineaVenta() : LineaVenta{
    return LineaVenta(
        id = this.id,
        cantidad = this.cantidad,
        precio = this.precio,
        producto = this.producto.toProducto(),
        createdAt = LocalDateTime.parse(this.createdAt),
        updatedAt = LocalDateTime.parse(this.createdAt),
        isDeleted = this.isDeleted
    )
}

fun VentaDto.toVenta () : Venta{
    val butacas = this.butacas.map {
        it.toButaca()
    }
    val lineas = this.lineasVenta.map {
        it.toLineaVenta()
    }
    return Venta(
        id = this.id,
        cliente = this.cliente.toCuenta(),
        lineasVenta = lineas,
        butacas = butacas,
        createdAt = LocalDateTime.parse(this.createdAt),
        updatedAt = LocalDateTime.parse(this.createdAt),
        isDeleted = this.isDeleted
    )
}