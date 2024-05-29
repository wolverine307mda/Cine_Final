package org.example.cine_proyecto_final.controllers.administrador

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
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

    //info del producto seleccionado
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

    @FXML
    private fun initialize() {
        logger.debug { "Iniciando pantalla de gestión de productos" }

        // Configuración de acciones de botones
        atrasButton.setOnAction { RoutesManager.changeScene(View.ADMIN_INICIO) }
        editarButton.setOnAction { RoutesManager.initDetalle(View.DETALLE_PRODUCTO, "Editar Producto") }
        nuevoButton.setOnAction { RoutesManager.initDetalle(View.DETALLE_PRODUCTO, "Nuevo Producto") }

        // Inicializar valores por defecto
        initDefaultValues()

        // Añadir listener a la tabla para manejar la selección de productos
        productoTable.selectionModel.selectedItemProperty().addListener { _, _, selectedProducto ->
            selectedProducto?.let { updateProductoSeleccionado(it) }
        }
    }

    /**
     * Inicializa los valores por defecto en la tabla de productos.
     */
    private fun initDefaultValues() {
        // Establecer los ítems de la tabla de productos
        productoTable.items = FXCollections.observableArrayList(viewModel.state.value.productos)

        // Configurar las columnas de la tabla
        productoPrecio.cellValueFactory = PropertyValueFactory("precio")
        productoNombre.cellValueFactory = PropertyValueFactory("nombre")
        productoCantidad.cellValueFactory = PropertyValueFactory("stock")
        productoTipo.cellValueFactory = PropertyValueFactory("tipo")
    }

    /**
     * Actualiza los campos de texto con la información del producto seleccionado.
     */
    private fun updateProductoSeleccionado(producto: Producto) {
        nombreProductoSeleccionado.text = producto.nombre
        precioProductoSeleccionado.text = producto.precio.toString()
        stockProductoSeleccionado.text = producto.stock.toString()
        ProductoSeleccionadoTipoComboBox.value = asignarTipo(producto)
    }

    private fun asignarTipo(producto: Producto): String {
        return when (producto.tipo) {
            TipoProducto.BEBIDA -> "Bebida"
            TipoProducto.COMIDA -> "Comida"
            TipoProducto.OTROS -> "Otros"
            null -> "otros"
        }
    }
}
