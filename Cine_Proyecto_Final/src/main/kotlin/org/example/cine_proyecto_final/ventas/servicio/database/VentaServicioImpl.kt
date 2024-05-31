package org.example.cine_proyecto_final.ventas.servicio.database

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onSuccess
import org.example.cine_proyecto_final.ventas.errors.VentaError
import org.example.cine_proyecto_final.ventas.models.Venta
import org.example.cine_proyecto_final.ventas.respositorio.VentaRepositorio
import org.example.cine_proyecto_final.ventas.validator.VentaValidator

/**
 * Implementación del service de gestión de ventas.
 * @property ventaRepositorio Repositorio de ventas para interactuar con la base de datos.
 */
class VentaServicioImpl (
    private var ventaRepositorio: VentaRepositorio,
    private val ventaValidator: VentaValidator
) : VentaServicio {


    /**
     * Guarda una venta en el repositorio de ventas.
     * @param venta La venta a guardar.
     * @return [Result] que contiene la venta guardada en caso de éxito, o un [VentaError] en caso de error.
     */
    override fun save(venta: Venta): Result<Venta, VentaError> {
        ventaValidator.validate(venta).onSuccess {
            ventaRepositorio.save(venta)?.let {
                return Ok(it)
            }
        }
        return Err(VentaError.VentaStorageError("No se ha podido guardar la venta con id: ${venta.id}"))
    }

    /**
     * Obtiene todas las ventas almacenadas en el repositorio.
     * @return [Result] que contiene la lista de ventas encontradas en caso de éxito, o un [VentaError] en caso de error.
     */
    override fun findAll(): Result<List<Venta>, VentaError> {
        return Ok(ventaRepositorio.findAll().filter { !it.isDeleted })
    }

    /**
     * Busca una venta por su identificador único.
     * @param id Identificador único de la venta.
     * @return [Result] que contiene la venta encontrada en caso de éxito, o un [VentaError] en caso de error.
     */
    override fun findById(id: String): Result<Venta, VentaError> {
        ventaRepositorio.findById(id)?.let {
            return Ok(it)
        }
        return Err(VentaError.VentaStorageError("No existe ninguna venta con el id: $id en la base de datos"))
    }

    /**
     * Elimina una venta del repositorio.
     * @param id Identificador único de la venta a eliminar.
     * @return [Result] que contiene la venta eliminada en caso de éxito, o un [VentaError] en caso de error.
     */
    override fun delete(id: String): Result<Venta, VentaError> {
        ventaRepositorio.delete(id)?.let {
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
        return Ok(ventaRepositorio.findAll().filter { it.cliente.email == id && !it.isDeleted})
    }


}