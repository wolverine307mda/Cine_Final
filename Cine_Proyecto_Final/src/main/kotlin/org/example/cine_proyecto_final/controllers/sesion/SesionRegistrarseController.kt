package org.example.cine_proyecto_final.controllers.sesion

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.stage.FileChooser
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.models.TipoCuenta
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.sesion.SesionRegistrarseViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import kotlin.system.exitProcess

private val logger = logging()

class SesionRegistrarseController: KoinComponent {

    private val viewModel: SesionRegistrarseViewModel by inject()

    //@FXML
    //private lateinit var imagen_button: Button

    @FXML
    private lateinit var nombre_field: TextField

    @FXML
    private lateinit var apellido_field: TextField

    @FXML
    private lateinit var email_field: TextField

    @FXML
    private lateinit var contrasenia_field: PasswordField

    @FXML
    private lateinit var repita_contrasenia_field: PasswordField

    @FXML
    private lateinit var volver_inicio_sesion_button: Button

    @FXML
    private lateinit var registrarse_button: Button

    @FXML
    private fun initialize(){
        logger.debug { "iniciando pantalla de registro" }
        registrarse_button.setOnAction {
            val nombre = nombre_field.text
            val apellido = apellido_field.text
            val email = email_field.text
            val contrasenia = contrasenia_field.text
            val repita_contrasenia = repita_contrasenia_field.text
            logger.debug { nombre + apellido }
            registrarUsuarioCuenta(nombre, apellido, email, contrasenia, repita_contrasenia)
        }
        volver_inicio_sesion_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.SESION_INICIO) }

        //imagen_button.setOnAction { onImageAction() }
    }

    /*private fun onImageAction() {
        logger.debug { "onImageAction" }
        FileChooser().run {
            title = "Selecciona una imagen"
            extensionFilters.addAll(FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"))
            showOpenDialog(RoutesManager.activeStage)
        }?.let {
        }
    }*/

    fun registrarUsuarioCuenta(nombre: String, apellido: String, email: String, contrasenia: String, repita_contrasenia: String) {
        if (contrasenia != repita_contrasenia) {
            logger.error { "Las contraseñas no coinciden" }
            return
        }

        val cuenta = Cuenta(
            nombre = nombre,
            apellido = apellido,
            email = email,
            password = contrasenia,
            imagen = "image.jpg",//imagen_button.graphic.toString(),
            tipo = TipoCuenta.USUARIO,
            createdAt = java.time.LocalDateTime.now(),
            updatedAt = java.time.LocalDateTime.now()
        )

        viewModel.crearUsuario(cuenta)
            .onSuccess {
                logger.debug { "Registro exitoso" }
                showAlertOperacion("Registro exitoso", "El usuario ha sido registrado con éxito", Alert.AlertType.INFORMATION)
                RoutesManager.changeScene(RoutesManager.View.SESION_INICIO)
            }
            .onFailure { error ->
                logger.error { "Error en el registro: $error" }
                showAlertOperacion("Error de registro", "Hubo un problema al registrar el usuario: $error", Alert.AlertType.ERROR)
            }
    }
    private fun showAlertOperacion(title: String, mensaje: String, alerta: Alert.AlertType = Alert.AlertType.INFORMATION) {
        val alert = Alert(alerta)
        alert.title = title
        alert.headerText = null
        alert.contentText = mensaje
        alert.showAndWait()
    }
}
