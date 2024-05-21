package org.example.cine_proyecto_final.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.GeneralBienvenidoViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

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
        continuar_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.COMPRAR_ENTRADA) }
        iniciar_sesion_button.setOnAction { RoutesManager.initSesionInicio() }
    }
}
