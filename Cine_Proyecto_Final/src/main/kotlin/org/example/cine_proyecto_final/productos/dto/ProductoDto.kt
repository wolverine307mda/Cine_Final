package org.example.cine_proyecto_final.productos.dto

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
/**
 * Representa un producto.
 * @property id El identificador único del producto.
 * @property nombre El nombre del producto.
 * @property precio El precio del producto.
 * @property stock El stock disponible del producto.
 * @property tipo El tipo de producto.
 * @property image El nombre de la imagen del producto.
 * @property createdAt La fecha y hora de creación del producto.
 * @property updatedAt La fecha y hora de la última actualización del producto.
 * @property isDeleted Indica si el producto ha sido eliminado.
 */
data class ProductoDto(
    val id: String = UUID.randomUUID().toString(),
    var nombre: String,
    var precio: Double,
    var stock: Int,
    var tipo: String,
    var image: String,
    var createdAt: String,
    var updatedAt: String,
    var isDeleted: Boolean = false
)