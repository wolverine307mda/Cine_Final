package org.example.cine_proyecto_final.cuentas.service.storage.json

import com.github.michaelbull.result.Result
import cuenta.errors.CuentaError
import org.example.cine_final.cuentas.servicio.storage.json.CuentaStorageJson
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import java.io.File

class CuentaStorageJsonImpl : CuentaStorageJson {
    override fun save(data: List<Cuenta>, file: File): Result<Unit, CuentaError> {
        TODO("Not yet implemented")
    }
}