package org.example.cine_proyecto_final.controllers.sesion

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.DatePicker
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.stage.FileChooser
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.sesion.SesionRegistrarseViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class SesionRegistrarseController: KoinComponent {

    private val viewModel: SesionRegistrarseViewModel by inject()
    //private val dbClient: SqlDelightManager by inject()

    @FXML
    private lateinit var imagen_button: Button

    @FXML
    private lateinit var nombre_field: TextField

    @FXML
    private lateinit var email_field: TextField

    @FXML
    private lateinit var contrasenia_field: PasswordField

    @FXML
    private lateinit var repita_contrasenia_field: PasswordField

    @FXML
    private lateinit var nacimiento_datepicker: DatePicker

    @FXML
    private lateinit var volver_inicio_sesion_button: Button

    @FXML
    private fun initialize(){
        logger.debug { "iniciando pantalla de registro" }
        val nombre = nombre_field.text
        val email = email_field.text
        val contrasenia = contrasenia_field.text
        val repita_contrasenia = repita_contrasenia_field.text
        volver_inicio_sesion_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.SESION_INICIO) }

    }

    private fun onImageAction() {
        logger.debug { "onImageAction" }
        // Abrimos un diálogo para seleccionar una imagen, esta vez lo he hecho más compacto!!!
        // Comparalo con los de Json!!!
        FileChooser().run {
            title = "Selecciona una imagen"
            extensionFilters.addAll(FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"))
            showOpenDialog(RoutesManager.activeStage)
        }?.let {
            //viewModel.updateImageAlumnoOperacion(it)
        }

    }
}