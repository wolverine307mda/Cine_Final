package org.example.cine_proyecto_final.ventas.respositorio

import database.DatabaseQueries
import org.example.cine_proyecto_final.productos.models.Producto
import org.example.cine_proyecto_final.productos.models.TipoProducto
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.models.Estado
import org.example.cine_proyecto_final.butacas.models.Tipo
import org.example.cine_proyecto_final.butacas.repository.ButacaRepositoryImpl
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.models.TipoCuenta
import org.example.cine_proyecto_final.cuentas.repository.CuentaRepositoryImpl
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.productos.repository.ProductoRepositoryImpl
import org.example.cine_proyecto_final.ventas.models.LineaVenta
import org.example.cine_proyecto_final.ventas.models.Venta
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.kotlin.whenever
import org.mockito.quality.Strictness
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class VentaRepositorioImplTest {

    @Mock
    lateinit var config: AppConfig

    @Mock
    lateinit var butacaRepo: ButacaRepositoryImpl

    @Mock
    lateinit var cuentaRepo: CuentaRepositoryImpl

    @Mock
    lateinit var productoRepo: ProductoRepositoryImpl

    private lateinit var repo: VentaRepositorioImpl
    private lateinit var sqlDelightManager: SqlDelightManager
    private lateinit var databaseQueries: DatabaseQueries

    @BeforeEach
    fun setUp() {
        // Mocking the database configuration
        whenever(config.databaseUrl).thenReturn("jdbc:sqlite::memory:")
        whenever(config.databaseInMemory).thenReturn(true)
        whenever(config.databaseInit).thenReturn(true)
        whenever(config.databaseRemoveData).thenReturn(true)

        sqlDelightManager = SqlDelightManager(config)
        databaseQueries = sqlDelightManager.databaseQueries

        repo = VentaRepositorioImpl(
            sqlDelightManager,
            productoRepo,
            cuentaRepo,
            butacaRepo
        )
    }

    @Test
    fun findAllDevuelveTodosLasVentas() {
        // Arrange
        databaseQueries.transaction {
            databaseQueries.insertVenta(
                id = "1",
                id_socio = "Cliente 1",
                createdAt = "2021-01-01T00:00:00.000",
                updatedAt = "2021-01-01T00:00:00.000",
                isDeleted = 0
            )
            databaseQueries.insertVenta(
                id = "2",
                id_socio = "Cliente 1",
                createdAt = "2021-01-01T00:00:00.000",
                updatedAt = "2021-01-01T00:00:00.000",
                isDeleted = 0
            )
        }

        whenever(butacaRepo.getAllByVentaId("1")).thenReturn(
            listOf(
                Butaca(
                    id = "A1",
                    tipo = Tipo.NORMAL,
                    estado = Estado.LIBRE,
                    precio = 10.0,
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now(),
                )
            )

        )
        whenever(cuentaRepo.findById("Cliente 1")).thenReturn(
            Cuenta(
                nombre = "Cliente 1",
                apellido = "Apellido 1",
                email = "email 1",
                imagen = "sin_imagen",
                password = "1234",
                tipo = TipoCuenta.USUARIO
            )
        )

        // Act
        val result = repo.findAll()


        // Assert
        assertEquals(2, result.size)
        assertTrue(result.any { it.id == "1" })
        assertTrue(result.any { it.id == "2" })
    }

    @Test
    fun findByIdDevuelveUnaVentaQueExiste() {
        //Arrange
        databaseQueries.transaction {
            databaseQueries.insertVenta(
                id = "1",
                id_socio = "Cliente 1",
                createdAt = "2021-01-01T00:00:00.000",
                updatedAt = "2021-01-01T00:00:00.000",
                isDeleted = 0
            )
        }

        whenever(butacaRepo.getAllByVentaId("1")).thenReturn(
            listOf(
                Butaca(
                    id = "A1",
                    tipo = Tipo.NORMAL,
                    estado = Estado.LIBRE,
                    precio = 10.0,
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now(),
                )
            )

        )
        whenever(cuentaRepo.findById("Cliente 1")).thenReturn(
            Cuenta(
                nombre = "Cliente 1",
                apellido = "Apellido 1",
                email = "email 1",
                imagen = "sin_imagen",
                password = "1234",
                tipo = TipoCuenta.USUARIO
            )
        )

        //Act
        val result = repo.findById("1")

        //Assert
        assertNotNull(result)
        assertEquals("1", result?.id)
    }

    @Test
    fun findByIdDevuelveUnaVentaQueNoExiste() {
        //Act
        val result = repo.findById("1")

        //Assert
        assertNull(result)
    }

    @Test
    fun saveGuardaUnaVentaQueNoExisteCorrectamente() {
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
        val result = repo.save(venta)

        //Assert
        assertNotNull(result)
        assertEquals("1", result?.id)
    }

    @Test
    fun deleteBorraUnaVentaQueExisteCorrectamente() {
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
            lineasVenta = listOf(
                LineaVenta(
                    id = "1",
                    producto = Producto(
                        id = "1",
                        nombre = "Producto 1",
                        image = "no-image",
                        precio = 10.0,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                        stock = 20,
                        tipo = TipoProducto.COMIDA,
                        isDeleted = false
                    ),
                    precio = 1.0,
                    cantidad = 1,
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now(),
                )
            ),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        whenever(butacaRepo.getAllByVentaId(id = "1")).thenReturn( //Porque llama a findById
            listOf(
                Butaca(
                    id = "A1",
                    tipo = Tipo.NORMAL,
                    estado = Estado.LIBRE,
                    precio = 10.0,
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now(),
                )
            )
        )
        whenever(cuentaRepo.findById("email 1")).thenReturn(
            Cuenta(
                nombre = "Cliente 1",
                apellido = "Apellido 1",
                email = "email 1",
                imagen = "sin_imagen",
                password = "1234",
                tipo = TipoCuenta.USUARIO
            )
        )
        whenever(productoRepo.findById("1")).thenReturn(
            Producto(
                id = "1",
                nombre = "Producto 1",
                image = "no-image",
                precio = 10.0,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                stock = 20,
                tipo = TipoProducto.COMIDA,
                isDeleted = false
            )
        )
        repo.save(venta)
        //Act
        val result = repo.delete("1")

        //Assert
        assertTrue(result != null)
        assertTrue(result?.isDeleted == true)
        assertTrue(result?.lineasVenta?.filter { !it.isDeleted }?.size == 1)
    }

    @Test
    fun deleteDevuelveNullCuandoIntentasBorrarUnaVentaQueNoExiste() {
        //Act
        val result = repo.delete("1")

        //Assert
        assertTrue(result == null)
    }
}