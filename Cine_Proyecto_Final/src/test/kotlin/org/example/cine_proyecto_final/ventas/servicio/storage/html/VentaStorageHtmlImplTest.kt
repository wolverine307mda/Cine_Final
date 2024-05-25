package org.example.cine_proyecto_final.ventas.servicio.storage.html

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

class VentaStorageHtmlImplTest{

    private lateinit var file : File
    private lateinit var storage : VentaStorageHtmlImpl

    @BeforeEach
    fun setUp(){
        file = Files.createTempFile("",".html").toFile()
        storage = VentaStorageHtmlImpl()
    }

    @AfterEach
    fun tearDown(){
        Files.deleteIfExists(file.toPath())
    }

    @Test
    fun exportDevuelveUnitCuandoSeExportaCorrectamente(){
        //Arrange
        val venta = Venta(
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

        //Act
        val result = storage.export(venta,file)

        //Assert
        assertTrue(result.isOk)
    }
}