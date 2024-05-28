package org.example.cine_proyecto_final.viewmodels.sesion

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import cuenta.errors.CuentaError
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.service.database.CuentaServicio
import org.example.cine_proyecto_final.cuentas.validator.CuentaValidator
import org.koin.core.component.KoinComponent
import org.lighthousegames.logging.logging
import org.koin.core.component.inject


private val logger= logging()

class SesionViewModel: KoinComponent {

    var usuario: Cuenta? = null
    private val validator: CuentaValidator by inject()
    private val service: CuentaServicio by inject()

    /**
     * Inicia sesión para un usuario basado en su email y contraseña.
     *
     * @param email El correo electrónico del usuario.
     * @param contraseña La contraseña del usuario.
     * @see CuentaServicio.findByEmail
     */
    fun iniciarSesion(email: String, contraseña: String) {
        service.findByEmail(email)
            .onSuccess {
                if (it.email == email && it.password == contraseña) {
                    usuario = it
                }
            }
    }

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