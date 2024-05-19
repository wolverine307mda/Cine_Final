package org.example.cine_proyecto_final.butacas.service

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.butacas.errors.ButacaError
import org.example.cine_proyecto_final.butacas.models.Butaca
import java.time.LocalDateTime

class ButacaServiceImpl: ButacaService{
    override fun findAll() : Result<List<Butaca>, ButacaError>{
        TODO("Not yet implemented")
    }
    override fun findById(id : String) : Result<Butaca, ButacaError>{
        TODO("Not yet implemented")
    }
    override fun update(id: String, butaca: Butaca): Result<Butaca, ButacaError>{
        TODO("Not yet implemented")
    }
    override fun findAllByDate(date : LocalDateTime) : Result<List<Butaca>, ButacaError>{
        TODO("Not yet implemented")
    }
}