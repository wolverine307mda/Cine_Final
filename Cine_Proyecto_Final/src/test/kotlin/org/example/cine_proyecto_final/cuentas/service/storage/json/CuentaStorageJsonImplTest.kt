package org.example.cine_proyecto_final.cuentas.service.storage.json

import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.example.cine_proyecto_final.cuentas.models.TipoCuenta
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.time.LocalDateTime

class CuentaStorageJsonImplTest {
    private lateinit var storageJson: CuentaStorageJsonImpl
    private lateinit var myFile: File

    @BeforeEach
    fun setup() {
        storageJson = CuentaStorageJsonImpl()
    }

    @Test
    fun exportDevuelveUnitCuandoSeExportaCorrectamente(@TempDir tempDir: File) {
        val data = listOf(
            Cuenta(
                email = "usuario1@user.com",
                nombre = "Usuario",
                apellido = "Apellido",
                imagen = "imagen1.jpg",
                password = "contraseña123",
                tipo = TipoCuenta.USUARIO,
                createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000")
            )
        )

        val file = File(tempDir, "cuentas.json")

        val result = storageJson.export(data, file)

        assertTrue(result.isOk)
        assertEquals(Unit, result.value)
    }

    @Test
    fun importDevuelveListaDeCuentasCuandoSeImportaCorrectamente(@TempDir tempDir: File) {
        val data = listOf(
            Cuenta(
                email = "usuario@example.com",
                nombre = "Usuario",
                apellido = "Apellido",
                imagen = "imagen.jpg",
                password = "contraseña",
                tipo = TipoCuenta.USUARIO,
                createdAt = LocalDateTime.parse("2023-01-01T00:00:00.000")
            )
        )

        // Crear un archivo en el directorio temporal
        myFile = File(tempDir, "cuentas.json")

        // Exportar los datos al archivo
        storageJson.export(data, myFile)

        // Importar los datos del archivo
        val result = storageJson.import(myFile)

        // Verificar que la importación fue exitosa y los datos son correctos
        assertTrue(result.isOk, "El resultado de la importación es: ${result.value}")
         assertEquals(data, result.value)
    }

}
