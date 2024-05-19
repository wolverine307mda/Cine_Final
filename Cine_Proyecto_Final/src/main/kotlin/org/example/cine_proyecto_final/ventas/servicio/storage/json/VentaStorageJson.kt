package org.example.cine_proyecto_final.ventas.servicio.storage.json

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.ventas.errors.VentaError
import org.example.cine_proyecto_final.ventas.models.Venta
import java.io.File

interface VentaStorageJson {
    fun export(file: File, data: List<Venta>): Result<Unit,VentaError>
    fun import(file: File): Result<List<Venta>, VentaError>
}