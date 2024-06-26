package org.example.cine_proyecto_final.controllers.sesion

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import cuenta.errors.CuentaError
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.validator.CuentaValidator
import org.example.cine_proyecto_final.viewmodels.sesion.SesionViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Controlador para la pantalla de cambio de contraseña.
 */
class SesionCambioContraseñaController : KoinComponent {

    private val viewModel: SesionViewModel by inject()
    private val validador: CuentaValidator by inject()


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
    fun initialize() {
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
    fun cambiarContraseña(email: String, contraseña: String, repitaContraseña: String) {
        if (email.isEmpty() || contraseña.isEmpty() || repitaContraseña.isEmpty()) {
            showAlertOperacion("Error de cambio de contraseña", "Todos los campos deben estar llenos", Alert.AlertType.ERROR)
            logger.error { "Todos los campos deben estar llenos" }
            return
        }

        if (!validador.emailIsValid(email)) {
            showAlertOperacion("Error de cambio de contraseña", "El correo electrónico no es válido", Alert.AlertType.ERROR)
            logger.error { "El correo electrónico no es válido" }
            return
        }

        if (contraseña != repitaContraseña) {
            showAlertOperacion("Error de cambio de contraseña", "Las contraseñas no coinciden", Alert.AlertType.ERROR)
            logger.error { "Las contraseñas no coinciden" }
            return
        }

        viewModel.actualizarContraseña(email, contraseña)
            .onSuccess {
                logger.debug { "Cambio de contraseña exitoso" }
                showAlertOperacion("Cambio de contraseña exitoso", "La contraseña ha sido cambiada con éxito", Alert.AlertType.INFORMATION)
                guardar_button.scene.window.hide()
            }
            .onFailure { error ->
                logger.error { "Error en el cambio de contraseña: $error" }
                val mensaje = when (error) {
                    is CuentaError.CuentaNotFoundError -> "No se encontró ninguna cuenta con el email proporcionado"
                    else -> "Hubo un problema al cambiar la contraseña: $error"
                }
                showAlertOperacion("Error de cambio de contraseña", mensaje, Alert.AlertType.ERROR)
            }
    }

    /**
     * Muestra una alerta con el resultado de la operación.
     *
     * @param title El título de la alerta.
     * @param mensaje El mensaje de la alerta.
     * @param alerta El tipo de alerta (por defecto, Alert.AlertType.INFORMATION).
     */
    private fun showAlertOperacion(title: String, mensaje: String, alerta: Alert.AlertType = Alert.AlertType.INFORMATION) {
        val alert = Alert(alerta)
        alert.title = title
        alert.headerText = null
        alert.contentText = mensaje
        alert.showAndWait()
    }
}
