package org.example.cine_proyecto_final.butacas.service.storage.json

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.butacas.errors.ButacaError
import org.example.cine_proyecto_final.butacas.models.Butaca
import java.io.File

interface ButacaStorageJson {
    fun import(file : File) : Result<List<Butaca>,ButacaError>
    fun export(file : File, data : List<Butaca>) : Result<Unit,ButacaError>
}