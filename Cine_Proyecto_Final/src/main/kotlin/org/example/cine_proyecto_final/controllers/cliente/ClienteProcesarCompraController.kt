package org.example.cine_proyecto_final.controllers.cliente

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.text.Text
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteProcesarCompraViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDateTime
import java.time.YearMonth
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
            RoutesManager.showAlertOperacion("Error", "Por favor, completa todos los campos", Alert.AlertType.ERROR)
            return
        }

        if (!validarTarjetaCredito(tarjetaCredito)) {
            RoutesManager.showAlertOperacion("Error", "Por favor, verifica el numero de la tarjeta de credito", Alert.AlertType.ERROR)
            return
        }

        if (!validarCVC(cvv)) {
            RoutesManager.showAlertOperacion("Error", "Por favor, verifica el cvv", Alert.AlertType.ERROR)
            return
        }

        if (!validarFechaCaducidad(fechaCaducidad)) {
            RoutesManager.showAlertOperacion("Error", "Por favor, verifica la fecha de caducidad", Alert.AlertType.ERROR)
            return
        }



        // Aquí puedes realizar la lógica de finalización de la compra
        RoutesManager.showAlertOperacion("Compra Finalizada", "La compra se ha procesado correctamente")
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
        return try {
            // Define el formato de la fecha MM/yy
            val formatter = DateTimeFormatter.ofPattern("MM/yy")
            // Parsea la fecha de caducidad
            val fechaCaducidad = YearMonth.parse(fecha, formatter)
            // Define la fecha de referencia 05/24
            val fechaReferencia = YearMonth.of(2024, 5)
            // Comprueba si la fecha de caducidad es posterior a 05/24
            fechaCaducidad.isAfter(fechaReferencia)
        } catch (e: Exception) {
            false
        }
    }
}



