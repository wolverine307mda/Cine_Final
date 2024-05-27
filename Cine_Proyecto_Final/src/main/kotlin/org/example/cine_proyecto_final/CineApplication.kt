package org.example.cine_proyecto_final

import javafx.application.Application
import javafx.stage.Stage
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.di.appModule
import org.example.cine_proyecto_final.routes.RoutesManager
import org.koin.core.context.startKoin
import org.koin.test.verify.verify
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
            // Initialize application configuration and database client
            RoutesManager.apply {
                app = this@CineApplication
            }.run {
                // Iniciamos la aplicación, podiamos hacerlo con also!!
                initMainStage(stage)
            }

            dbClient = SqlDelightManager(AppConfig())

        } catch (e: Exception) {
            e.printStackTrace()
            // Handle exception appropriately
        }
    }

    private fun initializeApp() {
        // Initialize application configuration
        appConfig = AppConfig()

        // Initialize database client
        dbClient = SqlDelightManager(appConfig)

        // Additional initializations if necessary
    }
}

fun main() {
    Application.launch(CineApplication::class.java)
}

