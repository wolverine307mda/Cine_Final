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

/**
 * ViewModel para la gestión de registro de usuarios.
 */
class SesionRegistrarseViewModel : KoinComponent {

    private val validator: CuentaValidator by inject()
    private val service: CuentaServicio by inject()

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param cuenta La cuenta de usuario a crear.
     * @return Un [Result] que contiene la cuenta creada en caso de éxito o un [CuentaError] en caso de fallo.
     * @see Cuenta
     * @see CuentaError
     */
    fun crearUsuario(cuenta: Cuenta): Result<Cuenta, CuentaError> {
        logger.debug { "Creando Usuario" }

        return validator.validate(cuenta)
            .andThen {
                service.save(cuenta)
            }
            .onSuccess {
                logger.debug { "Usuario creado con éxito" }
            }
            .onFailure {
                logger.debug { "Error al crear usuario: $it" }
            }
    }
}
