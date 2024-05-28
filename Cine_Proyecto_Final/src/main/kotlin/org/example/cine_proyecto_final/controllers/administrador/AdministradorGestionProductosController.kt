package org.example.cine_proyecto_final.controllers.administrador

import javafx.fxml.FXML
import javafx.scene.control.Button
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.routes.RoutesManager.View
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val looger = logging()

class AdministradorGestionProductosController: KoinComponent {

    private val dbClient: SqlDelightManager by inject()
    private val viewModel: AdministradorGestionProductosController by inject()

    @FXML
    private lateinit var atras_button: Button

    @FXML
    private lateinit var editarButton: Button

    @FXML
    private lateinit var nuevoButton: Button

    @FXML
    private lateinit var eliminarButton: Button

    @FXML
    private fun initialize() {
        looger.debug { "iniciando pantalla de gestion de butacas" }
        atras_button.setOnAction { RoutesManager.changeScene(View.ADMIN_INICIO) }
        editarButton.setOnAction { RoutesManager.initDetalle(View.DETALLE_PRODUCTO, "Editar Producto") }
        nuevoButton.setOnAction { RoutesManager.initDetalle(View.DETALLE_PRODUCTO,"Nueva Producto") }
    }
}