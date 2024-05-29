package org.example.cine_proyecto_final.butacas.repository

import database.DatabaseQueries
import org.junit.jupiter.api.Test
import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.butacas.models.Estado
import org.example.cine_proyecto_final.butacas.models.Tipo
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.database.SqlDelightManager
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

class ButacaRepositoryImplTest {

    @Mock
    lateinit var config: AppConfig

    private lateinit var db: SqlDelightManager
    private lateinit var databaseQueries: DatabaseQueries
    private lateinit var repositorio: ButacaRepositoryImpl

    @BeforeEach
    fun setUp() {
        // Mockeando la base de datos para que cree una base de datos en memoria
        whenever(config.databaseUrl).thenReturn("jdbc:sqlite::memory:")
        whenever(config.databaseInMemory).thenReturn(true)
        whenever(config.databaseInit).thenReturn(true)
        whenever(config.databaseRemoveData).thenReturn(true)

        // Iniciando la base de datos
        db = SqlDelightManager(config)
        databaseQueries = db.databaseQueries
        repositorio = ButacaRepositoryImpl(db)

        // Introduciendo butacas
        databaseQueries.transaction {
            databaseQueries.insertButaca(
                id = "A1",
                estado = "LIBRE",
                precio = 10.0,
                tipo = "NORMAL",
                id_venta = null,
                createdAt = "2023-01-01T00:00:00.000",
                updatedAt = "2023-01-01T00:00:00.000"
            )
            databaseQueries.insertButaca(
                id = "B1",
                estado = "LIBRE",
                precio = 10.0,
                tipo = "NORMAL",
                id_venta = null,
                createdAt = "2023-01-01T00:00:00.000",
                updatedAt = "2023-01-01T00:00:00.000"
            )
        }
    }

    @Test
    fun findAllDevuelveTodasLasButacas() {
        val butacas = repositorio.findAll();

        // Assert
        assertEquals(2, butacas.size)
        assertTrue(butacas.any { it.id == "A1" })
        assertTrue(butacas.any { it.id == "B1" })
    }

    @Test
    fun findByIdConButacaQueExiste() {
        // Creamos una nueva butaca y la buscamos para comprobar que la devuelta es igual que la creada.

        val butaca = Butaca(
            id = "B1",
            estado = Estado.LIBRE,
            tipo = Tipo.NORMAL,
            precio = 10.0,
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.parse("2023-01-01T00:00:00.000")
        )
        val butacaEncontrada = repositorio.findById(butaca.id)
        assertEquals(butacaEncontrada, butaca)
    }

    @Test
    fun saveButacaNuevaVip() {
        // Creamos una nueva butaca vip y precio 15
        val nuevaButaca = Butaca(
            id = "C1",
            estado = Estado.LIBRE,
            tipo = Tipo.VIP,
            precio = 15.0,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        // Guardamos la nueva butaca
        val butacaGuardada = repositorio.save(nuevaButaca, null)

        // Verificar que la butaca se ha guardado correctamente
        assertNotNull(butacaGuardada)
        assertEquals(nuevaButaca.id, butacaGuardada?.id)
        assertEquals(nuevaButaca.estado, butacaGuardada?.estado)
        assertEquals(nuevaButaca.tipo, butacaGuardada?.tipo)
        assertEquals(nuevaButaca.precio, butacaGuardada?.precio)

        // Verificar que la butaca se puede encontrar en la base de datos
        val butacaEncontrada = repositorio.findById(nuevaButaca.id)
        assertNotNull(butacaEncontrada)
        assertEquals(nuevaButaca.id, butacaEncontrada?.id)
    }

    @Test
    fun updateButacaExistente() {

        // Creamos una nueva butaca con un estado actualizado a fuera de servicio.
        val butacaActualizada = Butaca(
            id = "B1",
            estado = Estado.FUERA_DE_SERVICIO,
            tipo = Tipo.NORMAL,
            precio = 10.0,
            createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000"),
            updatedAt = LocalDateTime.now()
        )

        // Act
        val butaca = repositorio.update("B1", butacaActualizada, null)

        // Assert
        assertNotNull(butaca)
        assertEquals("B1", butaca?.id)
        assertEquals(Estado.FUERA_DE_SERVICIO, butaca?.estado)
        assertEquals(Tipo.NORMAL, butaca?.tipo)
        assertEquals(10.0, butaca?.precio)
    }

    @Test
    fun getAllByVentaIdConButacas() {

    }
}
