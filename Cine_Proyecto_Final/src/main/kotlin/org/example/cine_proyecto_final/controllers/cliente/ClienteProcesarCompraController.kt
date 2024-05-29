package org.example.cine_proyecto_final.controllers.cliente

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.DatePicker
import javafx.scene.control.TextField
import javafx.scene.text.Text
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteProcesarCompraViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val logger = logging()

class ClienteProcesarCompraController: KoinComponent {

    private val dbClient: SqlDelightManager by inject()
    private val viewModel: ClienteProcesarCompraViewModel by inject()

    @FXML
    private lateinit var atras_button: Button

    @FXML
    private lateinit var nombre_field: TextField

    @FXML
    private lateinit var email_field: TextField

    @FXML
    private lateinit var textInfo: Text

    @FXML
    private lateinit var fxCaducidad: TextField

    @FXML
    private lateinit var cvvField: TextField

    @FXML
    private lateinit var tarjet_credito_field: TextField

    @FXML
    private lateinit var precio_total_field: TextField

    @FXML
    private lateinit var finalizar_compra_button: Button

    @FXML
    fun initialize(){
        logger.debug { "iniciando pantalla de Procesar Compra" }

        atras_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.SELECCION_PRODUCTOS) }

        infoCompra()

        finalizar_compra_button.setOnAction { finalizarCompra() }
    }

    private fun infoCompra() {
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
        textInfo.text = """DeadPool & Wolverine
        Fecha de compra: ${LocalDateTime.now().format(formatter)}""".trimIndent()

        //implementar el precio Total
    }
    private fun finalizarCompra() {
        val tarjetaCredito = tarjet_credito_field.text
        val cvv = cvvField.text
        val fechaCaducidad = fxCaducidad.text

        if (tarjetaCredito.isEmpty() || cvv.isEmpty() || fechaCaducidad.isEmpty()) {
            showAlertOperacion("Error", "Por favor, completa todos los campos", Alert.AlertType.ERROR)
            return
        }

        if (!validarTarjetaCredito(tarjetaCredito)) {
            showAlertOperacion("Error", "Por favor, verifica el numero de la tarjeta de credito", Alert.AlertType.ERROR)
            return
        }

        if (!validarCVC(cvv)) {
            showAlertOperacion("Error", "Por favor, verifica el cvv", Alert.AlertType.ERROR)
            return
        }

        if (!validarFechaCaducidad(fechaCaducidad)) {
            showAlertOperacion("Error", "Por favor, verifica la fecha de caducidad", Alert.AlertType.ERROR)
            return
        }



        // Aquí puedes realizar la lógica de finalización de la compra
        showAlertOperacion("Compra Finalizada", "La compra se ha procesado correctamente")
    }


    private fun validarTarjetaCredito(tarjeta: String): Boolean {
        // Expresión regular para validar el número de tarjeta de crédito
        val regex = Regex("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$")
        return regex.matches(tarjeta)
    }

    private fun validarCVC(cvc: String): Boolean {
        // Expresión regular para validar el CVC de la tarjeta
        val regex = Regex("^\\d{3}$")
        return regex.matches(cvc)
    }

    private fun validarFechaCaducidad(fecha: String): Boolean {
        try {
            // Intenta parsear la fecha en el formato MM/YY
            val formatter = DateTimeFormatter.ofPattern("MM/yy")
            val fechaCaducidad = formatter.parse(fecha)
            // Comprueba si la fecha es válida (no está en el pasado)
            return !LocalDate.from(fechaCaducidad).isBefore(LocalDate.now())
        } catch (e: Exception) {
            return false
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



