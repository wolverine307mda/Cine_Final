package org.example.cine_proyecto_final.cuentas.service.database

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import cuenta.errors.CuentaError
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.repository.CuentaRepository

/**
 * Implementación de [CuentaServicio] que interactúa con el repositorio de cuentas para realizar operaciones.
 * @property cuentaRepository El repositorio de cuentas utilizado para acceder y manipular datos de cuentas.
 */
class CuentaServicioImpl(
    private var cuentaRepository: CuentaRepository,
): CuentaServicio {

    /**
     * Busca una cuenta de usuario por su identificador único.
     * @param id El identificador único de la cuenta de usuario a buscar.
     * @return un [Result] que contiene la cuenta de usuario encontrada o un error [CuentaError].
     */
    override fun findByEmail(email: String): Result<Cuenta, CuentaError> {
        val cuenta = cuentaRepository.findById(email)
        return if (cuenta != null) {
            Ok(cuenta)
        } else {
            Err(CuentaError.CuentaNotFoundError("La cuenta con email $email no existe"))
        }
    }

    /**
     * Guarda una nueva cuenta de usuario.
     * @param cuenta La cuenta de usuario a guardar.
     * @return un [Result] que contiene la cuenta de usuario guardada o un error [CuentaError].
     */
    override fun save(cuenta: Cuenta): Result<Cuenta, CuentaError> {
        cuentaRepository.save(cuenta)?.let {
            return Ok(it)
        }
        return Err(CuentaError.CuentaStorageError("La cuenta con el email: ${cuenta.email} no se pudo guardar"))
    }

    override fun update(email: String, cuenta: Cuenta): Result<Cuenta, CuentaError> {
        TODO("Not yet implemented")
    }


}