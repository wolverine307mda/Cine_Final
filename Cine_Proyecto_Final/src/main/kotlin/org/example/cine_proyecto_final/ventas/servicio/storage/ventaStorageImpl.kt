package org.example.cine_proyecto_final.ventas.servicio.storage

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.ventas.errors.VentaError
import org.example.cine_proyecto_final.ventas.models.Venta
import org.example.cine_proyecto_final.ventas.servicio.storage.html.VentaStorageHtml
import org.example.cine_proyecto_final.ventas.servicio.storage.json.VentaStorageJson
import java.io.File

class ventaStorageImpl (
    private val ventaStorageJson: VentaStorageJson,
    private val ventaStorageHtml : VentaStorageHtml
) : VentaStorage {

    override fun exportToJson(file: File, data: List<Venta>): Result<Unit, VentaError> {
        return ventaStorageJson.export(file, data)
    }

    override fun importFromJson(file: File): Result<List<Venta>, VentaError> {
        return ventaStorageJson.import(file)
    }

    override fun exportToHtml(file: File, venta: Venta): Result<Unit, VentaError> {
        return ventaStorageHtml.export(venta, file)
    }
}