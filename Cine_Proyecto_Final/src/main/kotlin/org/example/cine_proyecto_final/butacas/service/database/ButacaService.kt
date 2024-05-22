package org.example.cine_proyecto_final.butacas.service.database

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.butacas.errors.ButacaError
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.ventas.models.Venta
import java.time.LocalDateTime

interface ButacaService {
    fun save(butaca: Butaca) : Result<Butaca,ButacaError>
    fun findAll() : Result<List<Butaca>, ButacaError>
    fun findById(id : String) : Result<Butaca, ButacaError>
    fun update(id: String, butaca: Butaca, venta: Venta?): Result<Butaca, ButacaError>
}