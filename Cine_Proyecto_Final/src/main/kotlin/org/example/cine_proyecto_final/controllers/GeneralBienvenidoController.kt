package org.example.cine_proyecto_final.controllers

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.stage.Stage
import org.example.cine_proyecto_final.CineApplication
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.viewmodels.GeneralBienvenidoViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.io.IOException

private val logger = logging()

class GeneralBienvenidoController : KoinComponent {

    lateinit var dbClient: SqlDelightManager
    private val viewModel: GeneralBienvenidoViewModel by inject()

    @FXML
    lateinit var continuar_button: Button

    @FXML
    private fun initialize() {
        continuar_button.setOnAction { handleContinuarButton() }
    }

    private fun handleContinuarButton() {
        logger.debug { "Botón continuar presionado" }
        cambiarEscena()
    }

    private fun cambiarEscena() {
        logger.debug { "Cambiando escena a GeneralComprarEntradaController" }
        try {
            val resource = CineApplication::class.java.getResource("views/general_comprar_entrada_view.fxml")
            if (resource == null) {
                logger.error { "No se pudo encontrar el recurso views/general_comprar_entrada_view.fxml" }
                return
            }
            val loader = FXMLLoader(resource)
            val root = loader.load<Any>()

            // Obtener el controlador GeneralComprarEntradaController
            val controller = loader.getController<GeneralComprarEntradaController>()

            val newScene = Scene(root as javafx.scene.Parent)

            val currentStage = continuar_button.scene.window as Stage
            currentStage.scene = newScene
            currentStage.show()

            logger.debug { "Escena cambiada a GeneralComprarEntradaController" }
        } catch (e: IOException) {
            logger.error(e) { "No se pudo cargar la nueva escena" }
        }
    }

}
