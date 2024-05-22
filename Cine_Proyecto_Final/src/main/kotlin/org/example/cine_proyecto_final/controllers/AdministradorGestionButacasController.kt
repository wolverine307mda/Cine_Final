package org.example.cine_proyecto_final.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import org.example.cine_proyecto_final.routes.RoutesManager
import org.lighthousegames.logging.logging

private val looger = logging()

class AdministradorGestionButacasController {
    @FXML
    private lateinit var atras_button: Button

    @FXML
    private fun initialize() {
        looger.debug { "iniciando pantalla de gestion de butacas" }
        atras_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.ADMIN_INICIO) }
    }
}