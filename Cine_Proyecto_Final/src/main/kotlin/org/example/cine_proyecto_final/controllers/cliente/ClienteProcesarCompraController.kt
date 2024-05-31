package org.example.cine_proyecto_final.controllers.cliente

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.text.Text
import org.example.cine_proyecto_final.routes.RoutesManager
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteProcesarCompraViewModel
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteSeleccionButacaViewModel
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteSeleccionProductosViewModel
import org.example.cine_proyecto_final.viewmodels.sesion.SesionViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val logger = logging()

class ClienteProcesarCompraController : KoinComponent {

    private val viewModel: ClienteProcesarCompraViewModel by inject()
    private val viewModelSesion: SesionViewModel by inject()
    private val viewModelProductos: ClienteSeleccionProductosViewModel by inject()
    private val viewModelButacas: ClienteSeleccionButacaViewModel by inject()
    private val cesta: MutableList<Cesta> = mutableListOf()

    @FXML
    private lateinit var atras_button: Button

    // Datos del Usuario
    @FXML
    private lateinit var nombre_field: TextField
    @FXML
    private lateinit var email_field: TextField

    @FXML
    private lateinit var textInfo: Text

    @FXML
    private lateinit var fxCaducidad: TextField
    @FXML
    private lateinit var cvvField: TextField
    @FXML
    private lateinit var tarjet_credito_field: TextField

    @FXML
    private lateinit var precio_total_field: TextField
    @FXML
    private lateinit var finalizar_compra_button: Button

    // Cesta
    @FXML
    private lateinit var tableCesta: TableView<Cesta>
    @FXML
    private lateinit var nombreColum: TableColumn<Cesta, String>
    @FXML
    private lateinit var tipoColum: TableColumn<Cesta, String>
    @FXML
    private lateinit var precioColum: TableColumn<Cesta, Double>
    @FXML
    private lateinit var cantidadColum: TableColumn<Cesta, Int>

    @FXML
    fun initialize() {
        logger.debug { "iniciando pantalla de Procesar Compra" }
        atras_button.setOnAction { RoutesManager.changeScene(RoutesManager.View.SELECCION_PRODUCTOS) }
        infoCompra()
        finalizar_compra_button.setOnAction { finalizarCompra() }

        // Inicializar columnas de la tabla
        nombreColum.cellValueFactory = PropertyValueFactory("nombre")
        tipoColum.cellValueFactory = PropertyValueFactory("tipo")
        precioColum.cellValueFactory = PropertyValueFactory("precio")
        cantidadColum.cellValueFactory = PropertyValueFactory("cantidad")

        tableCesta.items = FXCollections.observableArrayList(cesta)
    }

    private fun infoCompra() {
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
        textInfo.text = """DeadPool & Wolverine
    Fecha de compra: ${LocalDateTime.now().format(formatter)}""".trimIndent()
        obtenerCesta()
        // Formatear el precio total con dos decimales
        val precioTotalFormateado = String.format("%.2f", obtenerTotal())
        precio_total_field.text = precioTotalFormateado
        nombre_field.text = viewModelSesion.usuario?.nombre + " " + viewModelSesion.usuario?.apellido
        email_field.text = viewModelSesion.usuario?.email
    }

    private fun obtenerCesta() {
        cesta.clear() // Limpiar la cesta antes de agregar nuevos elementos
        val lineas = viewModelProductos.state.value.lineas
        val butacas = viewModelButacas.butacasSeleccionadas

        // Usamos un mapa para agrupar productos por nombre
        val cestaMap = mutableMapOf<String, Cesta>()

        butacas.forEach { butaca ->
            val itemCesta = Cesta(
                nombre = "Butaca: " + butaca.id,
                tipo = butaca.tipo.toString(),
                precio = butaca.precio,
                cantidad = 1
            )
            cestaMap[itemCesta.nombre] = itemCesta
        }

        lineas.forEach { linea ->
            val itemCesta = Cesta(
                nombre = linea.producto.nombre,
                tipo = linea.producto.tipo.toString(),
                precio = linea.producto.precio,
                cantidad = linea.cantidad
            )
            cestaMap[itemCesta.nombre] = itemCesta
        }

        cesta.addAll(cestaMap.values)

        cesta.forEach {
            logger.debug { it }
        }

        tableCesta.items = FXCollections.observableArrayList(cesta)
    }

    private fun obtenerTotal(): Double {
        return cesta.sumByDouble { it.precio * it.cantidad }
    }

    private fun finalizarCompra() {
        val usuario = viewModelSesion.usuario ?: return

        val butacas = viewModelButacas.butacasSeleccionadas
        val lineas = viewModelProductos.state.value.lineas

        viewModel.procesarCompra(usuario, butacas, lineas, onSuccess = {
            // Limpiar la cesta y las líneas después de una compra exitosa
            viewModelButacas.butacasSeleccionadas.clear()
            viewModelProductos.clearList()
            cesta.clear()
            tableCesta.items.clear()
            tableCesta.refresh()
            logger.debug { "Compra finalizada con éxito y cesta limpiada." }
            // Volver a la pantalla de inicio
            RoutesManager.changeScene(RoutesManager.View.MAIN)
        }, onFailure = { mensajeError ->
            // Mostrar el mensaje de error
            logger.error { "Error al finalizar la compra: $mensajeError" }
        })
    }
    data class Cesta(
        val nombre: String,
        val tipo: String,
        val precio: Double,
        val cantidad: Int
    )
}
