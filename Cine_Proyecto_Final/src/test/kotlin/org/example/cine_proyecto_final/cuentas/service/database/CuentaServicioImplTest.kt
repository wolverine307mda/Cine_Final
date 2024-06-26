package org.example.cine_proyecto_final.cuentas.service.database

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import cuenta.errors.CuentaError
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.models.TipoCuenta
import org.example.cine_proyecto_final.cuentas.repository.CuentaRepository
import org.example.cine_proyecto_final.cuentas.service.cache.CuentaCache
import org.example.cine_proyecto_final.cuentas.validator.CuentaValidator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
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
class CuentaServicioImplTest {

    @Mock
    private lateinit var repository: CuentaRepository

    @Mock
    private lateinit var validador: CuentaValidator

    @Mock
    private lateinit var cache: CuentaCache

    @InjectMocks
    private lateinit var servicio: CuentaServicioImpl


    @Test
    fun guardaUnaCuentaNuevaQueNoExisteyEsValida() {
        // Arrange
        val cuenta = Cuenta(
            email = "admin@admin.com",
            nombre = "admin",
            apellido = "admin",
            imagen = "admin.jpg",
            password = "password",
            tipo = TipoCuenta.ADMINISTRADOR,
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000")
        )
        whenever(repository.save(cuenta)).thenReturn(cuenta)
        whenever(validador.validate(cuenta)).thenReturn(Ok(cuenta))
        whenever(cache.get(cuenta.email)).thenReturn(null)

        // Act
        val result = servicio.save(cuenta)

        // Assert
        assertEquals(cuenta, result.value)
    }

    @Test
    fun guardarCuentaQueNoExistePeroNoEsValida() {
        //Arrange
        val cuenta = Cuenta(
            email = "admin@admin.com",
            nombre = "admin",
            apellido = "admin",
            imagen = "admin.jpg",
            password = "password",
            tipo = TipoCuenta.ADMINISTRADOR,
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000")
        )
        whenever(repository.save(cuenta)).thenReturn(cuenta)
        whenever(validador.validate(cuenta)).thenReturn(Err(CuentaError.CuentaStorageError("\"No se pudo guardar la cuenta con email: ${cuenta.email}")))
        whenever(cache.get(cuenta.email)).thenReturn(null)

        // Act
        val result = servicio.save(cuenta)

        // Assert
        assertTrue(result.isErr)
    }

    @Test
    fun guardarCuentaQueYaExisteEnLaCachePeroEsValida() {
        // Arrange
        val cuenta = Cuenta(
            email = "admin@admin.com",
            nombre = "admin",
            apellido = "admin",
            imagen = "admin.jpg",
            password = "password",
            tipo = TipoCuenta.ADMINISTRADOR,
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000")
        )
        whenever(repository.save(cuenta)).thenReturn(null)
        whenever(validador.validate(cuenta)).thenReturn(Ok(cuenta))
        whenever(cache.get(cuenta.email)).thenReturn(cuenta)

        // Act
        val result = servicio.save(cuenta)

        // Assert
        assertTrue(result.isErr)
    }

    @Test
    fun guardarCuentaQueNoExisteEnLaCachePeroSiEnLaBDyEsValida() {
        // Arrange
        val cuenta = Cuenta(
            email = "admin@admin.com",
            nombre = "admin",
            apellido = "admin",
            imagen = "admin.jpg",
            password = "password",
            tipo = TipoCuenta.ADMINISTRADOR,
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000")
        )
        whenever(repository.save(cuenta)).thenReturn(null)
        whenever(validador.validate(cuenta)).thenReturn(Ok(cuenta))
        whenever(cache.get(cuenta.email)).thenReturn(null)

        // Act
        val result = servicio.save(cuenta)

        // Assert
        assertTrue(result.isErr)
    }

    @Test
    fun findByEmailDevuelveUnaCuentaExistenteQueEstaEnLaCache() {
        // Arrange
        val cuenta = Cuenta(
            email = "admin@admin.com",
            nombre = "admin",
            apellido = "admin",
            imagen = "admin.jpg",
            password = "password",
            tipo = TipoCuenta.ADMINISTRADOR,
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000")
        )
        whenever(repository.findById(cuenta.email)).thenReturn(cuenta)
        whenever(validador.validate(cuenta)).thenReturn(Ok(cuenta))
        whenever(cache.get(cuenta.email)).thenReturn(cuenta)

        // Act
        val result = servicio.findByEmail(cuenta.email).value

        // Assert
        assertEquals(cuenta, result)
    }

    @Test
    fun findByEmailDevuelveUnaCuentaExistenteQueNoEstaEnLaCache() {
        // Arrange
        val cuenta = Cuenta(
            email = "admin@admin.com",
            nombre = "admin",
            apellido = "admin",
            imagen = "admin.jpg",
            password = "password",
            tipo = TipoCuenta.ADMINISTRADOR,
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000")
        )
        whenever(repository.findById(cuenta.email)).thenReturn(cuenta)
        whenever(cache.get(cuenta.email)).thenReturn(null)

        // Act
        val result = servicio.findByEmail(cuenta.email).value

        // Assert
        assertEquals(cuenta, result)
    }

    @Test
    fun findByEmailDevuelveUnaCuentaQueNoExiste() {
        // Arrange
        val cuenta = Cuenta(
            email = "admin@admin.com",
            nombre = "admin",
            apellido = "admin",
            imagen = "admin.jpg",
            password = "password",
            tipo = TipoCuenta.ADMINISTRADOR,
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000")
        )
        whenever(repository.findById(cuenta.email)).thenReturn(null)
        whenever(cache.get(cuenta.email)).thenReturn(null)

        // Act
        val result = servicio.findByEmail(cuenta.email)

        // Assert
        assertTrue(result.isErr)
    }

    @Test
    fun updateActualizaUnaCuentaExistenteQueEstaEnLaCache() {
        // Arrange
        val cuenta = Cuenta(
            email = "user@user.com",
            nombre = "User",
            apellido = "uno",
            imagen = "user.jpg",
            password = "password",
            tipo = TipoCuenta.USUARIO,
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000")
        )
        whenever(repository.findById(cuenta.email)).thenReturn(cuenta)
        whenever(repository.update(cuenta.email, cuenta)).thenReturn(cuenta)
        whenever(validador.validate(cuenta)).thenReturn(Ok(cuenta))
        whenever(cache.get(cuenta.email)).thenReturn(cuenta)

        // Act
        val result = servicio.update(cuenta.email, cuenta)

        // Assert
        assertTrue(result.isOk)
    }

    @Test
    fun updateActualizaUnaCuentaExistenteQueNoEstaEnLaCache() {
        // Arrange
        val cuenta = Cuenta(
            email = "user@user.com",
            nombre = "User",
            apellido = "uno",
            imagen = "user.jpg",
            password = "password",
            tipo = TipoCuenta.USUARIO,
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000")
        )
        whenever(repository.findById(cuenta.email)).thenReturn(cuenta)
        whenever(repository.update(cuenta.email, cuenta)).thenReturn(cuenta)
        whenever(validador.validate(cuenta)).thenReturn(Ok(cuenta))
        whenever(cache.get(cuenta.email)).thenReturn(null)

        // Act
        val result = servicio.update(cuenta.email, cuenta)

        // Assert
        assertTrue(result.isOk)
    }

    @Test
    fun updateIntentaActualizarUnaCuentaQueNoExiste() {
        // Arrange
        val cuenta = Cuenta(
            email = "user@user.com",
            nombre = "User",
            apellido = "uno",
            imagen = "user.jpg",
            password = "password",
            tipo = TipoCuenta.USUARIO,
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000")
        )
        whenever(repository.findById(cuenta.email)).thenReturn(null)
        whenever(repository.update(cuenta.email, cuenta)).thenReturn(null)
        whenever(validador.validate(cuenta)).thenReturn(Err(CuentaError.CuentaStorageError("No se pudo actualizar la cuenta con email: ${cuenta.email}")))

        // Act
        val result = servicio.update(cuenta.email, cuenta)

        // Assert
        assertTrue(result.isErr)
    }
}
