package org.example.cine_proyecto_final.cuentas.service.storage

import com.github.michaelbull.result.Result
import cuenta.errors.CuentaError
import org.example.cine_final.cuentas.servicio.storage.CuentaStorage
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_final.cuentas.servicio.storage.json.CuentaStorageJson
import java.io.File

class CuentaStorageImpl(
    private val json: CuentaStorageJson
) : CuentaStorage {

    override fun saveInJson(list : List<Cuenta>, file: File): Result<Unit, CuentaError> {
        TODO("Not yet implemented")
    }

    override fun loadFromJson(file: File): Result<Unit, CuentaError> {
        TODO("Not yet implemented")
    }
}