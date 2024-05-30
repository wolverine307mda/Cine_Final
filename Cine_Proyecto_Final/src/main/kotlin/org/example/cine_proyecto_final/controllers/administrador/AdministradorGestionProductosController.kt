package org.example.cine_proyecto_final.controllers.administrador

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.collections.FXCollections
import javafx.collections.transformation.FilteredList
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.text.Text
import org.example.cine_proyecto_final.productos.models.Producto
import org.example.cine_proyecto_final.productos.models.TipoProducto
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.routes.RoutesManager.View
import org.example.cine_proyecto_final.viewmodels.administrador.AdministradorGestorProductosViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class AdministradorGestionProductosController : KoinComponent {

    private val viewModel: AdministradorGestorProductosViewModel by inject()

    @FXML
    private lateinit var atrasButton: Button

    @FXML
    private lateinit var editarButton: Button

    @FXML
    private lateinit var searchField: TextField

    @FXML
    private lateinit var filtrarTipoCombobox: ComboBox<String>

    @FXML
    private lateinit var labelnombreProducto: Text
    @FXML
    private lateinit var ProductoSeleccionadoTipoComboBox: ComboBox<String>
    @FXML
    private lateinit var nombreProductoSeleccionado: TextField
    @FXML
    private lateinit var precioProductoSeleccionado: TextField
    @FXML
    private lateinit var stockProductoSeleccionado: TextField

    @FXML
    private lateinit var nuevoButton: Button
    @FXML
    private lateinit var eliminarButton: Button

    @FXML
    private lateinit var productoTable: TableView<Producto>

    @FXML
    private lateinit var productoCantidad: TableColumn<Producto, Int>

    @FXML
    private lateinit var productoNombre: TableColumn<Producto, String>

    @FXML
    private lateinit var productoPrecio: TableColumn<Producto, Double>

    @FXML
    private lateinit var productoTipo: TableColumn<Producto, String>

    private lateinit var filteredData: FilteredList<Producto>

    @FXML
    private fun initialize() {
        logger.debug { "Iniciando pantalla de gestión de productos" }

        atrasButton.setOnAction { RoutesManager.changeScene(View.ADMIN_INICIO) }
        nuevoButton.setOnAction { RoutesManager.initDetalle(View.DETALLE_PRODUCTO, "Nuevo Producto") }

        initDefaultValues()
        initBindings()

        productoTable.selectionModel.selectedItemProperty().addListener { _, _, selectedProducto ->
            selectedProducto?.let { updateProductoSeleccionado(it) }
        }

        configureSearchAndFilter()

        editarButton.setOnAction { editarProductoSeleccionado() }
        eliminarButton.setOnAction { eliminarProductoSeleccionado() }
    }

    private fun initDefaultValues() {
        filteredData = FilteredList(FXCollections.observableArrayList(viewModel.state.value.productos))
        productoTable.items = filteredData

        productoPrecio.cellValueFactory = PropertyValueFactory("precio")
        productoNombre.cellValueFactory = PropertyValueFactory("nombre")
        productoCantidad.cellValueFactory = PropertyValueFactory("stock")
        productoTipo.cellValueFactory = PropertyValueFactory("tipo")

        filtrarTipoCombobox.items.addAll("Todos", "BEBIDA", "COMIDA", "OTROS")
        filtrarTipoCombobox.value = "Todos"
    }

    private fun initBindings() {
        viewModel.state.addListener { _, _, newValue ->
            if (newValue.productos != productoTable.items) {
                filteredData = FilteredList(FXCollections.observableArrayList(newValue.productos))
                productoTable.items = filteredData
            }
        }
    }

    private fun configureSearchAndFilter() {
        searchField.textProperty().addListener { _, _, _ -> filterProducts() }
        filtrarTipoCombobox.valueProperty().addListener { _, _, _ -> filterProducts() }
    }

    private fun filterProducts() {
        val searchText = searchField.text.lowercase().trim()
        val selectedType = filtrarTipoCombobox.value

        filteredData.setPredicate { producto ->
            val matchesSearchText = producto.nombre.lowercase().contains(searchText)
            val matchesType = when (selectedType) {
                "Todos" -> true
                else -> producto.tipo?.name == selectedType
            }
            matchesSearchText && matchesType
        }
    }

    private fun updateProductoSeleccionado(producto: Producto) {
        nombreProductoSeleccionado.text = producto.nombre
        precioProductoSeleccionado.text = producto.precio.toString()
        stockProductoSeleccionado.text = producto.stock.toString()
        ProductoSeleccionadoTipoComboBox.value = asignarTipo(producto)
        labelnombreProducto.text = producto.nombre
    }

    private fun asignarTipo(producto: Producto): String {
        return when (producto.tipo) {
            TipoProducto.BEBIDA -> "Bebida"
            TipoProducto.COMIDA -> "Comida"
            TipoProducto.OTROS -> "Otros"
            null -> "otros"
        }
    }

    private fun editarProductoSeleccionado() {
        val selectedProduct = productoTable.selectionModel.selectedItem
        if (selectedProduct != null) {
            ProductHolder.selectedProduct = selectedProduct
            RoutesManager.changeScene(View.DETALLE_PRODUCTO)
        } else {
            showAlertOperacion("Error de edición", "No se ha seleccionado ningún producto", Alert.AlertType.ERROR)
        }
    }


    private fun eliminarProductoSeleccionado() {
        val selectedProducto = productoTable.selectionModel.selectedItem
        if (selectedProducto != null) {
            val confirmation = showConfirmationDialog("Eliminar Producto", "¿Estás seguro de que quieres eliminar este producto?")
            if (confirmation == ButtonType.OK) {
                viewModel.eliminarProducto(selectedProducto)
                    .onSuccess {
                        logger.debug { "Producto eliminado con éxito" }
                        showAlertOperacion("Producto eliminado", "El producto ha sido eliminado con éxito", Alert.AlertType.INFORMATION)
                        productoTable.items.remove(selectedProducto)
                    }
                    .onFailure { error ->
                        logger.error { "Error al eliminar producto: $error" }
                        showAlertOperacion("Error de eliminación", "Hubo un problema al eliminar el producto: $error", Alert.AlertType.ERROR)
                    }
            }
        } else {
            showAlertOperacion("Error de eliminación", "No se ha seleccionado ningún producto", Alert.AlertType.ERROR)
        }
    }

    private fun showAlertOperacion(title: String, mensaje: String, alerta: Alert.AlertType = Alert.AlertType.INFORMATION) {
        val alert = Alert(alerta)
        alert.title = title
        alert.headerText = null
        alert.contentText = mensaje
        alert.showAndWait()
    }

    private fun showConfirmationDialog(title: String, mensaje: String): ButtonType {
        val alert = Alert(Alert.AlertType.CONFIRMATION)
        alert.title = title
        alert.headerText = null
        alert.contentText = mensaje

        val result = alert.showAndWait()
        return result.orElse(ButtonType.CANCEL)
    }

    object ProductHolder {
        var selectedProduct: Producto? = null
    }
}
