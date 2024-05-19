package org.example.cine_proyecto_final.ventas.servicio.storage.json

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.ventas.errors.VentaError
import org.example.cine_proyecto_final.ventas.models.Venta
import java.io.File

class VentaStorageJsonImpl : VentaStorageJson {
    override fun export(file: File, data: List<Venta>): Result<Unit, VentaError> {
        TODO("Not yet implemented")
    }

    override fun import(file: File): Result<List<Venta>, VentaError> {
        TODO("Not yet implemented")
    }
}