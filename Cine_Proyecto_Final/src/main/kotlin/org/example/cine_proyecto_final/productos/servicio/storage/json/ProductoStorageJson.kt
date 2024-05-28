package org.example.cine_proyecto_final.productos.servicio.storage.json

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.productos.models.Producto
import org.example.productos.errors.ProductoError
import java.io.File

interface ProductoStorageJson {
    fun import(file: File): Result<List<Producto>,ProductoError>
    fun export(file: File, data : List<Producto>): Result<Unit,ProductoError>
}