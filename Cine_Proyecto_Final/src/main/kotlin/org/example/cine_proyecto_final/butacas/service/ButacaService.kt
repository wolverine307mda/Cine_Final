package org.example.cine_proyecto_final.butacas.service

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.butacas.errors.ButacaError
import org.example.cine_proyecto_final.butacas.models.Butaca
import java.time.LocalDateTime

interface ButacaService {
    fun findAll() : Result<List<Butaca>, ButacaError>
    fun findById(id : String) : Result<Butaca, ButacaError>
    fun update(id: String, butaca: Butaca): Result<Butaca, ButacaError>
    fun findAllByDate(date : LocalDateTime) : Result<List<Butaca>, ButacaError>
    fun exportAllToFile(date : LocalDateTime) : Result<Unit, ButacaError>
    fun cargarButacas() : Result<Unit, ButacaError>
}