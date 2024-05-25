package org.example.cine_proyecto_final.viewmodels.cliente

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.application.Platform
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import javafx.scene.control.ToggleButton
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.models.Estado
import org.example.cine_proyecto_final.butacas.repository.ButacaRepositoryImpl
import org.example.cine_proyecto_final.butacas.service.database.ButacaServiceImpl
import org.example.cine_proyecto_final.butacas.validator.ButacaValidator
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

class ClienteSeleccionButacaViewModel : KoinComponent {

    private val dbClient: SqlDelightManager by inject()
    private val butacaValidator: ButacaValidator by inject()
    private val config: AppConfig by inject()
    private val butacaServicio = ButacaServiceImpl(ButacaRepositoryImpl(dbClient), butacaValidator, config)
    private val logger = logging()

    val butacas = SimpleListProperty<Butaca>(FXCollections.observableArrayList())

    fun buscarButacas(onSuccess: () -> Unit, onFailure: () -> Unit) {
        val findAllResult = butacaServicio.findAll()
        findAllResult.onSuccess {
            butacas.setAll(it)
            onSuccess()
        }.onFailure {
            logger.debug { "Error al obtener las butacas" }
            onFailure()
        }
    }

    fun actualizarEstadoButacas(obtenerToggleButtonPorId: (String) -> ToggleButton?) {
        Platform.runLater {
            butacas.forEach { butaca ->
                val toggleButton = obtenerToggleButtonPorId(butaca.id)
                when (butaca.estado) {
                    Estado.OCUPADA -> {
                        toggleButton?.style = "-fx-background-color: blue;"
                        toggleButton?.isDisable = true
                    }
                    Estado.LIBRE -> {
                        toggleButton?.style = "-fx-background-color: green;"
                        toggleButton?.isDisable = false
                    }
                    Estado.FUERA_DE_SERVICIO -> {
                        toggleButton?.style = "-fx-background-color: gray;"
                        toggleButton?.isDisable = true
                    }
                    else -> {
                        toggleButton?.style = "-fx-background-color: red;"
                        toggleButton?.isDisable = true
                    }
                }
            }
        }
    }
}
