package org.example.cine_proyecto_final.viewmodels.sesion

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.onSuccess
import com.github.michaelbull.result.onFailure
import cuenta.errors.CuentaError
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.models.TipoCuenta
import org.example.cine_proyecto_final.cuentas.repository.CuentaRepositoryImpl
import org.example.cine_proyecto_final.cuentas.service.cache.CuentaCacheImpl
import org.example.cine_proyecto_final.cuentas.service.database.CuentaServicio
import org.example.cine_proyecto_final.cuentas.service.database.CuentaServicioImpl
import org.example.cine_proyecto_final.cuentas.validator.CuentaValidator
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * ViewModel para gestionar el inicio de sesión de un usuario.
 */
class SesionInicioViewModel : KoinComponent {

    var usuario: Cuenta? = null
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
}
