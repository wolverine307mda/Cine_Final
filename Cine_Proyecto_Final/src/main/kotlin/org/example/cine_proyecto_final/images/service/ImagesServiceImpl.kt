package org.example.cine_proyecto_final.images.service

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.images.errors.ImagesError
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.time.Instant

private val logger = logging()

/**
 * Implementación del servicio para gestionar imágenes.
 *
 * @param appConfig La configuración de la aplicación.
 */
class ImagesServiceImpl(
    private val appConfig: AppConfig
) : ImagesService {

    init {
        Files.createDirectories(Paths.get(appConfig.imagesDirectory))
    }

    /**
     * Genera un nombre único para un nuevo archivo de imagen.
     *
     * @param newFileImage El nuevo archivo de imagen.
     * @return El nombre único para el archivo de imagen.
     */
    private fun getImagenName(newFileImage: File): String {
        val name = newFileImage.name
        val extension = name.substring(name.lastIndexOf(".") + 1)
        return "${Instant.now().toEpochMilli()}.$extension"
    }

    /**
     * Guarda un nuevo archivo de imagen en el directorio configurado.
     *
     * @param file El nuevo archivo de imagen para guardar.
     * @return Un [Result] que contiene el archivo guardado con éxito, o un [ImagesError] en caso de error.
     */
    override fun saveImage(file: File): Result<File, ImagesError> {
        logger.debug { "Guardando imagen $file" }
        return try {
            val newFileImage = File(appConfig.imagesDirectory + getImagenName(file))
            Files.copy(file.toPath(), newFileImage.toPath(), StandardCopyOption.REPLACE_EXISTING)
            Ok(newFileImage)
        } catch (e: Exception) {
            Err(ImagesError.SaveError("Error al guardar la imagen: ${e.message}"))
        }
    }

    /**
     * Carga un archivo de imagen del directorio configurado.
     *
     * @param fileName El nombre del archivo de imagen para cargar.
     * @return Un [Result] que contiene el archivo cargado con éxito, o un [ImagesError] en caso de error.
     */
    override fun loadImage(fileName: String): Result<File, ImagesError> {
        logger.debug { "Cargando imagen $fileName" }
        val file = File(appConfig.imagesDirectory + fileName)
        return if (file.exists()) {
            Ok(file)
        } else {
            Err(ImagesError.LoadError("La imagen no existe: ${file.name}"))
        }
    }

    /**
     * Elimina un archivo de imagen del directorio configurado.
     *
     * @param file El archivo de imagen para eliminar.
     * @return Un [Result] que contiene [Unit] con éxito, o un [ImagesError] en caso de error.
     */
    override fun deleteImage(file: File): Result<Unit, ImagesError> {
        Files.deleteIfExists(file.toPath())
        return Ok(Unit)
    }

    /**
     * Elimina todos los archivos de imagen del directorio configurado.
     *
     * @return Un [Result] que contiene el número de archivos eliminados con éxito, o un [ImagesError] en caso de error.
     */
    override fun deleteAllImages(): Result<Long, ImagesError> {
        logger.debug { "Borrando todas las imágenes" }
        return try {
            Ok(
                Files.walk(Paths.get(appConfig.imagesDirectory))
               .filter { Files.isRegularFile(it) }
               .map { Files.deleteIfExists(it) }
               .count())
        } catch (e: Exception) {
            Err(ImagesError.DeleteError("Error al borrar todas las imágenes: ${e.message}"))
        }
    }

    /**
     * Actualiza un archivo de imagen existente en el directorio configurado.
     *
     * @param imagenName El nombre del archivo de imagen existente.
     * @param newImage El nuevo archivo de imagen para actualizar.
     * @return Un [Result] que contiene el archivo actualizado con éxito, o un [ImagesError] en caso de error.
     */
    override fun updateImage(imagenName: String, newImage: File): Result<File, ImagesError> {
        logger.debug { "Actualizando imagen $imagenName" }
        return try {
            val newUpdateImage = File(appConfig.imagesDirectory + imagenName)
            Files.copy(newImage.toPath(), newUpdateImage.toPath(), StandardCopyOption.REPLACE_EXISTING)
            Ok(newUpdateImage)
        } catch (e: Exception) {
            Err(ImagesError.SaveError("Error al guardar la imagen: ${e.message}"))
        }
    }
}