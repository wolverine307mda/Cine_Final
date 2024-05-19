package org.example.cine_proyecto_final.butacas.repository

import org.example.cine_proyecto_final.butacas.models.Butaca
import java.time.LocalDateTime

class ButacaRepositoryImpl:ButacaRepository {
    override fun findAll(): List<Butaca>{
        TODO("Not yet implemented")
    }
    override fun findAllBasedOnDate(date: LocalDateTime) : List<Butaca>{
        TODO("Not yet implemented")
    }
    override fun findById(id: String): Butaca?{
        TODO("Not yet implemented")
    }
    override fun save(butaca: Butaca, ignoreKey : Boolean): Butaca?{
        TODO("Not yet implemented")
    }
    override fun update(id: String, butaca: Butaca): Butaca?{
        TODO("Not yet implemented")
    }
    override fun findByIdAndDate(id : String, date: LocalDateTime) : Butaca?{
        TODO("Not yet implemented")
    }

    override fun getAllByVentaId(id: String): List<Butaca> {
        TODO("Not yet implemented")
    }
}