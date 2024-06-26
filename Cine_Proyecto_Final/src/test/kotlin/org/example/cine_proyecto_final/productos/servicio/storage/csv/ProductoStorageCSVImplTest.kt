package org.example.cine_proyecto_final.productos.servicio.storage.csv

import org.jetbrains.dokka.InternalDokkaApi
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.nio.file.Files

class ProductoStorageCSVImplTest {

    private val file = Files.createTempFile("", ".csv")
    private var productoStorageCSV = ProductoStorageCSVImpl()

    @AfterEach
    fun tearDown() {
        Files.deleteIfExists(file)
    }

    @BeforeEach
    fun setUp() {
        file.toFile().writeText("nombre,precio,stock,tipo,imagen\n" +
                "Coca-Cola,1.50,100,BEBIDA,coca_cola.jpg\n" +
                "Pepsi,1.25,80,BEBIDA,pepsi.jpg")
    }

    @OptIn(InternalDokkaApi::class)
    @Test
    fun importDevuelveOkAlImportarUnaListaDeProductos(){
        //Act
        val result = productoStorageCSV.import(file.toFile())

        //Assert
        assertTrue(result.isOk)
        assertTrue(result.value.size == 2)
        assertNotNull(result.value.firstOrNull { it.nombre == "Coca-Cola" })
        assertNotNull(result.value.firstOrNull { it.nombre == "Pepsi" })
    }
}