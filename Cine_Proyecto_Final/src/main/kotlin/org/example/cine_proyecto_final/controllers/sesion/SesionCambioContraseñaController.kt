package org.example.cine_proyecto_final.controllers.sesion

import javafx.fxml.FXML
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteSeleccionProductosViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SesionCambioContraseñaController: KoinComponent {

    private val viewModel: SesionCambioContraseñaController by inject()
    private val dbClient: SqlDelightManager by inject()

    @FXML
    private lateinit var email_field: TextField

    @FXML
    private lateinit var contraseña_field: PasswordField

    @FXML
    private lateinit var contraseña_2_field: PasswordField

    @FXML
    private lateinit var guardar_button: PasswordField
}