package org.example.cine_proyecto_final.viewmodels.sesion

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.repository.CuentaRepositoryImpl
import org.example.cine_proyecto_final.cuentas.service.cache.CuentaCacheImpl
import org.example.cine_proyecto_final.cuentas.service.database.CuentaServicio
import org.example.cine_proyecto_final.cuentas.service.database.CuentaServicioImpl
import org.example.cine_proyecto_final.cuentas.validator.CuentaValidator
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SesionRegistrarseViewModel: KoinComponent {

    private val validator: CuentaValidator by inject()
    private val service : CuentaServicio by inject()
    private val inicioSesionViewModel : SesionInicioViewModel by inject()

    fun registrarseEsExitoso(cuenta: Cuenta) : Boolean {
        validator.validate(cuenta)
            .onSuccess {
                service.save(cuenta)
                    .onSuccess {
                        return true
                    }
            }.onFailure {
                return false
            }
        return true
    }
    //private val dbClient: SqlDelightManager by inject()
    //private var usuario: Cuenta? = null
    //private val cache: CuentaCacheImpl by inject()
    //private val repository = CuentaRepositoryImpl(dbClient)
}