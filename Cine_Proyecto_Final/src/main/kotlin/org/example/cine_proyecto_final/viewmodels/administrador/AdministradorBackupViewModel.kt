package org.example.cine_proyecto_final.viewmodels.administrador

import com.github.michaelbull.result.onSuccess
import org.example.cine_final.cuentas.servicio.storage.CuentaStorage
import org.example.cine_final.productos.servicio.storage.ProductoStorage
import org.example.cine_proyecto_final.butacas.service.database.ButacaService
import org.example.cine_proyecto_final.butacas.service.storage.ButacaStorage
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.cuentas.service.database.CuentaServicio
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.productos.servicio.database.ProductoServicio
import org.example.cine_proyecto_final.ventas.servicio.database.VentaServicio
import org.example.cine_proyecto_final.ventas.servicio.storage.VentaStorage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import kotlin.io.path.name


private val logger = logging()

class AdministradorBackupViewModel : KoinComponent {

    init {
        Files.createDirectories(Paths.get("imagenes"))
    }


    private val sqlDelightManager : SqlDelightManager by inject()
    private val appConfig : AppConfig by inject()

    private val productoServicio : ProductoServicio by inject()
    private val butacaServicio : ButacaService by inject()
    private val ventaServicio : VentaServicio by inject()
    private val cuentaServicio : CuentaServicio by inject()

    private val productoStorage : ProductoStorage by inject()
    private val butacaStorage : ButacaStorage by inject()
    private val ventaStorage : VentaStorage by inject()
    private val cuentaStorage : CuentaStorage by inject()

    fun restoreFromBackUp(file : File) : Boolean{
        logger.debug { "Importando desde ZIP $file" }
        // Creamos el directorio temporal
        val tempDir = Files.createTempDirectory("restoreTemp")
        return try {
            // Descomprimimos a un directorio temporal
            ZipFile(file).use { zip ->
                zip.entries().asSequence().forEach { entrada ->
                    zip.getInputStream(entrada).use { input ->
                        Files.copy(
                            input,
                            Paths.get(tempDir.toString(), entrada.name),
                            StandardCopyOption.REPLACE_EXISTING
                        )
                    }
                }
            }

            val butacas = butacaStorage.importFromJson(File(tempDir.toUri().path, "butacas.json"))
            val ventas = ventaStorage.importFromJson(File(tempDir.toUri().path, "ventas.json"))
            val cuentas = cuentaStorage.import(File(tempDir.toUri().path, "cuentas.json"))
            val productos = productoStorage.importFromJson(File(tempDir.toUri().path, "productos.json"))
            // Listamos por consola el contenido del directorio temporal
            Files.walk(tempDir).forEach {
                // copiamos todas las imagenes, es decir, todo lo que no es .json al directorio de imagenes
                if (!it.toString().endsWith(".json") && Files.isRegularFile(it)) {
                    Files.copy(
                        it,
                        Paths.get(appConfig.imagesDirectory, it.name),
                        StandardCopyOption.REPLACE_EXISTING
                    )
                }
            }
            if (butacas.isOk && ventas.isOk && cuentas.isOk && productos.isOk){
                sqlDelightManager.removeAllData()
                butacas.value.forEach {
                    butacaServicio.save(it)
                }
                ventas.value.forEach {
                    ventaServicio.save(it)
                }
                cuentas.value.forEach {
                    cuentaServicio.save(it)
                }
                productos.value.forEach {
                    productoServicio.save(it)
                }
            }
            tempDir.toFile().deleteRecursively()
            return true
        } catch (e: Exception) {
            logger.error { "Error al importar desde ZIP: ${e.message}" }
            false
        }
    }

    fun createBackup(file : File) : Boolean{
        logger.debug { "Exportando a ZIP $file" }
        // Creamos el directorio temporal
        val tempDir = Files.createTempDirectory("tempDir")
        // copiamos todas las imagenes al directorio temporal
        return try {
            exportProductos(tempDir)
            exportCuentas(tempDir)
            exportVentas(tempDir)
            exportButacas(tempDir)
            // Listamos por consola el contenido del directorio temporal
            Files.walk(tempDir).forEach { logger.debug { it } }
            // Eliminamos el directorio temporal al terminar
            // comprimimos
            val archivos = Files.walk(tempDir)
                .filter { Files.isRegularFile(it) }
                .toList()
            ZipOutputStream(Files.newOutputStream(file.toPath())).use { zip ->
                archivos.forEach { archivo ->
                    val entradaZip = ZipEntry(tempDir.relativize(archivo).toString())
                    zip.putNextEntry(entradaZip)
                    Files.copy(archivo, zip)
                    zip.closeEntry()
                }
            }
            tempDir.toFile().deleteRecursively()
            true
        } catch (e: Exception) {
            logger.error { "Error al exportar a ZIP: ${e.message}" }
            false
        }
    }

    private fun exportButacas(tempDir: Path?) {
        butacaServicio.findAll().onSuccess {
            // exportamos un json con el listado al directorio temporal
            butacaStorage.exportToJson(file = File("$tempDir/butacas.json"), data = it)
        }
    }


    private fun exportVentas(tempDir: Path?) {
        ventaServicio.findAll().onSuccess {
            // exportamos un json con el listado al directorio temporal
            ventaStorage.exportToJson(file = File("$tempDir/ventas.json"), data = it)
        }
    }

    private fun exportCuentas(tempDir: Path?) {
        cuentaServicio.findAll().onSuccess {
            // exportamos un json con el listado al directorio temporal
            cuentaStorage.export(file = File("$tempDir/cuentas.json"), list = it)
        }
    }

    private fun exportProductos (tempDir: Path) {
        productoServicio.findAll().onSuccess {
            it.forEach {
                val productoImage = File(appConfig.imagesDirectory + it.image)
                if (productoImage.exists()) {
                    Files.copy(
                        productoImage.toPath(),
                        Paths.get(tempDir.toString(), productoImage.name),
                        StandardCopyOption.REPLACE_EXISTING
                    )
                }
            }
            // exportamos un json con el listado al directorio temporal
            productoStorage.exportToJson(file = File("$tempDir/productos.json"), productos = it)
        }
    }


}