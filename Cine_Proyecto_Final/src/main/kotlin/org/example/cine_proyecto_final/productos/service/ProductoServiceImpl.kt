package org.example.cine_proyecto_final.productos.service

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.productos.errors.ProductoError
import org.example.cine_proyecto_final.productos.models.Producto

class ProductoServiceImpl:ProductoService {
    override fun save(producto: Producto) : Result<Producto, ProductoError>{
        TODO("Not yet implemented")
    }
    override fun findAll() : Result<List<Producto>, ProductoError>{
        TODO("Not yet implemented")
    }
    override fun findById(id : String) : Result<Producto, ProductoError>{
        TODO("Not yet implemented")
    }
    override fun update(id: String, producto: Producto): Result<Producto, ProductoError>{
        TODO("Not yet implemented")
    }
    override fun cargarTodosProductos() : Result<List<Producto>, ProductoError>{
        TODO("Not yet implemented")
    }
}