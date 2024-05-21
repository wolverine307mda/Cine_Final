package org.example.cine_proyecto_final.controllers

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.stage.Stage
import org.example.cine_proyecto_final.CineApplication
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.routes.RoutesManager
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

        atras_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.COMPRAR_ENTRADA) }
        siguiente_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.SELECCION_PRODUCTOS) }
    }
}