package org.example.cine_proyecto_final.ventas.validator

import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.models.Estado
import org.example.cine_proyecto_final.butacas.models.Tipo
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.models.TipoCuenta
import org.example.cine_proyecto_final.ventas.models.Venta
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class VentaValidatorTest {

    private val ventaValidator = VentaValidator()

    @Test
    fun validateDeberiaDevolverOkCuandoLaVentaEsValida() {
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
        val result = ventaValidator.validate(venta)

        //Assert
        assertTrue(result.isOk)
    }

    @Test
    fun validateDeberiaDevolverErrCuandoLaVentaNoTieneButacas() {
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
            butacas = emptyList(),
            lineasVenta = emptyList(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        //Act
        val result = ventaValidator.validate(venta)

        //Assert
        assertTrue(result.isErr)
    }
}