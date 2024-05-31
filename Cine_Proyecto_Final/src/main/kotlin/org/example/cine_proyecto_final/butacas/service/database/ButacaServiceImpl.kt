package org.example.cine_proyecto_final.butacas.service.database

import com.github.michaelbull.result.*
import org.example.cine_proyecto_final.butacas.errors.ButacaError
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.repository.ButacaRepository
import org.example.cine_proyecto_final.butacas.validator.ButacaValidator
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.ventas.models.Venta
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Implementación de los servicios relacionados con las butacas.
 *
 * @property butacaRepository El repositorio de butacas para acceder a la base de datos.
 * @property butacaValidator El validador de butacas para validar los datos importados.
 * @property config La configuración de la aplicación.
 */
class ButacaServiceImpl(
    private var butacaRepository: ButacaRepository,
    private var butacaValidator: ButacaValidator,
    private val config : AppConfig
) : ButacaService {
    /**
     * Crea una butaca en la base de datos.
     *
     * @param butaca La butaca a crear.
     */
    override fun save(butaca: Butaca): Result<Butaca, ButacaError> {
        logger.debug { "Guardando la butaca con id: ${butaca.id}" }
        butacaValidator.validate(butaca).onSuccess {
            butacaRepository.save(butaca)?.let {
                return Ok(it)
            }
        }
        return Err(ButacaError.ButacaStorageError("La butaca con el id: ${butaca.id} no se pudo guardar"))
    }

    /**
     * Busca y devuelve todas las butacas en la base de datos.
     *
     * @return Un resultado que contiene una lista de objetos Butaca si la operación tiene éxito,
     * o un error de ButacaError si no se encuentran butacas.
     */
    override fun findAll(): Result<List<Butaca>, Nothing> {
        logger.debug { "Buscando todas las butacas en la base de datos" }
        return Ok(butacaRepository.findAll())
    }


    /**
     * Busca y devuelve una butaca en la base de datos.
     *
     * @param id El ID de la butaca a buscar.
     * @return Un resultado que contiene una butaca si la operación tiene éxito,
     */
    override fun findById(id: String): Result<Butaca, ButacaError> {
        logger.debug { "Buscando una butaca con id= $id en la base de datos" }
        butacaRepository.findById(id)?.let {
            return Ok(it)
        }
        return Err(ButacaError.ButacaNotFoundError("La butaca con ID $id no existe"))
    }


    /**
     * Actualiza una butaca en la base de datos.
     *
     * @param butaca La butaca a actualizar.
     */
    override fun update(id: String, butaca: Butaca, venta: Venta?): Result<Butaca, ButacaError> {
        logger.debug { "Actualizando la butaca con id= $id en la base de datos" }
        butacaValidator.validate(butaca).onSuccess {
            butacaRepository.update(id = id, butaca = butaca, venta)?.let {
                return Ok(it)
            }
        }.onFailure {
            return Err(ButacaError.ButacaInvalida("La butaca con id= ${butaca.id} no es válida"))
        }
        return Err(ButacaError.ButacaStorageError("La butaca con id: ${butaca.id} no se pudo guardar"))
    }
}