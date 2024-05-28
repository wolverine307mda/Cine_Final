package org.example.cine_proyecto_final.controllers.sesion

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import cuenta.errors.CuentaError
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

/**
 * Controlador para la pantalla de cambio de contraseña.
 */
class SesionCambioContraseñaController : KoinComponent {

    private val viewModel: SesionCambioContraseñaViewModel by inject()

    @FXML
    private lateinit var email_field: TextField

    @FXML
    private lateinit var contraseñaField: PasswordField

    @FXML
    private lateinit var repitaContraseñaField: PasswordField

    @FXML
    private lateinit var guardar_button: Button

    /**
     * Método de inicialización para configurar el botón de guardar y su acción.
     */
    @FXML
    private fun initialize() {
        guardar_button.setOnAction {
            logger.debug { "Guardar Button clicked" }
            val email = email_field.text
            val contrasenia = contraseñaField.text
            val repitaContrasenia = repitaContraseñaField.text
            cambiarContraseña(email, contrasenia, repitaContrasenia)
        }
    }

    /**
     * Cambia la contraseña de una cuenta existente.
     *
     * @param email El correo electrónico de la cuenta.
     * @param contraseña La nueva contraseña.
     * @param repitaContraseña La confirmación de la nueva contraseña.
     */
    private fun cambiarContraseña(email: String, contraseña: String, repitaContraseña: String) {
        if (contraseña != repitaContraseña) {
            showAlert("Error de cambio de contraseña", "Las contraseñas no coinciden", Alert.AlertType.ERROR)
            logger.error { "Las contraseñas no coinciden" }
            return
        }

        viewModel.actualizarContraseña(email, contraseña)
            .onSuccess {
                logger.debug { "Cambio de contraseña exitoso" }
                showAlert("Cambio de contraseña exitoso", "La contraseña ha sido cambiada con éxito", Alert.AlertType.INFORMATION)
                guardar_button.scene.window.hide()
            }
            .onFailure { error ->
                logger.error { "Error en el cambio de contraseña: $error" }
                val mensaje = when (error) {
                    is CuentaError.CuentaNotFoundError -> "No se encontró ninguna cuenta con el email proporcionado"
                    else -> "Hubo un problema al cambiar la contraseña: $error"
                }
                showAlert("Error de cambio de contraseña", mensaje, Alert.AlertType.ERROR)
            }
    }

    /**
     * Muestra una alerta con un mensaje específico.
     *
     * @param title El título de la alerta.
     * @param mensaje El mensaje de la alerta.
     * @param alerta El tipo de alerta.
     */
    private fun showAlert(title: String, mensaje: String, alerta: Alert.AlertType) {
        val alert = Alert(alerta)
        alert.title = title
        alert.headerText = null
        alert.contentText = mensaje
        alert.showAndWait()
    }
}
