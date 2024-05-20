package org.example.cine_proyecto_final.productos.servicio.storage.csv

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.database.logger
import org.example.cine_final.productos.mappers.elegirTipoProducto
import org.example.productos.errors.ProductoError
import org.example.cine_final.productos.models.Producto
import java.io.File
import java.time.LocalDateTime
import java.util.*

/**
 * Implementación del almacenamiento de productos desde y hacia archivos CSV.
 */
class ProductoStorageCSVImpl : ProductoStorageCSV {
    /**
     * Carga productos desde un archivo CSV.
     * @param file El archivo CSV que contiene los productos.
     * @return Un resultado que contiene una lista de productos cargados o un error si hubo algún problema durante la carga.
     */
    override fun import(file: File): Result<List<Producto>, ProductoError> {
        try {
            return Ok(
                file.readLines()
                    .drop(1)
                    .map {
                        val producto = it.split(',')
                        Producto(
                            id = UUID.randomUUID().toString(),
                            nombre = producto[0],
                            stock = producto[1].toInt(),
                            tipo = elegirTipoProducto(producto[2]).toString(),
                            image = producto[3],
                            precio = producto[4].toDouble(),
                            isDeleted = false,
                            updatedAt = LocalDateTime.now(),
                            createdAt = LocalDateTime.now()
                        )
                    }
            )
        }catch (e : Exception){
            logger.debug { "Hubo un error al cargar las butacas del archivo ${file.name}" }
            return Err(ProductoError.ProductoStorageError("Hubo un error al cargar los productos del archivo ${file.name}"))
        }
    }
}