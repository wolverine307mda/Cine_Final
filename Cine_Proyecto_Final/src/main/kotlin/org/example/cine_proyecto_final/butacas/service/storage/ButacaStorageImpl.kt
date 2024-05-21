package org.example.cine_proyecto_final.butacas.service.storage

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.butacas.errors.ButacaError
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.service.storage.csv.ButacaStorageCsv
import org.example.cine_proyecto_final.butacas.service.storage.json.ButacaStorageJson
import java.io.File

class ButacaStorageImpl(
    private val butacaStorageCsv : ButacaStorageCsv,
    private val butacaStorageJson: ButacaStorageJson
) : ButacaStorage{

    /**
     * Utiliza la función exportar de ButacaStorageJson para esportar las butacas
     * @param file el fichero en el que quieres exportar las butacas
     * @param data la lista que quieres exportar
     * @return el resultado de la función
     * @see ButacaStorageJson.export
     */
    override fun exportToJson(file : File, data : List<Butaca>): Result<Unit, ButacaError> {
        return butacaStorageJson.export(file, data)
    }

    /**
     * Utiliza la función importar de ButacaStorageJson para importar las butacas que están en el fichero
     * @param file el fichero del que quieres importar las butacas
     * @return el resultado de la función
     * @see ButacaStorageJson.import
     */
    override fun importFromJson(file: File): Result<List<Butaca>, ButacaError> {
        return butacaStorageJson.import(file)
    }

    /**
     * Utiliza la función importar de ButacaStorageCsv para importar las butacas que están en el fichero
     * @param file el fichero del que quieres importar las butacas
     * @return el resultado de la función
     * @see ButacaStorageCsv.import
     */
    override fun importFromCsv(file : File): Result<List<Butaca>, ButacaError> {
        return butacaStorageCsv.import(file)
    }
}