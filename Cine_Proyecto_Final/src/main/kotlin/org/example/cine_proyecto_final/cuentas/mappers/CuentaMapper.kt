package org.example.cine_proyecto_final.cuentas.mappers

import org.example.cine_proyecto_final.cuentas.dto.CuentaDTO
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import java.time.LocalDateTime

/**
 * Convierte un objeto [CuentaDTO] en un objeto [Cuenta].
 * @return el objeto [Cuenta] resultante.
 */
fun CuentaDTO.toCuenta(): Cuenta {
    return Cuenta(
        email = this.email,
        nombre = this.nombre,
        apellido = this.apellido,
        imagen = this.imagen,
        password = this.password,
        tipo = this.tipo,
        createdAt = LocalDateTime.parse(this.createdAt),
        updatedAt = LocalDateTime.parse(this.updatedAt),
        isDeleted = this.isDeleted == 1
    )
}

/**
 * Convierte un objeto [Cuenta] en un objeto [CuentaDTO].
 * @return el objeto [CuentaDTO] resultante.
 */
fun Cuenta.toCuentaDto(): CuentaDTO {
    return CuentaDTO(
        email = this.email,
        nombre = this.nombre,
        apellido = this.apellido,
        imagen = this.imagen,
        password = this.password,
        tipo = this.tipo,
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString(),
        isDeleted = this.isDeleted.toLong().toInt()
    )
}

/**
 * Convierte un valor booleano en un valor Long.
 * @return 1 si el valor booleano es verdadero, 0 si es falso.
 */
fun Boolean.toLong(): Long {
    return if (this) 1 else 0
}