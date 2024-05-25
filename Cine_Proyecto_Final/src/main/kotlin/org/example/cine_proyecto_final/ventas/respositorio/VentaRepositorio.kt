package org.example.cine_proyecto_final.ventas.respositorio

import org.example.cine_proyecto_final.ventas.models.Venta

interface VentaRepositorio {
    fun findAll(): List<Venta>
    fun findById(id: String): Venta?
    fun save(venta: Venta, ignoreKey : Boolean = false): Venta?
    fun delete(id: String): Venta?
}