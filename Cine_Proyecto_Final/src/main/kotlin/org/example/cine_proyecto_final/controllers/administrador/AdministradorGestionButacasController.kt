package org.example.cine_proyecto_final.controllers.administrador

import javafx.fxml.FXML
import javafx.scene.control.Button
import org.example.cine_proyecto_final.routes.RoutesManager
import org.lighthousegames.logging.logging

private val looger = logging()
class AdministradorGestionButacasController {

    @FXML
    private lateinit var gestion_productos_button: Button

    @FXML
    private lateinit var gestion_butacas_button: Button

    @FXML
    private lateinit var salir_button: Button

    @FXML
    private fun initialize() {
        looger.debug { "iniciando pantalla de gestion de butacas" }

        gestion_productos_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.ADMIN_PRODUCTOS) }
        gestion_butacas_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.ADMIN_BUTACAS) }
        salir_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.MAIN) }
    }
}