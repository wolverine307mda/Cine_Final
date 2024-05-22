package org.example.cine_proyecto_final.cuentas.service.storage

import com.github.michaelbull.result.Result
import cuenta.errors.CuentaError
import org.example.cine_final.cuentas.servicio.storage.CuentaStorage
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_final.cuentas.servicio.storage.json.CuentaStorageJson
import java.io.File

class CuentaStorageImpl(
    private val json: CuentaStorageJson
) : CuentaStorage {

    /**
     * Utiliza la función exportar de CuentaStorageJson para exportar las cuentas
     * @param file el fichero en el que quieres exportar las cuentas
     * @param list la lista que quieres exportar
     * @return el resultado de la función
     * @see CuentaStorageJson.export
     */
    override fun export(list : List<Cuenta>, file: File): Result<Unit, CuentaError> {
        return json.export(list,file)
    }

    /**
     * Utiliza la función importar de CuentaStorageJson para importar las cuentas
     * @param file el fichero en el que quieres importar las cuentas
     * @return el resultado de la función
     * @see CuentaStorageJson.import
     */
    override fun import(file: File): Result<List<Cuenta>, CuentaError> {
        return json.import(file)
    }
}