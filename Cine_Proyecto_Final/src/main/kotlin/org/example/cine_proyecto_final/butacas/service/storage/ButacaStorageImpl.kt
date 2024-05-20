package org.example.cine_proyecto_final.butacas.service.storage

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.butacas.errors.ButacaError
import org.example.cine_proyecto_final.butacas.models.Butaca

class ButacaStorageImpl : ButacaStorage {
    override fun exportToJson(): Result<Unit, ButacaError> {
        TODO("Not yet implemented")
    }

    override fun importFromJson(): Result<List<Butaca>, ButacaError> {
        TODO("Not yet implemented")
    }

    override fun importFromCsv(): Result<List<Butaca>, ButacaError> {
        TODO("Not yet implemented")
    }
}