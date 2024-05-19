package org.example.cine_proyecto_final

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.controllers.GeneralBienvenidoController
import org.example.cine_proyecto_final.database.SqlDeLightClient

class CineApplication : Application() {

    private lateinit var appConfig: AppConfig
    private lateinit var dbClient: SqlDeLightClient

    override fun start(stage: Stage) {
        // Inicializar la configuración de la aplicación y el cliente de base de datos
        initializeApp()

        // Cargar la escena de JavaFX
        val fxmlLoader = FXMLLoader(CineApplication::class.java.getResource("/org/example/cine_proyecto_final/general_bienvenido_screen.fxml"))
        val scene = Scene(fxmlLoader.load(), 320.0, 240.0)

        // Obtener el controlador y pasar el cliente de base de datos
        val controller = fxmlLoader.getController<GeneralBienvenidoController>()
        controller.dbClient = dbClient

        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }


    private fun initializeApp() {
        // Inicializar configuración de la aplicación
        appConfig = AppConfig()

        // Inicializar cliente de base de datos
        dbClient = SqlDeLightClient(appConfig)

        // Puedes realizar otras inicializaciones aquí si es necesario
        // Ejemplo: dbClient.dbQueries.someQuery()
    }
}

fun main() {
    Application.launch(CineApplication::class.java)
}
