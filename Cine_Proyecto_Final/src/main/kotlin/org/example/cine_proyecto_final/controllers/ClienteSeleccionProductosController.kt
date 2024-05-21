package org.example.cine_proyecto_final.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.ClienteSeleccionProductosViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()


class ClienteSeleccionProductosController : KoinComponent{
    private val dbClient: SqlDelightManager by inject()
    private val viewModel: ClienteSeleccionProductosViewModel by inject()

    @FXML
    private lateinit var atras_button: Button

    @FXML
    private lateinit var siguiente_button: Button

    @FXML
    private fun initialize() {
        logger.debug { "iniciando pantalla general de Selección de Productos" }

        atras_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.SELECCION_BUTACAS) }
        siguiente_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.PROCESAR_COMPRA) }
    }
}