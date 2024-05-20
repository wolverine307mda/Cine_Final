package org.example.cine_proyecto_final

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.controllers.GeneralBienvenidoController
import org.example.cine_proyecto_final.database.SqlDeLightClient
import org.example.cine_proyecto_final.database.logger

class CineApplication : Application() {

    private lateinit var appConfig: AppConfig
    private lateinit var dbClient: SqlDeLightClient

    override fun start(stage: Stage) {
        try {
            // Initialize application configuration and database client
            initializeApp()



    private fun initializeApp() {
        // Initialize application configuration
        appConfig = AppConfig()

        // Initialize database client
        dbClient = SqlDeLightClient(appConfig)

        // Additional initializations if necessary
    }
}

fun main() {
    Application.launch(CineApplication::class.java)
}

