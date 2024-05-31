package org.example.cine_proyecto_final.butacas.service.storage.csv

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.butacas.errors.ButacaError
import org.example.cine_proyecto_final.butacas.mappers.elegirEstado
import org.example.cine_proyecto_final.butacas.mappers.elegirTipo
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.lighthousegames.logging.logging
import java.io.File
import java.time.LocalDateTime

private val logger = logging()

class ButacaStorageCsvImpl : ButacaStorageCsv {
    /**
     * Carga una lista de Butaca instancias desde un archivo CSV.
     *
     * @param file El File que contiene los datos del archivo CSV.
     * @return Un Result que contiene la lista de Butaca instancias si la carga fue exitosa, o un ButacaError si ocurrió un error.
     */
    override fun import(file: File): Result<List<Butaca>,ButacaError> {
        logger.debug { "Cargando las butacas del archivo ${file.name}" }
        try {
            return Ok(
                file.readLines()
                    .drop(1)
                    .map {
                        val butaca = it.split(',')
                        Butaca(
                            id = butaca[0],
                            estado = elegirEstado(butaca[2]),
                            precio = butaca[3].toDouble(),
                            tipo = elegirTipo(butaca[1]),
                            updatedAt = LocalDateTime.now(),
                            createdAt = LocalDateTime.now()
                        )
                    }
            )
        }catch (e : Exception){
            logger.debug { "Hubo un error al cargar las butacas del archivo ${file.name}" }
            return Err(ButacaError.ButacaStorageError("Hubo un error al cargar las butacas del archivo ${file.name}"))
        }
    }
}