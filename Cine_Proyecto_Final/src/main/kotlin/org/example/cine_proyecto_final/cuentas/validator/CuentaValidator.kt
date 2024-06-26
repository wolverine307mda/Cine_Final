package org.example.cine_proyecto_final.cuentas.validator

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import cuenta.errors.CuentaError
import org.example.cine_proyecto_final.cuentas.models.Cuenta

class CuentaValidator {

    fun validate(cuenta: Cuenta): Result<Cuenta, CuentaError> {
        when {
            !emailIsValid(cuenta.email) -> return Err(CuentaError.CuentaInvalida("El email ${cuenta.email} no es válido"))
            cuenta.nombre.isEmpty() -> return Err(CuentaError.CuentaInvalida("El nombre no puede estar vacío"))
            cuenta.apellido.isEmpty() -> return Err(CuentaError.CuentaInvalida("Los apellidos no pueden estar vacíos"))
            cuenta.password.isEmpty() -> return Err(CuentaError.CuentaInvalida("La contraseña no puede estar vacía"))
            !passwordIsValid(cuenta.password) -> return Err(CuentaError.CuentaInvalida("La contraseña no cumple con los requisitos de seguridad"))
            cuenta.tipo == null -> return Err(CuentaError.CuentaInvalida("El tipo de cuenta no es válido"))
            else -> return Ok(cuenta)
        }
    }

    /**
     * @return true si es válido o false si no lo es
     *
     * Esta función utiliza una expresión regular para comprobar si la dirección de correo es válida.
     */
    fun emailIsValid(email: String): Boolean {
        val emailRegex = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}".toRegex()
        return email.matches(emailRegex)
    }

    /**
     * @return true si la contraseña es válida o false si no lo es
     *
     * Esta función comprueba si la contraseña contiene al menos una letra mayúscula,
     * una letra minúscula, un número y tiene al menos 5 caracteres.
     */
    fun passwordIsValid(password: String): Boolean {
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{5,}$".toRegex()
        return password.matches(passwordRegex)
    }
}
