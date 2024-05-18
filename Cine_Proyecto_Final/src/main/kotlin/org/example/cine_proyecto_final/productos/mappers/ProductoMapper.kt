package org.example.cine_proyecto_final.productos.mappers

import org.example.cine_proyecto_final.productos.dto.ProductoDTO
import org.example.cine_proyecto_final.productos.models.Producto
import org.example.cine_proyecto_final.productos.models.TipoProducto
import java.time.LocalDateTime

fun toDTO(producto: Producto): ProductoDTO {
    return ProductoDTO(
        id = producto.id,
        nombre = producto.nombre,
        precio = producto.precio,
        stock = producto.stock,
        tipo = producto.tipo,
        createdAt = producto.createdAt,
        updatedAt = producto.updatedAt,
        isDeleted = producto.isDeleted
    )
}

fun toEntity(dto: ProductoDTO): Producto {
    return Producto(
        id = dto.id ?: "",
        nombre = dto.nombre,
        precio = dto.precio,
        stock = dto.stock,
        tipo = dto.tipo,
        createdAt = dto.createdAt ?: LocalDateTime.now(),
        updatedAt = dto.updatedAt ?: LocalDateTime.now(),
        isDeleted = dto.isDeleted ?: false
    )
}

fun elegirTipoProducto(s: String): TipoProducto? {
    return when (s) {
        "BEBIDA" -> TipoProducto.BEBIDA
        "COMIDA" -> TipoProducto.COMIDA
        "OTROS" -> TipoProducto.OTROS
        else -> null
    }
}
