package org.example.cine_proyecto_final.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import database.DatabaseQueries
import org.cine.database.AppDatabase
import org.example.cine_proyecto_final.config.AppConfig
import org.lighthousegames.logging.logging

val logger = logging()

class SqlDelightManager(
    private val config : AppConfig
) {
    private val databaseUrl: String = config.databaseUrl
    private val databaseInitData: Boolean = config.databaseInit
    val databaseQueries: DatabaseQueries = initQueries()

    init {
        logger.debug { "Inicializando el gestor de Bases de Datos con SQLDelight" }
        initialize()
    }

    private fun initQueries(): DatabaseQueries {
    val logger = logging()

    logger.debug { "SqlDeLightClient - InMemory" }
    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)

    // Create the database
    logger.debug { "Creando Tablas (si es necesario)" }
    AppDatabase.Schema.create(driver)
    val appDatabase = AppDatabase(driver)

    return appDatabase.databaseQueries
}

    /**
     * Borra todos los datos existentes en la base datos y carga los de ejemplo
     * @see removeAllData
     */
    fun initialize() {
        if (databaseInitData) {
            removeAllData()
        }
    }


    /**
     * Borra todos los datos de la base de datos con la ayuda de las funciones que crea
     * SqlDelight dentro del fichero Database.sq
     */
    private fun removeAllData() {
        logger.debug { "Borrando todo el data existente en la base de datos" }
        databaseQueries.transaction {
            databaseQueries.removeAllButacas()
            databaseQueries.removeAllCuentas()
            databaseQueries.removeAllProductos()
            databaseQueries.removeAllVentas()
        }
    }
}