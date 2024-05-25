package org.example.cine_proyecto_final.ventas.servicio.storage.json

import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.models.Estado
import org.example.cine_proyecto_final.butacas.models.Tipo
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.models.TipoCuenta
import org.example.cine_proyecto_final.ventas.models.Venta
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files
import java.time.LocalDateTime

class VentaStorageJsonImplTest{
    private lateinit var storageJson: VentaStorageJson
    private lateinit var myFile: File

    @BeforeEach
    fun setup() {
        storageJson = VentaStorageJsonImpl()
        myFile = Files.createTempFile("", ".json").toFile()
    }

    @AfterEach
    fun tearDown() {
        Files.deleteIfExists(myFile.toPath())
    }

    @Test
    fun exportDevuelveUnitCuandoSeExportaCorrectamente() {
        val data = listOf(
            Venta(
                id = "1",
                cliente = Cuenta(
                    nombre = "Cliente 1",
                    apellido = "Apellido 1",
                    email = "email 1",
                    imagen = "sin_imagen",
                    password = "1234",
                    tipo = TipoCuenta.USUARIO
                ),
                butacas = listOf(
                    Butaca(
                        id = "A1",
                        tipo = Tipo.NORMAL,
                        estado = Estado.LIBRE,
                        precio = 10.0,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                    )
                ),
                lineasVenta = emptyList(),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        )

        val result = storageJson.export(myFile, data)

        assertTrue(result.isOk)
        assertEquals(Unit, result.value)
    }

    @Test
    fun importDevuelveDaraCuandoSeImportaCorrectamente() {
        val data = listOf(
            Venta(
                id = "1",
                cliente = Cuenta(
                    nombre = "Cliente 1",
                    apellido = "Apellido 1",
                    email = "email 1",
                    imagen = "sin_imagen",
                    password = "1234",
                    tipo = TipoCuenta.USUARIO
                ),
                butacas = listOf(
                    Butaca(
                        id = "A1",
                        tipo = Tipo.NORMAL,
                        estado = Estado.LIBRE,
                        precio = 10.0,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                    )
                ),
                lineasVenta = emptyList(),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        )

        storageJson.export(myFile, data)

        val result = storageJson.import(myFile)

        assertTrue(result.isOk)
        assertTrue(result.value.firstOrNull { it.id == "1" } != null)
    }
}