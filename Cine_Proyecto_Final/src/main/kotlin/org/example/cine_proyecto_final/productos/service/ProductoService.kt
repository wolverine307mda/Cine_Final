package org.example.cine_proyecto_final.productos.service

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.productos.errors.ProductoError
import org.example.cine_proyecto_final.productos.models.Producto

interface ProductoService {
    fun save(producto: Producto) : Result<Producto, ProductoError>
    fun findAll() : Result<List<Producto>, ProductoError>
    fun findById(id : String) : Result<Producto, ProductoError>
    fun update(id: String, producto: Producto): Result<Producto, ProductoError>
    fun cargarTodosProductos() : Result<List<Producto>, ProductoError>
}