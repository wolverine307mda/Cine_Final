package org.example.cine_proyecto_final.cuentas.repository

import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Implementación del repositorio de cuentas de usuarios.
 * Esta clase se encarga de administrar las cuentas de usuario en la base de datos.
 */
class CuentaRepositoryImpl(
    val dataBaseManager: SqlDelightManager
) : CuentaRepository {

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
        TODO()
    }

    /**
     * Guarda una nueva cuenta de usuario en la base de datos.
     * @param cuenta La cuenta de usuario a guardar.
     * @return la cuenta de usuario guardada, o null si la cuenta ya existe en la base de datos o si ocurrió un error.
     */
    override fun save(cuenta: Cuenta): Cuenta? {
        TODO()
    }

    override fun update(email: String, cuenta: Cuenta): Cuenta? {
        TODO("Not yet implemented")
    }
}