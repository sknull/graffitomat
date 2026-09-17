package de.visualdigits.graffitomat.di

import de.visualdigits.graffitomat.data.datasource.BackendDataSource
import de.visualdigits.graffitomat.data.datasource.BackendKtorDataSource
import de.visualdigits.graffitomat.data.http.HttpClientFactory
import de.visualdigits.graffitomat.data.repository.DefaultBackendRepository
import de.visualdigits.graffitomat.domain.repository.BackendRepository
import de.visualdigits.graffitomat.presentation.model.GraffitomatViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

expect val homeDirectory: String

val sharedModule = module {

    single(named("homeDirectory")) { homeDirectory }

    singleOf(::GraffitomatViewModel)
    singleOf(::DefaultBackendRepository).bind<BackendRepository>()
    singleOf(::BackendKtorDataSource).bind<BackendDataSource>()

    single { HttpClientFactory.create(get()) }
}
