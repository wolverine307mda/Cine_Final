package org.example.cine_proyecto_final.viewmodels.cliente

import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import org.example.cine_proyecto_final.productos.models.Producto
import org.example.cine_proyecto_final.CineApplication
import org.example.cine_proyecto_final.productos.models.TipoProducto
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

    /*
     * Inicializamos los datos de los productos con un fichero que deberia estar en el producto
     */
    init {
        logger.debug { "Inicializando ExpedientesViewModel" }
        val file = CineApplication::class.java.getResource("data/productos.csv")
        if (file != null) {
            productoCsv.import(file.toFile())
                .onSuccess {
                    it.forEach { productoService.save(it) }
                }
        }
        state.value.allProductos = productoService.findAll().value // Cargamos los datos de todos los productos
        state.value.productos = state.value.allProductos
    }

    /**
     * Añade una linea de venta a la lista de lineas de venta
     * @param producto el producto que formará parte de la línea de venta
     */
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

    /**
     * Borra todas las lineas de venta
     */
    fun clearList() {
        state.value = state.value.copy (
            lineas = listOf()
        )
    }

    /**
     * Borra una linea de venta de la lista de lineas de venta
     * @param linea la linea de venta que se quiere borrar
     */
    fun removeLinea(linea: LineaVenta) {
        val index = state.value.lineas.indexOf(linea)
        if (index != -1){
            state.value = state.value.copy (
                lineas = state.value.lineas.filter { it.id != linea.id }
            )
        }
    }

    fun filterListByType(type: TipoProducto) {
        state.value = state.value.copy (
            productos = state.value.allProductos.filter { it.tipo == type }
        )
    }

    /**
     * Actualiza la cantidad de un producto de una linea de venta
     * @param linea la linea de venta que ya tiene la cantidad correcta
     */
    fun updateItem(linea: LineaVenta) {
        val lineas = state.value.lineas
        lineas.forEach {
            if (it.id == linea.id) it.cantidad = linea.cantidad
        }
        state.value = state.value.copy (
            lineas = emptyList()
        )
        state.value = state.value.copy (
            lineas = lineas
        )
    }

    fun showAllProducts() {
        state.value = state.value.copy(
            productos = state.value.allProductos,
        )
    }

    /**
     * El objeto observable que contiene las lineas y los productos
     */
    data class ProductSelectionState(
        var allProductos: List<Producto> = emptyList(),
        var lineas: List<LineaVenta> = mutableListOf(),
        var productos: List<Producto> = emptyList(),
    )
}