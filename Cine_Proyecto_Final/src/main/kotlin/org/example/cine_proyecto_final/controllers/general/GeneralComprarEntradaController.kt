package org.example.cine_proyecto_final.controllers.general

import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.text.Text
import javafx.stage.FileChooser
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteSeleccionButacaViewModel
import org.example.cine_proyecto_final.viewmodels.sesion.SesionViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class GeneralComprarEntradaController : KoinComponent {

    private val seleccionButacasViewModel : ClienteSeleccionButacaViewModel by inject()
    private val sesionViewModel : SesionViewModel by inject()

    @FXML
    private lateinit var duracionLabel: Label

    @FXML
    private lateinit var directorLabel: Label

    @FXML
    private lateinit var actoresLabel: Label

    @FXML
    private lateinit var sinopsisText: Text

    @FXML
    private lateinit var nombreTextField: TextField

    @FXML
    private lateinit var devolver_entrada_button: Button

    @FXML
    private lateinit var iniciar_sesion_button: Button

    @FXML
    private lateinit var comprar_entrada_button: Button

    @FXML
    private lateinit var atras_button: Button

    @FXML
    private fun initialize() {
        logger.debug { "iniciando pantalla general de comprar entrada" }

        devolver_entrada_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.ADMIN_INICIO)/*devolverEntradaAction()*/ }
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
        comprar_entrada_button.setOnAction {
            if (seleccionButacasViewModel.butacas.size != 35) RoutesManager.showAlertOperacion(
                alerta = Alert.AlertType.ERROR,
                title = "Error",
                mensaje = "El cine se encuentra cerrado actualmente"
            )else RoutesManager.changeScene(RoutesManager.View.SELECCION_BUTACAS)
        }
        atras_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.MAIN) }

        configurarDatosPelicula()
    }

    private fun configurarDatosPelicula() {
        duracionLabel.text = "DURACION"
        directorLabel.text = "DIRECTOR/ES"
        actoresLabel.text = "ACTORES"
        sinopsisText.text = "Lobezno se recupera de sus heridas cuando se cruza con el bocazas, Deadpool, que ha viajado en el tiempo para curarlo con la esperanza de hacerse amigos y formar un equipo para acabar con un enemigo común."
    }

    private fun devolverEntradaAction() {
        logger.debug { "selecciona el archivo para devolver entrada" }
        FileChooser().run {
            title = "Selecciona la entrada"
            extensionFilters.addAll(FileChooser.ExtensionFilter("HTML","*.html"))
            showOpenDialog(RoutesManager.activeStage)
        }?.let {
        }
    }
}