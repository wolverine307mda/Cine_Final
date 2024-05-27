package org.example.cine_proyecto_final.viewmodels.sesion

import com.github.michaelbull.result.Result
import cuenta.errors.CuentaError
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.models.TipoCuenta
import org.example.cine_proyecto_final.cuentas.service.database.CuentaServicio
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class SesionCambioContraseñaViewModel : KoinComponent {

    private val service: CuentaServicio by inject()

    /*fun cambiarContraseña(email: String, nuevaContraseña: String): Result<Unit, CuentaError> {
        logger.debug { "Cambiando contraseña para el usuario con email: $email" }
        service.findByEmail(email)
        val cuenta = service.findByEmail(email)
        var cuentaActualizada:Cuenta = Cuenta(
            email = email,
            password =  nuevaContraseña,
            tipo = TipoCuenta.USUARIO,
            nombre = cuenta.,
            apellido = "",
            telefono = "",
            direccion = "",
            fechaNacimiento = "",
            fechaCreacion = "",
            fechaModificacion = ""
        )
        return service.update(
            email,
            nuevaContraseña)
    }*/
}
