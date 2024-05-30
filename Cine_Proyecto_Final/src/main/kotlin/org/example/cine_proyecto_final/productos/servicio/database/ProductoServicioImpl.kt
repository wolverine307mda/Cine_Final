package org.example.cine_proyecto_final.productos.servicio.database

import com.github.michaelbull.result.*
import org.example.productos.errors.ProductoError
import org.example.cine_proyecto_final.productos.models.Producto
import org.example.cine_proyecto_final.productos.repository.ProductosRepository
import org.example.cine_proyecto_final.productos.validador.ProductoValidator
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Implementación del service de productos.
 * @param productosRepositorio El repositorio de productos.
 * @param productoValidador El validador de productos.
 */
class ProductoServicioImpl(
    private var productosRepositorio: ProductosRepository,
    private var productoValidador: ProductoValidator,
) : ProductoServicio {
    /**
     * Guarda un nuevo producto.
     * @param producto El producto a guardar.
     * @return Un resultado que contiene el producto guardado o un error.
     */
    override fun save(producto: Producto) : Result<Producto, ProductoError> {
        logger.debug { "Guardando el producto con id: ${producto.id}" }
        productoValidador.validate(producto) //Para asegurarse que es un producto válido
            .onSuccess {
                productosRepositorio.save(producto)?.let {
                    return Ok(producto)
                }
            }
        return Err(ProductoError.ProductoStorageError("No se pudo guardar el producto con id: ${producto.id}"))
    }

    /**
     * Obtiene todos los productos.
     * @return Un resultado que contiene una lista de productos o un error.
     */
    override fun findAll(): Result<List<Producto>, ProductoError> {
        logger.debug { "Obteniendo todos los productos" }
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
        logger.debug { "Buscando un producto con id: $id" }
        productosRepositorio.findById(id)?.let {
            return Ok(it)
        }
        return Err(ProductoError.ProductoNotFoundError("El producto con ID $id no existe"))
    }

    /**
     * Actualiza un producto existente.
     * @param id El ID del producto a actualizar.
     * @param producto El nuevo estado del producto.
     * @return Un resultado que contiene el producto actualizado o un error si no se pudo actualizar.
     */
    override fun update(id: String, producto: Producto): Result<Producto, ProductoError> {
        logger.debug { "Actualizando el producto con id: $id"}
        productoValidador.validate(producto).onSuccess {
            val updatedProducto = producto.copy(id = id)
            productosRepositorio.update(id,updatedProducto)?.let {
            }
        }
        return Err(ProductoError.ProductoStorageError("No se pudo actualizar el Producto"))
    }

    /**
     * Borra un producto existente.
     * @param id El ID del producto a borrar.
     * @return Un resultado que contiene Unit si se ha borrado bien o un error sino.
     */
    override fun delete(id: String): Result<Unit, ProductoError> {
        logger.debug { "Borrando el producto con id: $id" }
        productosRepositorio.delete(id)?.let {
            return Ok(Unit)
        }
        return Err(ProductoError.ProductoStorageError("No se pudo borrar el producto con id: $id"))
    }
}