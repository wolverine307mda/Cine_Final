package org.example.cine_proyecto_final.controllers.cliente

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import javafx.scene.control.ToggleButton
import org.example.cine_proyecto_final.butacas.models.Estado
import org.example.cine_proyecto_final.butacas.models.Tipo
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteSeleccionButacaViewModel
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteSeleccionProductosViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class ClienteSeleccionButacaController : KoinComponent {

    private val viewModel: ClienteSeleccionButacaViewModel by inject()
    private val seleccionProductosViewModel : ClienteSeleccionProductosViewModel by inject()

    @FXML
    private lateinit var atras_button: Button

    @FXML
    private lateinit var siguiente_button: Button

    @FXML
    private lateinit var butacasArray: List<ToggleButton>

    @FXML
    fun initialize() {
        logger.debug { "Iniciando pantalla general de Selección de Butacas" }
        reselectButacas()
        atras_button.setOnAction {
            logger.debug { "Botón 'Atrás' presionado" }
            val alert = Alert(AlertType.CONFIRMATION)
            alert.title = "¿Está seguro/a?"
            alert.headerText = "Perderá su seleccion de butacas y productos"
            alert.contentText = "Si vuelve a la pantalla anterior perderá su seleccion, ¿desea continuar?"
            alert.showAndWait().ifPresent {
                if (it == ButtonType.OK) {
                    RoutesManager.changeScene(RoutesManager.View.COMPRAR_ENTRADA)
                }
                seleccionProductosViewModel.clearList()
                viewModel.butacasSeleccionadas = mutableListOf()
            }
        }
        siguiente_button.setOnAction {
            logger.debug { "Botón 'Siguiente' presionado" }
            if (viewModel.butacasSeleccionadas.isEmpty()){
                RoutesManager.showAlertOperacion(
                    mensaje = "No puede seguir sin seleccionar butacas",
                    alerta = Alert.AlertType.ERROR,
                    title = "Error"
                )
            }else RoutesManager.changeScene(RoutesManager.View.SELECCION_PRODUCTOS)
        }
        initBindings()
        actualizarEstadoButacas()
    }

    /**
     * Reselecciona los botones que representan a las butacas que están seleccionadas
     * en la lista de butacas seleccionadas
     */
    private fun reselectButacas() {
        viewModel.butacasSeleccionadas.forEach { butaca ->
            val index = viewModel.butacas.indexOfFirst { it.id == butaca.id }
            butacasArray[index].isSelected = true
        }
    }

    private fun initBindings() {
        butacasArray.forEachIndexed { index, button ->
            button.setOnAction {
                if (button.isSelected) viewModel.addButaca(viewModel.butacas[index])
                else viewModel.removeButaca(viewModel.butacas[index])
            }
        }
    }
    /**
     * Actualiza el color de fondo de los botones dependiendo de la butaca que represente
     */
    private fun actualizarEstadoButacas() {
        viewModel.butacas.forEachIndexed { index,butaca ->
        val toggleButton = butacasArray[index]
            when (butaca.estado) {
                Estado.OCUPADA -> {
                    toggleButton.style = "-fx-background-color: #B22222;"
                    toggleButton.isDisable = true
                }
                Estado.LIBRE -> {
                    when (butaca.tipo) {
                        Tipo.VIP -> {
                            toggleButton.style = "-fx-background-color: #d59c0c;"
                            toggleButton.isDisable = false
                        }
                        Tipo.NORMAL -> {
                            toggleButton.style = "-fx-background-color: #29577c;"
                            toggleButton.isDisable = false
                        }
                        null -> {
                            toggleButton.style = "-fx-background-color: red;"
                            toggleButton.isDisable = true
                        }
                    }
                }
                Estado.FUERA_DE_SERVICIO -> {
                    toggleButton.style = "-fx-background-color: #A9A9A9;"
                    toggleButton.isDisable = true
                }
                else -> {
                    toggleButton.style = "-fx-background-color: #FF6347;" // Rojo para errores
                    toggleButton.isDisable = true
                }
            }
        }
    }
}
