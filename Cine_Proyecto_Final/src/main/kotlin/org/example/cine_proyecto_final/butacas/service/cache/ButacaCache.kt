package org.example.cine_proyecto_final.butacas.service.cache

import org.example.cine_proyecto_final.butacas.models.Butaca

interface ButacaCache {
    fun put(key: String, value: Butaca)
    fun get(key: String): Butaca?
    fun remove(key: String): Butaca?
}