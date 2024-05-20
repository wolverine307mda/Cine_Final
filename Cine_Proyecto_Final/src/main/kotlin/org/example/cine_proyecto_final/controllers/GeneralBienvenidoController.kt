package org.example.cine_proyecto_final.controllers

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.stage.Stage
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.database.logger
import org.example.cine_proyecto_final.viewmodels.GeneralBienvenidoViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.IOException

class GeneralBienvenidoController : KoinComponent {

    // Mantener dbClient como lateinit var para inyección manual
    lateinit var dbClient: SqlDelightManager
    private val viewModel: GeneralBienvenidoViewModel by inject()

    @FXML
    lateinit var continuar_button: Button

    @FXML
    private fun initialize() {
        continuar_button.setOnAction { handleContinuarButton() }
    }

    private fun handleContinuarButton() {
        // Lógica que se ejecuta al presionar el botón continuar
        logger.debug { "Botón continuar presionado" }
        cambiarEscena()
    }

    private fun cambiarEscena() {
        try {
            // Cargar la nueva vista
            val loader = FXMLLoader(GeneralBienvenidoController::class.java.getResource("views/general_comprar_entrada_view.fxml"))
            val newScene = Scene(loader.load())

            // Obtener el escenario actual
            val currentStage = continuar_button.scene.window as Stage

            // Establecer la nueva escena en el escenario
            currentStage.scene = newScene
        } catch (e: IOException) {
            logger.error(e) { "No se pudo cargar la nueva escena" }
        }
    }
}
