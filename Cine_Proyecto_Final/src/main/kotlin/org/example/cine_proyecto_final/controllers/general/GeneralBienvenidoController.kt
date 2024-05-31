package org.example.cine_proyecto_final.controllers.general

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.sesion.SesionViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class GeneralBienvenidoController : KoinComponent {

    private val sesionViewModel : SesionViewModel by inject()

    lateinit var dbClient: SqlDelightManager

    @FXML
    lateinit var continuar_button: Button

    @FXML
    lateinit var iniciar_sesion_button: Button

    @FXML
    private fun initialize() {
        logger.debug { "iniciando General Bienvenido" }

        continuar_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.COMPRAR_ENTRADA) }
        iniciar_sesion_button.setOnAction {
            if (sesionViewModel.usuario != null) {
                val alert = Alert(AlertType.CONFIRMATION)
                alert.title = "¿Desea cerrar sesión?"
                alert.headerText = """Cuenta actual: ${sesionViewModel.usuario!!.email}
                    |nombre: ${sesionViewModel.usuario!!.nombre} ${sesionViewModel.usuario!!.apellido}
                """.trimMargin()
                alert.contentText = "¿Desea cerrar sesión?"
                alert.showAndWait().ifPresent {
                    if (it == ButtonType.OK) {
                        sesionViewModel.usuario = null
                    }
                }
            } else RoutesManager.initSesionInicio()
        }
    }
}