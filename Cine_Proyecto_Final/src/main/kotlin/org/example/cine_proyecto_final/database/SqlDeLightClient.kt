package org.example.cine_proyecto_final.database

import org.example.cine_proyecto_final.config.AppConfig
import org.lighthousegames.logging.logging

class SqlDeLightClient(
    private val appConfig: AppConfig
) {
    companion object {
        private val logger by lazy { logging() }
    }

    // Rest of the class implementation
}
