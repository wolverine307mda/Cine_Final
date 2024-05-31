package org.example.cine_proyecto_final.controllers.administrador

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.stage.Stage
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.models.Estado
import org.example.cine_proyecto_final.butacas.models.Tipo
import org.example.cine_proyecto_final.viewmodels.administrador.AdministradorGestorButacasViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class DetalleButacaController : KoinComponent {

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

    private val viewModel: AdministradorGestorButacasViewModel by inject()

    private var currentButaca: Butaca? = null

    @FXML
    private fun initialize() {
        logger.debug { "Iniciando pantalla de gestión de butacas" }

        initComboBox()

        guardarButton.setOnAction {
            guardarCambios()
        }

        // Cargar los datos de la butaca seleccionada si existe
        currentButaca = AdministradorGestionButacasController.ButacaHolder.selectedButaca
        currentButaca?.let { mostrarDetalle(it) }
    }

    private fun initComboBox() {
        val tipos = listOf("NORMAL", "VIP")
        val estados = listOf("LIBRE", "OCUPADA", "FUERA_DE_SERVICIO")

        tipoCombo.items.addAll(tipos)
        estadoCombo.items.addAll(estados)
    }

    private fun mostrarDetalle(butaca: Butaca) {
        idField.text = butaca.id
        tipoCombo.value = butaca.tipo?.toString()
        estadoCombo.value = butaca.estado.toString()
        precioField.text = butaca.precio.toString()
    }

    private fun guardarCambios() {
        val id = idField.text
        val tipo = tipoCombo.value
        val estado = estadoCombo.value
        val precio = precioField.text.toDoubleOrNull()
        val estadoButaca: Estado

        when(estado){
            "LIBRE" -> estadoButaca = Estado.LIBRE
            "OCUPADA" -> estadoButaca = Estado.OCUPADA
            "FUERA_DE_SERVICIO" -> estadoButaca = Estado.FUERA_DE_SERVICIO
            else -> estadoButaca = Estado.LIBRE
        }

        if (id.isBlank() || tipo == null || estado == null || precio == null) {
            logger.error { "Error: Todos los campos son obligatorios y el precio debe ser un número válido." }
            return
        }


        currentButaca?.let {
            it.tipo = Tipo.valueOf(tipo)
            it.estado = estadoButaca
            it.precio = precio

            logger.debug { "Guardando butaca con ID: $id, Tipo: $tipo, Estado: $estado, Precio: $precio" }

            // Actualizar la butaca en la base de datos
            viewModel.actualizarButaca(it)

            // Cerrar la ventana de detalle después de guardar
            val stage = guardarButton.scene.window as Stage
            stage.close()
        }
    }
}
