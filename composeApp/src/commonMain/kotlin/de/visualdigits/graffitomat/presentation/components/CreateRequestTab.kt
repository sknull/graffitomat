package de.visualdigits.graffitomat.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.model.form.LocalFormResources
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.common.presentation.components.form.ConfigurationEditForm
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.flag_de
import de.visualdigits.compose.resources.flag_en
import de.visualdigits.graffitomat.domain.model.type.Language
import de.visualdigits.graffitomat.presentation.model.GraffitomatAction
import de.visualdigits.graffitomat.presentation.model.GraffitomatState
import de.visualdigits.graffitomat.presentation.style.gap
import kotlinx.coroutines.NonCancellable.key
import org.jetbrains.compose.resources.painterResource


@Composable
fun CreateRequestTab(
    state: GraffitomatState,
    platformType: PlatformType,
    onAction: (GraffitomatAction) -> Unit
) {

    val formResources = LocalFormResources.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        key(state.currentLanguage) {
            ConfigurationEditForm(
                modifier = Modifier
                    .fillMaxSize(),
                platformType = platformType,
                configuration = state.editedRequest!!,
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
                    ) {
                        IndicatorButton(
                            width = 50.dp,
                            height = 50.dp,
                            buttonColor = formResources.buttonColor,
                            shape = formResources.buttonShape,
                            content = {
                                Image(
                                    painter = painterResource(Res.drawable.flag_de),
                                    contentDescription = null
                                )
                            },
                            indicatorPosition = Alignment.BottomCenter,
                            indicatorColor = Color.Green,
                            selected = state.currentLanguage == Language.DE,
                            onClick = {
                                onAction(GraffitomatAction.OnLanguageClicked(Language.DE))
                            }
                        )

                        IndicatorButton(
                            width = 50.dp,
                            height = 50.dp,
                            buttonColor = formResources.buttonColor,
                            shape = formResources.buttonShape,
                            content = {
                                Image(
                                    painter = painterResource(Res.drawable.flag_en),
                                    contentDescription = null
                                )
                            },
                            indicatorPosition = Alignment.BottomCenter,
                            indicatorColor = Color.Green,
                            selected = state.currentLanguage == Language.EN,
                            onClick = {
                                onAction(GraffitomatAction.OnLanguageClicked(Language.EN))
                            }
                        )
                    }
                }
            )
        }
    }
}
