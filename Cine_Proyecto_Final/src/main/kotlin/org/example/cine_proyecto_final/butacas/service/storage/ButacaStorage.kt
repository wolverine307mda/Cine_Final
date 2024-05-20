package org.example.cine_proyecto_final.butacas.service.storage

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.butacas.errors.ButacaError
import org.example.cine_proyecto_final.butacas.models.Butaca

interface ButacaStorage {
    fun exportToJson() : Result<Unit,ButacaError>
    fun importFromJson() : Result<List<Butaca>,ButacaError>
    fun importFromCsv() : Result<List<Butaca>,ButacaError>
}