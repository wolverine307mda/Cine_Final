package org.example.cine_proyecto_final.butacas.service.storage.csv

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.butacas.errors.ButacaError
import org.example.cine_proyecto_final.butacas.models.Butaca
import java.io.File

interface ButacaStorageCsv {
    fun import(file : File): Result<List<Butaca>,ButacaError>
}