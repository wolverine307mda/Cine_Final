package org.example.cine_proyecto_final.ventas.servicio.database

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.ventas.errors.VentaError
import org.example.cine_proyecto_final.ventas.models.Venta
import java.time.LocalDateTime

interface VentaServicio {
    fun save(venta: Venta) : Result<Venta, VentaError>
    fun findAll() : Result<List<Venta>, VentaError>
    fun findById(id : String) : Result<Venta, VentaError>
    fun delete(id: String) : Result<Venta, VentaError>
    fun findVentasByClienteId(id : String) : Result<List<Venta>, VentaError>
}