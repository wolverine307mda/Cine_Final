package org.example.cine_proyecto_final.cuentas.service

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.cuentas.errors.CuentaError
import org.example.cine_proyecto_final.cuentas.models.Cuenta

class CuentaServiceImpl: CuentaService{
    override fun findAll(): Result<List<Cuenta>, CuentaError> {
        TODO("Not yet implemented")
    }
    override fun findById(id : String) : Result<Cuenta, CuentaError> {
        TODO("Not yet implemented")
    }
    override fun save(cuenta: Cuenta): Result<Cuenta, CuentaError> {
        TODO("Not yet implemented")
    }
}