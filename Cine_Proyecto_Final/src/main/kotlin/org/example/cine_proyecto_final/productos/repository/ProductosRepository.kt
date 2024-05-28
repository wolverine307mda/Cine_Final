package org.example.cine_proyecto_final.productos.repository

import org.example.cine_proyecto_final.productos.models.Producto

interface ProductosRepository {
    fun findAll(): List<Producto>
    fun findById(id: String): Producto?
    fun save(producto: Producto): Producto?
    fun update(id: String, producto: Producto): Producto?
    fun delete(id: String): Producto?
}