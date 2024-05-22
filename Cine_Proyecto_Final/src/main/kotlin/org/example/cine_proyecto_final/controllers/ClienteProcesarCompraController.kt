package org.example.cine_proyecto_final.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import org.example.cine_proyecto_final.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()


class ClienteProcesarCompraController {

    @FXML
    private lateinit var atras_button: Button

    @FXML
    fun initialize(){
        logger.debug { "iniciando pantalla de Procesar Compra" }

        atras_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.SELECCION_PRODUCTOS) }
    }
}