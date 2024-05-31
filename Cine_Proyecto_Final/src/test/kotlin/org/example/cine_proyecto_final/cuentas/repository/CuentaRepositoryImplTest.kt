package org.example.cine_proyecto_final.cuentas.repository

import database.DatabaseQueries
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.models.TipoCuenta
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.kotlin.whenever
import org.mockito.quality.Strictness
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CuentaRepositoryImplTest {

    @Mock
    lateinit var config: AppConfig

    private lateinit var db: SqlDelightManager
    private lateinit var databaseQueries: DatabaseQueries
    private lateinit var repository: CuentaRepositoryImpl

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
        repository = CuentaRepositoryImpl(db)

        // Agregando Cuentas
        databaseQueries.transaction {
            databaseQueries.insertCuenta(
                email = "admin@admin.com",
                nombre = "admin",
                apellido = "uno",
                updatedAt = "2021-01-01T00:00:00.000",
                createdAt = "2021-01-01T00:00:00.000",
                imagen = "admin.jpg",
                password = "contrasenia",
                tipo = "ADMINISTRADOR"
            )
            databaseQueries.insertCuenta(
                email = "usuario@usuario.com",
                nombre = "usuario",
                apellido = "uno",
                updatedAt = "2021-01-01T00:00:00.000",
                createdAt = "2021-01-01T00:00:00.000",
                imagen = "useer.jpg",
                password = "contrasenia2",
                tipo = "USUARIO"
            )
        }
    }

    @Test
    fun buscarEmailQueExiste() {
        val result = repository.findById("admin@admin.com")
        assertNotNull(result)
        assertEquals("admin@admin.com", result?.email)
    }

    @Test
    fun buscarEmailQueNoExiste() {
        val result = repository.findById("nonexistent@example.com")
        assertNull(result)
    }

    @Test
    fun GuardaUnaCuentaNuevaQueNoExiste() {
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

        repository.save(cuenta)

        val result = repository.findById("admin@admin.com")
        assertNotNull(result)
        assertEquals("admin@admin.com", result.email)
    }

    @Test
    fun NoGuardaUnaCuentaQueYaExiste() {
        val existingAccount = Cuenta(
            email = "admin@admin.com",
            nombre = "admin",
            apellido = "uno",
            updatedAt = LocalDateTime.now(),
            createdAt = LocalDateTime.parse("2021-01-01T00:00:00.000"),
            imagen = "admin.jpg",
            password = "contrasenia",
            tipo = TipoCuenta.ADMINISTRADOR
        )
        val result = repository.save(existingAccount)
        assertNull(result)
    }

    @Test
    fun ActualizaUnaCuentaQueExiste() {
        val cuenta = Cuenta(
            email = "admin@admin.com",
            nombre = "administrador",
            apellido = "apellido",
            updatedAt = LocalDateTime.now(),
            createdAt = LocalDateTime.parse("2021-01-01T00:00:00.000"),
            imagen = "upAdmin.jpg",
            password = "Contrasenia",
            tipo = TipoCuenta.ADMINISTRADOR
        )
        val result = repository.update("admin@admin.com", cuenta)
        assertNotNull(result)
        assertEquals("administrador", result?.nombre)
        assertEquals("apellido", result?.apellido)
        assertEquals("upAdmin.jpg", result?.imagen)
    }

    @Test
    fun NoActualizaUnaCuentaQueNoExiste() {
        val cuenta = Cuenta(
            email = "noexiste@gmail.com",
            nombre = "no",
            apellido = "existe",
            updatedAt = LocalDateTime.now(),
            createdAt = LocalDateTime.now(),
            imagen = "no.jpg",
            password = "password",
            tipo = TipoCuenta.USUARIO
        )
        val result = repository.update("nonexistent@example.com", cuenta)
        assertNull(result)
    }
}
