package org.example.cine_proyecto_final.viewmodels.cliente

import com.github.michaelbull.result.onSuccess
import org.example.cine_proyecto_final.CineApplication
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.models.Estado
import org.example.cine_proyecto_final.butacas.service.database.ButacaService
import org.example.cine_proyecto_final.butacas.service.storage.ButacaStorage
import org.jetbrains.dokka.InternalDokkaApi
import org.jetbrains.dokka.utilities.ServiceLocator.toFile
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private val logger = logging()

@OptIn(InternalDokkaApi::class)
class ClienteSeleccionButacaViewModel : KoinComponent {

    var butacas : List<Butaca> = emptyList()
    var butacasSeleccionadas : MutableList<Butaca> = mutableListOf()

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
        butacas = getExpectedButacasSorted(butacaServicio.findAll().value)
    }




    /**
     * Se encarga en ordenar por ID para poder mostrar la matriz
     *
     * @return lista de butacas ordenada por fila y columna
     */
    private fun getExpectedButacasSorted(butacas : List<Butaca>): List<Butaca> {
        val sorted = mutableListOf<Butaca>()
        val letters = listOf('A','B','C','D','E')
        val numbers = listOf(1, 2, 3, 4, 5, 6, 7) // Changed to list of individual numbers

        for (number in numbers) {
            for (letter in letters) {
                butacas.firstOrNull { butaca ->
                    //Si la clave tiene dos cifras
                    (butaca.id.length == 2)
                            &&
                    //Si es el elemento que se espera
                    (butaca.id[0] == letter && butaca.id[1] == number.digitToChar())
                    &&
                    (sorted.firstOrNull { it.id[0] == letter && it.id[1] == number.digitToChar() } == null)
                }?.let {
                    sorted.add(it)
                }
            }
        }
        return sorted
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
}
