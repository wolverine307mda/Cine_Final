package org.example.cine_proyecto_final.controllers.cliente

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteSeleccionButacaViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class ClienteSeleccionButacaController: KoinComponent {

    private val dbClient: SqlDelightManager by inject()
    private val viewModel: ClienteSeleccionButacaViewModel by inject()

    @FXML
    private lateinit var email_textField: TextField

    @FXML
    private lateinit var contrasenia_field: PasswordField

    @FXML
    private lateinit var atras_button: Button

    @FXML
    private lateinit var siguiente_button: Button

    //butacas
    /*@FXML
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
    private lateinit var butaca_e7: ToggleButton*/


    @FXML
    fun initialize() {
        logger.debug { "Iniciando pantalla general de Selección de Butacas" }

        atras_button.setOnAction {
            logger.debug { "Botón 'Atrás' presionado" }
            RoutesManager.changeScene(RoutesManager.View.COMPRAR_ENTRADA)
        }
        siguiente_button.setOnAction {
            logger.debug { "Botón 'Siguiente' presionado" }
            RoutesManager.changeScene(RoutesManager.View.SELECCION_PRODUCTOS)
        }
    }
}