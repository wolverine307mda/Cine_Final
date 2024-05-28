package org.example.cine_proyecto_final.controllers.cliente

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.ToggleButton
import javafx.scene.control.cell.PropertyValueFactory
import org.example.cine_proyecto_final.productos.models.Producto
import org.example.cine_proyecto_final.controllers.listCell.ItemCellFactory
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


    @FXML
    private fun initialize() {
        logger.debug { "iniciando pantalla general de Selección de Productos" }
        initDefaultValues()
        initBindings()
        initEventos()
    }

    private fun initEventos() {
        atras_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.SELECCION_BUTACAS) }
        siguiente_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.PROCESAR_COMPRA) }
        limpiarCesta_button.setOnAction { viewModel.clearList() }
        productoTable.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { onTablaSelected(newValue) }
            productoTable.selectionModel.clearSelection()
        }
    }

    private fun onTablaSelected(value : Producto){
        logger.debug { "Añadiendo una nueva linea de venta" }
        if (viewModel.state.value.lineas.firstOrNull { it.producto.id == value.id } == null){
            viewModel.addLinea(value)
        }

    }

    private fun initDefaultValues() {
        lineaList.items = FXCollections.observableArrayList(viewModel.state.value.lineas)
        productoTable.items = FXCollections.observableArrayList(viewModel.state.value.productos)

        productoPrecio.cellValueFactory = PropertyValueFactory("precio")
        productoNombre.cellValueFactory = PropertyValueFactory("nombre")
        productoCantidad.cellValueFactory = PropertyValueFactory("stock")

    }

    private fun initBindings() {
        lineaList.cellFactory = ItemCellFactory()
        viewModel.state.addListener { _, _, newValue ->
            lineaList.items = FXCollections.observableArrayList(newValue.lineas)
            productoTable.items = FXCollections.observableArrayList(newValue.productos)
        }
    }
}