package org.example.cine_proyecto_final.productos.repository

import org.example.cine_final.productos.models.Producto
import java.time.LocalDateTime

interface ProductosRepository {
    fun findAll(): List<Producto>
    fun findById(id: String): Producto?
    fun save(producto: Producto): Producto?
    fun update(id: String, producto: Producto): Producto?
    fun delete(id: String): Producto?
}