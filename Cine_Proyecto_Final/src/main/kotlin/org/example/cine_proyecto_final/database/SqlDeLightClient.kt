package org.example.cine_proyecto_final.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.example.cine_proyecto_final.config.AppConfig
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files

private val logger = logging()

class SqlDeLightClient(
    private val appConfig: AppConfig
) {

}
