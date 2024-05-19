package org.example.cine_proyecto_final.productos.mappers

import org.example.cine_final.productos.models.Producto
import org.example.cine_final.productos.models.TipoProducto
import org.example.cine_proyecto_final.productos.dto.ProductoDto
import java.time.LocalDateTime

fun toDTO(producto: Producto): ProductoDto {
    return ProductoDto(
        id = producto.id,
        nombre = producto.nombre,
        precio = producto.precio,
        stock = producto.stock,
        tipo = producto.tipo.toString(),
        createdAt = producto.createdAt.toString(),
        updatedAt = producto.updatedAt.toString(),
        image = producto.image,
        isDeleted = producto.isDeleted
    )
}

fun toEntity(dto: ProductoDto): Producto {
    return Producto(
        id = dto.id ?: "",
        nombre = dto.nombre,
        precio = dto.precio,
        stock = dto.stock,
        tipo = dto.tipo,
        createdAt = LocalDateTime.parse(dto.createdAt),
        updatedAt = LocalDateTime.parse(dto.updatedAt),
        image = dto.image,
        isDeleted = dto.isDeleted
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
