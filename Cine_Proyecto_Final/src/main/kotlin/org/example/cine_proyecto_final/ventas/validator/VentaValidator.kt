package org.example.cine_proyecto_final.ventas.validator

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.ventas.errors.VentaError
import org.example.cine_proyecto_final.ventas.models.Venta
import org.example.cine_proyecto_final.ventas.servicio.storage.VentaStorage

class VentaValidator {
    fun validate(venta: Venta) : Result<Venta, VentaError>{
        when{
            venta.butacas.isEmpty() -> return Err(VentaError.VentaInvalida("Una venta tiene que tener al menos una butaca"))
            else -> return Ok(venta)
        }
    }
}