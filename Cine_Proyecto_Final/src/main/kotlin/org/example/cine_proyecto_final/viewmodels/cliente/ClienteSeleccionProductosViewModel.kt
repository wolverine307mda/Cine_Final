package org.example.cine_proyecto_final.viewmodels.cliente

import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import org.example.cine_proyecto_final.productos.models.Producto
import org.example.cine_proyecto_final.CineApplication
import org.example.cine_proyecto_final.productos.servicio.database.ProductoServicio
import org.example.cine_proyecto_final.productos.servicio.storage.csv.ProductoStorageCSV
import org.example.cine_proyecto_final.ventas.models.LineaVenta
import org.jetbrains.dokka.InternalDokkaApi
import org.jetbrains.dokka.utilities.ServiceLocator.toFile
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

@OptIn(InternalDokkaApi::class)
class ClienteSeleccionProductosViewModel : KoinComponent{

    private val productoService : ProductoServicio by inject()
    private val productoCsv : ProductoStorageCSV by inject()
    val state: SimpleObjectProperty<ProductSelectionState> = SimpleObjectProperty(ProductSelectionState())

    init {
        logger.debug { "Inicializando ExpedientesViewModel" }
        val file = CineApplication::class.java.getResource("data/productos.csv")
        productoCsv.import(file.toFile())
            .onSuccess {
                it.forEach { productoService.save(it) }
            }
        state.value.productos = productoService.findAll().value // Cargamos los datos de todos los productos
    }

    fun addLinea (producto: Producto) {
        state.value= state.value.copy(
            lineas = state.value.lineas.plus(
                LineaVenta(
                    producto = producto,
                    precio = producto.precio,
                    cantidad = 1
                )
            )
        )
    }

    fun clearList() {
        state.value = state.value.copy (
            lineas = listOf()
        )
    }

    fun removeLinea(linea: LineaVenta) {
        val index = state.value.lineas.indexOf(linea)
        if (index != -1){
            state.value = state.value.copy (
                lineas = state.value.lineas.filter { it.id != linea.id }
            )
        }
    }

    fun updateItem(linea: LineaVenta) {
        state.value.lineas.forEach {
            if (linea.id == it.id) {
                it.cantidad = linea.cantidad
            }
        }
    }

    data class ProductSelectionState(
        var lineas: List<LineaVenta> = emptyList(),
        var productos : List<Producto> = emptyList(),
    )
}