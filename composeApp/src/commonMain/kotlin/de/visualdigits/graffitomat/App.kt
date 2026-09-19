package de.visualdigits.graffitomat

import androidx.compose.runtime.Composable
import co.touchlab.kermit.Logger
import coil3.compose.setSingletonImageLoaderFactory
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.graffitomat.data.provider.HostUrlProvider
import de.visualdigits.graffitomat.data.repository.ImageCache
import de.visualdigits.graffitomat.presentation.model.GraffitomatViewModel
import de.visualdigits.graffitomat.presentation.page.MainPage
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.qualifier.named

@Composable
fun App(
    platformType: PlatformType,
) {
    val viewModel = koinViewModel<GraffitomatViewModel>()
    val isDevMode = koinInject<Boolean>(named("isDevMode"))
    val hostUrlProvider = koinInject<HostUrlProvider>()

    if (!isDevMode) {
        Logger.i("Enabling image cache")
        val imageCache = koinInject<ImageCache>()
        setSingletonImageLoaderFactory { _ ->
            imageCache.getImageLoader()
        }
    } else {
        Logger.i("Devmode - not enabling image cache")
    }

    MainPage(
        viewModel = viewModel,
        platformType = platformType,
        hostUrlProvider = hostUrlProvider
    )
}
