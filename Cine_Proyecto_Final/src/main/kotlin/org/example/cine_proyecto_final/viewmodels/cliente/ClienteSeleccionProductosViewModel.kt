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

/**
 * ViewModel para gestionar la selección de productos por parte del cliente.
 */
@OptIn(InternalDokkaApi::class)
class ClienteSeleccionProductosViewModel : KoinComponent {

    // Inyección de dependencias para ProductoServicio y ProductoStorageCSV
    private val productoService: ProductoServicio by inject()
    private val productoCsv: ProductoStorageCSV by inject()

    // Propiedad de estado que contiene el estado actual de la selección de productos
    val state: SimpleObjectProperty<ProductSelectionState> = SimpleObjectProperty(ProductSelectionState())

    init {
        logger.debug { "Inicializando ClienteSeleccionProductosViewModel" }

        // Cargar productos desde un archivo CSV y guardarlos en la base de datos
        val file = CineApplication::class.java.getResource("data/productos.csv")
        productoCsv.import(file.toFile())
            .onSuccess {
                it.forEach { productoService.save(it) }
            }

        // Cargar todos los productos en el estado
        state.value.productos = productoService.findAll().value
    }

    /**
     * Agrega un producto a la lista de elementos seleccionados.
     *
     * @param producto El producto a agregar.
     */
    fun addLinea(producto: Producto) {
        state.value = state.value.copy(
            lineas = state.value.lineas.plus(
                LineaVenta(
                    producto = producto,
                    precio = producto.precio,
                    cantidad = 1
                )
            )
        )
    }

    /**
     * Limpia la lista de elementos seleccionados.
     */
    fun clearList() {
        state.value = state.value.copy(
            lineas = listOf()
        )
    }

    /**
     * Elimina un elemento específico de la lista de elementos seleccionados.
     *
     * @param linea El elemento a eliminar.
     */
    fun removeLinea(linea: LineaVenta) {
        val index = state.value.lineas.indexOf(linea)
        if (index != -1) {
            state.value = state.value.copy(
                lineas = state.value.lineas.filter { it.id != linea.id }
            )
        }
    }

    /**
     * Actualiza la cantidad de un elemento específico en la lista de elementos seleccionados.
     *
     * @param linea El elemento a actualizar.
     */
    fun updateItem(linea: LineaVenta) {
        state.value.lineas.forEach {
            if (linea.id == it.id) {
                it.cantidad = linea.cantidad
            }
        }
    }

    /**
     * Clase de datos que representa el estado de la selección de productos.
     *
     * @property lineas Lista de líneas de venta.
     * @property productos Lista de productos disponibles.
     */
    data class ProductSelectionState(
        var lineas: List<LineaVenta> = emptyList(),
        var productos: List<Producto> = emptyList(),
    )
}
