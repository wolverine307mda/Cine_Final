package org.example.cine_proyecto_final.controllers.sesion

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.FileChooser
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.models.TipoCuenta
import org.example.cine_proyecto_final.cuentas.validator.CuentaValidator
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.sesion.SesionViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDate
import java.time.Period

private val logger = logging()

/**
 * Controlador para la pantalla de registro de usuario.
 */
class SesionRegistrarseController : KoinComponent {

    private val viewModel: SesionViewModel by inject()
    private val validador: CuentaValidator by inject()

    @FXML
    private lateinit var imagen_button: Button

    @FXML
    private lateinit var fechaNacimiento: DatePicker

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

    /**
     * Inicializa la pantalla de registro.
     */
    @FXML
    private fun initialize() {
        logger.debug { "iniciando pantalla de registro" }

        // Establecer la fecha de nacimiento por defecto como el día de hoy
        fechaNacimiento.value = LocalDate.now()

        registrarse_button.setOnAction {
            val nombre = nombre_field.text
            val apellido = apellido_field.text
            val email = email_field.text
            val contrasenia = contrasenia_field.text
            val repita_contrasenia = repita_contrasenia_field.text
            val fechaNac = fechaNacimiento.value

            logger.debug { "$nombre $apellido" }
            registrarUsuarioCuenta(nombre, apellido, email, contrasenia, repita_contrasenia, fechaNac)
        }
        volver_inicio_sesion_button.setOnAction { volver_inicio_sesion_button.scene.window.hide() }

        imagen_button.setOnAction { onImageAction() }
    }

    /**
     * Abre un diálogo para seleccionar una imagen.
     */
    private fun onImageAction() {
        logger.debug { "onImageAction" }
        FileChooser().run {
            title = "Selecciona una imagen"
            extensionFilters.addAll(FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"))
            showOpenDialog(RoutesManager.activeStage)
        }?.let {
            // Código para manejar la imagen seleccionada
        }
    }

    /**
     * Registra una nueva cuenta de usuario.
     *
     * @param nombre El nombre del usuario.
     * @param apellido El apellido del usuario.
     * @param email El correo electrónico del usuario.
     * @param contrasenia La contraseña del usuario.
     * @param repita_contrasenia La repetición de la contraseña para verificación.
     * @param fechaNacimiento La fecha de nacimiento del usuario.
     */
    fun registrarUsuarioCuenta(nombre: String, apellido: String, email: String, contrasenia: String, repita_contrasenia: String, fechaNacimiento: LocalDate) {
        if (nombre.isBlank() || apellido.isBlank() || email.isBlank() || contrasenia.isBlank() || repita_contrasenia.isBlank() || fechaNacimiento == null) {
            showAlertOperacion("Error de registro", "Todos los campos deben estar completos", Alert.AlertType.ERROR)
            return
        }

        if (!validador.emailIsValid(email)) {
            showAlertOperacion("Error de registro", "El correo electrónico no es válido", Alert.AlertType.ERROR)
            logger.error { "El correo electrónico no es válido" }
            return
        }

        if (contrasenia != repita_contrasenia) {
            showAlertOperacion("Error de registro", "Las contraseñas no coinciden", Alert.AlertType.ERROR)
            return
        }

        if (contrasenia.length < 4) {
            showAlertOperacion("Error de registro", "La contraseña debe tener al menos 4 caracteres", Alert.AlertType.ERROR)
            return
        }

        val edad = Period.between(fechaNacimiento, LocalDate.now()).years
        if (edad < 18) {
            showAlertOperacion("Error de registro", "Debes ser mayor de 18 años para registrarte", Alert.AlertType.ERROR)
            return
        }

        val cuenta = Cuenta(
            nombre = nombre,
            apellido = apellido,
            email = email,
            password = contrasenia,
            imagen = imagen_button.graphic?.toString() ?: "",
            tipo = TipoCuenta.USUARIO,
            createdAt = java.time.LocalDateTime.now(),
            updatedAt = java.time.LocalDateTime.now()
        )

        viewModel.crearUsuario(cuenta)
            .onSuccess {
                logger.debug { "Registro exitoso" }
                showAlertOperacion("Registro exitoso", "El usuario ha sido registrado con éxito", Alert.AlertType.INFORMATION)
                registrarse_button.scene.window.hide()
            }
            .onFailure { error ->
                logger.error { "Error en el registro: $error" }
                showAlertOperacion("Error de registro", "Hubo un problema al registrar el usuario: $error", Alert.AlertType.ERROR)
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
