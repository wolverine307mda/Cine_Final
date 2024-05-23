package org.example.cine_proyecto_final.viewmodels.administrador

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.cine_final.productos.servicio.storage.ProductoStorage
import org.example.cine_proyecto_final.productos.servicio.database.ProductoServicio
import org.example.productos.errors.ProductoError
import org.lighthousegames.logging.logging

private val logger = logging()

class AdministradorGestorProductosViewModel(
    private val service: ProductoServicio,
    private val storage: ProductoStorage
) {

}