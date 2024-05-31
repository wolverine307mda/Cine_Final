package org.example.cine_proyecto_final.productos.servicio.storage.csv

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.productos.models.Producto
import org.example.productos.errors.ProductoError
import java.io.File

interface ProductoStorageCSV {
    fun import(file: File) : Result<List<Producto>,ProductoError>
}