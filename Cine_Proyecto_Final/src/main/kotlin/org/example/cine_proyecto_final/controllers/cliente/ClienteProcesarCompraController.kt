package org.example.cine_proyecto_final.controllers.cliente

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
import java.time.YearMonth
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

        // Vincular la lista cesta con la TableView
        tableCesta.items = javafx.collections.FXCollections.observableArrayList(cesta)
    }

    private fun infoCompra() {
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
        textInfo.text = """DeadPool & Wolverine
        Fecha de compra: ${LocalDateTime.now().format(formatter)}""".trimIndent()
        obtenerCesta()
        precio_total_field.text = obtenerTotal().toString()
        nombre_field.text = viewModelSesion.usuario?.nombre + " " + viewModelSesion.usuario?.apellido
        email_field.text = viewModelSesion.usuario?.email
    }

    private fun obtenerCesta() {
        cesta.clear() // Limpiar la cesta antes de agregar nuevos elementos
        val lineas = viewModelProductos.state.value.lineas
        val butacas = viewModelButacas.butacasSeleccionadas

        // Usamos un mapa para agrupar productos por nombre
        val cestaMap = mutableMapOf<String, Cesta>()

        lineas.forEach { linea ->
            val nombre = linea.producto.nombre
            val tipo = linea.producto.tipo.toString()
            val precio = linea.producto.precio
            val cantidad = linea.cantidad

            if (cestaMap.containsKey(nombre)) {
                val existingCesta = cestaMap[nombre]!!
                cestaMap[nombre] = existingCesta.copy(cantidad = existingCesta.cantidad + cantidad)
            } else {
                cestaMap[nombre] = Cesta(nombre, tipo, precio, cantidad)
            }
        }

        butacas.forEach { butaca ->
            val nombre = butaca.id
            val tipo = butaca.tipo.toString()
            val precio = butaca.precio
            val cantidad = 1

            if (cestaMap.containsKey(nombre)) {
                val existingCesta = cestaMap[nombre]!!
                cestaMap[nombre] = existingCesta.copy(cantidad = existingCesta.cantidad + cantidad)
            } else {
                cestaMap[nombre] = Cesta(nombre, tipo, precio, cantidad)
            }
        }

        cesta.addAll(cestaMap.values)

        cesta.forEach {
            logger.debug { it }
        }
    }

    private fun obtenerTotal(): Double {
        return cesta.sumOf { it.precio * it.cantidad }
    }

    private fun finalizarCompra() {
        val tarjetaCredito = tarjet_credito_field.text
        val cvv = cvvField.text
        val fechaCaducidad = fxCaducidad.text

        if (tarjetaCredito.isEmpty() || cvv.isEmpty() || fechaCaducidad.isEmpty()) {
            RoutesManager.showAlertOperacion("Error", "Por favor, completa todos los campos", Alert.AlertType.ERROR)
            return
        }

        if (!validarTarjetaCredito(tarjetaCredito)) {
            RoutesManager.showAlertOperacion("Error", "Por favor, verifica el numero de la tarjeta de credito", Alert.AlertType.ERROR)
            return
        }

        if (!validarCVC(cvv)) {
            RoutesManager.showAlertOperacion("Error", "Por favor, verifica el cvv", Alert.AlertType.ERROR)
            return
        }

        if (!validarFechaCaducidad(fechaCaducidad)) {
            RoutesManager.showAlertOperacion("Error", "Por favor, verifica la fecha de caducidad", Alert.AlertType.ERROR)
            return
        }

        val usuario = viewModelSesion.usuario ?: throw IllegalStateException("Usuario no encontrado")
        val butacas = viewModelButacas.butacasSeleccionadas
        val lineas = viewModelProductos.state.value.lineas

        viewModel.procesarCompra(usuario, butacas, lineas,
            onSuccess = {
                RoutesManager.showAlertOperacion("Compra Finalizada", "La compra se ha procesado correctamente", Alert.AlertType.INFORMATION)
                RoutesManager.changeScene(RoutesManager.View.MAIN)
            },
            onFailure = { error ->
                RoutesManager.showAlertOperacion("Error", "Hubo un problema al procesar la compra: $error", Alert.AlertType.ERROR)
            }
        )
    }


    private fun validarTarjetaCredito(tarjeta: String): Boolean {
        // Expresión regular para validar el número de tarjeta de crédito
        val regex = Regex("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$")
        return regex.matches(tarjeta)
    }

    private fun validarCVC(cvc: String): Boolean {
        // Expresión regular para validar el CVC de la tarjeta
        val regex = Regex("^\\d{3}$")
        return regex.matches(cvc)
    }

    private fun validarFechaCaducidad(fecha: String): Boolean {
        return try {
            // Define el formato de la fecha MM/yy
            val formatter = DateTimeFormatter.ofPattern("MM/yy")
            // Parsea la fecha de caducidad
            val fechaCaducidad = YearMonth.parse(fecha, formatter)
            // Define la fecha de referencia 05/24
            val fechaReferencia = YearMonth.of(2024, 5)
            // Comprueba si la fecha de caducidad es posterior a 05/24
            fechaCaducidad.isAfter(fechaReferencia)
        } catch (e: Exception) {
            false
        }
    }

    data class Cesta(
        val nombre: String,
        val tipo: String,
        val precio: Double,
        val cantidad: Int
    )
}