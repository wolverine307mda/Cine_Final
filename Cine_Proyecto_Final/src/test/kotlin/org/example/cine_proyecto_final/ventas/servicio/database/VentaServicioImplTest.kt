package org.example.cine_proyecto_final.ventas.servicio.database

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.models.Estado
import org.example.cine_proyecto_final.butacas.models.Tipo
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.models.TipoCuenta
import org.example.cine_proyecto_final.ventas.errors.VentaError
import org.example.cine_proyecto_final.ventas.models.Venta
import org.example.cine_proyecto_final.ventas.respositorio.VentaRepositorio
import org.example.cine_proyecto_final.ventas.validator.VentaValidator
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.kotlin.whenever
import org.mockito.quality.Strictness
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class VentaServicioImplTest {
    @Mock
    private lateinit var repo: VentaRepositorio

    @Mock
    private lateinit var validator: VentaValidator

    @InjectMocks
    private lateinit var servicio: VentaServicioImpl

    @Test
    fun guardarVentaValidaQueNoExiste() {
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
        whenever(repo.save(venta)).thenReturn(venta)
        whenever(validator.validate(venta)).thenReturn(Ok(venta))

        //Act
        val result = servicio.save(venta).value

        //Assert
        assertEquals(venta, result)
    }

    @Test
    fun guardarVentaQueYaExistePeroEsValida() {
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
        whenever(repo.save(venta)).thenReturn(null)
        whenever(validator.validate(venta)).thenReturn(Ok(venta))

        //Act
        val result = servicio.save(venta)

        //Assert
        assertTrue(result.isErr)
    }

    @Test
    fun guardarVentaQueNoExistePeroNoEsValida() {
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
        whenever(repo.save(venta)).thenReturn(venta)
        whenever(validator.validate(venta)).thenReturn(Err(VentaError.VentaStorageError("No se ha podido guardar la venta con id: ${venta.id}")))

        //Act
        val result = servicio.save(venta)

        //Assert
        assertTrue(result.isErr)
    }

    @Test
    fun findAllDevuelveTodosLasVentas(){
        //Arrange
        val list = listOf(
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
        list.forEach {
            servicio.save(it)
        }

        whenever(repo.findAll()).thenReturn(list)

        //Act
        val result = servicio.findAll().value

        //Assert
        assertEquals(result.size,1)
        assertTrue((result.firstOrNull { it.id == "1" } != null))

    }

    @Test
    fun deleteConUnaVentaQueSiExiste() {
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
        whenever(repo.findById(venta.id)).thenReturn(venta)
        whenever(repo.delete(venta.id)).thenReturn(venta).also {
            venta.isDeleted = true
            venta.updatedAt = LocalDateTime.now()
        }

        //Act
        val result = servicio.delete(venta.id)

        //Assert
        assertTrue(result.isOk)
        assertTrue(venta.isDeleted)
    }

    @Test
    fun deleteConUnProductoQueNoExiste() {
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
        whenever(repo.findById(venta.id)).thenReturn(null)

        //Act
        val result = servicio.delete(venta.id)

        //Assert
        assertTrue(result.isErr)
    }
}