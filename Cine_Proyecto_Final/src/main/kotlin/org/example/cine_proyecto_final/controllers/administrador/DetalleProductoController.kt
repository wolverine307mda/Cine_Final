package org.example.cine_proyecto_final.controllers.administrador

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import org.koin.core.component.KoinComponent

class DetalleProductoController: KoinComponent {

    @FXML
    private lateinit var guardarButton: Button

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
    }
}































