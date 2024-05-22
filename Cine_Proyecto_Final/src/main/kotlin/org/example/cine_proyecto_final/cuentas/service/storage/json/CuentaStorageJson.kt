package org.example.cine_final.cuentas.servicio.storage.json

import com.github.michaelbull.result.Result
import cuenta.errors.CuentaError
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import java.io.File

interface CuentaStorageJson {
    fun export(data : List<Cuenta>, file: File) : Result<Unit,CuentaError>
    fun import(file: File) : Result<List<Cuenta>,CuentaError>
}