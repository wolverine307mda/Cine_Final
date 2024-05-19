package org.example.cine_final.cuentas.servicio.storage

import com.github.michaelbull.result.Result
import cuenta.errors.CuentaError
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import java.io.File

interface CuentaStorage {
    fun saveInJson(list : List<Cuenta>, file: File) : Result<Unit,CuentaError>
    fun loadFromJson(file: File) : Result<Unit,CuentaError>
}