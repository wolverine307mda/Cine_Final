package org.example.cine_proyecto_final.ventas.servicio.storage.json

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.cine_proyecto_final.database.logger
import org.example.cine_proyecto_final.ventas.dto.VentaDto
import org.example.cine_proyecto_final.ventas.errors.VentaError
import org.example.cine_proyecto_final.ventas.mappers.toDto
import org.example.cine_proyecto_final.ventas.mappers.toVenta
import org.example.cine_proyecto_final.ventas.models.Venta
import java.io.File

class VentaStorageJsonImpl : VentaStorageJson {
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
            Err(VentaError.VentaStorageError("No se pudieron guardar las ventas en un archivo Json"))
        }
    }
}