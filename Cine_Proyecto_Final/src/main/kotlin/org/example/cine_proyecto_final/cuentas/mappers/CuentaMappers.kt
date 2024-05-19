package org.example.cuenta.mappers

import org.example.cine_final.cuentas.dto.CuentaDTO
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.models.TipoCuenta
import java.time.LocalDateTime

/**
 * Convierte un objeto [CuentaDTO] en un objeto [Cuenta].
 * @return el objeto [Cuenta] resultante.
 */
fun CuentaDTO.toCuenta() : Cuenta {
    return Cuenta(
        email = this.email,
        password =  this.password,
        apellido = this.apellido,
        tipo = chooseTypeCuenta(this.tipo),
        updatedAt = LocalDateTime.parse(this.updatedAt),
        createdAt = LocalDateTime.parse(this.createdAt),
        nombre = this.nombre,
        imagen = this.imagen
    )
}

/**
 * Convierte un objeto [Cuenta] en un objeto [CuentaDTO].
 * @return el objeto [CuentaDTO] resultante.
 */
fun Cuenta.toCuentaDto() : CuentaDTO {
    return CuentaDTO(
        email = this.email,
        createdAt = this.toString(),
        updatedAt = this.toString(),
        apellido = this.apellido,
        password =  this.password,
        tipo = this.tipo!!.name,
        nombre = this.nombre,
        imagen = this.imagen
    )
}

fun chooseTypeCuenta(input: String) : TipoCuenta?{
    return when(input) {
        "ADMIN" -> TipoCuenta.ADMINISTRADOR
        "USUARIO" -> TipoCuenta.USUARIO
        else -> null
    }
}


/**
 * Convierte un valor booleano en un valor Long.
 * @return 1 si el valor booleano es verdadero, 0 si es falso.
 */
fun Boolean.toLong(): Long {
    if (this) return 1
    else return 0
}

