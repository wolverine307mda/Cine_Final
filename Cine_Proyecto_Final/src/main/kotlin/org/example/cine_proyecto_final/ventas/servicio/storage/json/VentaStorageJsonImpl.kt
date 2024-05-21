package org.example.cine_proyecto_final.ventas.servicio.storage.json

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.cine_proyecto_final.ventas.dto.VentaDto
import org.example.cine_proyecto_final.ventas.errors.VentaError
import org.example.cine_proyecto_final.ventas.mappers.toDto
import org.example.cine_proyecto_final.ventas.mappers.toVenta
import org.example.cine_proyecto_final.ventas.models.Venta
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

class VentaStorageJsonImpl : VentaStorageJson {
    /**
     * Exporta una lista de Ventas a un archivo JSON dado.
     *
     * @return Un Result que contiene un Unit si se han exportado bien o un error si no se han podido exportar.
     * @param data la lista de Ventas que se quiere exportar
     * @param file el fichero en que se quieren exportar las ventas
     * @see Result
     */
    override fun export(file: File, data: List<Venta>): Result<Unit, VentaError> {
        logger.debug { "Guardando ventas en fichero $file" }
        return try {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
            val list = data.map {
                it.toDto()
            }
            val jsonString = json.encodeToString<List<VentaDto>>(list)
            file.writeText(jsonString)
            Ok(Unit)
        } catch (e: Exception) {
            Err(VentaError.VentaStorageError("No se pudieron guardar las ventas en un archivo Json"))
        }
    }

    /**
     * Importa una lista de Ventas desde un archivo JSON.
     *
     * @return Un Result que contiene la lista de Ventas o un error si no se han podido importar.
     * @param file el fichero desde el que se quieren importar las ventas
     * @see Result
     */
    override fun import(file: File): Result<List<Venta>, VentaError> {
        logger.debug { "Importando ventas desde el fichero $file" }
        return try {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
            val jsonText = file.readText()
            val jsonString = json.decodeFromString<List<VentaDto>>(jsonText)
            val list = jsonString.map {
                it.toVenta()
            }
            Ok(list)
        } catch (e: Exception) {
            Err(VentaError.VentaStorageError("No se pudieron importar las ventas desde el archivo ${file.name}"))
        }
    }
}