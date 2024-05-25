package org.example.cine_proyecto_final.viewmodels.sesion

import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.repository.CuentaRepositoryImpl
import org.example.cine_proyecto_final.cuentas.service.cache.CuentaCacheImpl
import org.example.cine_proyecto_final.cuentas.service.database.CuentaServicioImpl
import org.example.cine_proyecto_final.cuentas.validator.CuentaValidator
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SesionRegistrarseViewModel: KoinComponent {
    private val dbClient: SqlDelightManager by inject()
    private var usuario: Cuenta? = null
    private val validador: CuentaValidator by inject()
    private val cache: CuentaCacheImpl by inject()
    private val repository = CuentaRepositoryImpl(dbClient)
    private val service = CuentaServicioImpl(repository, validador, cache)
}