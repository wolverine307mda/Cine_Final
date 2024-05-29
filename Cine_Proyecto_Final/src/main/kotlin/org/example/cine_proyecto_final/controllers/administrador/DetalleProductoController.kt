package org.example.cine_proyecto_final.controllers.administrador

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.stage.FileChooser
import org.example.cine_proyecto_final.productos.models.Producto
import org.example.cine_proyecto_final.productos.models.TipoProducto
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.administrador.AdministradorGestorProductosViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDateTime

private val logger = logging()

class DetalleProductoController : KoinComponent {

    private val viewModel: AdministradorGestorProductosViewModel by inject()

    @FXML
    private lateinit var guardarButton: Button

    @FXML
    private lateinit var imagen_button: Button

    @FXML
    private lateinit var idField: TextField

    @FXML
    private lateinit var nombreField: TextField

    @FXML
    private lateinit var precioField: TextField

    @FXML
    private lateinit var stockField: TextField

    @FXML
    private lateinit var tipoCombo: ComboBox<String>

    @FXML
    private fun initialize() {
        val tipos = listOf("BEBIDA", "COMIDA", "OTROS")
        tipoCombo.items.addAll(tipos)

        guardarButton.setOnAction {
            val tipo = tipoCombo.value
            val nombre = nombreField.text
            val precio = precioField.text
            val stock = stockField.text
            guardarNuevoProducto(tipo, nombre, precio, stock)
        }

        imagen_button.setOnAction { onImageAction() }
    }

    private fun onImageAction() {
        logger.debug { "OnImageAction" }
        FileChooser().run {
            title = "Selecciona una imagen"
            extensionFilters.addAll(FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"))
            showOpenDialog(RoutesManager.activeStage)
        }?.let {
            // Código para manejar la imagen seleccionada
        }
    }

    private fun guardarNuevoProducto(tipo: String, nombre: String, precio: String, stock: String) {
        if (nombre.isBlank() || precio.isBlank() || stock.isBlank()) {
            showAlertOperacion("Error de registro", "Todos los campos deben estar completos", Alert.AlertType.ERROR)
            return
        }

        val precioInt = precio.toIntOrNull()
        val stockInt = stock.toIntOrNull()

        if (precioInt == null || stockInt == null || precioInt < 0 || stockInt < 0) {
            showAlertOperacion("Error de guardado", "Precio y stock deben ser números válidos y positivos", Alert.AlertType.ERROR)
            return
        }

        val tipoProducto = when (tipo) {
            "BEBIDA" -> TipoProducto.BEBIDA
            "COMIDA" -> TipoProducto.COMIDA
            "OTROS" -> TipoProducto.OTROS
            else -> {
                showAlertOperacion("Error de guardado", "Tipo de producto inválido", Alert.AlertType.ERROR)
                return
            }
        }
        nuevoProducto(tipoProducto, nombre, precioInt, stockInt)
    }

    private fun nuevoProducto(tipo: TipoProducto, nombre: String, precio: Int, stock: Int) {
        val producto = Producto(
            nombre = nombre,
            precio = precio.toDouble(),
            stock = stock,
            tipo = tipo,
            image = "producto.jpg",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            isDeleted = false
        )
        viewModel.nuevoProducto(producto)
            .onSuccess {
                logger.debug { "Producto agregado con exito" }
                showAlertOperacion("Producto agregado con exito", "El producto ha sido guardado con éxito", Alert.AlertType.INFORMATION)
                guardarButton.scene.window.hide()
            }
            .onFailure { error ->
                logger.error { "Error en el registro del producto: $error" }
                showAlertOperacion("Error de registro", "Hubo un problema al registrar el producto: $error", Alert.AlertType.ERROR)
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
