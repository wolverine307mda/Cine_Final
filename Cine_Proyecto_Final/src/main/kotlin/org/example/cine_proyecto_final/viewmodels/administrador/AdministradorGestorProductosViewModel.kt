package org.example.cine_proyecto_final.viewmodels.administrador

import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import org.example.cine_proyecto_final.CineApplication
import org.example.cine_proyecto_final.productos.models.Producto
import org.example.cine_proyecto_final.productos.servicio.database.ProductoServicio
import org.example.cine_proyecto_final.productos.servicio.storage.csv.ProductoStorageCSV
import org.jetbrains.dokka.InternalDokkaApi
import org.jetbrains.dokka.utilities.ServiceLocator.toFile
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

@OptIn(InternalDokkaApi::class)
class AdministradorGestorProductosViewModel : KoinComponent {
    // Inyección de dependencias para ProductoServicio y ProductoStorageCSV
    private val productoService: ProductoServicio by inject()
    private val productoCsv: ProductoStorageCSV by inject()

    // Propiedad de estado que contiene el estado actual de los productos
    val state: SimpleObjectProperty<ProductSelectionState> = SimpleObjectProperty(ProductSelectionState())

    init {
        logger.debug { "Inicializando AdministradorGestorProductosViewModel" }

        // Cargar productos desde un archivo CSV y guardarlos en la base de datos
        val file = CineApplication::class.java.getResource("data/productos.csv")
        file?.let {
            productoCsv.import(file.toFile())
                .onSuccess {
                    it.forEach { productoService.save(it) }
                }

            // Cargar todos los productos en el estado
            state.value.productos = productoService.findAll().value
        }
    }

    // Clase interna para representar el estado de la selección de productos
    data class ProductSelectionState(
        var productos: List<Producto> = emptyList()
    )
}
