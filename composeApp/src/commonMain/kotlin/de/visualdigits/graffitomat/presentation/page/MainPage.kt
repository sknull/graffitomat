package de.visualdigits.graffitomat.presentation.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.background_graffitomat
import de.visualdigits.graffitomat.data.provider.HostUrlProvider
import de.visualdigits.graffitomat.domain.mapper.toQueryParams
import de.visualdigits.graffitomat.presentation.components.CreateRequestTab
import de.visualdigits.graffitomat.presentation.components.ErrorCard
import de.visualdigits.graffitomat.presentation.components.Image
import de.visualdigits.graffitomat.presentation.model.GraffitomatViewModel
import de.visualdigits.graffitomat.presentation.style.AppCompositionProvider
import de.visualdigits.graffitomat.presentation.style.BackgroundColor
import de.visualdigits.graffitomat.presentation.style.MyShapes
import de.visualdigits.graffitomat.presentation.style.SpotColor
import de.visualdigits.graffitomat.presentation.style.TextColor
import de.visualdigits.graffitomat.presentation.style.gap
import de.visualdigits.graffitomat.presentation.style.theme
import de.visualdigits.graffitomat.presentation.style.typography
import org.jetbrains.compose.resources.imageResource

@Composable
fun MainPage(
    viewModel: GraffitomatViewModel,
    platformType: PlatformType,
    hostUrlProvider: HostUrlProvider
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val screenWidth = maxWidth
        val sizeFactor = when {
            screenWidth < 500.dp -> 0.9f
            else -> 1.0f
        }

        MaterialTheme(
            colorScheme = theme(
                backgroundColor = BackgroundColor,
                textColor = TextColor,
                spotColor = SpotColor
            ),
            typography = typography(
                textColor = TextColor,
                sizeFactor = sizeFactor
            ),
            shapes = MyShapes
        ) {
            AppCompositionProvider {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .safeDrawingPadding()
                ) {
                    // background image
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .paint(
                                painter = BitmapPainter(imageResource(Res.drawable.background_graffitomat)),
                                alignment = Alignment.TopStart,
                                contentScale = ContentScale.Crop
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(MaterialTheme.shapes.gap)
                    ) {
                        if (state.uiMessage != null) {
                            ErrorCard(
                                errorMessage = state.uiMessage,
                                severity = state.uiMessageSeverity,
                                shapeContainer = MaterialTheme.shapes.small
                            )
                        }

                        val url = "${hostUrlProvider.hostUrl}/preview?${state.editedRequest.toQueryParams()}"
                        Logger.i("preview: $url")
                        Image(
                            modifier = Modifier
                                .fillMaxWidth(),
                            url = url,
                            height = 100.dp,
                            contentScale = ContentScale.FillHeight,
                            contentDescription = "",
                            maxImageSize = 1024,
                        )

                        CreateRequestTab(
                            state = state,
                            platformType = platformType,
                            onAction = viewModel::onAction
                        )
                    }
                }
            }
        }
    }
}
