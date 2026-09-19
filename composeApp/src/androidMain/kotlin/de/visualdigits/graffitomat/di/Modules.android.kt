package de.visualdigits.graffitomat.di

import de.visualdigits.graffitomat.data.repository.ImageCache
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val homeDirectory: String
    get() = ""

actual val isDevMode: Boolean
    get() = false

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }

        single { ImageCache(context = get()) }
    }
