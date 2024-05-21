package org.example.cine_proyecto_final.ventas.respositorio

import org.example.cine_proyecto_final.butacas.repository.ButacaRepository
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cuenta.mappers.toLong
import org.example.cine_proyecto_final.cuentas.repository.CuentaRepository
import org.example.cine_proyecto_final.productos.repository.ProductosRepository
import org.example.cine_proyecto_final.ventas.mappers.toLineaVenta
import org.example.cine_proyecto_final.ventas.mappers.toVenta
import org.example.cine_proyecto_final.ventas.models.LineaVenta
import org.example.cine_proyecto_final.ventas.models.Venta
import org.lighthousegames.logging.logging
import java.time.LocalDateTime

private val logger = logging()
/**
 * Implementación del repositorio de ventas que interactúa con la base de datos.
 * @property sqlDelightManager Gestor de SqlDelight para acceder a la base de datos.
 * @property productosRepositorio Repositorio de productos para acceder a la información de los productos.
 * @property clienteRepositorio Repositorio de cuentas de cliente para acceder a la información del cliente.
 */
class VentaRepositorioImpl(
    private val sqlDelightManager: SqlDelightManager,
    private val productosRepositorio: ProductosRepository,
    private val clienteRepositorio : CuentaRepository,
    private val butacaRepository: ButacaRepository
) : VentaRepositorio {

    private var db = sqlDelightManager.databaseQueries

    /**
     * Obtiene todas las ventas almacenadas en la base de datos.
     * @return Lista de ventas encontradas.
     */
    override fun findAll(): List<Venta> {
        logger.debug { "Buscando todas las ventas en la base de datos" }
        if (db.countVentas().executeAsOne() > 0){ //Para evitar que executeAsList te de una excepcion
            return db
                .getAllVentas()
                .executeAsList()
                .map{
                    val butacas = butacaRepository.getAllByVentaId(it.id)
                    val lineas =  getAllLineasByVentaId(it.id)
                    val cliente = clienteRepositorio.findById(it.id_socio)
                    it.toVenta(lineas = lineas, cliente = cliente!!, butacas = butacas)
                }
        }
        return emptyList()
    }

    /**
     * @param id el id de la linea de venta que está utilizando para buscar las lineas de venta
     */
    private fun getAllLineasByVentaId(id : String) : List<LineaVenta>{
        if (db.countLineasVentaByVentaId(id).executeAsOne() > 0){
            return db.getLineaVentaByVentaId(id)
                .executeAsList()
                .map {
                    val producto = productosRepositorio.findById(it.id_producto)
                    it.toLineaVenta(producto!!)
                }
        }
        return emptyList()
    }

    /**
     * Obtiene una venta específica según su identificador.
     * @param id Identificador único de la venta.
     * @return La venta encontrada, o null si no se encuentra.
     */
    override fun findById(id: String): Venta? {
        logger.debug { "Buscando la venta con id : $id" }
        if (db.existsVenta(id).executeAsOne()){
            val ventaEntity = db.getVentaById(id).executeAsOne()
            val lineas = getAllLineasByVentaId(ventaEntity.id).filter { !it.isDeleted }
            val cliente = clienteRepositorio.findById(ventaEntity.id_socio)
            val butacas = butacaRepository.getAllByVentaId(id)
            return ventaEntity.toVenta(cliente = cliente!!, lineas = lineas, butacas = butacas)
        }
        return null
    }

    /**
     * Guarda una venta en la base de datos.
     * @param venta La venta a guardar.
     * @param ignoreKey Indica si se debe ignorar la clave única al guardar.
     * @return La venta guardada, o null si ya existe una venta con la misma clave única y ignoreKey es false.
     */
    override fun save(venta: Venta, ignoreKey : Boolean): Venta? {
        logger.debug { "Guardando venta con id: ${venta.id}" }
        if (ignoreKey || findById(venta.id) == null){
            db.insertVenta(
                id = venta.id,
                id_socio = venta.cliente.email,
                updatedAt = LocalDateTime.now().toString(),
                createdAt = venta.createdAt.toString(),
                isDeleted = venta.isDeleted.toLong()
            )
            venta.lineasVenta.forEach {
                db.insertLineaVenta(
                    id = it.id,
                    id_venta = venta.id,
                    id_producto = it.producto.id,
                    precio = it.precio,
                    cantidad = it.cantidad.toLong(),
                    createdAt = it.createdAt.toString(),
                    updatedAt = LocalDateTime.now().toString(),
                    isDeleted = it.isDeleted.toLong()
                )
            }
            return venta
        }
        return null
    }

    /**
     * Elimina una venta de la base de datos.
     * @param id Identificador único de la venta a eliminar.
     * @return La venta eliminada, o null si la venta no existe.
     */
    override fun delete(id: String): Venta? {
        logger.debug { "Borrando venta con id: $id" }
        findById(id)?.let { //Si existe
            val nuevaVenta = Venta(
                cliente = it.cliente,
                isDeleted = true,
                createdAt = it.createdAt,
                updatedAt = LocalDateTime.now(),
                id = id,
                lineasVenta = it.lineasVenta,
                butacas = it.butacas
            )
            save(nuevaVenta, true)
                ?.let {
                    it.lineasVenta.forEach {
                        deleteLineaVenta(it)
                    }
                    return nuevaVenta
                }
            return null
        }
        return null
    }

    /**
     * Obtiene todas las ventas realizadas en una fecha específica.
     * @param date Fecha para la cual buscar las ventas.
     * @return Lista de ventas realizadas en la fecha especificada.
     */
    override fun findAllByDate(date: LocalDateTime): List<Venta> {
        logger.debug { "Buscando las ventas en: ${date.dayOfMonth}/${date.monthValue}/${date.year} ${date.hour}:${date.minute}:${date.second}" }
        if (db.countVentasByDate(date.toString()).executeAsOne() > 0){
            return db
                .getVentasByDate(date.toString())
                .executeAsList()
                .map {
                    val lineas = getAllLineasByVentaIdAndDate(it.id,date)
                    val cliente = clienteRepositorio.findById(it.id_socio)
                    val butacas = butacaRepository.getAllByVentaId(it.id)
                    it.toVenta(lineas = lineas, cliente = cliente!!, butacas = butacas)
                }
        }
        return emptyList()
    }

    /**
     * Elimina una línea de venta de la base de datos.
     * @param lineaVenta La línea de venta a eliminar.
     * @return La línea de venta eliminada.
     */
    override fun deleteLineaVenta(lineaVenta: LineaVenta): LineaVenta {
        db.deleteLineaVenta(lineaVenta.id,lineaVenta.id)
        return lineaVenta
    }

    /**
     * Obtiene todas las líneas de venta asociadas a una venta y a una fecha específica.
     * @param id Identificador único de la venta.
     * @param date Fecha para la cual buscar las líneas de venta.
     * @return Lista de líneas de venta asociadas a la venta y fecha especificadas.
     */
    private fun getAllLineasByVentaIdAndDate(id : String, date: LocalDateTime) : List<LineaVenta>{
        if (db.countLineaVentaByVentaIdAndDate(id_venta = id, updatedAt = date.toString()).executeAsOne() > 0){
            return db.getLineaVentaByVentaIdAndDate(id_venta = id, updatedAt = date.toString())
                .executeAsList()
                .map {
                    val producto = productosRepositorio.findByIdAndDate(id = it.id_producto, date = date)
                    it.toLineaVenta(producto!!)
                }
        }
        return emptyList()
    }


}