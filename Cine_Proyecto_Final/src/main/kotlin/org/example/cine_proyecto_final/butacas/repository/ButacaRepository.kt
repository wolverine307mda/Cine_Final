package org.example.cine_proyecto_final.butacas.repository

import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.ventas.models.Venta

interface ButacaRepository {
    fun findAll(): List<Butaca>
    fun findById(id: String): Butaca?
    fun save(butaca: Butaca, venta: Venta? = null): Butaca?
    fun update(id: String, butaca: Butaca, venta : Venta?): Butaca?
    fun getAllByVentaId(id: String): List<Butaca>
}
