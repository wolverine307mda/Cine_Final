package org.example.cine_proyecto_final

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.controllers.GeneralBienvenidoController
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.database.logger

class CineApplication : Application() {

    private lateinit var appConfig: AppConfig
    private lateinit var dbClient: SqlDelightManager

    override fun start(stage: Stage) {
        try {
            // Initialize application configuration and database client
            initializeApp()

            // Load JavaFX scene
            val fxmlLoader = FXMLLoader(javaClass.getResource("views/general_bienvenido_screen.fxml"))
            val scene = Scene(fxmlLoader.load(), 1280.0, 800.0)
            stage.isResizable = false
            logger.info { "b" }
            // Get the controller and pass the database client
            val controller = fxmlLoader.getController<GeneralBienvenidoController>()
            dbClient = SqlDelightManager(AppConfig())
            controller.dbClient = dbClient
            stage.title = "Hello!"
            stage.scene = scene
            stage.show()
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

