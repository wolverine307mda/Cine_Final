package org.example.cine_proyecto_final.butacas.service.storage

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.butacas.errors.ButacaError
import org.example.cine_proyecto_final.butacas.models.Butaca
import java.io.File

interface ButacaStorage {
    fun exportToJson(file : File, data : List<Butaca>) : Result<Unit,ButacaError>
    fun importFromJson(file : File) : Result<List<Butaca>,ButacaError>
    fun importFromCsv(file: File) : Result<List<Butaca>,ButacaError>
}