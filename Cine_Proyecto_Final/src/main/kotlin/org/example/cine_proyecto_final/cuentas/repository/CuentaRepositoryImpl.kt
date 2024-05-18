package org.example.cine_proyecto_final.cuentas.repository

import org.example.cine_proyecto_final.cuentas.models.Cuenta

class CuentaRepositoryImpl :CuentaRepository {
    override fun findAll(): List<Cuenta> {
        TODO("Not yet implemented")
    }
    override fun save(cuenta: Cuenta): Cuenta {
        TODO("Not yet implemented")
    }

    override fun update(email: String, cuenta: Cuenta): Cuenta? {
        TODO("Not yet implemented")
    }

    override fun findByEmail(email: String): Cuenta? {
        TODO("Not yet implemented")
    }

    override fun delete(email: String): Cuenta?{
        TODO("Not yet implemented")
    }
}