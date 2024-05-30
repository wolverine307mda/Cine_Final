package org.example.cine_proyecto_final.ventas.servicio.storage.html

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.ventas.errors.VentaError
import org.example.cine_proyecto_final.ventas.models.Venta
import org.lighthousegames.logging.logging
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val logger = logging()

class VentaStorageHtmlImpl : VentaStorageHtml {
    override fun export(venta: Venta, file: File): Result<Unit, VentaError> {
        try {
            val output = """
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Recibo de Compra</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .receipt {
            max-width: 600px;
            background-color: #ffffff;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .receipt h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        .receipt p {
            margin: 5px 0;
        }
        .receipt ul {
            list-style-type: none;
            padding: 0;
        }
        .receipt ul li {
            background-color: #f9f9f9;
            margin: 5px 0;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <div class="receipt">
        <h2>Recibo de Compra</h2>
        <p><strong>Cliente:</strong> ${venta.cliente.nombre} (${venta.cliente.email})</p>
        <p><strong>Fecha:</strong> ${LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT))}</p>
        <h3>Detalles de la Compra</h3>
        <p><strong>Butacas:</strong></p>
        <ul>
            ${getButacaList(venta.butacas)}
        </ul>
        <p><strong>Productos:</strong></p>
        <ul>
            ${getProductList(venta)}
        </ul>
        <p><strong>Total Pagado:</strong> ${getPrice(venta)}€</p>
    </div>
</body>
</html>
"""
            file.writeText(output)
            return Ok(Unit)
        } catch (e: Exception) {
            logger.error { "Hubo un fallo al generar su recibo" }
            return Err(VentaError.VentaStorageError("Hubo un fallo al generar su recibo de la venta con id: ${venta.id}"))
        }
    }

    private fun getPrice(venta: Venta): String {
        var price = 0.0
        venta.lineasVenta.forEach {
            price += (it.precio * it.cantidad)
        }
        venta.butacas.forEach {
            price += it.precio
        }
        return price.toString()
    }

    private fun getProductList(venta: Venta): String {
        var output = ""
        venta.lineasVenta.forEach {
            output += "<li>${it.producto.nombre} - ${it.cantidad} x ${it.producto.precio}€</li>"
        }
        if (output.isBlank()) return "<li>No se compraron productos adicionales</li>"
        return output
    }

    private fun getButacaList(butacas: List<Butaca>): String {
        var output = ""
        butacas.forEach {
            output += "<li>Butaca ${it.id} - ${it.tipo} - ${it.precio}€</li>"
        }
        return output
    }
}
