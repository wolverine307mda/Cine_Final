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
            padding: 0;
            background-color: #121212;
            color: #ffffff;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-image: url('https://hips.hearstapps.com/hmg-prod/images/deadpool-lobezno-jackman-reynolds-662677816d477.jpg');
            background-size: cover;
            background-position: center;
        }
        .receipt-container {
            background-color: rgba(0, 0, 0, 0.8);
            padding: 20px;
            border-radius: 10px;
            max-width: 600px;
            width: 100%;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.5);
        }
        .receipt-container h2, .receipt-container h3 {
            text-align: center;
        }
        .receipt-container p, .receipt-container ul {
            margin: 10px 0;
        }
        .receipt-container ul {
            list-style-type: none;
            padding: 0;
        }
        .receipt-container ul li {
            background-color: #333333;
            margin: 5px 0;
            padding: 10px;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <div class="receipt-container">
        <h2>Recibo de Compra</h2>
        <p><strong>Película:</strong> Deadpool & Wolverine</p>
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
        return String.format("%.2f", price)
    }

    private fun getProductList(venta: Venta): String {
        var output = ""
        venta.lineasVenta.forEach {
            output += "<li>${it.producto.nombre} - ${it.cantidad} x ${String.format("%.2f", it.producto.precio)}€</li>"
        }
        if (output.isBlank()) return "<li>No se compraron productos adicionales</li>"
        return output
    }

    private fun getButacaList(butacas: List<Butaca>): String {
        var output = ""
        butacas.forEach {
            output += "<li>Butaca ${it.id} - ${it.tipo} - ${String.format("%.2f", it.precio)}€</li>"
        }
        return output
    }
}
