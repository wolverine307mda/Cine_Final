package org.example.cine_proyecto_final.routes

import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.WindowEvent
import org.lighthousegames.logging.logging
import java.io.InputStream
import java.net.URL
import java.util.*

private val logger = logging()

object RoutesManager {

    private lateinit var mainStage: Stage // La ventana principal
    private lateinit var _activeStage: Stage // La ventana actual
    val activeStage: Stage
        get() = _activeStage
    lateinit var app: Application // La aplicación

    // Cache de escenas cargadas
    private var scenesMap: HashMap<String, Pane> = HashMap()

    enum class View(val fxml: String) {
        MAIN("views/general_bienvenido_screen.fxml"),
        COMPRAR_ENTRADA("views/general_comprar_entrada_view.fxml"),
        SELECCION_BUTACAS("views/cliente_seleccion_butaca_view.fxml"),
        SELECCION_PRODUCTOS("views/cliente_seleccion_productos_view.fxml"),
        PROCESAR_COMPRA("views/cliente_procesar_compra_view.fxml"),
        SESION_INICIO("views/sesion_inicio_screen.fxml"),
        REGISTRARSE("views/sesion_registrarse_screen.fxml"),
        CAMBIAR_CONTRASEÑA("views/sesion_cambio_contraseña_view.fxml"),
        ADMIN_INICIO("views/administrador_inicio_sesion.fxml"),
        ADMIN_PRODUCTOS("views/administrador_gestion_de_productos.fxml"),
        ADMIN_BUTACAS("views/administrador_gestion_de_butacas.fxml"),
        DETALLE_BUTACA("views/detalle/detalle_butaca.fxml"),
        DETALLE_PRODUCTO("views/detalle/detalle_producto.fxml")
    }

    init {
        logger.debug { "Inicializando RoutesManager" }
        Locale.setDefault(Locale.forLanguageTag("es-ES"))
    }

    // Inicializa la scena principal
    fun initMainStage(stage: Stage) {
        logger.debug { "Inicializando MainStage" }

        val fxmlLoader = FXMLLoader(getResource(View.MAIN.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 1280.0, 800.0)
        stage.title = "CineVerso"
        stage.isResizable = false
        stage.icons.add(Image(getResourceAsStream("icons/logo.png")))
        stage.setOnCloseRequest { onAppExit(event = it) }
        stage.scene = scene
        mainStage = stage
        _activeStage = stage
        mainStage.show()
    }

    // Cambia la escena en la ventana principal
    fun changeScene(view: View) {
        logger.debug { "Cambiando a la escena ${view.fxml}" }
        val fxmlLoader = FXMLLoader(getResource(view.fxml))
        val newRoot = fxmlLoader.load<Pane>()
        val scene = activeStage.scene
        scene.root = newRoot
        activeStage.sizeToScene() // Ajusta el tamaño de la ventana si es necesario
    }

    // Abre una nueva ventana
    private fun openNewStage(view: View, title: String, width: Double, height: Double) {
        logger.debug { "Inicializando $title Stage" }

        val fxmlLoader = FXMLLoader(getResource(view.fxml))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, width, height)
        val stage = Stage()
        stage.title = title
        stage.scene = scene
        stage.initOwner(mainStage)
        stage.initModality(Modality.WINDOW_MODAL)
        stage.isResizable = false
        stage.show()
    }

    // Abre la ventana de inicio de sesión
    fun initSesionInicio() {
        openNewStage(View.SESION_INICIO, "Inicio de Sesión", 580.0, 320.0)
    }

    fun initDetalle(vista: View, titulo: String){
        openNewStage(vista, titulo, 380.0, 560.0)
    }

    // Abre la ventana de registro
    fun initRegistrarse() {
        openNewStage(View.REGISTRARSE, "Registrarse", 640.0, 480.0)
    }

    // Abre la ventana de cambiar contraseña
    fun initCambiarContrasena() {
        openNewStage(View.CAMBIAR_CONTRASEÑA, "Cambiar Contraseña", 580.0, 320.0)
    }

    fun getResource(resource: String): URL {
        return app::class.java.getResource(resource)
            ?: throw RuntimeException("No se ha encontrado el recurso: $resource")
    }

    fun getResourceAsStream(resource: String): InputStream {
        return app::class.java.getResourceAsStream(resource)
            ?: throw RuntimeException("No se ha encontrado el recurso como stream: $resource")
    }

    fun onAppExit(
        title: String = "Salir de ${mainStage.title}?",
        headerText: String = "¿Estás seguro de que quieres salir de ${mainStage.title}?",
        contentText: String = "Si sales, se cerrará la aplicación y perderás todos los datos no guardados",
        event: WindowEvent? = null
    ) {
        logger.debug { "Cerrando aplicación" }
        Alert(Alert.AlertType.CONFIRMATION).apply {
            this.title = title
            this.headerText = headerText
            this.contentText = contentText
        }.showAndWait().ifPresent { opcion ->
            if (opcion == ButtonType.OK) {
                Platform.exit()
            } else {
                event?.consume()
            }
        }
    }
}
