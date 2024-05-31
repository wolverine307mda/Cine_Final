package org.example.cine_proyecto_final.cuentas.service.cache

import org.example.cine_proyecto_final.butacas.models.Butaca
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.cuentas.models.Cuenta
import org.lighthousegames.logging.logging

private val logger = logging()

class CuentaCacheImpl(
    private val appConfig: AppConfig
) : CuentaCache {
    private val cache = mutableMapOf<String,Cuenta>()

    /**
     * Añade un valor a la caché
     * @param key la llave del valor que se quiere añadir
     * @param value el valor que se quiere añadir
     */
    override fun put(key: String, value: Cuenta) {
        logger.debug { "Añadiendo la cuenta ${value.email} a la caché" }
        cache[key] = value
    }

    /**
     * Busca el valor atado a esa llave en la caché, si no existe devolverá null
     * @param key la llave del valor que estás buscando
     */
    override fun get(key: String): Cuenta? {
        logger.debug { "Buscando la cuenta con id: $key en la caché" }
        return cache[key]
    }

    /**
     * Borra el valor atado a esa llave en la cache, si no existe devolverá null
     * @param key la llave del valor que estás intentando borrar
     */
    override fun remove(key: String): Cuenta? {
        logger.debug { "Borrando la cuenta con id: $key de la caché" }
        return cache.remove(key)
    }
}