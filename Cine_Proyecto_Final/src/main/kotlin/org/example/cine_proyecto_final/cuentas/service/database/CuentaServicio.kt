package org.example.cine_proyecto_final.cuentas.service.database

import com.github.michaelbull.result.Result
import cuenta.errors.CuentaError
import org.example.cine_proyecto_final.cuentas.models.Cuenta

interface CuentaServicio {
    fun findByEmail(email : String) : Result<Cuenta, CuentaError>
    fun save(cuenta: Cuenta) : Result<Cuenta,CuentaError>
    fun update(email: String, cuenta: Cuenta) : Result<Cuenta, CuentaError>
}