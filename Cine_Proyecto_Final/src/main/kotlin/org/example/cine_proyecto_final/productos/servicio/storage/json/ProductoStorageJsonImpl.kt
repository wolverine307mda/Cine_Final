package org.example.cine_proyecto_final.productos.servicio.storage.json

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.cine_final.productos.mappers.toDto
import org.example.cine_final.productos.mappers.toProducto
import org.example.cine_final.productos.models.Producto
import org.example.cine_proyecto_final.productos.dto.ProductoDto
import org.example.productos.errors.ProductoError
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()
class ProductoStorageJsonImpl : ProductoStorageJson {

    /**
     * Importa una lista de productos desde un archivo JSON.
     *
     * @return Un Result que contiene la lista de productos o un error si no se han podido importar.
     * @see Result
     */
    override fun import(file: File): Result<List<Producto>, ProductoError> {
        logger.debug { "Importando productos desde el fichero $file" }
        return try {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
            val jsonText = file.readText()
            val jsonString = json.decodeFromString<List<ProductoDto>>(jsonText)
            val list = jsonString.map {
                it.toProducto()
            }
            Ok(list)
        } catch (e: Exception) {
            Err(ProductoError.ProductoStorageError("No se pudieron importar los productos desde el archivo ${file.name}"))
        }
    }

    /**
     * Exporta una lista de productos a un archivo JSON.
     *
     * @return Un Result que contiene un Unit si lo pudo hacer bien o un error si hubo algún problema durante el procesoa.
     * @see Result
     */
    override fun export(file: File, data: List<Producto>): Result<Unit, ProductoError> {
        logger.debug { "Exportando productos en el fichero $file" }
        return try {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
            val list = data.map { it.toDto() }
            val jsonString = json.encodeToString<List<ProductoDto>>(list)
            file.writeText(jsonString)
            Ok(Unit)
        } catch (e: Exception) {
            Err(ProductoError.ProductoStorageError("No se pudieron exportar los productos en el archivo ${file.name}"))
        }
    }
}