package org.example.cine_proyecto_final.butacas.service.storage.json

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.cine_proyecto_final.butacas.dto.ButacaDto
import org.example.cine_proyecto_final.butacas.errors.ButacaError
import org.example.cine_proyecto_final.butacas.mappers.toButaca
import org.example.cine_proyecto_final.butacas.mappers.toDto
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.database.logger
import java.io.File

class ButacaStorageJsonImpl : ButacaStorageJson {
    /**
     * Importa una lista de Butacas desde un archivo JSON.
     *
     * @return Un Result que contiene la lista de Butacas o un error si no se han podido importar.
     * @see Result
     */
    override fun import(file: File): Result<List<Butaca>, ButacaError> {
        logger.debug { "Importando las butacas desde el fichero $file" }
        return try {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
            val jsonText = file.readText()
            val jsonString = json.decodeFromString<List<ButacaDto>>(jsonText)
            val list = jsonString.map {
                it.toButaca()
            }
            Ok(list)
        } catch (e: Exception) {
            Err(ButacaError.ButacaStorageError("No se pudieron guardar las butacas en un archivo Json"))
        }
    }

    /**
     * Exporta una lista de Butacas a un archivo JSON.
     *
     * @param data La lista de Butacas a ser exportadas.
     * @return Un Result que contiene un Unit o un error si no se ha podido guardar.
     * @see Result
     */
    override fun export(file: File, data: List<Butaca>): Result<Unit, ButacaError> {
        logger.debug { "Guardando las butacas en el fichero $file" }
        return try {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
            val list = data.map {
                it.toDto()
            }
            val jsonString = json.encodeToString<List<ButacaDto>>(list)
            file.writeText(jsonString)
            Ok(Unit)
        } catch (e: Exception) {
            Err(ButacaError.ButacaStorageError("No se pudieron guardar las butacas en el archivo ${file.name}"))
        }
    }
}