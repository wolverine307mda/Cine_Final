package org.example.cine_proyecto_final.controllers.administrador

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import org.koin.core.component.KoinComponent
import org.lighthousegames.logging.logging

private val logger = logging()

class DetalleButacaController: KoinComponent {

    @FXML
    private lateinit var idField: TextField

    @FXML
    private lateinit var tipoCombo: ComboBox<String>

    @FXML
    private lateinit var estadoCombo: ComboBox<String>

    @FXML
    private lateinit var precioField: TextField

    @FXML
    private lateinit var guardarButton: Button

    @FXML
    private fun initialize() {
        logger.debug { "Iniciando pantalla de gestión de butacas" }

        initComboBox()

        guardarButton.setOnAction {
            // TODO: guardar los cambios en la base de datos
        }
    }

    private fun initComboBox() {
        // Definir las opciones para los ComboBox
        val tipos = listOf("NORMAL", "VIP")
        val estados = listOf("LIBRE", "OCUPADA", "FUERA_DE_SERVICIO")

        // Inicializar los ComboBox con las opciones
        tipoCombo.items.addAll(tipos)
        estadoCombo.items.addAll(estados)
    }
}
