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
import org.example.cine_proyecto_final.cuentas.service.database.CuentaServicioImpl
import org.example.cine_proyecto_final.cuentas.validator.CuentaValidator
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SesionInicioViewModel : KoinComponent {

    private val dbClient: SqlDelightManager by inject()
    private var usuario: Cuenta? = null
    private val validador: CuentaValidator by inject()
    private val cache: CuentaCacheImpl by inject()
    private val repository = CuentaRepositoryImpl(dbClient)
    private val service = CuentaServicioImpl(repository, validador, cache)

    fun iniciarSesion(email: String, contraseña: String, callback: (Boolean, String, String) -> Unit) {
        val result: Result<Cuenta, CuentaError> = service.findByEmail(email)

        result.onSuccess { cuenta ->
            if (cuenta.password == contraseña) {
                val tipoCuenta = cuenta.tipo
                when (tipoCuenta) {
                    TipoCuenta.ADMINISTRADOR -> {
                        callback(true, "A", "Inicio de sesión exitoso")
                    }
                    TipoCuenta.USUARIO -> {
                        callback(true, "U", "Inicio de sesión exitoso")
                    }
                    else -> {
                        callback(false, "", "Tipo de cuenta no válido")
                    }
                }
            } else {
                callback(false, "", "Email o contraseña incorrectos")
            }
        }.onFailure { error ->
            callback(false, "", "Error al iniciar sesión: ${error.message}")
        }
    }
}
