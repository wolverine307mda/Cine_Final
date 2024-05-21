package org.example.cine_proyecto_final.controllers

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.text.Text
import javafx.stage.Stage
import org.example.cine_proyecto_final.CineApplication
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.viewmodels.GeneralComprarEntradaViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.io.IOException

private val logger = logging()

class GeneralComprarEntradaController : KoinComponent {

    private val dbClient: SqlDelightManager by inject()
    private val viewModel: GeneralComprarEntradaViewModel by inject()

    @FXML
    private lateinit var duracionLabel: Label

    @FXML
    private lateinit var directorLabel: Label

    @FXML
    private lateinit var actoresLabel: Label

    @FXML
    private lateinit var sinopsisText: Text

    @FXML
    private lateinit var nombreTextField: TextField

    @FXML
    private lateinit var devolver_entrada_button: Button

    @FXML
    private lateinit var iniciar_sesion_button: Button

    @FXML
    private lateinit var comprar_entrada_button: Button

    @FXML
    private fun initialize() {
        logger.debug { "iniciando pantalla general de comprar entrada" }

        devolver_entrada_button.setOnAction { devolverEntrada() }
        iniciar_sesion_button.setOnAction { iniciarSesion() }
        comprar_entrada_button.setOnAction { comprarEntrada() }

        configurarDatosPelicula()
    }

    private fun configurarDatosPelicula() {
        duracionLabel.text = "DURACION"
        directorLabel.text = "DIRECTOR/ES"
        actoresLabel.text = "ACTORES"
        sinopsisText.text = "Lobezno se recupera de sus heridas cuando se cruza con el bocazas, Deadpool, que ha viajado en el tiempo para curarlo con la esperanza de hacerse amigos y formar un equipo para acabar con un enemigo común."
    }

    private fun iniciarSesion() {
        // Lógica para iniciar sesión
    }

    private fun devolverEntrada() {
        // Lógica para devolver entrada
    }

    private fun comprarEntrada() {
        logger.debug { "Botón comprar entrada presionado" }
        try {
            val loader = FXMLLoader(CineApplication::class.java.getResource("views/cliente_seleccion_butaca_view.fxml"))
            val root = loader.load<Any>()
            val newScene = Scene(root as javafx.scene.Parent)

            val currentStage = comprar_entrada_button.scene.window as Stage
            currentStage.scene = newScene
            currentStage.show()

            logger.debug { "Escena cambiada a cliente_seleccion_butaca_view.fxml" }
        } catch (e: IOException) {
            logger.error(e) { "No se pudo cargar la nueva escena" }
        }
    }
}
