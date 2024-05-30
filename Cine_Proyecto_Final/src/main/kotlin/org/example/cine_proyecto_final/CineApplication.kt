package org.example.cine_proyecto_final

import com.github.michaelbull.result.onSuccess
import javafx.application.Application
import javafx.stage.Stage
import org.example.cine_final.productos.servicio.storage.ProductoStorage
import org.example.cine_proyecto_final.butacas.service.database.ButacaService
import org.example.cine_proyecto_final.butacas.service.storage.ButacaStorage
import org.example.cine_proyecto_final.butacas.service.storage.ButacaStorageImpl
import org.example.cine_proyecto_final.butacas.service.storage.csv.ButacaStorageCsvImpl
import org.example.cine_proyecto_final.butacas.service.storage.json.ButacaStorageJsonImpl
import org.example.cine_proyecto_final.butacas.validator.ButacaValidator
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.di.appModule
import org.example.cine_proyecto_final.productos.servicio.database.ProductoServicio
import org.example.cine_proyecto_final.routes.RoutesManager
import org.jetbrains.dokka.InternalDokkaApi
import org.jetbrains.dokka.utilities.ServiceLocator.toFile
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.lighthousegames.logging.logging
import java.time.LocalDateTime

private val logger = logging()

class CineApplication : Application(), KoinComponent{

    private lateinit var appConfig: AppConfig
    private lateinit var dbClient: SqlDelightManager

    private val butacaStorage: ButacaStorage by inject()
    private val butacaService : ButacaService by inject()
    private val productoStorage: ProductoStorage by inject()
    private val productoServicio : ProductoServicio by inject()


    init {
        startKoin {
            printLogger() // Logger de Koin
            modules(appModule) // Módulos de Koin
            initButacasFromCsv()
            initProductosFromCsv()
        }

    }

    @OptIn(InternalDokkaApi::class)
    private fun initButacasFromCsv(){
        logger.debug { "Inicializando las butacas de ejemplo" }
        val file = CineApplication::class.java.getResource("data/butacas.csv")
        if (file != null) {
            butacaStorage.importFromCsv(file.toFile())
                .onSuccess {
                    it.forEach { butaca ->
                        butacaService.save(butaca)
                    }
                }
        }
    }

    @OptIn(InternalDokkaApi::class)
    private fun initProductosFromCsv(){
        logger.debug { "Inicializando los productos de ejemplo" }
        val file = CineApplication::class.java.getResource("data/productos.csv")
        if (file != null) {
            productoStorage.importFromCSV(file.toFile())
                .onSuccess {
                    it.forEach { producto ->
                        productoServicio.save(producto)
                    }
                }
        }
    }

    override fun start(stage: Stage) {
        try {
            logger.debug { "Iniciando la aplicación" }
            // Initialize application configuration and database client
            RoutesManager.apply {
                app = this@CineApplication
            }.run {
                // Iniciamos la aplicación, podiamos hacerlo con also!!
                initMainStage(stage)
            }

            dbClient = SqlDelightManager(config = appConfig)

        } catch (e: Exception) {
            e.printStackTrace()
            // Handle exception appropriately
        }
    }
}

fun main() {
    Application.launch(CineApplication::class.java)
}

