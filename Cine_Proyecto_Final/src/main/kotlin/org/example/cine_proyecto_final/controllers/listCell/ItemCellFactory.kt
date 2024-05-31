package org.example.cine_proyecto_final.controllers.listCell

import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.util.Callback
import org.example.cine_proyecto_final.ventas.models.LineaVenta

class ItemCellFactory : Callback<ListView<LineaVenta>, ListCell<LineaVenta>> {
    override fun call(param: ListView<LineaVenta>): ListCell<LineaVenta> {
        return ItemCell()
    }
}