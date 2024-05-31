package org.example.cine_proyecto_final.butacas.repository

import org.example.cine_proyecto_final.butacas.mappers.toButaca
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.ventas.models.Venta
import org.lighthousegames.logging.logging
import java.time.LocalDateTime

private val logger = logging()

/**
 * Implementación de la interfaz ButacaRepository.
 *
 * @property sqlDelightManager El gestor de la base de datos SqlDelight.
 * @constructor Crea una nueva instancia de ButacaRepositoryImpl con el gestor de base de datos proporcionado.
 */
class ButacaRepositoryImpl (
    private val sqlDelightManager: SqlDelightManager,
): ButacaRepository {
    private val db = sqlDelightManager.databaseQueries

    /**
     * Recupera todas las butacas de la base de datos.
     *
     * @return Una lista de todas las butacas en la base de datos, o una lista vacía si no hay butacas.
     */
    override fun findAll(): List<Butaca> {
        logger.debug { "Buscando todas las butacas en la base de datos" }
        if (db.countButacas().executeAsOne() > 0) {
            return db.getAllButacas().executeAsList().map { it.toButaca() }
        }
        return emptyList()
    }

    /**
     * Busca y devuelve una butaca con el ID especificado.
     *
     * @param id El ID de la butaca a buscar.
     * @return Un objeto Butaca si se encuentra, o null si no se encuentra.
     */
    override fun findById(id: String): Butaca? {
        logger.debug { "Buscando una butaca con id: $id" }
        if (db.butacaExists(id).executeAsOne()) {
            return db.getButacaById(id).executeAsOne().toButaca()
        }
        return null
    }

    /**
     * Guarda una nueva butaca en la base de datos.
     *
     * @param butaca La butaca a guardar.
     * @param venta La venta asociada a la butaca, si existe.
     * @return La butaca guardada si se guarda con éxito, o null si ya existe una butaca con el mismo ID.
     */
    override fun save(butaca: Butaca, venta: Venta?): Butaca? {
        logger.debug { "Buscando la butaca con id: ${butaca.id}" }
        if (findById(butaca.id) == null) {
            db.insertButaca(
                id = butaca.id.uppercase(),
                tipo = butaca.tipo!!.name,
                estado = butaca.estado!!.name,
                createdAt = butaca.createdAt.toString(),
                updatedAt = butaca.updatedAt.toString(),
                precio = butaca.precio,
                id_venta = venta?.id
            )
            return butaca
        }
        return null
    }

    /**
     * Actualiza una butaca existente en la base de datos.
     *
     * @param id El ID de la butaca a actualizar.
     * @param butaca El objeto Butaca con los nuevos datos.
     * @param venta La venta asociada a la butaca, si existe.
     * @return El objeto Butaca actualizado si se actualiza con éxito, o null si la butaca no existe.
     */
    override fun update(id: String, butaca: Butaca, venta: Venta?): Butaca? {
        logger.debug { "Actualizando la butaca con id: $id" }
        val date = LocalDateTime.now()
        findById(id)?.let {
            db.updateButaca(
                id = id,
                estado = butaca.estado!!.name,
                updatedAt = date.toString(),
                tipo = butaca.tipo!!.name,
                id_venta = venta?.id
            )
            return butaca.copy(
                id = id,
                estado = butaca.estado!!,
                updatedAt = date,
                tipo = butaca.tipo
            )
        }
        return null
    }

    /**
     * Recupera todas las butacas asociadas a una venta.
     *
     * @param id El ID de la venta a buscar.
     * @return Una lista de butacas asociadas a la venta, o una lista vacía si no se encuentran.
     */
    override fun getAllByVentaId(id: String): List<Butaca> {
        logger.debug { "Buscando las butacas que pertenecen a la venta con id: $id" }
        if (db.countButacasByVentaId(id).executeAsOne() > 0) {
            return db.getButacasByVentaId(id).executeAsList().map { it.toButaca() }
        }
        return emptyList()
    }
}
