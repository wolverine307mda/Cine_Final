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

    }

    private fun showAlertOperacion(title: String, mensaje: String, alerta: Alert.AlertType = Alert.AlertType.INFORMATION) {
        val alert = Alert(alerta)
        alert.title = title
        alert.headerText = null
        alert.contentText = mensaje
        alert.showAndWait()
    }
}



