package org.example.cine_proyecto_final.controllers.administrador

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.stage.FileChooser
import org.example.cine_proyecto_final.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.lighthousegames.logging.logging

private val logger = logging()
class DetalleProductoController: KoinComponent {

    @FXML
    private lateinit var guardarButton: Button

    @FXML
    private lateinit var imagen_button: Button

    @FXML
    private lateinit var idField: TextField

    @FXML
    private lateinit var nombreField: TextField

    @FXML
    private lateinit var precioField: TextField

    @FXML
    private lateinit var stockField: TextField

    @FXML
    private lateinit var tipoCombo: ComboBox<String>

    @FXML
    private fun initialize(){
        val tipos = listOf("BEBIDA", "COMIDA", "OTROS")
        // Inicializar los ComboBox con las opciones
        tipoCombo.items.addAll(tipos)

        guardarButton.setOnAction {
        }

        imagen_button.setOnAction { onImageAction() }

    }

    /**
     * Abre un diálogo para seleccionar una imagen.
     */
    private fun onImageAction() {
        logger.debug { "OnImageAction" }
        FileChooser().run {
            title = "Selecciona una imagen"
            extensionFilters.addAll(FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"))
            showOpenDialog(RoutesManager.activeStage)
        }?.let {
            // Código para manejar la imagen seleccionada
        }
    }

}































