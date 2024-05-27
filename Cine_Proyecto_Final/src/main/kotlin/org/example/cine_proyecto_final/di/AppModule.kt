package org.example.cine_proyecto_final.di

import org.example.cine_final.cuentas.servicio.storage.CuentaStorage
import org.example.cine_final.cuentas.servicio.storage.json.CuentaStorageJson
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.butacas.validator.ButacaValidator
import org.example.cine_proyecto_final.cuentas.service.cache.CuentaCache
import org.example.cine_proyecto_final.ventas.validator.VentaValidator
import org.example.cine_proyecto_final.cuentas.validator.CuentaValidator
import org.example.cine_proyecto_final.cuentas.service.cache.CuentaCacheImpl
import org.example.cine_proyecto_final.cuentas.repository.CuentaRepository
import org.example.cine_proyecto_final.cuentas.repository.CuentaRepositoryImpl
import org.example.cine_proyecto_final.cuentas.service.database.CuentaServicio
import org.example.cine_proyecto_final.cuentas.service.database.CuentaServicioImpl
import org.example.cine_proyecto_final.cuentas.service.storage.CuentaStorageImpl
import org.example.cine_proyecto_final.cuentas.service.storage.json.CuentaStorageJsonImpl
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteSeleccionButacaViewModel
import org.example.cine_proyecto_final.viewmodels.sesion.SesionInicioViewModel
import org.example.cine_proyecto_final.viewmodels.sesion.SesionRegistrarseViewModel
import org.koin.core.module.dsl.bind
import org.koin.dsl.bind

val appModule = module {
    singleOf(::AppConfig)

    singleOf(::SqlDelightManager)

    singleOf(::ButacaValidator)

    singleOf(::VentaValidator)

    singleOf(::CuentaValidator)

    singleOf(::SesionInicioViewModel)

    singleOf(::SesionRegistrarseViewModel)

    singleOf(::ClienteSeleccionButacaViewModel)

    singleOf(::CuentaCacheImpl) {
        bind<CuentaCache>()
    }

    singleOf(::CuentaRepositoryImpl) {
        bind<CuentaRepository>()
    }

    singleOf(::CuentaServicioImpl) {
        bind<CuentaServicio>()
    }

    singleOf(::CuentaStorageImpl) {
        bind<CuentaStorage>()
    }

    singleOf(::CuentaStorageJsonImpl) {
        bind<CuentaStorageJson>()
    }

}