package de.visualdigits.graffitomat.di

import de.visualdigits.graffitomat.data.http.HttpClientFactory
import de.visualdigits.graffitomat.presentation.model.GraffitomatViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

val testHomeDirectory = File("E:\\temp\\.planespottersfriend")

val testModule = module {

    single(named("homeDirectory")) { testHomeDirectory }

    singleOf(::GraffitomatViewModel)

    single { HttpClientFactory.create(get()) }
}
