package org.example.cine_proyecto_final.productos.servicio.storage.csv

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.cine_final.productos.mappers.elegirTipoProducto
import org.example.productos.errors.ProductoError
import org.example.cine_final.productos.models.Producto
import org.lighthousegames.logging.logging
import java.io.File
import java.time.LocalDateTime
import java.util.*

private val logger = logging()

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
                            precio = producto[1].toDouble(),
                            stock = producto[2].toInt(),
                            tipo = elegirTipoProducto(producto[3]),
                            image = producto[4],
                            isDeleted = false,
                            updatedAt = LocalDateTime.now(),
                            createdAt = LocalDateTime.now()
                        )
                    }
            )
        }catch (e : Exception){
            logger.debug { "Hubo un error al cargar los productos del archivo ${file.name}" }
            return Err(ProductoError.ProductoStorageError("Hubo un error al cargar los productos del archivo ${file.name}"))
        }
    }
}