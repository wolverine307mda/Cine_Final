package org.example.cine_proyecto_final.viewmodels.cliente

import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.models.Estado
import org.example.cine_proyecto_final.butacas.service.database.ButacaService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

class ClienteSeleccionButacaViewModel : KoinComponent {

    var butacas : List<Butaca> = emptyList()
    var butacasSeleccionadas : MutableList<Butaca> = mutableListOf()

    private val butacaServicio : ButacaService by inject()

    init {
        logger.debug { "Inicializando ClienteSeleccionProductosViewModel" }
        // Carga las butacas desde la base de datos
        butacas = getExpectedButacasSorted(butacaServicio.findAll().value)
    }

    /**
     * Se encarga en ordenar por ID para poder mostrar la matriz
     *
     * @return lista de butacas ordenada por fila y columna
     */
    private fun getExpectedButacasSorted(butacas: List<Butaca>): List<Butaca> {
        //El orden en el que tienen que estar
        val expectedOrder = listOf(
            "A1", "B1", "C1", "D1", "E1",
            "A2", "B2", "C2", "D2", "E2",
            "A3", "B3", "C3", "D3", "E3",
            "A4", "B4", "C4", "D4", "E4",
            "A5", "B5", "C5", "D5", "E5",
            "A6", "B6", "C6", "D6", "E6",
            "A7", "B7", "C7", "D7", "E7"
        )

        val sortedButacas = mutableListOf<Butaca>()

        for (expectedId in expectedOrder) {
            val butaca = butacas.firstOrNull { it.id == expectedId }
            if (butaca != null) {
                sortedButacas.add(butaca)
            }
        }

        return sortedButacas
    }

    /**
     * Añade una butaca a la lista de butacas seleccionadas
     * @param butaca la butaca que se quiere añadir
     */
    fun addButaca(butaca: Butaca){
        val nuevaButaca = butaca.copy( estado = Estado.OCUPADA)
        butacasSeleccionadas = butacasSeleccionadas.plus(nuevaButaca).toMutableList()
    }

    /**
     * Elimina una butaca de la lista de butacas seleccionadas
     * @param butaca la butaca que se quiere borrar
     */
    fun removeButaca(butaca: Butaca){
        val index = butacasSeleccionadas.indexOfFirst { it.id == butaca.id }
        butacasSeleccionadas.removeAt(index)
    }

    /**
     * Actualiza la lista de butacas
     */
    fun updateButacas(){
        butacas = getExpectedButacasSorted(butacaServicio.findAll().value)
    }
}
