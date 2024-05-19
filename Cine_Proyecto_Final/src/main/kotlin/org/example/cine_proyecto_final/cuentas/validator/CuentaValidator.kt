package org.example.cine_proyecto_final.cuentas.validator

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import cuenta.errors.CuentaError
import org.example.cine_proyecto_final.cuentas.models.Cuenta


class CuentaValidator {
    /**
 * Valida un objeto de tipo [Cuenta] dado.
 * @param cuenta La cuenta que se desea validar.
 * @return Un error o la Cuenta envuelta en un Result
 */
fun validate(cuenta: Cuenta): Result<Cuenta,CuentaError> {
    when{
        cuenta.nombre.isEmpty() -> return Err(CuentaError.CuentaInvalidaError("El nombre no puede estar vacío"))
        cuenta.apellido.isEmpty() -> return Err(CuentaError.CuentaInvalidaError("Los apellidos no pueden estar vacíos"))
        cuenta.email.isEmpty() -> return verifyEmail(cuenta)
        cuenta.password.isEmpty() -> return Err(CuentaError.CuentaInvalidaError("La contraseña no puede estar vacía"))
        cuenta.tipo == null -> return Err(CuentaError.CuentaInvalidaError("El tipo de cuenta no es válido"))
        else -> return Ok(cuenta)
    }
}

    /**
 * Función privada para verificar la validez de un correo electrónico.
 *
 * @param cuenta La cuenta cuyo correo electrónico se desea verificar.
 * @return Un error o la Cuenta envuelta en un Result
 *
 * Esta función utiliza una expresión regular para comprobar si la dirección de correo es válida.
 */
private fun verifyEmail(cuenta: Cuenta) : Result<Cuenta,CuentaError> {
    val emailRegex = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}".toRegex()
    if (cuenta.email.matches(emailRegex)) return Ok(cuenta)
    else return Err(CuentaError.CuentaInvalidaError("El email no es válido"))
}
}