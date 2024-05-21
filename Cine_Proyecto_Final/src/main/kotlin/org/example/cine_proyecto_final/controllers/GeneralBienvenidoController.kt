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
    lateinit var iniciar_sesion_button: Button

    @FXML
    private fun initialize() {
        continuar_button.setOnAction { handleContinuarButton() }
        iniciar_sesion_button.setOnAction { inicioDeSesion() }
    }

    private fun handleContinuarButton() {
        logger.debug { "Botón continuar presionado" }
        cambioDeEscena("general_comprar_entrada_view.fxml", continuar_button)
    }

    private fun inicioDeSesion() {
        logger.debug { "Botón inicio de sesion presionado" }
        cambioDeEscena("sesion_inicio_screen.fxml", iniciar_sesion_button)
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
