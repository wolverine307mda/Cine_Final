package org.example.cine_proyecto_final.controllers.administrador

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import org.example.cine_proyecto_final.productos.models.Producto
import org.example.cine_proyecto_final.productos.models.TipoProducto
import org.example.cine_proyecto_final.routes.RoutesManager.showAlertOperacion
import org.example.cine_proyecto_final.viewmodels.administrador.AdministradorGestorProductosViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDateTime

private val logger = logging()

class DetalleProductoController: KoinComponent {
    private val viewModel: AdministradorGestorProductosViewModel by inject()
    //private var producto: Producto? = null

    @FXML
    private lateinit var guardarButton: Button

    @FXML
    private lateinit var nombreField: TextField

    @FXML
    private lateinit var precioField: TextField

    @FXML
    private lateinit var stockField: TextField

    @FXML
    private lateinit var tipoCombo: ComboBox<String>

    @FXML
    private lateinit var image : ImageView

    @FXML
    fun initialize() {
        val tipos = listOf("BEBIDA", "COMIDA", "OTROS")
        tipoCombo.items.addAll(tipos)

        // Obtén el producto seleccionado de ProductHolder
        val producto = AdministradorGestionProductosController.ProductHolder.selectedProduct

        // Si hay un producto seleccionado, configura los campos con sus valores
        if (producto != null) {
            nombreField.text = producto.nombre
            precioField.text = producto.precio.toString()
            stockField.text = producto.stock.toString()
            tipoCombo.value = producto.tipo.toString()
        }

        guardarButton.setOnAction {
            if (producto != null){editarProducto(producto)}
            else {crearProducto()}

        }

        image.image = viewModel.state.value.currentImage
    }

    private fun editarProducto(producto: Producto) {
        viewModel.editarProducto(producto)
    }

    private fun crearProducto() {
        val tipo = tipoCombo.value
        val nombre = nombreField.text
        val precio = precioField.text
        val stock = stockField.text

        guardarProductoNuevo(tipo, nombre, precio, stock)
    }

    private fun guardarProductoNuevo(tipo: String, nombre: String, precio: String, stock: String) {
        if (nombre.isBlank() || precio.isBlank() || stock.isBlank()) {
            showAlertOperacion("Error de actualización", "Todos los campos deben estar completos", Alert.AlertType.ERROR)
            return
        }

        val precioDouble = precio.toDoubleOrNull()
        val stockInt = stock.toIntOrNull()

        if (precioDouble == null || stockInt == null || precioDouble < 0 || stockInt < 0) {
            showAlertOperacion("Error de actualización", "Precio y stock deben ser números válidos y positivos", Alert.AlertType.ERROR)
            return
        }

        val tipoProducto = when (tipo) {
            "BEBIDA" -> TipoProducto.BEBIDA
            "COMIDA" -> TipoProducto.COMIDA
            "OTROS" -> TipoProducto.OTROS
            else -> {
                showAlertOperacion("Error de actualización", "Tipo de producto inválido", Alert.AlertType.ERROR)
                return
            }
        }
        val producto = Producto(
            nombre = nombre,
            precio = precioDouble,
            stock = stockInt,
            tipo = tipoProducto,
            image = "producto.jpg",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            isDeleted = false
        )

        viewModel.nuevoProducto(producto)
            .onSuccess {
                logger.debug { "Producto actualizado con éxito" }
                showAlertOperacion("Producto actualizado", "El producto ha sido actualizado con éxito", Alert.AlertType.INFORMATION)
                guardarButton.scene.window.hide()
            }
            .onFailure { error ->
                logger.error { "Error en la actualización del producto: $error" }
                showAlertOperacion("Error de actualización", "Hubo un problema al actualizar el producto: $error", Alert.AlertType.ERROR)
            }
    }


}

/*import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import org.example.cine_proyecto_final.controllers.administrador.AdministradorGestionProductosController
import org.example.cine_proyecto_final.productos.models.Producto
import org.example.cine_proyecto_final.productos.models.TipoProducto
import org.example.cine_proyecto_final.viewmodels.administrador.AdministradorGestorProductosViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDateTime

private val logger = logging()

class DetalleProductoController : KoinComponent {



    fun initialize() {
        val tipos = listOf("BEBIDA", "COMIDA", "OTROS")
        tipoCombo.items.addAll(tipos)

        // Obtén el producto seleccionado de ProductHolder
        val producto = AdministradorGestionProductosController.ProductHolder.selectedProduct

        // Si hay un producto seleccionado, configura los campos con sus valores
        if (producto != null) {
            idField.text = producto.id.toString()
            nombreField.text = producto.nombre
            precioField.text = producto.precio.toString()
            stockField.text = producto.stock.toString()
            tipoCombo.value = producto.tipo.toString()
        }

        guardarButton.setOnAction {
            if (producto != null){editarProducto(producto)}
            else {crearProducto()}

        }
    }
    private fun editarProducto(producto: Producto) {
        val tipo = tipoCombo.value
        val nombre = nombreField.text
        val precio = precioField.text
        val stock = stockField.text
        val id = idField.text.toIntOrNull()

        if (id != null) {
            val productoEditado = Producto(
                id = id.toString(),
                nombre = nombre,
                precio = precio.toDoubleOrNull() ?: 0.0,
                stock = stock.toIntOrNull() ?: 0,
                tipo = when (tipo) {
                    "BEBIDA" -> TipoProducto.BEBIDA
                    "COMIDA" -> TipoProducto.COMIDA
                    "OTROS" -> TipoProducto.OTROS
                    else -> {
                        showAlertOperacion("Error de actualización", "Tipo de producto inválido", Alert.AlertType.ERROR)
                        return
                    }
                },
                image = "producto.jpg",
                createdAt = producto.createdAt,
                updatedAt = LocalDateTime.now(),
                isDeleted = false
            )

            viewModel.editarProducto(productoEditado)
                .onSuccess {
                    logger.debug { "Producto editado con éxito" }
                    showAlertOperacion("Producto editado", "El producto ha sido editado con éxito", Alert.AlertType.INFORMATION)
                    guardarButton.scene.window.hide()
                }
                .onFailure { error ->
                    logger.error { "Error en la edición del producto: $error" }
                    showAlertOperacion("Error de edición", "Hubo un problema al editar el producto: $error", Alert.AlertType.ERROR)
                }
        } else {
            showAlertOperacion("Error de actualización", "El ID del producto es inválido", Alert.AlertType.ERROR)
        }
    }

    /*private fun guardarProductoNuevo(id: Int, tipo: String, nombre: String, precio: String, stock: String) {
        if (nombre.isBlank() || precio.isBlank() || stock.isBlank()) {
            showAlertOperacion("Error de actualización", "Todos los campos deben estar completos", Alert.AlertType.ERROR)
            return
        }

        val precioDouble = precio.toDoubleOrNull()
        val stockInt = stock.toIntOrNull()

        if (precioDouble == null || stockInt == null || precioDouble < 0 || stockInt < 0) {
            showAlertOperacion("Error de actualización", "Precio y stock deben ser números válidos y positivos", Alert.AlertType.ERROR)
            return
        }

        val tipoProducto = when (tipo) {
            "BEBIDA" -> TipoProducto.BEBIDA
            "COMIDA" -> TipoProducto.COMIDA
            "OTROS" -> TipoProducto.OTROS
            else -> {
                showAlertOperacion("Error de actualización", "Tipo de producto inválido", Alert.AlertType.ERROR)
                return
            }
        }
        val producto = Producto(
            id = id.toString(),
            nombre = nombre,
            precio = precioDouble,
            stock = stockInt,
            tipo = tipoProducto,
            image = "producto.jpg",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            isDeleted = false
        )

        viewModel.nuevoProducto(producto)
            .onSuccess {
                logger.debug { "Producto actualizado con éxito" }
                showAlertOperacion("Producto actualizado", "El producto ha sido actualizado con éxito", Alert.AlertType.INFORMATION)
                guardarButton.scene.window.hide()
            }
            .onFailure { error ->
                logger.error { "Error en la actualización del producto: $error" }
                showAlertOperacion("Error de actualización", "Hubo un problema al actualizar el producto: $error", Alert.AlertType.ERROR)
            }
    }

}
*/