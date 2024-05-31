package org.example.cine_proyecto_final.cuentas.service.storage.json

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import cuenta.errors.CuentaError
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.cine_proyecto_final.cuentas.dto.CuentaDto
import org.example.cine_final.cuentas.servicio.storage.json.CuentaStorageJson
import org.example.cine_proyecto_final.cuentas.mappers.toCuenta
import org.example.cine_proyecto_final.cuentas.mappers.toDto
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

class CuentaStorageJsonImpl : CuentaStorageJson {

    /**
     * Importa una lista de cuentas desde un archivo JSON.
     *
     * @return Un Result que contiene la lista de Butacas o un error si no se han podido importar.
     * @see Result
     */
    override fun export(data: List<Cuenta>, file: File): Result<Unit, CuentaError> {
        logger.debug { "Importando las cuentas desde el fichero $file" }
        return try {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
            val list = data.map {
                it.toDto()
            }
            val jsonString = json.encodeToString<List<CuentaDto>>(list)
            file.writeText(jsonString)
            Ok(Unit)
        } catch (e: Exception) {
            Err(CuentaError.CuentaStorageError("No se pudieron guardar las cuentas en el archivo ${file.name}"))
        }
    }

    /**
     * Importa una lista de cuentas desde un archivo JSON.
     *
     * @return Un Result que contiene la lista de Butacas o un error si no se han podido importar.
     * @see Result
     */
    override fun import(file: File): Result<List<Cuenta>, CuentaError> {
        logger.debug { "Importando cuentas desde el fichero $file" }
        return try {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
            val jsonText = file.readText()
            val jsonString = json.decodeFromString<List<CuentaDto>>(jsonText)
            val list = jsonString.map {
                it.toCuenta()
            }
            Ok(list)
        } catch (e: Exception) {
            Err(CuentaError.CuentaStorageError("No se pudieron guardar las cuentas en un archivo Json"))
        }
    }

}