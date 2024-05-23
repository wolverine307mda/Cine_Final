package org.example.cine_proyecto_final.productos.repository

import database.DatabaseQueries
import org.example.cine_final.productos.models.Producto
import org.example.cine_final.productos.models.TipoProducto
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.kotlin.whenever
import org.mockito.quality.Strictness
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductoRepositoryImplTest {

    @Mock
    lateinit var config: AppConfig

    private lateinit var db: SqlDelightManager
    private lateinit var databaseQueries: DatabaseQueries
    private lateinit var repository: ProductoRepositoryImpl


    @BeforeEach
    fun setUp() {
        // Mockeando la base de datos para que cree ona base de datos en memoria
        whenever(config.databaseUrl).thenReturn("jdbc:sqlite::memory:")
        whenever(config.databaseInMemory).thenReturn(true)
        whenever(config.databaseInit).thenReturn(true)
        whenever(config.databaseRemoveData).thenReturn(true)

        // Iniciando la base de datos
        db = SqlDelightManager(config)
        databaseQueries = db.databaseQueries
        repository = ProductoRepositoryImpl(db)

        // Metiendole productos
        databaseQueries.transaction {
            databaseQueries.insertProducto(
                id = "1",
                nombre = "Product 1",
                precio = 10.0,
                stock = 100,
                tipo = TipoProducto.BEBIDA.name,
                imagen = "image1.jpg",
                createdAt = "2021-01-01T00:00:00.000",
                updatedAt = "2021-01-01T00:00:00.000",
                isDeleted = 0
            )
            databaseQueries.insertProducto(
                id = "2",
                nombre = "Product 2",
                precio = 20.0,
                stock = 50,
                tipo = TipoProducto.COMIDA.name,
                imagen = "image2.jpg",
                createdAt = "2021-01-01T00:00:00.000",
                updatedAt = "2021-01-01T00:00:00.000",
                isDeleted = 0
            )
        }
    }

    @Test
    fun findAllDevuelveTodosLosProductos() {
        // Act
        val result = repository.findAll()

        // Assert
        assertEquals(2, result.size)
        assertTrue(result.any { it.id == "1" })
        assertTrue(result.any { it.id == "2" })
    }

    @Test
    fun findByIdConElementoQueExiste() {
        // Act
        val result = repository.findById("1")

        // Assert
        assertNotNull(result)
        assertEquals("1", result?.id)
    }

    @Test
    fun findByIdConElementoQueNoExiste() {
        // Act
        val result = repository.findById("8")

        // Assert
        assertNull(result)
    }

    @Test
    fun guardarGuardaUnProductoNuevoQueNoExiste() {
        // Arrange
        val newProduct = Producto(
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

        // Act
        repository.save(newProduct)

        // Assert
        val result = repository.findById("3")
        assertNotNull(result)
        assertEquals("3", result?.id)
    }

    @Test
    fun guardarGuardaUnProductoNuevoQueSiExiste() {
        // Arrange
        val newProduct = Producto(
            id = "1",
            nombre = "Product 3",
            precio = 30.0,
            stock = 20,
            tipo = TipoProducto.BEBIDA,
            image = "image3.jpg",
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            isDeleted = false
        )

        // Act & Assert
        assertNull(repository.save(newProduct))
    }

    @Test
    fun deleteMarcaElProductoComoBorrado() {
        // Act
        repository.delete("1")

        // Assert
        val result = repository.findById("1")
        assertNotNull(result)
        assertTrue(result!!.isDeleted)
    }

    @Test
    fun updateActualizaElProductoQueSiExiste() {
        // Arrange
        val newProduct = Producto(
            id = "1",
            nombre = "Product Actualizado",
            precio = 30.0,
            stock = 20,
            tipo = TipoProducto.BEBIDA,
            image = "image3.jpg",
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            isDeleted = false
        )

        // Act
        repository.update(newProduct.id,newProduct)

        // Assert
        val result = repository.findById("1")
        assertNotNull(result)
        assertEquals("Product Actualizado", result?.nombre)
        assertEquals(30.0, result?.precio)
        assertEquals(20, result?.stock)
        assertEquals(TipoProducto.BEBIDA, result?.tipo)
        assertEquals("image3.jpg", result?.image)
        assertFalse(result!!.isDeleted)
    }

    @Test
    fun updateNoActualizaElProductoQueNoExiste() {
        // Arrange
        val newProduct = Producto(
            id = "8",
            nombre = "Product Actualizado",
            precio = 30.0,
            stock = 20,
            tipo = TipoProducto.BEBIDA,
            image = "image3.jpg",
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            isDeleted = false
        )

        // Act
        repository.update(newProduct.id,newProduct)

        // Assert
        val result = repository.findById("8")
        assertNull(result)
    }
}