package org.example.cine_final.productos.servicio.database

import com.github.michaelbull.result.*
import org.example.productos.errors.ProductoError
import org.example.cine_final.productos.models.Producto
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.productos.repository.ProductosRepository
import org.example.cine_proyecto_final.productos.servicio.database.ProductoServicio
import org.example.cine_proyecto_final.productos.validador.ProductoValidador

/**
 * Implementación del service de productos.
 * @param productosRepositorio El repositorio de productos.
 * @param productoValidador El validador de productos.
 * @param config La configuración de la aplicación.
 */
class ProductoServicioImpl(
    private var productosRepositorio: ProductosRepository,
    private var productoValidador: ProductoValidador,
    private var config: AppConfig
) : ProductoServicio {

    /**
     * Guarda un nuevo producto.
     * @param producto El producto a guardar.
     * @return Un resultado que contiene el producto guardado o un error.
     */
    override fun save(producto: Producto) : Result<Producto, ProductoError> {
        productoValidador.validate(producto) //Para esegurarse que es un producto válido
            .onSuccess {
                productosRepositorio.save(producto)?.let {
                    return Ok(producto)
                }
                return Err(ProductoError.ProductoStorageError("El producto no se pudo guardar en la base de datos"))
            }
        return Err(ProductoError.ProductoStorageError("No se pudo guardar el producto con id: ${producto.id}"))
    }

    /**
     * Obtiene todos los productos.
     * @return Un resultado que contiene una lista de productos o un error.
     */
    override fun findAll(): Result<List<Producto>, ProductoError> {
        val result = productosRepositorio.findAll()
        if (result.isNotEmpty()) return Ok(result)
        else return Err(ProductoError.ProductoStorageError("No hay ningún producto en la base de datos"))
    }

    /**
     * Encuentra un producto por su ID.
     * @param id El ID del producto a buscar.
     * @return Un resultado que contiene el producto encontrado o un error si no se encontró ningún producto con el ID dado.
     */
    override fun findById(id: String): Result<Producto, ProductoError> {
        val producto = productosRepositorio.findById(id)
        return if (producto != null) {
            Ok(producto)
        } else {
            Err(ProductoError.ProductoNotFoundError("El producto con ID $id no existe"))
        }
    }

    /**
     * Actualiza un producto existente.
     * @param id El ID del producto a actualizar.
     * @param producto El nuevo estado del producto.
     * @return Un resultado que contiene el producto actualizado o un error si no se pudo actualizar.
     */
    override fun update(id: String, producto: Producto): Result<Producto, ProductoError> {
        val existingProducto = productosRepositorio.findById(id)
        return if (existingProducto != null) {
            val updatedProducto = producto.copy(id = id)
            val result = productosRepositorio.update(id, updatedProducto)
            if (result != null) {
                Ok(updatedProducto)
            } else {
                Err(ProductoError.ProductoStorageError("No se pudo actualizar el Producto"))
            }
        } else {
            Err(ProductoError.ProductoNotFoundError("El producto con ID $id no existe"))
        }
    }

    override fun delete(id: String): Result<Unit, ProductoError> {
        TODO("Not yet implemented")
    }
}