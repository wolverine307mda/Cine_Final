package org.example.cine_proyecto_final.viewmodels.administrador

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import org.example.cine_proyecto_final.CineApplication
import org.example.cine_proyecto_final.butacas.errors.ButacaError
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.service.database.ButacaService
import org.example.cine_proyecto_final.butacas.service.storage.csv.ButacaStorageCsv
import org.example.cine_proyecto_final.butacas.validator.ButacaValidator
import org.jetbrains.dokka.InternalDokkaApi
import org.jetbrains.dokka.utilities.ServiceLocator.toFile
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

@OptIn(InternalDokkaApi::class)
class AdministradorGestorButacasViewModel : KoinComponent {
    // Inyección de dependencias para ButacaService y ButacaStorageCSV
    private val butacaService: ButacaService by inject()
    private val butacaCsv: ButacaStorageCsv by inject()
    private val validador: ButacaValidator by inject()

    // Propiedad de estado que contiene el estado actual de las butacas
    val state: SimpleObjectProperty<ButacaSelectionState> = SimpleObjectProperty(ButacaSelectionState())

    init {
        logger.debug { "Inicializando AdministradorGestorButacasViewModel" }

        // Cargar butacas desde un archivo CSV y guardarlas en la base de datos
        val file = CineApplication::class.java.getResource("data/butacas.csv")
        file?.let {
            butacaCsv.import(file.toFile())
                .onSuccess {
                    it.forEach { butacaService.save(it) }
                }
            // Cargar todas las butacas en el estado
            state.value.butacas = butacaService.findAll().value
        }
    }


    /**
     * El objeto observable que contiene las butacas
     */
    data class ButacaSelectionState(
        var allButacas: List<Butaca> = emptyList(),
        var butacas: List<Butaca> = emptyList(),
    )
}
