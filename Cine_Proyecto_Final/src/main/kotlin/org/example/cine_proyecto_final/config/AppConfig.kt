package org.example.cine_proyecto_final.config

import org.lighthousegames.logging.logging
import java.io.File
import java.io.InputStream
import java.util.*

private val logger = logging()

private const val CONFIG_FILE_NAME = "application.properties"

/**
 * Configuración de la aplicación.
 * Se utiliza la carga perezosa (lazy) para leer las propiedades según se necesiten.
 */
class AppConfig {

    private val APP_PATH: String = System.getProperty("user.dir")

    // Directorio de imágenes
    val imagesDirectory: String by lazy {
        val path = readProperty("app.images") ?: "imagenes"
        "$APP_PATH${File.separator}$path/"
    }

    // URL de la base de datos
    val databaseUrl: String by lazy {
        readProperty("app.database.url") ?: "jdbc:sqlite:database.db"
    }

    // Inicialización de la base de datos
    val databaseInit: Boolean by lazy {
        readProperty("app.database.init")?.toBoolean() ?: false
    }

    // Eliminación de datos de la base de datos
    val databaseRemoveData: Boolean by lazy {
        readProperty("app.database.removedata")?.toBoolean() ?: false
    }

    // Tamaño de la caché
    val cacheSize: Int by lazy {
        readProperty("app.cache.size")?.toInt() ?: 10
    }

    init {
        logger.debug { "Cargando configuración de la aplicación" }
    }

    /**
     * Lee una propiedad del archivo de configuración.
     *
     * @param propiedad Nombre de la propiedad a leer.
     * @return El valor de la propiedad, o null si no se encuentra.
     */
    private fun readProperty(propiedad: String): String? {
        return try {
            logger.debug { "Leyendo propiedad: $propiedad" }
            val properties = Properties()
            val inputStream: InputStream = ClassLoader.getSystemResourceAsStream(CONFIG_FILE_NAME)
                ?: throw Exception("No se puede leer el fichero de configuración $CONFIG_FILE_NAME")
            properties.load(inputStream)
            properties.getProperty(propiedad)
        } catch (e: Exception) {
            logger.error { "Error al leer la propiedad $propiedad: ${e.message}" }
            null
        }
    }
}
