package org.example.cine_proyecto_final.viewmodels.sesion

import com.github.michaelbull.result.*
import cuenta.errors.CuentaError
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.service.database.CuentaServicio
import org.example.cine_proyecto_final.cuentas.validator.CuentaValidator
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class SesionRegistrarseViewModel: KoinComponent {

    private val validator: CuentaValidator by inject()
    private val service : CuentaServicio by inject()
    private val inicioSesionViewModel : SesionInicioViewModel by inject()

    fun crearUsuario(cuenta: Cuenta): Result<Cuenta, CuentaError> {
        logger.debug { "Creando Usuario" }

        return validator.validate(cuenta).onSuccess {
            service.save(cuenta)
        }onFailure {
            logger.debug { "no se ha guardado cunta" }
        }
    }
}
