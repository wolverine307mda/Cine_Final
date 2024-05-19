package org.example.cine_proyecto_final.productos.servicio.database

import com.github.michaelbull.result.Result
import org.example.productos.errors.ProductoError
import org.example.cine_final.productos.models.Producto

interface ProductoServicio {
    fun save(producto: Producto) : Result<Producto, ProductoError>
    fun findAll() : Result<List<Producto>, ProductoError>
    fun findById(id : String) : Result<Producto, ProductoError>
    fun update(id: String, producto: Producto): Result<Producto, ProductoError>
    fun delete(id: String) : Result<Unit, ProductoError>
}