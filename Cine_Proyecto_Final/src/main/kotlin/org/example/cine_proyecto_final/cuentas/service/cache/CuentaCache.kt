package org.example.cine_proyecto_final.cuentas.service.cache

import org.example.cine_proyecto_final.cuentas.models.Cuenta

interface CuentaCache {
    fun put(key: String, value: Cuenta)
    fun get(key: String): Cuenta?
    fun remove(key: String): Cuenta?
}