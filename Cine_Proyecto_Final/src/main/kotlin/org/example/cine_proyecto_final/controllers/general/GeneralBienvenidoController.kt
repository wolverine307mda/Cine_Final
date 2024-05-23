package org.example.cine_proyecto_final.controllers.general

import javafx.fxml.FXML
import javafx.scene.control.Button
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.lighthousegames.logging.logging

private val logger = logging()

class GeneralBienvenidoController : KoinComponent {

    lateinit var dbClient: SqlDelightManager

    @FXML
    lateinit var continuar_button: Button

    @FXML
    lateinit var iniciar_sesion_button: Button

    @FXML
    private fun initialize() {
        logger.debug { "iniciando General Bienvenido" }

        continuar_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.COMPRAR_ENTRADA) }
        iniciar_sesion_button.setOnAction { RoutesManager.initSesionInicio() }
    }
}