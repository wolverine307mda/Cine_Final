package org.example.cine_proyecto_final.controllers.sesion

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import org.example.cine_proyecto_final.cuentas.models.TipoCuenta
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.sesion.SesionInicioViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class SesionInicioController : KoinComponent {

    private val viewModel: SesionInicioViewModel by inject()

    private val sesionViewModel : SesionInicioViewModel by inject()

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

    //private var stage: Stage? = null

    /*
    fun setStage(stage: Stage) {
        this.stage = stage
    }
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

    private fun iniciarSesion(email: String, contraseña: String) {
        /*
        viewModel.iniciarSesion(email, contraseña) { success, tipo, message ->
            if (success) {
                when (tipo) {
                    "A" -> {
                        logger.debug { "Administrador logged in" }
                        RoutesManager.changeScene(RoutesManager.View.COMPRAR_ENTRADA)
                    }
                    "U" -> {
                        logger.debug { "Usuario logged in" }
                        RoutesManager.changeScene(RoutesManager.View.COMPRAR_ENTRADA)
                    }
                    else -> {
                        logger.debug { "Unknown account type: $tipo" }
                        println("Error: Tipo de cuenta no válido")
                    }
                }
            } else {
                logger.debug { "Login failed: $message" }
                println("Error: $message")
            }
        }
        */
        if (sesionViewModel.usuario == null){
            sesionViewModel.iniciarSesion(email, contraseña)
            if (sesionViewModel.usuario == null){
                println("Error al iniciar sesión")
                showAlertOperacion(
                    title = "Error al iniciar sesión",
                    mensaje = "La cuenta que ha introducido no corresponde con una cuenta"
                )
            }else {
                if (sesionViewModel.usuario!!.tipo == TipoCuenta.ADMINISTRADOR){
                    RoutesManager.changeScene(RoutesManager.View.ADMIN_INICIO)
                }
                email_textField.scene.window.hide()
            }
        }else showAlertOperacion( //Estp esta mal
            alerta = AlertType.CONFIRMATION,
            title = "¿Desea cerrar sesión?",
            mensaje = "Está inciado sesión como ${viewModel.usuario!!.email}, ¿desea cerrar sesión?"
        )
    }

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
