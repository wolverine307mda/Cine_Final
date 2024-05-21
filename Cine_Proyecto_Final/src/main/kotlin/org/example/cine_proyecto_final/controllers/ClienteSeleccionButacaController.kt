package org.example.cine_proyecto_final.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import org.example.cine_proyecto_final.routes.RoutesManager
import org.lighthousegames.logging.logging

private val logger = logging()

class ClienteSeleccionButacaController {

    @FXML
    private lateinit var atras_button: Button

    @FXML
    private lateinit var siguiente_button: Button

    @FXML
    fun initialize() {
        logger.debug { "Iniciando pantalla general de Selección de Butacas" }

        atras_button.setOnAction {
            logger.debug { "Botón 'Atrás' presionado" }
            RoutesManager.changeScene(RoutesManager.View.COMPRAR_ENTRADA)
        }
        siguiente_button.setOnAction {
            logger.debug { "Botón 'Siguiente' presionado" }
            RoutesManager.changeScene(RoutesManager.View.SELECCION_PRODUCTOS)
        }
    }
}
