package org.example.cine_proyecto_final.ventas.servicio.storage.html

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.ventas.errors.VentaError
import org.example.cine_proyecto_final.ventas.models.Venta
import java.io.File

interface VentaStorageHtml {
    fun export(venta : Venta, file : File) : Result<Unit,VentaError>
}