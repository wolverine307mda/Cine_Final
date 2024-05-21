package org.example.cine_proyecto_final.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.stage.Stage
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.SesionInicioViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SesionInicioController: KoinComponent {

    lateinit var dbClient: SqlDelightManager
    private val viewModel: SesionInicioViewModel by inject()

    @FXML
    private lateinit var email_textField: TextField

    @FXML
    private lateinit var contraseña_field: PasswordField

    @FXML
    private lateinit var iniciarSesion_Button: Button

    @FXML
    private lateinit var registrate_button: Button

    @FXML
    private lateinit var olvidoContraseña_button: Button

    private var stage: Stage? = null

    fun setStage(stage: Stage) {
        this.stage = stage
    }

    @FXML
    private fun initialize() {
        iniciarSesion_Button.setOnAction {  }
        registrate_button.setOnAction { RoutesManager.initRegistrarse() }
        olvidoContraseña_button.setOnAction { RoutesManager.initCambiarContrasena() }
    }
}