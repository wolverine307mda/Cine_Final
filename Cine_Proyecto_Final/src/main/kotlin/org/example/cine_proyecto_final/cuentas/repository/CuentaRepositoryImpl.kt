package org.example.cine_proyecto_final.cuentas.repository

import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.cuentas.mappers.toCuenta
import org.lighthousegames.logging.logging
import java.time.LocalDateTime

private val logger = logging()

/**
 * Implementación del repositorio de cuentas de usuarios.
 * Esta clase se encarga de administrar las cuentas de usuario en la base de datos.
 */
class CuentaRepositoryImpl(
    private val dataBaseManager: SqlDelightManager
) : CuentaRepository {

    private val db = dataBaseManager.databaseQueries
    /**
     * Recupera todas las cuentas de usuario almacenadas en la base de datos.
     * @return una lista de todas las cuentas de usuario, o una lista vacía si no se encontraron cuentas o si ocurrió un error.
     */

    /**
     * Busca una cuenta de usuario por su identificador único.
     * @param id El identificador único de la cuenta de usuario a buscar.
     * @return la cuenta de usuario encontrada, o null si no se encontró ninguna cuenta con el identificador proporcionado o si ocurrió un error.
     */
    override fun findById(id: String): Cuenta? {
        logger.debug { "Buscando una cuenta con id: $id" }
        if (db.cuentaExists(id).executeAsOne()){
            return db.getCuentaById(id).executeAsOne().toCuenta()
        }
        return null
    }

    /**
     * Guarda una nueva cuenta de usuario en la base de datos.
     * @param cuenta La cuenta de usuario a guardar.
     * @return la cuenta de usuario guardada, o null si la cuenta ya existe en la base de datos o si ocurrió un error.
     */
    override fun save(cuenta: Cuenta): Cuenta? {
        logger.debug { "Guardando una cuenta con email: ${cuenta.email}" }
        findById(cuenta.email)?.let {
            return null
        }
        db.insertCuenta(
            email = cuenta.email,
            nombre = cuenta.nombre,
            apellido = cuenta.apellido,
            updatedAt = cuenta.updatedAt.toString(),
            createdAt = cuenta.createdAt.toString(),
            imagen = cuenta.imagen,
            password =  cuenta.password,
            tipo = cuenta.tipo!!.name
        )
        return cuenta
    }

    /**
     * Actualiza una cuenta de usuario en la base de datos.
     * @param email El identificador único de la cuenta de usuario a actualizar.
     * @param cuenta La cuenta de usuario a actualizar.
     * @return la cuenta de usuario actualizada, o null si no se encontró ninguna cuenta con ese email
     */
    override fun update(email: String, cuenta: Cuenta): Cuenta? {
        logger.debug { "Actualizando una cuenta con email: $email" }
        findById(email)?.let {
            val date = LocalDateTime.now()
            val nuevaCuenta = cuenta.copy(
                email = email,
                nombre = cuenta.nombre,
                apellido = cuenta.apellido,
                updatedAt = date
            )
            db.updateCuenta(
                nombre = cuenta.nombre,
                apellido = cuenta.apellido,
                updatedAt = date.toString(),
                imagen = cuenta.imagen,
                password =  cuenta.password
            )
            return nuevaCuenta
        }
        return null
    }
}