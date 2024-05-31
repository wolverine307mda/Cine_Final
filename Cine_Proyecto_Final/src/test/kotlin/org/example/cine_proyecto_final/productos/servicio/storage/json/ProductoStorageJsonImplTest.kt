package org.example.cine_proyecto_final.productos.servicio.storage.json;

import org.example.cine_proyecto_final.productos.models.Producto
import org.example.cine_proyecto_final.productos.models.TipoProducto
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.File
import java.nio.file.Files
import java.time.LocalDateTime

class ProductoStorageJsonImplTest {
    private lateinit var storageJson: ProductoStorageJsonImpl
    private lateinit var myFile: File

    @BeforeEach
    fun setup() {
        storageJson = ProductoStorageJsonImpl()
        myFile = Files.createTempFile("", ".json").toFile()
    }

    @AfterEach
    fun tearDown() {
        Files.deleteIfExists(myFile.toPath())
    }

    @Test
    fun exportDevuelveUnitCuandoSeExportaCorrectamente() {
        val data = listOf(
            Producto(
                id = "3",
                nombre = "Product 3",
                precio = 30.0,
                stock = 20,
                tipo = TipoProducto.BEBIDA,
                image = "image3.jpg",
                createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
                updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
                isDeleted = false
            )
        )

        val result = storageJson.export(myFile, data)

        assertTrue(result.isOk)
        assertEquals(Unit, result.value)
    }

    @Test
    fun importDevuelveDaraCuandoSeImportaCorrectamente() {
        val data = listOf(
            Producto(
                id = "3",
                nombre = "Product 3",
                precio = 30.0,
                stock = 20,
                tipo = TipoProducto.BEBIDA,
                image = "image3.jpg",
                createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
                updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
                isDeleted = false
            )
        )

        storageJson.export(myFile, data)

        val result = storageJson.import(myFile)

        assertTrue(result.isOk)
        assertEquals(data, result.value)
    }
}