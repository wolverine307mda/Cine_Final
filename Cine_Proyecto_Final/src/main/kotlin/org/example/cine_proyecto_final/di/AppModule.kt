package org.example.cine_proyecto_final.di

import org.example.cine_final.cuentas.servicio.storage.CuentaStorage
import org.example.cine_final.cuentas.servicio.storage.json.CuentaStorageJson
import org.example.cine_final.productos.servicio.storage.ProductoStorage
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
import org.example.cine_proyecto_final.viewmodels.sesion.SesionViewModel
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteSeleccionProductosViewModel
import org.example.cine_proyecto_final.viewmodels.administrador.AdministradorGestorProductosViewModel
import org.example.cine_proyecto_final.productos.repository.ProductoRepositoryImpl
import org.example.cine_proyecto_final.productos.repository.ProductosRepository
import org.example.cine_proyecto_final.productos.servicio.database.ProductoServicio
import org.example.cine_proyecto_final.productos.servicio.database.ProductoServicioImpl
import org.example.cine_proyecto_final.productos.servicio.storage.csv.ProductoStorageCSV
import org.example.cine_proyecto_final.productos.servicio.storage.json.ProductoStorageJson
import org.example.cine_proyecto_final.productos.servicio.storage.json.ProductoStorageJsonImpl
import org.example.cine_proyecto_final.productos.servicio.storage.csv.ProductoStorageCSVImpl
import org.example.cine_proyecto_final.productos.servicio.storage.ProductoStorageImpl
import org.example.cine_proyecto_final.productos.validador.ProductoValidator
import org.koin.core.module.dsl.bind

val appModule = module {
    singleOf(::AppConfig)

    singleOf(::SqlDelightManager)

    singleOf(::ButacaValidator)

    singleOf(::ProductoValidator)

    singleOf(::VentaValidator)

    singleOf(::CuentaValidator)

    singleOf(::SesionViewModel)

    singleOf(::ClienteSeleccionButacaViewModel)

    singleOf(::ClienteSeleccionProductosViewModel)

    singleOf(::AdministradorGestorProductosViewModel)

    singleOf(::ProductoRepositoryImpl) {
        bind<ProductosRepository>()
    }

    singleOf(::ProductoServicioImpl) {
        bind<ProductoServicio>()
    }

    singleOf(::ProductoStorageCSVImpl) {
        bind<ProductoStorageCSV>()
    }

    singleOf(::ProductoStorageJsonImpl) {
        bind<ProductoStorageJson>()
    }

    singleOf(::ProductoStorageImpl) {
        bind<ProductoStorage>()
    }

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