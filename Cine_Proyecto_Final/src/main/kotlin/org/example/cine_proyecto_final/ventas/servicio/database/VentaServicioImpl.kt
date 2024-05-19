package org.example.cine_proyecto_final.ventas.servicio.database

import com.github.michaelbull.result.*
import org.example.cine_proyecto_final.ventas.errors.VentaError
import org.example.cine_proyecto_final.ventas.models.Venta
import org.example.cine_proyecto_final.ventas.respository.VentaRepository
import java.time.LocalDateTime

/**
 * Implementación del service de gestión de ventas.
 * @property ventaRepository Repositorio de ventas para interactuar con la base de datos.
 */
class VentaServicioImpl (
    private var ventaRepository: VentaRepository,
) : VentaServicio {


    /**
     * Guarda una venta en el repositorio de ventas.
     * @param venta La venta a guardar.
     * @return [Result] que contiene la venta guardada en caso de éxito, o un [VentaError] en caso de error.
     */
    override fun save(venta: Venta): Result<Venta, VentaError> {
        ventaRepository.save(venta)?.let {
            return Ok(it)
        }
        return Err(VentaError.VentaStorageError("No se ha podido guardar la venta con id: ${venta.id}"))
    }

    /**
     * Obtiene todas las ventas almacenadas en el repositorio.
     * @return [Result] que contiene la lista de ventas encontradas en caso de éxito, o un [VentaError] en caso de error.
     */
    override fun findAll(): Result<List<Venta>, VentaError> {
        return Ok(ventaRepository.findAll().filter { !it.isDeleted })
    }

    /**
     * Busca una venta por su identificador único.
     * @param id Identificador único de la venta.
     * @return [Result] que contiene la venta encontrada en caso de éxito, o un [VentaError] en caso de error.
     */
    override fun findById(id: String): Result<Venta, VentaError> {
        ventaRepository.findById(id)?.let {
            return Ok(it)
        }
        return Err(VentaError.VentaStorageError("No existe ninguna venta con el id: $id en la base de datos"))
    }

    /**
     * Obtiene todas las ventas realizadas en una fecha específica.
     * @param date Fecha para la cual buscar las ventas.
     * @return [Result] que contiene la lista de ventas encontradas en caso de éxito, o un [VentaError] en caso de error.
     */
    override fun findAllByDate(date: LocalDateTime): Result<List<Venta>, VentaError> {
        return Ok(ventaRepository.findAllByDate(date))
    }

    /**
     * Obtiene todas las ventas realizadas en una fecha específica.
     * @param date Fecha para la cual buscar las ventas.
     * @return [Result] que contiene la lista de ventas encontradas en caso de éxito, o un [VentaError] en caso de error.
     */
    override fun getAllVentasByDate(date : LocalDateTime): Result<List<Venta>, VentaError> {
        return Ok(ventaRepository.findAllByDate(date))
    }

    /**
     * Elimina una venta del repositorio.
     * @param id Identificador único de la venta a eliminar.
     * @return [Result] que contiene la venta eliminada en caso de éxito, o un [VentaError] en caso de error.
     */
    override fun delete(id: String): Result<Venta, VentaError> {
        ventaRepository.delete(id)?.let {
            return Ok(it)
        }
        return Err(VentaError.VentaStorageError("La venta que está intentando borrar no existe"))
    }

    /**
     * Elimina una venta del repositorio.
     * @param id Identificador del cliente
     * @return [Result] que contiene las ventas de un cliente o un [VentaError] en caso de error.
     */
    override fun findVentasByClienteId(id: String): Result<List<Venta>, VentaError> {
        return Ok(ventaRepository.findAll().filter { it.cliente.email == id && !it.isDeleted})
    }


}