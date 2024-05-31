package org.example.cine_proyecto_final.viewmodels.administrador

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import org.example.cine_proyecto_final.butacas.errors.ButacaError
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.service.database.ButacaService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class AdministradorGestorButacasViewModel : KoinComponent {

    private val butacaService: ButacaService by inject()

    val state: SimpleObjectProperty<ButacaSelectionState> = SimpleObjectProperty(ButacaSelectionState())

    init {
        logger.debug { "Inicializando AdministradorGestorButacasViewModel" }
        state.value.allButacas = butacaService.findAll().value
        state.value.butacas = butacaService.findAll().value
    }

    /**
     * El objeto observable que contiene las butacas
     */
    data class ButacaSelectionState(
        var allButacas: List<Butaca> = emptyList(),
        var butacas: List<Butaca> = emptyList(),
    )

    fun actualizarButaca(butaca: Butaca): Result<Butaca, ButacaError> {
        return butacaService.update(butaca.id, butaca, null)
            .onSuccess {
                val updatedButacas = state.value.butacas.map {
                    if (it.id == butaca.id) butaca else it
                }
                state.value = state.value.copy(butacas = updatedButacas)
            }
            .onFailure {
                logger.error { "Error al actualizar la butaca: $it" }
            }
    }

    /*fun sowAllButacas() {
        state.value = state.value.copy(
            butacas = state.value.allButacas,
        )
    }*/
}
