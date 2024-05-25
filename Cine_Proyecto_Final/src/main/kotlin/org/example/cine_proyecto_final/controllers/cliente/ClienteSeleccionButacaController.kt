package org.example.cine_proyecto_final.controllers.cliente

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.control.ToggleButton
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteSeleccionButacaViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class ClienteSeleccionButacaController : KoinComponent {

    private val viewModel: ClienteSeleccionButacaViewModel by inject()

    @FXML
    private lateinit var email_textField: TextField

    @FXML
    private lateinit var contrasenia_field: PasswordField

    @FXML
    private lateinit var atras_button: Button

    @FXML
    private lateinit var siguiente_button: Button

    @FXML
    private lateinit var butaca_a1: ToggleButton
    @FXML
    private lateinit var butaca_a2: ToggleButton
    @FXML
    private lateinit var butaca_a3: ToggleButton
    @FXML
    private lateinit var butaca_a4: ToggleButton
    @FXML
    private lateinit var butaca_a5: ToggleButton
    @FXML
    private lateinit var butaca_a6: ToggleButton
    @FXML
    private lateinit var butaca_a7: ToggleButton
    @FXML
    private lateinit var butaca_b1: ToggleButton
    @FXML
    private lateinit var butaca_b2: ToggleButton
    @FXML
    private lateinit var butaca_b3: ToggleButton
    @FXML
    private lateinit var butaca_b4: ToggleButton
    @FXML
    private lateinit var butaca_b5: ToggleButton
    @FXML
    private lateinit var butaca_b6: ToggleButton
    @FXML
    private lateinit var butaca_b7: ToggleButton
    @FXML
    private lateinit var butaca_c1: ToggleButton
    @FXML
    private lateinit var butaca_c2: ToggleButton
    @FXML
    private lateinit var butaca_c3: ToggleButton
    @FXML
    private lateinit var butaca_c4: ToggleButton
    @FXML
    private lateinit var butaca_c5: ToggleButton
    @FXML
    private lateinit var butaca_c6: ToggleButton
    @FXML
    private lateinit var butaca_c7: ToggleButton
    @FXML
    private lateinit var butaca_d1: ToggleButton
    @FXML
    private lateinit var butaca_d2: ToggleButton
    @FXML
    private lateinit var butaca_d3: ToggleButton
    @FXML
    private lateinit var butaca_d4: ToggleButton
    @FXML
    private lateinit var butaca_d5: ToggleButton
    @FXML
    private lateinit var butaca_d6: ToggleButton
    @FXML
    private lateinit var butaca_d7: ToggleButton
    @FXML
    private lateinit var butaca_e1: ToggleButton
    @FXML
    private lateinit var butaca_e2: ToggleButton
    @FXML
    private lateinit var butaca_e3: ToggleButton
    @FXML
    private lateinit var butaca_e4: ToggleButton
    @FXML
    private lateinit var butaca_e5: ToggleButton
    @FXML
    private lateinit var butaca_e6: ToggleButton
    @FXML
    private lateinit var butaca_e7: ToggleButton

    @FXML
    fun initialize() {
        logger.debug { "Iniciando pantalla general de Selección de Butacas" }
        viewModel.buscarButacas(
            onSuccess = { viewModel.actualizarEstadoButacas(::obtenerToggleButtonPorId) },
            onFailure = { logger.debug { "Error al buscar butacas" } }
        )
        atras_button.setOnAction {
            logger.debug { "Botón 'Atrás' presionado" }
            RoutesManager.changeScene(RoutesManager.View.COMPRAR_ENTRADA)
        }
        siguiente_button.setOnAction {
            logger.debug { "Botón 'Siguiente' presionado" }
            RoutesManager.changeScene(RoutesManager.View.SELECCION_PRODUCTOS)
        }
    }

    private fun obtenerToggleButtonPorId(id: String): ToggleButton? {
        return when (id) {
            "A1" -> butaca_a1
            "A2" -> butaca_a2
            "A3" -> butaca_a3
            "A4" -> butaca_a4
            "A5" -> butaca_a5
            "A6" -> butaca_a6
            "A7" -> butaca_a7
            "B1" -> butaca_b1
            "B2" -> butaca_b2
            "B3" -> butaca_b3
            "B4" -> butaca_b4
            "B5" -> butaca_b5
            "B6" -> butaca_b6
            "B7" -> butaca_b7
            "C1" -> butaca_c1
            "C2" -> butaca_c2
            "C3" -> butaca_c3
            "C4" -> butaca_c4
            "C5" -> butaca_c5
            "C6" -> butaca_c6
            "C7" -> butaca_c7
            "D1" -> butaca_d1
            "D2" -> butaca_d2
            "D3" -> butaca_d3
            "D4" -> butaca_d4
            "D5" -> butaca_d5
            "D6" -> butaca_d6
            "D7" -> butaca_d7
            "E1" -> butaca_e1
            "E2" -> butaca_e2
            "E3" -> butaca_e3
            "E4" -> butaca_e4
            "E5" -> butaca_e5
            "E6" -> butaca_e6
            "E7" -> butaca_e7
            else -> null
        }
    }
}
