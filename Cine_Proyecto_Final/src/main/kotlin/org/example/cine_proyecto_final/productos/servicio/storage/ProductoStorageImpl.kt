package org.example.cine_proyecto_final.productos.servicio.storage

import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.productos.models.Producto
import org.example.cine_final.productos.servicio.storage.ProductoStorage
import org.example.cine_proyecto_final.productos.servicio.storage.csv.ProductoStorageCSV
import org.example.cine_proyecto_final.productos.servicio.storage.json.ProductoStorageJson
import org.example.productos.errors.ProductoError
import java.io.File

class ProductoStorageImpl(
    private val productoStorageCSV: ProductoStorageCSV,
    private val productoStorageJson: ProductoStorageJson,
) : ProductoStorage{

    /**
     * Exporta una lista de productos a un archivoJSON.
     *
     * @return Un Result que contiene un Unit si se ha realizado bien o un error si no se han podido exportar.
     * @param productos los productos que se quieren guardar en la base de datos
     * @param file el fichero en el que se quieren guardar los productos
     */
    override fun exportToJson(productos : List<Producto>, file : File) : Result<Unit, ProductoError> {
        return productoStorageJson.export(file, productos)
    }

    /**
     * Importa una lista de productos desde un archivo JSON.
     *
     * @return Un Result que contiene una lista de productos si se ha realizado bien o un error si no se han podido exportar.
     * @param file el fichero Json que contiene una lista de productos
     */
    override fun importFromJson(file : File) : Result<List<Producto>, ProductoError> {
        return productoStorageJson.import(file)
    }

    /**
     * Importa una lista de productos desde un archivo CSV.
     *
     * @return Un Result que contiene una lista de productos si se ha realizado bien o un error si no se han podido exportar.
     * @param file el fichero CSV que contiene una lista de productos
     */
    override fun importFromCSV(file : File) : Result<List<Producto>, ProductoError> {
        return productoStorageCSV.import(file)
    }
}