package org.example.cine_proyecto_final.controllers.administrador

import com.github.michaelbull.result.onSuccess
import javafx.collections.FXCollections
import javafx.collections.transformation.FilteredList
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.models.Estado
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

    // Tabla butacas con sus respectivas columnas
    @FXML
    private lateinit var tableButacas: TableView<Butaca>
    @FXML
    private lateinit var idButacaColumna: TableColumn<Butaca, String>
    @FXML
    private lateinit var tipoColumna: TableColumn<Butaca, String>
    @FXML
    private lateinit var precioColum: TableColumn<Butaca, Double>
    @FXML
    private lateinit var estadoColum: TableColumn<Butaca, String>

    // Información de la butaca seleccionada
    @FXML
    private lateinit var labelId: Label
    @FXML
    private lateinit var IdButacaSeleccionada: TextField
    @FXML
    private lateinit var tipoComboBox: ComboBox<String>
    @FXML
    private lateinit var butacaSeleccionadaEstadoCombox: ComboBox<String>
    @FXML
    private lateinit var precioTextField: TextField

    // Ordenar la tabla por estado y tipo
    private lateinit var estado: ToggleGroup
    @FXML
    private lateinit var libreButton: ToggleButton
    @FXML
    private lateinit var fueraServicioButton: ToggleButton
    @FXML
    private lateinit var ocupadaButton: ToggleButton
    private lateinit var tipo: ToggleGroup
    @FXML
    private lateinit var normalButton: ToggleButton
    @FXML
    private lateinit var vipButton: ToggleButton

    @FXML
    private lateinit var butacasLibresCountField: TextField
    @FXML
    private lateinit var butacasNoDisponiblesCountField: TextField

    // Filtrado de la butaca
    private lateinit var filteredData: FilteredList<Butaca>

    @FXML
    private fun initialize() {
        logger.debug { "Iniciando pantalla de gestión de butacas" }

        // Configuración de acciones de botones
        atrasButton.setOnAction { RoutesManager.changeScene(View.ADMIN_INICIO) }

        // Inicializar valores por defecto
        initDefaultValues()
        initBindings()

        // Añadir listener a la tabla para manejar la selección de butacas
        tableButacas.selectionModel.selectedItemProperty().addListener { _, _, selectedButaca ->
            selectedButaca?.let { updateButacaSeleccionada(it) }
        }

        // Agrupar botones
        estado = ToggleGroup()
        libreButton.toggleGroup = estado
        ocupadaButton.toggleGroup = estado
        fueraServicioButton.toggleGroup = estado

        tipo = ToggleGroup()
        normalButton.toggleGroup = tipo
        vipButton.toggleGroup = tipo

        // Configurar búsqueda y filtrado
        configureSearchAndFilter()

        // Configurar acción del botón editar
        editarButton.setOnAction { editarButacaSeleccionada() }
    }

    /**
     * Inicializa los valores por defecto en la tabla de butacas.
     */
    private fun initDefaultValues() {
        // Establecer los ítems de la tabla de butacas
        filteredData = FilteredList(FXCollections.observableArrayList(viewModel.state.value.butacas))
        tableButacas.items = filteredData

        // Configurar las columnas de la tabla
        idButacaColumna.cellValueFactory = PropertyValueFactory("id")
        tipoColumna.cellValueFactory = PropertyValueFactory("tipo")
        estadoColum.cellValueFactory = PropertyValueFactory("estado")
        precioColum.cellValueFactory = PropertyValueFactory("precio")
    }

    private fun initBindings() {
        viewModel.state.addListener { _, _, newValue ->
            if (newValue.butacas != tableButacas.items) {
                filteredData = FilteredList(FXCollections.observableArrayList(newValue.butacas))
                tableButacas.items = filteredData
                calcularButacas() // Asegúrate de recalcular el número de butacas libres y no disponibles
            }
        }
    }


    /**
     * Configura la búsqueda y el filtrado de butacas.
     */
    private fun configureSearchAndFilter() {
        buscarPorIdField.textProperty().addListener { _, _, _ -> filterButacas() }
    }

    /**
     * Filtra las butacas en la tabla basadas en el texto de búsqueda.
     */
    private fun filterButacas() {
        val searchText = buscarPorIdField.text.lowercase().trim()

        filteredData.setPredicate { butaca ->
            butaca.id.lowercase().contains(searchText)
        }
    }

    /**
     * Actualiza los campos de texto con la información de la butaca seleccionada.
     */
    private fun updateButacaSeleccionada(butaca: Butaca) {
        labelId.text = butaca.id
        IdButacaSeleccionada.text = butaca.id
        precioTextField.text = butaca.precio.toString()
        tipoComboBox.value = butaca.tipo.toString()
        butacaSeleccionadaEstadoCombox.value = butaca.estado.toString()
    }

    /**
     * Maneja la acción de editar la butaca seleccionada.
     */
    private fun editarButacaSeleccionada() {
        val selectedButaca = tableButacas.selectionModel.selectedItem
        if (selectedButaca != null) {
            ButacaSeleccionada.selectedButaca = selectedButaca
            RoutesManager.initDetalle(View.DETALLE_BUTACA, "Editar Butaca")

            // Llama al método actualizarButaca del viewModel
            val result = viewModel.actualizarButaca(selectedButaca)
            result.onSuccess {
                // Actualiza la tabla de butacas
                viewModel.state.value = viewModel.state.value.copy(butacas = viewModel.state.value.butacas.map { if (it.id == selectedButaca.id) selectedButaca else it })
            }
        } else {
            showAlertOperacion("Error de edición", "No se ha seleccionado ninguna butaca", Alert.AlertType.ERROR)
        }
    }


    private fun calcularButacas() {
        val butacas = viewModel.state.value.allButacas
        val butacasLibres = butacas.count { it.estado == Estado.LIBRE }
        val butacasNoDisponibles = butacas.count { it.estado != Estado.LIBRE }

        butacasLibresCountField.text = butacasLibres.toString()
        butacasNoDisponiblesCountField.text = butacasNoDisponibles.toString()
    }

    private fun showAlertOperacion(title: String, mensaje: String, alerta: Alert.AlertType = Alert.AlertType.INFORMATION) {
        val alert = Alert(alerta)
        alert.title = title
        alert.headerText = null
        alert.contentText = mensaje
        alert.showAndWait()
    }

    object ButacaSeleccionada {
        var selectedButaca: Butaca? = null
    }
}