package org.example.cine_proyecto_final.ventas.respositorio

import org.example.cine_proyecto_final.ventas.models.LineaVenta
import org.example.cine_proyecto_final.ventas.models.Venta
import java.time.LocalDateTime

interface VentaRepositorio {
    fun findAll(): List<Venta>
    fun findById(id: String): Venta?
    fun save(venta: Venta, ignoreKey : Boolean = false): Venta?
    fun delete(id: String): Venta?
    fun findAllByDate(date : LocalDateTime): List<Venta>
    fun deleteLineaVenta(lineaVenta: LineaVenta) : LineaVenta
}