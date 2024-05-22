package org.example.cine_proyecto_final.productos.validador

import com.github.michaelbull.result.Ok
import org.example.cine_final.productos.models.Producto
import org.example.cine_final.productos.models.TipoProducto
import org.example.productos.errors.ProductoError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ProductoValidadorTest {

    private val validador = ProductoValidador()

    @Test
    fun `validate should return Ok when producto is valid`() {
        val producto = Producto(
            nombre = "CocaCola",
            tipo = TipoProducto.BEBIDA,
            precio = 1.0,
            stock = 100,
            image = "sin_imagen.jpg"
        )
        val result = validador.validate(producto)

        assertTrue(result.isOk)
        assertEquals(Ok(producto), result)
    }

    @Test
    fun `validate should return Err when producto tipo is null`() {
        val producto = Producto(
            nombre = "CocaCola",
            tipo = null,
            precio = 1.0,
            stock = 100,
            image = "sin_imagen.jpg"
        )
        val result = validador.validate(producto)

        assertTrue(result.isErr)
        assertTrue(result.error is ProductoError.ProductoInvalido)
        assertEquals("Categoria inválida", result.error.message)
    }

    @Test
    fun `validate should return Err when producto stock is less than 0`() {
        val producto = Producto(
            nombre = "CocaCola",
            tipo = TipoProducto.BEBIDA,
            precio = 1.0,
            stock = -100,
            image = "sin_imagen.jpg"
        )
        val result = validador.validate(producto)

        assertTrue(result.isErr)
        assertTrue(result.error is ProductoError.ProductoInvalido)
        assertEquals("El stock no puede ser menos de 0", result.error.message)
    }

    @Test
    fun `validate should return Err when producto precio is less than 0`() {
        val producto = Producto(
            nombre = "CocaCola",
            tipo = TipoProducto.BEBIDA,
            precio = -1.0,
            stock = 100,
            image = "sin_imagen.jpg"
        )
        val result = validador.validate(producto)

        assertTrue(result.isErr)
        assertTrue(result.error is ProductoError.ProductoInvalido)
        assertEquals("El precio no puede ser menos de 0", result.error.message)
    }

    @Test
    fun `validate should return Err when producto precio and stock are less than 0`() {
        val producto = Producto(
            nombre = "CocaCola",
            tipo = null,
            precio = -1.0,
            stock = -100,
            image = "sin_imagen.jpg"
        )
        val result = validador.validate(producto)

        assertTrue(result.isErr)
        assertTrue(result.error is ProductoError.ProductoInvalido)
    }
}
