package org.example.cine_proyecto_final.controllers.administrador

import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.stage.FileChooser
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val looger = logging()

class AdministradorInicioController: KoinComponent {

    private val dbClient: SqlDelightManager by inject()
    private val viewModel: AdministradorInicioController by inject()

    @FXML
    private lateinit var gestion_productos_button: Button

    @FXML
    private lateinit var gestion_butacas_button: Button

    @FXML
    private lateinit var salir_button: Button

    @FXML
    private lateinit var ver_recaudacion_button: Button

    @FXML
    private lateinit var restaurar_button: Button

    @FXML
    private lateinit var exportar_estado_button: Button

    @FXML
    private fun initialize() {
        looger.debug { "iniciando pantalla de Administrador" }

        gestion_productos_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.ADMIN_PRODUCTOS) }
        gestion_butacas_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.ADMIN_BUTACAS) }
        salir_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.MAIN) }

        ver_recaudacion_button.setOnAction {  }
        restaurar_button.setOnAction { restaurarEstado() }
        exportar_estado_button.setOnAction {  }
    }

    private fun restaurarEstado(){
        looger.debug { "restaurarAction" }
        FileChooser().run {
            title = "Selecciona un archivo"
            extensionFilters.addAll(FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"))
            showOpenDialog(RoutesManager.activeStage)
        }?.let {
        }
    }

    private fun exportarEstado(){
        looger.debug { "exportarAction" }
    }

    private fun verRecaudacion(){
        looger.debug { "verRecaudaciónAction" }
        calcularRecaudacion()
        showAlertOperacion("Recaudación", "La recaudación Total es de: ")
    }

    private fun calcularRecaudacion() {
        TODO("Not yet implemented")
    }

    private fun showAlertOperacion(title: String, mensaje: String, alerta: Alert.AlertType = Alert.AlertType.INFORMATION) {
        val alert = Alert(alerta)
        alert.title = title
        alert.headerText = null
        alert.contentText = mensaje
        alert.showAndWait()
    }
}