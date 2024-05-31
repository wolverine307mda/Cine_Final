package org.example.cine_proyecto_final.cuentas.repository

import org.example.cine_proyecto_final.cuentas.models.Cuenta

interface CuentaRepository {
    fun findById(email: String): Cuenta?
    fun save(cuenta: Cuenta): Cuenta?
    fun update(email : String ,cuenta: Cuenta): Cuenta?
    fun findAll(): List<Cuenta>
}