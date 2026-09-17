package de.visualdigits.graffitomat.presentation.style

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.model.form.DateTimeFieldResources
import de.visualdigits.common.domain.model.form.FileChooserResources
import de.visualdigits.common.domain.model.form.FormFieldResources
import de.visualdigits.common.domain.model.form.FormResources
import de.visualdigits.common.domain.model.form.LocalDateTimeFieldResources
import de.visualdigits.common.domain.model.form.LocalFileChooserResources
import de.visualdigits.common.domain.model.form.LocalFormFieldResources
import de.visualdigits.common.domain.model.form.LocalFormResources
import de.visualdigits.common.domain.model.form.dateDefaultColors
import de.visualdigits.common.domain.model.form.timeDefaultColors
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.common.presentation.components.util.LocalSwitchColors
import de.visualdigits.common.presentation.components.util.switchBoxColors
import de.visualdigits.common.presentation.model.LocalPlatformScrollbarStyle
import de.visualdigits.common.presentation.model.PlatformScrollbarStyle
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.cancel
import de.visualdigits.compose.resources.icon_alarm_24px
import de.visualdigits.compose.resources.icon_calendar_month_24px
import de.visualdigits.compose.resources.icon_cancel_24px
import de.visualdigits.compose.resources.icon_check_small_24px
import de.visualdigits.compose.resources.icon_folder_open_24px
import de.visualdigits.compose.resources.icon_visibility_24px
import de.visualdigits.compose.resources.ok
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCompositionProvider(
    content: @Composable () -> Unit
) {
    val platformScrollbarStyle = PlatformScrollbarStyle(
        minimalHeight = 16.dp,
        thickness = 8.dp,
        shape = RoundedCornerShape(4.dp),
        hoverDurationMillis = 300,
        unhoverColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
        hoverColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    )
    val formResource = FormResources(
        backgroundColor = Color.Transparent,
        buttonShape = MaterialTheme.shapes.extraSmall,
        iconOk = painterResource(Res.drawable.icon_check_small_24px),
        tooltipOk = UiText.StringResourceId(Res.string.ok),
        iconCancel = painterResource(Res.drawable.icon_cancel_24px),
        tooltipCancel = UiText.StringResourceId(Res.string.cancel),
        buttonColor = Color.Black,
        containerShape = MaterialTheme.shapes.small,
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center,
    )
    val formFieldResources = FormFieldResources(
        fieldHeight = 50.dp,
        textStyle = MaterialTheme.typography.bodyMedium,
        iconTint = SpotColor,
        shape = MaterialTheme.shapes.extraSmall,
        focusedBorderColor = MaterialTheme.colorScheme.outline,
        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        visibilityIcon = painterResource(Res.drawable.icon_visibility_24px),
    )
    val fileChooserResources = FileChooserResources(
        iconFolder = painterResource(Res.drawable.icon_folder_open_24px),
        titleDirectories = "Choose Directory",
        titleFiles = "Choose File",
    )
    val switchColors = switchBoxColors()

    val dateTimeFieldResources = DateTimeFieldResources(
        datePickerColors = dateDefaultColors(),
        timePickerColors = timeDefaultColors().copy(
            clockDialColor = Color.White,
        ),
        dateIcon = painterResource(Res.drawable.icon_calendar_month_24px),
        timeIcon = painterResource(Res.drawable.icon_alarm_24px),
        labelOk = UiText.DynamicString("Ok"),
        labelCancel = UiText.DynamicString("Cancel")
    )

    CompositionLocalProvider(
        LocalFormResources provides formResource,
        LocalFormFieldResources provides formFieldResources,
        LocalPlatformScrollbarStyle provides platformScrollbarStyle,
        LocalFileChooserResources provides fileChooserResources,
        LocalSwitchColors provides switchColors,
        LocalDateTimeFieldResources provides dateTimeFieldResources
    ) {
        content()
    }
}
