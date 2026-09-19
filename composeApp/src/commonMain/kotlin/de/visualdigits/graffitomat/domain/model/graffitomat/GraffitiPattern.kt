package de.visualdigits.graffitomat.domain.model.graffitomat

import de.visualdigits.common.domain.model.configuration.keyfactory.KeyFactory
import de.visualdigits.common.domain.model.ui.StringResourceEnumerable
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.enum_pattern_circles
import de.visualdigits.compose.resources.enum_pattern_circles_bottom
import de.visualdigits.compose.resources.enum_pattern_circles_mid
import de.visualdigits.compose.resources.enum_pattern_circles_top
import de.visualdigits.compose.resources.enum_pattern_circles_with_horizontal_lines
import de.visualdigits.compose.resources.enum_pattern_circles_with_vertical_lines
import de.visualdigits.compose.resources.enum_pattern_cuts
import de.visualdigits.compose.resources.enum_pattern_dots
import de.visualdigits.compose.resources.enum_pattern_dots_full
import de.visualdigits.compose.resources.enum_pattern_fire
import de.visualdigits.compose.resources.enum_pattern_none
import de.visualdigits.compose.resources.enum_pattern_skyline
import de.visualdigits.compose.resources.enum_pattern_waves
import de.visualdigits.compose.resources.enum_pattern_waves_with_line
import org.jetbrains.compose.resources.DrawableResource

enum class GraffitiPattern(
    override val uiText: UiText,
    override val drawableResourceId: DrawableResource?,
    val patternName: String?
) : StringResourceEnumerable<GraffitiPattern> {
    
    CIRCLES(UiText.StringResourceId(Res.string.enum_pattern_circles), null, "CIRCLES"),
    CIRCLES_WITH_HORIZONTAL_LINES(UiText.StringResourceId(Res.string.enum_pattern_circles_with_horizontal_lines), null, "CIRCLES_WITH_HORIZONTAL_LINES"),
    CIRCLES_WITH_VERTICAL_LINES(UiText.StringResourceId(Res.string.enum_pattern_circles_with_vertical_lines), null, "CIRCLES_WITH_VERTICAL_LINES"),
    CIRCLES_TOP(UiText.StringResourceId(Res.string.enum_pattern_circles_top), null, "CIRCLES_TOP"),
    CIRCLES_MID(UiText.StringResourceId(Res.string.enum_pattern_circles_mid), null, "CIRCLES_MID"),
    CIRCLES_BOTTOM(UiText.StringResourceId(Res.string.enum_pattern_circles_bottom), null, "CIRCLES_BOTTOM"),
    CUTS(UiText.StringResourceId(Res.string.enum_pattern_cuts), null, "CUTS"),
    DOTS(UiText.StringResourceId(Res.string.enum_pattern_dots), null, "DOTS"),
    DOTS_FULL(UiText.StringResourceId(Res.string.enum_pattern_dots_full), null, "DOTS_FULL"),
    FIRE(UiText.StringResourceId(Res.string.enum_pattern_fire), null, "FIRE"),
    SKYLINE(UiText.StringResourceId(Res.string.enum_pattern_skyline), null, "SKYLINE"),
    WAVES(UiText.StringResourceId(Res.string.enum_pattern_waves), null, "WAVES"),
    WAVES_WITH_LINE(UiText.StringResourceId(Res.string.enum_pattern_waves_with_line), null, "WAVES_WITH_LINE"),
    NONE(UiText.StringResourceId(Res.string.enum_pattern_none), null, null),
    ;

    companion object : KeyFactory<GraffitiPattern> {

        override val options: List<Triple<GraffitiPattern, UiText?, DrawableResource?>> = GraffitiPattern.entries.map { e -> Triple(e, e.uiText, e.drawableResourceId) }

        override fun fromString(value: String?): GraffitiPattern {
            return value?.let { v -> GraffitiPattern.valueOf(v) } ?: NONE
        }

        override fun fromValue(value: Any?): GraffitiPattern? {
            return when (value) {
                is String -> fromString(value)
                is GraffitiPattern -> value
                else -> null
            }
        }

        override fun stringValue(value: Any?): String? {
            return (value as? GraffitiPattern)?.name
        }
    }
}
