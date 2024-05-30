package org.example.cine_proyecto_final.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.github.michaelbull.result.onSuccess
import database.DatabaseQueries
import org.cine.database.AppDatabase
import org.example.cine_proyecto_final.CineApplication
import org.example.cine_proyecto_final.butacas.service.storage.ButacaStorage
import org.example.cine_proyecto_final.butacas.validator.ButacaValidator
import org.example.cine_proyecto_final.config.AppConfig
import org.jetbrains.dokka.InternalDokkaApi
import org.jetbrains.dokka.utilities.ServiceLocator.toFile
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDateTime

val logger = logging()

class SqlDelightManager(
    private val config : AppConfig
) : KoinComponent{
    private val databaseUrl: String = config.databaseUrl
    private val databaseInitData: Boolean = config.databaseInit
    private val databaseInMemory: Boolean = config.databaseInMemory
    var databaseQueries: DatabaseQueries = initQueries()

    private val butacaStorage: ButacaStorage by inject()
    private val butacaValidator: ButacaValidator by inject()

    init {
        logger.debug { "Inicializando el gestor de Bases de Datos con SQLDelight" }
        initialize()
    }

    /**
     * Crea la base de datos en memoria o en fichero dependiendo de lo que ponga en
     * @return un objeto DatabaseQueries que es utilizado por otras clases para
     * utilizar las funciones creadas automaticamente por SQLDelight a partir de las
     * que están presentes en el fichero Database.sq
     */
    private fun initQueries(): DatabaseQueries {

        return if (databaseInMemory) {
            logger.debug { "SqlDeLightClient - InMemory" }
            JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        } else {
            logger.debug { "SqlDeLightClient - File: ${databaseUrl}" }
            JdbcSqliteDriver(databaseUrl)
        }.let { driver ->
            // Creamos la base de datos
            logger.debug { "Creando Tablas (si es necesario)" }
            AppDatabase.Schema.create(driver)
            AppDatabase(driver)
        }.databaseQueries
    }

    /**
     * Borra todos los datos existentes en la base datos y carga los de ejemplo
     * @see removeAllData
     */
    fun initialize() {
        if (databaseInitData) {
            removeAllData()
            initSampleButacas()
            databaseQueries.insertAdmin()
        }
    }

    @OptIn(InternalDokkaApi::class)
    private fun initSampleButacas() {
        logger.debug { "Inicializando las butacas de ejemplo" }
        val file = CineApplication::class.java.getResource("data/butacas.csv")
        if (file != null) {
            butacaStorage.importFromCsv(file.toFile())
               .onSuccess {
                    it.forEach {
                        butacaValidator.validate(it).onSuccess {
                            databaseQueries.insertButaca(
                                id = it.id,
                                tipo = it.tipo!!.name,
                                estado = it.estado!!.name,
                                precio = it.precio,
                                createdAt = LocalDateTime.now().toString(),
                                updatedAt = LocalDateTime.now().toString(),
                                id_venta = null
                            )
                        }
                    }
                }
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