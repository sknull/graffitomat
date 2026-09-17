package de.visualdigits.graffitomat.di

import de.visualdigits.graffitomat.data.repository.ImageCache
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

actual val homeDirectory: String
    get() = File(System.getProperty("user.home"), ".graffitomat-client").canonicalPath

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single {
            ImageCache(
                basePath = get<String>(named("homeDirectory")),
                context = coil3.PlatformContext.INSTANCE
            )
        }
    }
