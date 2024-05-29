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
    // Inyección de dependencias para ButacaServicio y ButacaStorageCSV
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
     * Crea una nueva butaca en el sistema.
     *
     * @param butaca La butaca a crear.
     * @return Un [Result] que contiene la butaca creada en caso de éxito o un [ButacaError] en caso de fallo.
     */
    fun nuevaButaca(butaca: Butaca): Result<Butaca, ButacaError> {
        logger.debug { "Añadiendo Butaca" }

        return validador.validate(butaca)
            .onSuccess {
                butacaService.save(butaca)
                logger.debug { "Butaca creada con éxito" }
                state.value = state.value.copy(
                    butacas = state.value.butacas + butaca
                )
            }
            .onFailure {
                logger.debug { "Error al crear butaca: $it" }
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
