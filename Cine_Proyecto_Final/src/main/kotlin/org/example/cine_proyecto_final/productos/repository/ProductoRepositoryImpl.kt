package org.example.cine_proyecto_final.productos.repository

import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cuenta.mappers.toLong
import org.example.cine_final.productos.mappers.toProducto
import org.example.cine_final.productos.models.Producto
import org.lighthousegames.logging.logging
import java.time.LocalDateTime

private val logger = logging()

/**
 * Implementación del repositorio de productos que interactúa con la base de datos.
 * @param sqlDelightManager El gestor de SQLDelight para acceder a la base de datos.
 */
class ProductoRepositoryImpl(
    sqlDelightManager: SqlDelightManager
) : ProductosRepository {
    private val db = sqlDelightManager.databaseQueries

    /**
     * Obtiene todos los productos de la base de datos.
     * @return Una lista de productos.
     */
    override fun findAll(): List<Producto> {
        logger.debug { "Buscando todos los productos en la base de datos" }
        if (db.countProductos().executeAsOne() > 0){
            return db.getAllProductos().executeAsList().map {
                it.toProducto()
            }
        }
        return emptyList()
    }

    /**
     * Encuentra un producto por su ID.
     * @param id El ID del producto a buscar.
     * @return El producto encontrado o null si no se encontró ningún producto con el ID dado.
     */
    override fun findById(id: String): Producto? {
        logger.debug { "Buscando un producto con id: $id" }
        if (db.productoExists(id).executeAsOne()){
            return db.getProductoById(id).executeAsOne().toProducto()
        }
        return null
    }

    /**
     * Guarda un nuevo producto en la base de datos.
     * @param producto El producto a guardar.
     * @param ignoreKey Indica si se debe ignorar el ID del producto.
     * @return El producto guardado o null si no se pudo guardar.
     */
    override fun save(producto: Producto, ignoreKey : Boolean) : Producto? {
        logger.debug { "Añadiendo el producto: '${producto.nombre}' al inventario" }
        if (ignoreKey || findById(producto.id) == null){
            db.insertProducto(
                id = producto.id,
                nombre = producto.nombre,
                precio = producto.precio,
                stock = producto.stock.toLong(),
                tipo = producto.tipo.toString(),
                createdAt = producto.createdAt.toString(),
                updatedAt = producto.updatedAt.toString(),
                isDeleted = producto.isDeleted.toLong(),
                imagen = producto.image
            )
            return producto
        }
        return null
    }

    /**
     * Actualiza un producto existente en la base de datos.
     * @param id El ID del producto a actualizar.
     * @param producto El nuevo estado del producto.
     * @return El producto actualizado o null si no se pudo actualizar.
     */
    override fun update(id: String, producto: Producto): Producto? {
        logger.debug { "Actualizando el producto con id: $id"}
        findById(id)?.let {
            val date = LocalDateTime.now()
            db.updateProducto(
                id = id,
                nombre = producto.nombre,
                precio = producto.precio,
                stock = producto.stock.toLong(),
                tipo = producto.tipo.toString(),
                updatedAt = date.toString(),
                imagen = producto.image
            )
            return producto.copy(
                nombre = producto.nombre,
                precio = producto.precio,
                stock = producto.stock,
                tipo = producto.tipo,
                updatedAt = date,
                image = producto.image
            )
        }
        return null
    }

    /**
     * Elimina un producto de la base de datos.
     * @param id El ID del producto a eliminar.
     * @return El producto eliminado o null si no se pudo eliminar.
     */
    override fun delete(id: String): Producto? {
        logger.debug { "Borrando Producto con id: $id" }
        val date = LocalDateTime.now()
        findById(id)?.let {
            db.deleteProducto(id = id, updatedAt = date.toString())
            return it.copy(isDeleted = true, updatedAt = date)
        }
        return null
    }
}