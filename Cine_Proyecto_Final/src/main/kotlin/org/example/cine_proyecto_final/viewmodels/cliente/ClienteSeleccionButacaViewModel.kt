package org.example.cine_proyecto_final.viewmodels.cliente

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.application.Platform
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.scene.control.ToggleButton
import org.example.cine_proyecto_final.CineApplication
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.models.Estado
import org.example.cine_proyecto_final.butacas.repository.ButacaRepositoryImpl
import org.example.cine_proyecto_final.butacas.service.database.ButacaService
import org.example.cine_proyecto_final.butacas.service.database.ButacaServiceImpl
import org.example.cine_proyecto_final.butacas.service.storage.ButacaStorage
import org.example.cine_proyecto_final.butacas.validator.ButacaValidator
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.jetbrains.dokka.InternalDokkaApi
import org.jetbrains.dokka.utilities.ServiceLocator.toFile
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

@OptIn(InternalDokkaApi::class)
class ClienteSeleccionButacaViewModel : KoinComponent {

    data class ButacaSeleccionState(
        var butacas : List<Butaca> = emptyList(),
        var butacasSeleccionadas : MutableList<Butaca> = mutableListOf()
    )

    val state : SimpleObjectProperty<ButacaSeleccionState> = SimpleObjectProperty(ButacaSeleccionState())
    private val butacaServicio : ButacaService by inject()
    private val butacaStorageCSV : ButacaStorage by inject()

    init {
        logger.debug { "Inicializando ClienteSeleccionProductosViewModel" }

        // Cargar productos desde un archivo CSV y guardarlos en la base de datos
        val file = CineApplication::class.java.getResource("data/butacas.csv")
        if (file != null) {
            butacaStorageCSV.importFromCsv(file.toFile())
                .onSuccess {
                    it.forEach { butacaServicio.save(it) }
                }
        }
        state.value.butacas = sortButacas(butacaServicio.findAll().value)
    }


    fun addButaca(butaca: Butaca){
        state.value.butacasSeleccionadas = state.value.butacasSeleccionadas.plus(butaca).toMutableList()
    }

    /**
     * Se encarga en ordenar por ID para poder mostrar la matriz
     *
     * @return lista de butacas ordenada por fila y columna
     */
    private fun sortButacas(butacas : List<Butaca>): List<Butaca> {
        val sorted = mutableListOf<Butaca>()
        val letters = listOf('A','B','C','D','E')
        val numbers = listOf(1, 2, 3, 4, 5, 6, 7) // Changed to list of individual numbers

        for (number in numbers) {
            for (letter in letters) {
                butacas.firstOrNull {
                    (it.id[0] == letter && it.id.substring(1).toIntOrNull() == number)
                    &&
                    (sorted.firstOrNull { it.id[0] == letter && it.id.substring(1).toIntOrNull() == number } == null)
                }?.let {
                    sorted.add(it)
                }
            }
        }
        return sorted

    }
}
