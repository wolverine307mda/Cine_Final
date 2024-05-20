package org.example.cine_final.productos.mappers

import database.ProductoEntity
import org.example.cine_proyecto_final.productos.dto.ProductoDto
import org.example.cine_final.productos.models.Producto
import org.example.cine_final.productos.models.TipoProducto
import java.time.LocalDateTime

/**
 * Convierte un objeto productoDto a producto
 * @return El productoDto convertido
 */
fun ProductoDto.toProducto() : Producto{
    return Producto(
        id = this.id,
        nombre = this.nombre,
        precio = this.precio,
        tipo = elegirTipoProducto(this.tipo),
        stock = this.stock.toInt(),
        image = this.image,
        createdAt = LocalDateTime.parse(this.createdAt),
        updatedAt = LocalDateTime.parse(this.createdAt),
        isDeleted = this.isDeleted.toInt() == 1
    )
}

/**
 * Convierte un objeto productoDto a producto
 * @return El producto convertido a un objeto ProductoDto
 */
fun Producto.toDto() : ProductoDto {
    return ProductoDto(
        id = this.id,
        nombre = this.nombre,
        precio = this.precio,
        tipo = this.tipo!!.name,
        stock = this.stock.toInt(),
        image = this.image,
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString(),
        isDeleted = this.isDeleted.toInt() == 1
    )
}

private fun Boolean.toInt(): Any {
    return if (this) 1 else 0
}

/**
 * Convierte un [ComplementoEntity] en un [Producto].
 * @return El producto convertido.
 */
fun ProductoEntity.toProducto(): Producto {
    return Producto(
        id = this.id,
        nombre = this.nombre,
        precio = this.precio,
        tipo = elegirTipoProducto(this.tipo),
        stock = this.stock.toInt(),
        image = this.imagen,
        createdAt = LocalDateTime.parse(this.createdAt),
        updatedAt = LocalDateTime.parse(this.createdAt),
        isDeleted = this.isDeleted.toInt() == 1
    )
}

/**
 * Determina el [TipoProducto] basado en una cadena.
 * @param s La cadena que representa el tipo de producto.
 * @return El tipo de producto correspondiente, o null si no se reconoce la cadena.
 */
fun elegirTipoProducto(s: String): TipoProducto? {
    return when (s) {
        "BEBIDA" -> TipoProducto.BEBIDA
        "COMIDA" -> TipoProducto.COMIDA
        "OTROS" -> TipoProducto.OTROS
        else -> null
    }
}