package org.example.cine_final.productos.servicio.storage

import com.github.michaelbull.result.Result
import org.example.productos.errors.ProductoError
import org.example.cine_final.productos.models.Producto
import org.example.cine_proyecto_final.productos.servicio.storage.csv.ProductoStorageCSV
import org.example.cine_proyecto_final.productos.servicio.storage.json.ProductoStorageJson
import java.io.File

interface ProductoStorage{
        fun exportToJson(productos : List<Producto>, file : File) : Result<Unit,ProductoError>
        fun importFromJson(file : File) : Result<List<Producto>,ProductoError>
        fun importFromCSV(file : File) : Result<List<Producto>,ProductoError>
}