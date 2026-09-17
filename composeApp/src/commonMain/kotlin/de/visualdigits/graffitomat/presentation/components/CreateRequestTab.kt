package de.visualdigits.graffitomat.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.presentation.components.form.ConfigurationEditForm
import de.visualdigits.common.presentation.components.modifier.beveledBorder
import de.visualdigits.common.presentation.components.util.conditional
import de.visualdigits.graffitomat.domain.mapper.toQueryParams
import de.visualdigits.graffitomat.domain.model.graffitomat.GraffitomatConstants.HOST_URL
import de.visualdigits.graffitomat.presentation.model.GraffitomatAction
import de.visualdigits.graffitomat.presentation.model.GraffitomatViewModel


@Composable
fun CreateRequestTab(
    viewModel: GraffitomatViewModel,
    platformType: PlatformType,
    onAction: (GraffitomatAction) -> Unit
) {

    val editedRequest by viewModel.editedRequest.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        ConfigurationEditForm(
            modifier = Modifier
                .fillMaxSize(),
            platformType = platformType,
            configuration = editedRequest!!,
            scrollbarModifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .width(10.dp)
                .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
            onValueChange = { keyValue ->
                onAction(
                    GraffitomatAction.OnCreateRequestValueChanged(
                        keyValue = keyValue
                    )
                )
            },
            onCancelClick = {
                onAction(
                    GraffitomatAction.OnCreateRequestCancelClick()
                )
            },
            onOkClick = {
                onAction(
                    GraffitomatAction.OnCreateRequestOkClick()
                )
            },
            headerContent = {
                val url = "$HOST_URL/preview?${editedRequest.toQueryParams()}"
                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    height = 100.dp,
                    url = url,
                    contentDescription = "",
                    contentScale = ContentScale.FillHeight,
                    maxImageSize = 1024
                )
            }
        )
    }
}
