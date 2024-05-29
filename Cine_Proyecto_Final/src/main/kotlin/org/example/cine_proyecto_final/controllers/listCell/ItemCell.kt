package org.example.cine_proyecto_final.controllers.listCell

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import org.example.cine_proyecto_final.CineApplication
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.ventas.models.LineaVenta
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteSeleccionProductosViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.IOException


class ItemCell : ListCell<LineaVenta>(), KoinComponent{

    private val viewModel : ClienteSeleccionProductosViewModel by inject()

    @FXML
    lateinit var name: Label

    @FXML
    lateinit var amount: Label

    @FXML
    lateinit var more : Button

    @FXML
    lateinit var less : Button

    @FXML
    lateinit var delete : Button

    @FXML
    fun initialize() {
        more.setOnAction {
            item?.let {
                if (it.cantidad+1 <= item.producto.stock){
                    it.cantidad += 1
                    amount.text = it.cantidad.toString()
                    viewModel.updateItem(it)
                }
            }
        }
        less.setOnAction {
            item?.let {
                it.cantidad -= 1
                if (it.cantidad == 0) viewModel.removeLinea(it)
                else {
                    amount.text = it.cantidad.toString()
                    viewModel.updateItem(it)
                }
            }
        }
        delete.setOnAction {
            item?.let {
                viewModel.removeLinea(it)
            }
        }
    }

    init {
        loadFXML()
    }

    private fun loadFXML() {
        try {
            val loader = FXMLLoader(CineApplication::class.java.getResource("views/item.fxml"))
            loader.setController(this)
            loader.setRoot(this)
            loader.load<Any>()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    override fun updateItem(linea : LineaVenta?, empty: Boolean) {
        super.updateItem(linea, empty)
        if (linea == null || empty) {
            text = null
            contentDisplay = ContentDisplay.TEXT_ONLY
        } else {
            name.text = linea.producto.nombre
            amount.text = linea.cantidad.toString()
            contentDisplay = ContentDisplay.GRAPHIC_ONLY
        }
    }
}