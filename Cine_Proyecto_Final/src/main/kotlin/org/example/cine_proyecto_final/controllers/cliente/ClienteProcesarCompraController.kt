package org.example.cine_proyecto_final.controllers.cliente

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextField
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteProcesarCompraViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class ClienteProcesarCompraController: KoinComponent {

    private val dbClient: SqlDelightManager by inject()
    private val viewModel: ClienteProcesarCompraViewModel by inject()

    @FXML
    private lateinit var atras_button: Button

    @FXML
    private lateinit var nombre_field: TextField

    @FXML
    private lateinit var email_field: TextField

    @FXML
    private lateinit var info_pelicula: TextField

    @FXML
    private lateinit var tarjet_credito_field: TextField

    @FXML
    private lateinit var precio_total_field: TextField

    @FXML
    fun initialize(){
        logger.debug { "iniciando pantalla de Procesar Compra" }

        atras_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.SELECCION_PRODUCTOS) }
    }
}