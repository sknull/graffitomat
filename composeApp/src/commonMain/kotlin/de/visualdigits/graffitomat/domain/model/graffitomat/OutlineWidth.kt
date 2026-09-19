package de.visualdigits.graffitomat.domain.model.graffitomat

import de.visualdigits.common.domain.model.configuration.keyfactory.KeyFactory
import de.visualdigits.common.domain.model.ui.StringResourceEnumerable
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.enum_outlinewidth_medium
import de.visualdigits.compose.resources.enum_outlinewidth_none
import de.visualdigits.compose.resources.enum_outlinewidth_thick
import de.visualdigits.compose.resources.enum_outlinewidth_thin
import org.jetbrains.compose.resources.DrawableResource

enum class OutlineWidth(
    override val uiText: UiText,
    override val drawableResourceId: DrawableResource?,
    val width: Float?
) : StringResourceEnumerable<OutlineWidth> {

    WIDTH_THICK(UiText.StringResourceId(Res.string.enum_outlinewidth_thick), null, 5.0f),
    WIDTH_MEDIUM(UiText.StringResourceId(Res.string.enum_outlinewidth_medium), null, 3.0f),
    WIDTH_THIN(UiText.StringResourceId(Res.string.enum_outlinewidth_thin), null, 1.0f),
    WIDTH_NONE(UiText.StringResourceId(Res.string.enum_outlinewidth_none), null, 0.0f)
    ;

    companion object : KeyFactory<OutlineWidth> {

        override val options: List<Triple<OutlineWidth, UiText?, DrawableResource?>> = entries.map { e -> Triple(e, e.uiText, e.drawableResourceId) }

        override fun fromString(value: String?): OutlineWidth? {
            return value?.let { v -> OutlineWidth.valueOf(v) }
        }

        override fun fromValue(value: Any?): OutlineWidth? {
            return when (value) {
                is String -> fromString(value)
                is OutlineWidth -> value
                else -> null
            }
        }

        override fun stringValue(value: Any?): String? {
            return (value as? OutlineWidth)?.name
        }
    }
}
