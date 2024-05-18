package org.example.cine_proyecto_final.productos.repository

import org.example.cine_proyecto_final.productos.models.Producto

interface ProductoRepository {
    fun findById(id: String): Producto?
    fun findAll(): List<Producto>
    fun save(producto: Producto): Producto
    fun update(producto: Producto): Producto
    fun delete(id: String)
}