package org.example.cine_proyecto_final.controllers.sesion

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import org.example.cine_proyecto_final.cuentas.models.TipoCuenta
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.sesion.SesionViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Controlador para la pantalla de inicio de sesión.
 */
class SesionInicioController : KoinComponent {

    private val viewModel: SesionViewModel by inject()

    @FXML
    private lateinit var email_textField: TextField

    @FXML
    private lateinit var contrasenia_field: PasswordField

    @FXML
    private lateinit var iniciarSesion_Button: Button

    @FXML
    private lateinit var registrate_button: Button

    @FXML
    private lateinit var olvidoContraseña_button: Button

    /**
     * Método de inicialización para configurar los botones y sus acciones.
     */
    @FXML
    private fun initialize() {
        println("Initialize inicio de sesion")
        iniciarSesion_Button.setOnAction {
            logger.debug { "Iniciar Sesión Button clicked" }
            val email = email_textField.text
            val contraseña = contrasenia_field.text
            logger.debug { "Email: $email, Contraseña: $contraseña" }
            iniciarSesion(email, contraseña)
        }
        registrate_button.setOnAction {
            logger.debug { "Registrate Button clicked" }
            RoutesManager.initRegistrarse()
        }
        olvidoContraseña_button.setOnAction {
            logger.debug { "Olvido Contraseña Button clicked" }
            RoutesManager.initCambiarContrasena()
        }
    }

    /**
     * Inicia sesión con los datos proporcionados.
     *
     * @param email El correo electrónico del usuario.
     * @param contraseña La contraseña del usuario.
     */
    private fun iniciarSesion(email: String, contraseña: String) {
        if (viewModel.usuario == null) {
            viewModel.iniciarSesion(email, contraseña)
            if (viewModel.usuario == null) {
                println("Error al iniciar sesión")
                showAlertOperacion(
                    title = "Error al iniciar sesión",
                    mensaje = "La cuenta que ha introducido no corresponde con una cuenta"
                )
            } else {
                if (viewModel.usuario!!.tipo == TipoCuenta.ADMINISTRADOR) {
                    RoutesManager.changeScene(RoutesManager.View.ADMIN_INICIO)
                }
                email_textField.scene.window.hide()
            }
        } else showAlertOperacion(
            alerta = AlertType.CONFIRMATION,
            title = "¿Desea cerrar sesión?",
            mensaje = "Está iniciado sesión como ${viewModel.usuario!!.email}, ¿desea cerrar sesión?"
        )
    }

    /**
     * Muestra una alerta con la operación realizada.
     *
     * @param alerta El tipo de alerta.
     * @param title El título de la alerta.
     * @param mensaje El mensaje de la alerta.
     */
    private fun showAlertOperacion(
        alerta: AlertType = AlertType.ERROR,
        title: String = "",
        mensaje: String = ""
    ) {
        Alert(alerta).apply {
            this.title = title
            this.contentText = mensaje
        }.showAndWait()
    }
}
