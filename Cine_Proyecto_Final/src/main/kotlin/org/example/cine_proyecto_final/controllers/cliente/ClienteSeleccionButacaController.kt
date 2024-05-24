package org.example.cine_proyecto_final.controllers.cliente

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.control.ToggleButton
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.models.Estado
import org.example.cine_proyecto_final.butacas.repository.ButacaRepositoryImpl
import org.example.cine_proyecto_final.butacas.service.database.ButacaServiceImpl
import org.example.cine_proyecto_final.butacas.validator.ButacaValidator
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteSeleccionButacaViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class ClienteSeleccionButacaController : KoinComponent {

    private val dbClient: SqlDelightManager by inject()
    private val viewModel: ClienteSeleccionButacaViewModel by inject()
    /*private val butacaValidator: ButacaValidator by inject()
    private val config: AppConfig by inject()
    private lateinit var butacaServicio: ButacaServiceImpl

    private var butacas: List<Butaca>? = null*/

    @FXML
    private lateinit var email_textField: TextField

    @FXML
    private lateinit var contrasenia_field: PasswordField

    @FXML
    private lateinit var atras_button: Button

    @FXML
    private lateinit var siguiente_button: Button

    // butacas
    /*@FXML
    private var butaca_a1: ToggleButton? = null
    @FXML
    private var butaca_a2: ToggleButton? = null
    @FXML
    private var butaca_a3: ToggleButton? = null
    @FXML
    private var butaca_a4: ToggleButton? = null
    @FXML
    private var butaca_a5: ToggleButton? = null
    @FXML
    private var butaca_a6: ToggleButton? = null
    @FXML
    private var butaca_a7: ToggleButton? = null
    @FXML
    private var butaca_b1: ToggleButton? = null
    @FXML
    private var butaca_b2: ToggleButton? = null
    @FXML
    private var butaca_b3: ToggleButton? = null
    @FXML
    private var butaca_b4: ToggleButton? = null
    @FXML
    private var butaca_b5: ToggleButton? = null
    @FXML
    private var butaca_b6: ToggleButton? = null
    @FXML
    private var butaca_b7: ToggleButton? = null
    @FXML
    private var butaca_c1: ToggleButton? = null
    @FXML
    private var butaca_c2: ToggleButton? = null
    @FXML
    private var butaca_c3: ToggleButton? = null
    @FXML
    private var butaca_c4: ToggleButton? = null
    @FXML
    private var butaca_c5: ToggleButton? = null
    @FXML
    private var butaca_c6: ToggleButton? = null
    @FXML
    private var butaca_c7: ToggleButton? = null
    @FXML
    private var butaca_d1: ToggleButton? = null
    @FXML
    private var butaca_d2: ToggleButton? = null
    @FXML
    private var butaca_d3: ToggleButton? = null
    @FXML
    private var butaca_d4: ToggleButton? = null
    @FXML
    private var butaca_d5: ToggleButton? = null
    @FXML
    private var butaca_d6: ToggleButton? = null
    @FXML
    private var butaca_d7: ToggleButton? = null
    @FXML
    private var butaca_e1: ToggleButton? = null
    @FXML
    private var butaca_e2: ToggleButton? = null
    @FXML
    private var butaca_e3: ToggleButton? = null
    @FXML
    private var butaca_e4: ToggleButton? = null
    @FXML
    private var butaca_e5: ToggleButton? = null
    @FXML
    private var butaca_e6: ToggleButton? = null
    @FXML
    private var butaca_e7: ToggleButton? = null*/

    @FXML
    fun initialize() {
        logger.debug { "Iniciando pantalla general de Selección de Butacas" }
        /*butacaServicio = ButacaServiceImpl(
            ButacaRepositoryImpl(dbClient),
            butacaValidator,
            config
        )*/
        //buscarButacas()
        atras_button.setOnAction {
            logger.debug { "Botón 'Atrás' presionado" }
            RoutesManager.changeScene(RoutesManager.View.COMPRAR_ENTRADA)
        }
        siguiente_button.setOnAction {
            logger.debug { "Botón 'Siguiente' presionado" }
            RoutesManager.changeScene(RoutesManager.View.SELECCION_PRODUCTOS)
        }
    }

    /*private fun buscarButacas() {
        val findAllResult = butacaServicio.findAll()
        findAllResult.onSuccess { butacas = it }
            .onFailure { logger.debug { "Error al obtener las butacas" } }
        actualizarEstadoButacas()
    }

    private fun actualizarEstadoButacas() {
        Platform.runLater {
            butacas?.forEach { butaca ->
                val toggleButton = obtenerToggleButtonPorId(butaca.id)
                when (butaca.estado) {
                    Estado.OCUPADA -> toggleButton?.style = "-fx-background-color: blue;"
                    Estado.LIBRE -> toggleButton?.style = "-fx-background-color: green;"
                    Estado.FUERA_DE_SERVICIO -> toggleButton?.style = "-fx-background-color: gray;"
                    else -> toggleButton?.style = "-fx-background-color: red;"
                }
            }
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
    }*/
}
