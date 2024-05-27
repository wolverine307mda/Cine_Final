package org.example.cine_proyecto_final.controllers.sesion

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.sesion.SesionCambioContraseñaViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class SesionCambioContraseñaController : KoinComponent {

    private val viewModel: SesionCambioContraseñaViewModel by inject()

    @FXML
    private lateinit var emailField: TextField

    @FXML
    private lateinit var contraseñaField: PasswordField

    @FXML
    private lateinit var repitaContraseñaField: PasswordField

    @FXML
    private lateinit var guardarButton: Button

    @FXML
    private fun initialize() {
        /*guardarButton.setOnAction {
            logger.debug { "Guardar Button clicked" }
            val email = emailField.text
            val contraseña = contraseñaField.text
            val repitaContraseña = repitaContraseñaField.text
            //cambiarContraseña(email, contraseña, repitaContraseña)
        }*/
    }

    /*private fun cambiarContraseña(email: String, contraseña: String, repitaContraseña: String) {
        if (contraseña != repitaContraseña) {
            showAlert("Error de cambio de contraseña", "Las contraseñas no coinciden", Alert.AlertType.ERROR)
            logger.error { "Las contraseñas no coinciden" }
            return
        }

        viewModel.cambiarContraseña(email, contraseña)
            .onSuccess {
                logger.debug { "Cambio de contraseña exitoso" }
                showAlert("Cambio de contraseña exitoso", "La contraseña ha sido cambiada con éxito", Alert.AlertType.INFORMATION)
                RoutesManager.changeScene(RoutesManager.View.SESION_INICIO)
            }
            .onFailure { error ->
                logger.error { "Error en el cambio de contraseña: $error" }
                showAlert("Error de cambio de contraseña", "Hubo un problema al cambiar la contraseña: $error", Alert.AlertType.ERROR)
            }
    }

    private fun showAlert(title: String, mensaje: String, alerta: Alert.AlertType) {
        val alert = Alert(alerta)
        alert.title = title
        alert.headerText = null
        alert.contentText = mensaje
        alert.showAndWait()
    }*/
}
