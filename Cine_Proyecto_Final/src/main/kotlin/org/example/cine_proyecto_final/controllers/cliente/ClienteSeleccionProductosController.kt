package org.example.cine_proyecto_final.controllers.cliente

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import org.example.cine_proyecto_final.controllers.listCell.ItemCellFactory
import org.example.cine_proyecto_final.productos.models.Producto
import org.example.cine_proyecto_final.productos.models.TipoProducto
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.ventas.models.LineaVenta
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteSeleccionProductosViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class ClienteSeleccionProductosController : KoinComponent {

    private val viewModel: ClienteSeleccionProductosViewModel by inject()

    @FXML
    private lateinit var atras_button: Button

    @FXML
    private lateinit var siguiente_button: Button

    @FXML
    private lateinit var limpiarCesta_button: Button

    @FXML
    private lateinit var otros_button: ToggleButton

    @FXML
    private lateinit var bibida_button: ToggleButton

    @FXML
    private lateinit var comida_button: ToggleButton

    @FXML
    private lateinit var lineaList : ListView<LineaVenta>

    @FXML
    private lateinit var productoTable : TableView<Producto>

    @FXML
    private lateinit var productoCantidad : TableColumn<Int,Producto>

    @FXML
    private lateinit var productoNombre : TableColumn<String,Producto>

    @FXML
    private lateinit var productoPrecio : TableColumn<Double,Producto>

    private lateinit var toggleGroup: ToggleGroup

    @FXML
    private fun initialize() {
        logger.debug { "iniciando pantalla general de Selección de Productos" }
        initDefaultValues()
        initBindings()
        initEventos()
        toggleGroup = ToggleGroup()
        comida_button.toggleGroup = toggleGroup
        bibida_button.toggleGroup = toggleGroup
        otros_button.toggleGroup = toggleGroup
    }

    /**
     * Inicia los eventos que queremos que ocurran
     */
    private fun initEventos() {
        atras_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.SELECCION_BUTACAS) }
        siguiente_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.PROCESAR_COMPRA)
        logger.debug { "${viewModel.state.value.lineas[0].cantidad}" } }

        limpiarCesta_button.setOnAction { viewModel.clearList() }
        otros_button.setOnAction {
            viewModel.showAllProducts()
            if (otros_button.isSelected) viewModel.filterListByType(TipoProducto.OTROS)
        }
        bibida_button.setOnAction {
            viewModel.showAllProducts()
            if (bibida_button.isSelected) viewModel.filterListByType(TipoProducto.BEBIDA)
        }
        comida_button.setOnAction {
            viewModel.showAllProducts()
            if (comida_button.isSelected) viewModel.filterListByType(TipoProducto.COMIDA)
        }
        productoTable.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { onTablaSelected(newValue) }
            productoTable.selectionModel.clearSelection()
        }
    }

    /**
     * Añade una nueva linea de venta a la cesta o modifica una existente
     */
    private fun onTablaSelected(value : Producto){
        logger.debug { "Añadiendo una nueva linea de venta" }
        val existingLinea = viewModel.state.value.lineas.firstOrNull { it.producto.id == value.id }
        if (existingLinea == null){
            viewModel.addLinea(value)
        }else {
            existingLinea.cantidad += 1
            viewModel.updateItem(existingLinea)
        }

    }

    /**
     * Inicializa los valores por defecto
     */
    private fun initDefaultValues() {
        lineaList.items = FXCollections.observableArrayList(viewModel.state.value.lineas)
        productoTable.items = FXCollections.observableArrayList(viewModel.state.value.productos)

        productoPrecio.cellValueFactory = PropertyValueFactory("precio")
        productoNombre.cellValueFactory = PropertyValueFactory("nombre")
        productoCantidad.cellValueFactory = PropertyValueFactory("stock")

    }

    /**
     * Inicializa los bindings
     */
    private fun initBindings() {
        lineaList.cellFactory = ItemCellFactory()
        viewModel.state.addListener { _, _, newValue ->
            lineaList.items = FXCollections.observableArrayList(newValue.lineas)

            if (newValue.productos != productoTable.items){
                productoTable.items = FXCollections.observableArrayList(newValue.productos)
            }
        }
    }
}