package org.example.cine_proyecto_final.ventas.servicio.storage

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.butacas.service.storage.json.ButacaStorageJson
import org.example.cine_proyecto_final.ventas.errors.VentaError
import org.example.cine_proyecto_final.ventas.models.Venta
import org.example.cine_proyecto_final.ventas.servicio.storage.html.VentaStorageHtml
import org.example.cine_proyecto_final.ventas.servicio.storage.json.VentaStorageJson
import java.io.File

class VentaStorageImpl (
    private val ventaStorageJson: VentaStorageJson,
    private val ventaStorageHtml : VentaStorageHtml
) : VentaStorage {

    /**
     * Utiliza la función exportar de VentaStorageJson para exportar las ventas
     * @param file el fichero en el que quieres exportar las ventas
     * @param data la lista que quieres exportar
     * @return el resultado de la función
     * @see VentaStorageJson.export
     */
    override fun exportToJson(file: File, data: List<Venta>): Result<Unit, VentaError> {
        return ventaStorageJson.export(file, data)
    }

    /**
     * Utiliza la función exportar de VentaStorageJson para importar las ventas de un fichero dado
     * @param file el fichero que contiene las ventas
     * @return el resultado de la función
     * @see VentaStorageJson.import
     */
    override fun importFromJson(file: File): Result<List<Venta>, VentaError> {
        return ventaStorageJson.import(file)
    }

    /**
     * Utiliza la función exportar de VentaStorageHtml para exportar la venta en un fichero Html dado
     * @param file el fichero en el que quieres exportar la venta
     * @param venta la venta que quieres exportar
     * @return el resultado de la función
     * @see VentaStorageHtml.export
     */
    override fun exportToHtml(file: File, venta: Venta): Result<Unit, VentaError> {
        return ventaStorageHtml.export(venta, file)
    }
}