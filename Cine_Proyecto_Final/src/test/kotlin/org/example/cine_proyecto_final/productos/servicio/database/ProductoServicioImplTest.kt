package org.example.cine_proyecto_final.productos.servicio.database

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.example.cine_final.productos.models.Producto
import org.example.cine_final.productos.models.TipoProducto
import org.example.cine_proyecto_final.productos.repository.ProductosRepository
import org.example.cine_proyecto_final.productos.validador.ProductoValidator
import org.example.productos.errors.ProductoError
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.kotlin.whenever
import org.mockito.quality.Strictness
import java.time.LocalDateTime


@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductoServicioImplTest {

    @Mock
    private lateinit var repo: ProductosRepository

    @Mock
    private lateinit var validator: ProductoValidator

    @InjectMocks
    private lateinit var servicio: ProductoServicioImpl

    @Test
    fun guardarProductoValidoQueNoExiste() {
        //Arrange
        val producto = Producto(
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
        whenever(repo.save(producto)).thenReturn(producto)
        whenever(validator.validate(producto)).thenReturn(Ok(producto))

        //Act
        val result = servicio.save(producto).value

        //Assert
        assertEquals(producto, result)
    }

    @Test
    fun guardarProductoQueYaExistePeroEsValido() {
        //Arrange
        val producto = Producto(
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
        whenever(repo.save(producto)).thenReturn(null)
        whenever(validator.validate(producto)).thenReturn(Ok(producto))

        //Act
        val result = servicio.save(producto)

        //Assert
        assertTrue(result.isErr)
    }

    @Test
    fun guardarProductoQueNoExistePeroNoEsValido() {
        //Arrange
        val producto = Producto(
            id = "3",
            nombre = "Product 3",
            precio = -30.0,
            stock = 20,
            tipo = TipoProducto.BEBIDA,
            image = "image3.jpg",
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            isDeleted = false
        )
        whenever(repo.save(producto)).thenReturn(producto)
        whenever(validator.validate(producto)).thenReturn(Err(ProductoError.ProductoStorageError("\"No se pudo guardar el producto con id: ${producto.id}")))

        //Act
        val result = servicio.save(producto)

        //Assert
        assertTrue(result.isErr)
    }

    @Test
    fun findAllDevuelveTodosLosProductos(){
        //Arrange
        val list = listOf(
            Producto(
            id = "1",
            nombre = "Product 1",
            precio = 10.0,
            stock = 10,
            tipo = TipoProducto.BEBIDA,
            image = "image1.jpg",
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            isDeleted = false
        ),
        Producto(
            id = "2",
            nombre = "Product 2",
            precio = 20.0,
            stock = 20,
            tipo = TipoProducto.BEBIDA,
            image = "image2.jpg",
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            isDeleted = false
        ))
        whenever(repo.findAll()).thenReturn(list)

        //Act
        val result = servicio.findAll().value

        //Assert
        assertEquals(result.size,2)
        assertTrue((result.firstOrNull { it.id == "2" } != null) && (result.firstOrNull { it.id == "1"} != null))

    }

    @Test
    fun updateConUnProductoValidoQueYaExiste() {
        //Arrange
        val producto = Producto(
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
        whenever(repo.findById(producto.id)).thenReturn(producto)
        whenever(repo.update(producto.id, producto)).thenReturn(producto)
        whenever(validator.validate(producto)).thenReturn(Ok(producto))

        //Act
        val result = servicio.update(producto.id, producto)

        //Assert
        assertTrue(result.isErr)
    }

    @Test
    fun updateConUnProductoNoValidoQueNoExiste(){
        //Arrange
        val producto = Producto(
            id = "3",
            nombre = "Product 3",
            precio = -30.0,
            stock = 20,
            tipo = TipoProducto.BEBIDA,
            image = "image3.jpg",
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            isDeleted = false
        )
        whenever(repo.findById(producto.id)).thenReturn(null)
        whenever(repo.update(producto.id, producto)).thenReturn(null)
        whenever(validator.validate(producto)).thenReturn(Err(ProductoError.ProductoStorageError("No se pudo actualizar el producto con id: ${producto.id}")))

        //Act
        val result = servicio.update(producto.id, producto)

        //Assert
        assertTrue(result.isErr)
    }

    @Test
    fun deleteConUnProductoQueSiExiste() {
        //Arrange
        val producto = Producto(
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
        whenever(repo.findById(producto.id)).thenReturn(producto)
        whenever(repo.delete(producto.id)).thenReturn(producto).also {
            producto.isDeleted = true
            producto.updatedAt = LocalDateTime.now()
        }

        //Act
        val result = servicio.delete(producto.id)

        //Assert
        assertTrue(result.isOk)
        assertTrue(producto.isDeleted)
    }

    @Test
    fun deleteConUnProductoQueNoExiste() {
        //Arrange
        val producto = Producto(
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
        whenever(repo.findById(producto.id)).thenReturn(null)

        //Act
        val result = servicio.delete(producto.id)

        //Assert
        assertTrue(result.isErr)
    }
}