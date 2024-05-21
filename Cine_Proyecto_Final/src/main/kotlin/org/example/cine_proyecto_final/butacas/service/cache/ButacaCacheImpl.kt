package org.example.cine_proyecto_final.butacas.service.cache

import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.config.AppConfig
import org.lighthousegames.logging.logging

private val logger = logging()

class ButacaCacheImpl(
    private val appConfig: AppConfig
) : ButacaCache {
    private val cache = mutableMapOf<String,Butaca>()

    override fun put(key: String, value: Butaca) {
        logger.debug { "put $key" }
        if (cache.size >= appConfig.cacheSize) {
            logger.debug { "Cache completa, eliminando el primer elemento" }
            val oldestKey = cache.keys.first()
            cache.remove(oldestKey)
        }
        cache[key] = value
    }

    override fun get(key : String): Butaca? {
        logger.debug { "get $key" }
        return cache[key]
    }

    override fun remove(key: String): Butaca? {
        logger.debug { "remove $key" }
        return cache.remove(key)
    }
}