package org.example.cine_proyecto_final.productos.dto

import org.example.cine_proyecto_final.productos.models.TipoProducto
import java.time.LocalDateTime

data class ProductoDTO(
    val id: String?,
    val nombre: String,
    val precio: Double,
    val stock: Int,
    val tipo: TipoProducto?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val isDeleted: Boolean?
)