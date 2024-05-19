package org.example.cine_proyecto_final.butacas.repository

import org.example.cine_proyecto_final.butacas.models.Butaca
import java.time.LocalDateTime

interface ButacaRepository {
    fun findAll(): List<Butaca>
    fun findAllBasedOnDate(date: LocalDateTime) : List<Butaca>
    fun findById(id: String): Butaca?
    fun save(butaca: Butaca, ignoreKey : Boolean = false): Butaca?
    fun update(id: String, butaca: Butaca): Butaca?
    fun findByIdAndDate(id : String, date: LocalDateTime) : Butaca?
    fun getAllByVentaId(id: String): List<Butaca>
}
