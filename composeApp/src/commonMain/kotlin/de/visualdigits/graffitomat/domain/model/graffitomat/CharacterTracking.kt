package de.visualdigits.graffitomat.domain.model.graffitomat

import de.visualdigits.common.domain.model.configuration.keyfactory.KeyFactory
import de.visualdigits.common.domain.model.ui.StringResourceEnumerable
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.enum_tracking_heavy
import de.visualdigits.compose.resources.enum_tracking_light
import de.visualdigits.compose.resources.enum_tracking_medium
import de.visualdigits.compose.resources.enum_tracking_none
import org.jetbrains.compose.resources.DrawableResource

enum class CharacterTracking(
    override val uiText: UiText,
    override val drawableResourceId: DrawableResource?,
    val tracking: Float?
) : StringResourceEnumerable<CharacterTracking> {

    TRACKING_HEAVY(UiText.StringResourceId(Res.string.enum_tracking_heavy), null, -0.5f),
    TRACKING_MEDIUM(UiText.StringResourceId(Res.string.enum_tracking_medium), null, -0.3f),
    TRACKING_LIGHT(UiText.StringResourceId(Res.string.enum_tracking_light), null, -0.1f),
    TRACKING_NONE(UiText.StringResourceId(Res.string.enum_tracking_none), null, 0.0f)
    ;

    companion object : KeyFactory<CharacterTracking> {

        override val options: List<Triple<CharacterTracking, UiText?, DrawableResource?>> = entries.map { e -> Triple(e, e.uiText, e.drawableResourceId) }

        override fun fromString(value: String?): CharacterTracking? {
            return value?.let { v -> valueOf(v) }
        }

        override fun fromValue(value: Any?): CharacterTracking? {
            return when (value) {
                is String -> fromString(value)
                is CharacterTracking -> value
                else -> null
            }
        }

        override fun stringValue(value: Any?): String? {
            return (value as? CharacterTracking)?.name
        }
    }
}
