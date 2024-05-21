package org.example.cine_proyecto_final.butacas.validator

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.butacas.errors.ButacaError
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.lighthousegames.logging.logging

private val logger = logging()

class ButacaValidator {
    fun validate(butaca : Butaca) : Result<Butaca,ButacaError>{
        logger.debug { "Validando butaca con id= ${butaca.id}" }
        when{
            butaca.tipo == null -> return Err(ButacaError.ButacaInvalida("El tipo de la butaca con id= ${butaca.id} no es válido"))
            butaca.precio < 0 -> return Err(ButacaError.ButacaInvalida("El precio de la butaca con id= ${butaca.id} no es válido"))
            butaca.estado == null -> return Err(ButacaError.ButacaInvalida("El estado de la butaca con id= ${butaca.id} no es válido"))
            else -> return Ok(butaca)
        }
    }
}