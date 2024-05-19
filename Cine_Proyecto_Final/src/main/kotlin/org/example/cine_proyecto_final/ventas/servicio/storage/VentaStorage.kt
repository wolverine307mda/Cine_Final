package org.example.cine_proyecto_final.ventas.servicio.storage

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.ventas.errors.VentaError
import org.example.cine_proyecto_final.ventas.models.Venta
import java.io.File

interface VentaStorage {
    fun exportToJson(file : File, data : List<Venta>) : Result<Unit,VentaError>
    fun importFromJson(file : File) : Result<List<Venta>,VentaError>
    fun exportToHtml(file: File, venta: Venta) : Result<Unit,VentaError>
}