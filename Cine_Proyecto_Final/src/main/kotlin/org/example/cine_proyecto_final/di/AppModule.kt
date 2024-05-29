package org.example.cine_proyecto_final.di

import org.example.cine_final.cuentas.servicio.storage.CuentaStorage
import org.example.cine_final.cuentas.servicio.storage.json.CuentaStorageJson
import org.example.cine_final.productos.servicio.storage.ProductoStorage
import org.example.cine_proyecto_final.butacas.repository.ButacaRepository
import org.example.cine_proyecto_final.butacas.repository.ButacaRepositoryImpl
import org.example.cine_proyecto_final.butacas.service.database.ButacaService
import org.example.cine_proyecto_final.butacas.service.database.ButacaServiceImpl
import org.example.cine_proyecto_final.butacas.service.storage.ButacaStorage
import org.example.cine_proyecto_final.butacas.service.storage.ButacaStorageImpl
import org.example.cine_proyecto_final.butacas.service.storage.csv.ButacaStorageCsv
import org.example.cine_proyecto_final.butacas.service.storage.csv.ButacaStorageCsvImpl
import org.example.cine_proyecto_final.butacas.service.storage.json.ButacaStorageJson
import org.example.cine_proyecto_final.butacas.service.storage.json.ButacaStorageJsonImpl
import org.example.cine_proyecto_final.butacas.validator.ButacaValidator
import org.example.cine_proyecto_final.config.AppConfig
import org.example.cine_proyecto_final.cuentas.repository.CuentaRepository
import org.example.cine_proyecto_final.cuentas.repository.CuentaRepositoryImpl
import org.example.cine_proyecto_final.cuentas.service.cache.CuentaCache
import org.example.cine_proyecto_final.cuentas.service.cache.CuentaCacheImpl
import org.example.cine_proyecto_final.cuentas.service.database.CuentaServicio
import org.example.cine_proyecto_final.cuentas.service.database.CuentaServicioImpl
import org.example.cine_proyecto_final.cuentas.service.storage.CuentaStorageImpl
import org.example.cine_proyecto_final.cuentas.service.storage.json.CuentaStorageJsonImpl
import org.example.cine_proyecto_final.cuentas.validator.CuentaValidator
import org.example.cine_proyecto_final.database.SqlDelightManager
import org.example.cine_proyecto_final.productos.repository.ProductoRepositoryImpl
import org.example.cine_proyecto_final.productos.repository.ProductosRepository
import org.example.cine_proyecto_final.productos.servicio.database.ProductoServicio
import org.example.cine_proyecto_final.productos.servicio.database.ProductoServicioImpl
import org.example.cine_proyecto_final.productos.servicio.storage.ProductoStorageImpl
import org.example.cine_proyecto_final.productos.servicio.storage.csv.ProductoStorageCSV
import org.example.cine_proyecto_final.productos.servicio.storage.csv.ProductoStorageCSVImpl
import org.example.cine_proyecto_final.productos.servicio.storage.json.ProductoStorageJson
import org.example.cine_proyecto_final.productos.servicio.storage.json.ProductoStorageJsonImpl
import org.example.cine_proyecto_final.productos.validador.ProductoValidator
import org.example.cine_proyecto_final.ventas.validator.VentaValidator
import org.example.cine_proyecto_final.viewmodels.administrador.AdministradorGestorButacasViewModel
import org.example.cine_proyecto_final.viewmodels.administrador.AdministradorGestorProductosViewModel
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteSeleccionButacaViewModel
import org.example.cine_proyecto_final.viewmodels.cliente.ClienteSeleccionProductosViewModel
import org.example.cine_proyecto_final.viewmodels.sesion.SesionViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


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

    singleOf(::ButacaRepositoryImpl) {
        bind<ButacaRepository>()
    }
    
    singleOf(::AdministradorGestorButacasViewModel)

    singleOf(::ProductoRepositoryImpl) {
        bind<ProductosRepository>()
    }

    singleOf(::ButacaStorageImpl) {
        bind<ButacaStorage>()
    }

    singleOf(::ButacaStorageCsvImpl) {
        bind<ButacaStorageCsv>()
    }

    singleOf(::ButacaServiceImpl) {
        bind<ButacaService>()
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

    singleOf(::ButacaStorageJsonImpl) {
        bind<ButacaStorageJson>()
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

    singleOf(::ButacaRepositoryImpl) {
        bind<ButacaRepository>()
    }

    singleOf(::ButacaServiceImpl) {
        bind<ButacaService>()
    }

    singleOf(::ButacaStorageCsvImpl) {
        bind<ButacaStorageCsv>()
    }

    singleOf(::ButacaStorageJsonImpl) {
        bind<ButacaStorageJson>()
    }

    singleOf(::ButacaStorageImpl) {
        bind<ButacaStorage>()
    }
}