package org.example.cine_proyecto_final.viewmodels.sesion

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import cuenta.errors.CuentaError
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.service.database.CuentaServicio
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * ViewModel para gestionar el cambio de contraseña de un usuario.
 */
class SesionCambioContraseñaViewModel : KoinComponent {

    private val service: CuentaServicio by inject()

    /**
     * Actualiza la contraseña de un usuario basado en su email.
     *
     * @param email El correo electrónico del usuario cuya contraseña se va a cambiar.
     * @param nuevaContraseña La nueva contraseña que se va a establecer.
     * @return Un `Result` que contiene la cuenta actualizada si la operación fue exitosa,
     *         o un `CuentaError` si ocurrió algún error.
     * @see CuentaServicio.findByEmail
     * @see CuentaServicio.update
     */
    fun actualizarContraseña(email: String, nuevaContraseña: String): Result<Cuenta, CuentaError> {
        logger.debug { "Cambiando contraseña para el usuario con email: $email" }

        return service.findByEmail(email)
            .onSuccess { cuenta ->
                val cuentaActualizada = cuenta.copy(password = nuevaContraseña)
                service.update(cuentaActualizada.email, cuentaActualizada)
            }
            .onFailure { error ->
                logger.error { "No se pudo cambiar la contraseña para el usuario con email: $email" }
                error
            }
    }
}
