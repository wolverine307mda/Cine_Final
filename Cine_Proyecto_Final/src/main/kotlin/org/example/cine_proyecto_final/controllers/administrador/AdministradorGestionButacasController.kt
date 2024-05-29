package org.example.cine_proyecto_final.controllers.administrador

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.routes.RoutesManager.View
import org.example.cine_proyecto_final.viewmodels.administrador.AdministradorGestorButacasViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class AdministradorGestionButacasController : KoinComponent {

    private val viewModel: AdministradorGestorButacasViewModel by inject()

    @FXML
    private lateinit var atrasButton: Button

    @FXML
    private lateinit var editarButton: Button

    @FXML
    private lateinit var buscarPorIdField: TextField

    @FXML
    private lateinit var tableButacas: TableView<Butaca>

    @FXML
    private lateinit var idButacaColumna: TableColumn<Butaca, String>
    @FXML
    private lateinit var tipoColumna: TableColumn<Butaca, String>
    @FXML
    private lateinit var precioColum: TableColumn<Butaca, Int>
    @FXML
    private lateinit var estadoColum: TableColumn<Butaca, String>

    // Información de la butaca seleccionada
    @FXML
    private lateinit var labelId: Label
    @FXML
    private lateinit var tipoComboBox: ComboBox<String>
    @FXML
    private lateinit var precioTextField: TextField


    @FXML
    private fun initialize() {
        logger.debug { "Iniciando pantalla de gestión de butacas" }

        // Configuración de acciones de botones
        atrasButton.setOnAction { RoutesManager.changeScene(View.ADMIN_INICIO) }
        editarButton.setOnAction { RoutesManager.initDetalle(View.DETALLE_BUTACA, "Editar Butaca") }

        // Inicializar valores por defecto
        initDefaultValues()
        initBindings()

        // Añadir listener a la tabla para manejar la selección de butacas
        tableButacas.selectionModel.selectedItemProperty().addListener { _, _, selectedButaca ->
            selectedButaca?.let { updateButacaSeleccionada(it) }
        }
    }

    /**
     * Inicializa los valores por defecto en la tabla de butacas.
     */
    private fun initDefaultValues() {
        // Establecer los ítems de la tabla de butacas
        tableButacas.items = FXCollections.observableArrayList(viewModel.state.value.butacas)

        // Configurar las columnas de la tabla
        idButacaColumna.cellValueFactory = PropertyValueFactory("id")
        tipoColumna.cellValueFactory = PropertyValueFactory("tipo")
        estadoColum.cellValueFactory = PropertyValueFactory("estado")
        precioColum.cellValueFactory = PropertyValueFactory("precio")
    }

    private fun initBindings() {
        viewModel.state.addListener { _, _, newValue ->
            if (newValue.butacas != tableButacas.items) {
                tableButacas.items = FXCollections.observableArrayList(newValue.butacas)
            }
        }
    }

    /**
     * Actualiza los campos de texto con la información de la butaca seleccionada.
     */
    private fun updateButacaSeleccionada(butaca: Butaca) {
        labelId.text = butaca.id
        precioTextField.text = butaca.precio.toString()
        tipoComboBox.value = butaca.tipo.toString()
        editarButton.setOnAction { RoutesManager.initDetalle(View.DETALLE_BUTACA, "Editar Butaca") }
    }
}
