package org.example.cine_proyecto_final.cuentas.validator

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import cuenta.errors.CuentaError
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.models.TipoCuenta
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CuentaValidatorTest {

    private val validator = CuentaValidator()

    @Test
    fun cuentaValida() {
        // Arrange
        val cuenta = Cuenta(
            email = "ejemplo@example.com",
            nombre = "John",
            apellido = "Doe",
            imagen = "imagen.jpg",
            password = "A1b2C",
            tipo = TipoCuenta.USUARIO
        )

        // Act
        val result = validator.validate(cuenta)

        // Assert
        assertTrue(result.isOk)
        assertEquals(cuenta, result.get())
    }

    @Test
    fun errorSiEmailEsInvalido() {
        // Arrange
        val cuenta = Cuenta(
            email = "correoInvalido",
            nombre = "John",
            apellido = "Doe",
            imagen = "imagen.jpg",
            password = "password",
            tipo = TipoCuenta.USUARIO
        )

        // Act
        val result = validator.validate(cuenta)

        // Assert
        assertTrue(result.isErr)
        val error = result.getError()
        assertNotNull(error)
        assertTrue(error is CuentaError.CuentaInvalida)
        if (error != null) {
            assertEquals("El email correoInvalido no es válido", error.message)
        }
    }

    @Test
    fun errorNombreVacio() {
        // Arrange
        val cuenta = Cuenta(
            email = "ejemplo@example.com",
            nombre = "",
            apellido = "Doe",
            imagen = "imagen.jpg",
            password = "password",
            tipo = TipoCuenta.USUARIO
        )

        // Act
        val result = validator.validate(cuenta)

        // Assert
        assertTrue(result.isErr)
        val error = result.getError()
        assertNotNull(error)
        assertTrue(error is CuentaError.CuentaInvalida)
        if (error != null) {
            assertEquals("El nombre no puede estar vacío", error.message)
        }
    }

    @Test
    fun errorApellidoVacio() {
        // Arrange
        val cuenta = Cuenta(
            email = "ejemplo@example.com",
            nombre = "John",
            apellido = "",
            imagen = "imagen.jpg",
            password = "password",
            tipo = TipoCuenta.USUARIO
        )

        // Act
        val result = validator.validate(cuenta)

        // Assert
        assertTrue(result.isErr)
        val error = result.getError()
        assertNotNull(error)
        assertTrue(error is CuentaError.CuentaInvalida)
        if (error != null) {
            assertEquals("Los apellidos no pueden estar vacíos", error.message)
        }
    }

    @Test
    fun errorContraseñaVacia() {
        // Arrange
        val cuenta = Cuenta(
            email = "ejemplo@example.com",
            nombre = "John",
            apellido = "Doe",
            imagen = "imagen.jpg",
            password = "",
            tipo = TipoCuenta.USUARIO
        )

        // Act
        val result = validator.validate(cuenta)

        // Assert
        assertTrue(result.isErr)
        val error = result.getError()
        assertNotNull(error)
        assertTrue(error is CuentaError.CuentaInvalida)
        if (error != null) {
            assertEquals("La contraseña no puede estar vacía", error.message)
        }
    }

    @Test
    fun errorTipoCuentaVacio() {
        // Arrange
        val cuenta = Cuenta(
            email = "ejemplo@example.com",
            nombre = "John",
            apellido = "Doe",
            imagen = "imagen.jpg",
            password = "A1b2C",
            tipo = null
        )

        // Act
        val result = validator.validate(cuenta)

        // Assert
        assertTrue(result.isErr)
        val error = result.getError()
        assertNotNull(error)
        assertTrue(error is CuentaError.CuentaInvalida)
        if (error != null) {
            assertEquals("El tipo de cuenta no es válido", error.message)
        }
    }

}
