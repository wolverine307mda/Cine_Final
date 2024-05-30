package org.example.cine_proyecto_final

import javafx.application.Application
import javafx.stage.Stage
import org.example.cine_proyecto_final.butacas.service.storage.ButacaStorageImpl
import org.example.cine_proyecto_final.butacas.service.storage.csv.ButacaStorageCsvImpl
import org.example.cine_proyecto_final.butacas.service.storage.json.ButacaStorageJsonImpl
import org.example.cine_proyecto_final.butacas.validator.ButacaValidator
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.di.appModule
import org.example.cine_proyecto_final.routes.RoutesManager
import org.koin.core.context.startKoin
import org.lighthousegames.logging.logging

private val logger = logging()

class CineApplication : Application() {

    private lateinit var appConfig: AppConfig
    private lateinit var dbClient: SqlDelightManager

    init {
        startKoin {
            printLogger() // Logger de Koin
            modules(appModule) // Módulos de Koin
        }
    }

    override fun start(stage: Stage) {
        try {
            logger.debug { "Iniciando la aplicación" }
            // Initialize application configuration and database client
            RoutesManager.apply {
                app = this@CineApplication
            }.run {
                // Iniciamos la aplicación, podiamos hacerlo con also!!
                initMainStage(stage)
            }

            dbClient = SqlDelightManager(
                butacaStorage = ButacaStorageImpl(
                    butacaStorageJson = ButacaStorageJsonImpl(),
                    butacaStorageCsv = ButacaStorageCsvImpl(),
                ),
                config = AppConfig(),
                butacaValidator = ButacaValidator()
            )

        } catch (e: Exception) {
            e.printStackTrace()
            // Handle exception appropriately
        }
    }
}

fun main() {
    Application.launch(CineApplication::class.java)
}

