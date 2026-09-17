package de.visualdigits.graffitomat

import androidx.compose.runtime.Composable
import coil3.compose.setSingletonImageLoaderFactory
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.graffitomat.data.repository.ImageCache
import de.visualdigits.graffitomat.presentation.model.GraffitomatViewModel
import de.visualdigits.graffitomat.presentation.page.MainPage
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(
    platformType: PlatformType
) {
    val imageCache = koinInject<ImageCache>()
    val viewModel = koinViewModel<GraffitomatViewModel>()

    setSingletonImageLoaderFactory { _ ->
        imageCache.getImageLoader()
    }

    MainPage(
        viewModel = viewModel,
        platformType = platformType
    )
}
