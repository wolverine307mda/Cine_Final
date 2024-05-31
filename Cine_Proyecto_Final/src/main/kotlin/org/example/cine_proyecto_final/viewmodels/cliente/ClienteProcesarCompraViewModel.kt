package org.example.cine_proyecto_final.viewmodels.cliente

import javafx.stage.FileChooser
import javafx.stage.Stage
import org.example.cine_proyecto_final.ventas.servicio.storage.html.VentaStorageHtmlImpl
import org.example.cine_proyecto_final.productos.servicio.database.ProductoServicio
import org.example.cine_proyecto_final.ventas.servicio.database.VentaServicio
import org.example.cine_proyecto_final.butacas.service.database.ButacaService
import org.example.cine_proyecto_final.ventas.models.LineaVenta
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.models.Estado
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.ventas.models.Venta
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.io.File
import java.util.*

private val logger = logging()

class ClienteProcesarCompraViewModel : KoinComponent {

    private val serviceButacas: ButacaService by inject()
    private val serviceProducto: ProductoServicio by inject()
    private val serviceVentas: VentaServicio by inject()

    private val seleccionButacaViewModel : ClienteSeleccionButacaViewModel by inject()
    private val seleccionProductosViewModel : ClienteSeleccionProductosViewModel by inject()

    fun procesarCompra(usuario: Cuenta, butacas: List<Butaca>, lineas: List<LineaVenta>, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        try {
            val venta = generarVenta(usuario, butacas, lineas)

            // Actualizar estado de las butacas
            butacas.forEach { butaca ->
                butaca.estado = Estado.OCUPADA
                serviceButacas.update(butaca.id, butaca, venta) // Método que actualiza el estado de la butaca en la base de datos
            }

            // Actualizar stock de productos
            lineas.forEach { linea ->
                val producto = linea.producto
                producto.stock -= linea.cantidad
                serviceProducto.update(producto.id, producto) // Método que actualiza el stock del producto en la base de datos
            }

            // Generar venta y crear recibo HTML
            val resultado = serviceVentas.save(venta)

            //Quitando las lineas y las butacas seleccionadas guardadas
            seleccionButacaViewModel.butacasSeleccionadas = mutableListOf()
            seleccionProductosViewModel.state.value.lineas = emptyList()

            resultado.let {
                val stage = Stage() // Necesitamos una instancia de Stage para mostrar el FileChooser
                exportarReciboHtml(venta, stage)
                onSuccess()
            }
        } catch (e: Exception) {
            logger.error { "Hubo un problema al procesar la compra: ${e.message}" }
            onFailure("Hubo un problema al procesar la compra: ${e.message}")
        }
    }

    private fun generarVenta(usuario: Cuenta, butacas: List<Butaca>, lineas: List<LineaVenta>): Venta {
        return Venta(
            id = generateVentaId(),
            cliente = usuario,
            butacas = butacas,
            lineasVenta = lineas
        )
    }

    private fun generateVentaId(): String {
        return UUID.randomUUID().toString()
    }

    private fun exportarReciboHtml(venta: Venta, stage: Stage) {
        val fileChooser = FileChooser()
        fileChooser.title = "Guardar Recibo"
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("Archivos HTML", "*.html"))
        val file = fileChooser.showSaveDialog(stage) ?: return

        VentaStorageHtmlImpl().export(venta, file)
    }
}
