package org.example.cine_proyecto_final.controllers

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.stage.Stage
import org.example.cine_proyecto_final.CineApplication
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.viewmodels.ClienteSeleccionButacaViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.io.IOException

private val logger = logging()

class ClienteSeleccionButacaController : KoinComponent {

    private val dbClient: SqlDelightManager by inject()
    private val viewModel: ClienteSeleccionButacaViewModel by inject()


    @FXML
    private lateinit var atras_button: Button

    @FXML
    private lateinit var siguiente_button: Button

    @FXML
    fun initialize() {
        logger.debug { "iniciando pantalla general de comprar entrada" }

        atras_button.setOnAction { atras() }
        siguiente_button.setOnAction { siguiente() }
    }


    private fun atras() {
        logger.debug { "Botón atras entrada presionado" }
        cambioDeEscena("general_comprar_entrada_view.fxml", atras_button)
    }

    private fun siguiente() {
        logger.debug { "Botón siguiente entrada presionado" }
        cambioDeEscena("cliente_seleccion_productos_view.fxml", siguiente_button)
    }

    private fun cambioDeEscena(escena: String, button: Button) {
        logger.debug { "Botón $button entrada presionado" }
        try {
            val loader = FXMLLoader(CineApplication::class.java.getResource("views/$escena"))
            val root = loader.load<Any>()
            val newScene = Scene(root as javafx.scene.Parent)

            val currentStage = button.scene.window as Stage
            currentStage.scene = newScene
            currentStage.show()

            logger.debug { "Escena cambiada a $escena" }
        } catch (e: IOException) {
            logger.error(e) { "No se pudo cargar la nueva escena: $escena" }
        }
    }
}