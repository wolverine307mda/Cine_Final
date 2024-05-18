package org.example.cine_proyecto_final.cuentas.service

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.cuentas.errors.CuentaError
import org.example.cine_proyecto_final.cuentas.models.Cuenta

interface CuentaService {
    fun findAll() : Result<List<Cuenta>, CuentaError>
    fun findById(id : String) : Result<Cuenta, CuentaError>
    fun save(cuenta: Cuenta) : Result<Cuenta, CuentaError>
}