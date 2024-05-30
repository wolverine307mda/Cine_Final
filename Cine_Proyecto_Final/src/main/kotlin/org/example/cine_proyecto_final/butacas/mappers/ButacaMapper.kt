package org.example.cine_proyecto_final.butacas.mappers

import database.ButacaEntity
import org.example.cine_proyecto_final.butacas.dto.ButacaDto
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.models.Estado
import org.example.cine_proyecto_final.butacas.models.Tipo
import org.example.cine_proyecto_final.ventas.models.Venta
import java.time.LocalDateTime

/**
 * Convierte una entidad de butaca a un objeto Butaca.
 *
 * @receiver La entidad de butaca a convertir.
 * @return Un objeto Butaca.
 * @see ButacaEntity
 * @see Butaca
 */
fun ButacaEntity.toButaca(): Butaca {
    return Butaca(
        id = this.id,
        estado = elegirEstado(this.estado),
        tipo = elegirTipo(this.tipo),
        createdAt = LocalDateTime.parse(this.createdAt),
        updatedAt = LocalDateTime.parse(this.updatedAt),
        precio = this.precio
    )
}

/**
 * Convierte un objeto Butaca a una entidad de butaca.
 *
 * @receiver El objeto Butaca a convertir.
 * @param venta La venta asociada a la butaca, si existe.
 * @return Una entidad ButacaEntity.
 * @see Butaca
 * @see ButacaEntity
 * @see Venta
 */
fun Butaca.toButacaEntity(venta: Venta?): ButacaEntity {
    return ButacaEntity(
        id = this.id,
        tipo = this.tipo.toString(),
        estado = this.estado.toString(),
        id_venta = venta?.id,
        precio = this.precio,
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString()
    )
}

/**
 * Convierte una cadena que representa el tipo de butaca a su enumeración correspondiente.
 *
 * @param s La cadena que representa el tipo de butaca.
 * @return El tipo de butaca correspondiente, o null si no coincide.
 * @see Tipo
 */
fun elegirTipo(s: String): Tipo? {
    return when (s) {
        "NORMAL" -> Tipo.NORMAL
        "VIP" -> Tipo.VIP
        else -> null
    }
}

/**
 * Convierte una cadena que representa el estado de una butaca a su enumeración correspondiente.
 *
 * @param s La cadena que representa el estado de la butaca.
 * @return El estado de la butaca correspondiente, o null si no coincide.
 * @see Estado
 */
fun elegirEstado(s: String): Estado? {
    return when (s) {
        "LIBRE" -> Estado.LIBRE
        "OCUPADA" -> Estado.OCUPADA
        "FUERA_DE_SERVICIO" -> Estado.FUERA_DE_SERVICIO
        else -> null
    }
}

/**
 * Convierte un objeto de modelo de butaca a un objeto DTO de butaca.
 *
 * @receiver El objeto Butaca a convertir.
 * @return Un objeto DTO de butaca.
 * @see Butaca
 * @see ButacaDto
 */
fun Butaca.toDto(): ButacaDto {
    return ButacaDto(
        id = this.id,
        tipo = this.tipo!!.name,
        estado = this.estado!!.name,
        precio = this.precio,
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString()
    )
}

/**
 * Convierte un objeto DTO de butaca a un objeto de modelo de butaca.
 *
 * @receiver El objeto DTO de butaca a convertir.
 * @return Un objeto Butaca.
 * @see ButacaDto
 * @see Butaca
 */
fun ButacaDto.toButaca(): Butaca {
    return Butaca(
        id = this.id,
        tipo = elegirTipo(this.tipo),
        estado = elegirEstado(this.estado),
        createdAt = LocalDateTime.parse(this.createdAt),
        updatedAt = LocalDateTime.parse(this.updatedAt),
        precio = this.precio
    )
}
