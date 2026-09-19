package de.visualdigits.graffitomat.domain.model.graffitomat

import de.visualdigits.common.domain.model.configuration.keyfactory.KeyFactory
import de.visualdigits.common.domain.model.ui.StringResourceEnumerable
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.enum_background_destroyed_stone_tile
import de.visualdigits.compose.resources.enum_background_distressed_travertine_slab
import de.visualdigits.compose.resources.enum_background_distressed_white_granit
import de.visualdigits.compose.resources.enum_background_none
import de.visualdigits.compose.resources.enum_background_stone_with_moss
import de.visualdigits.compose.resources.enum_background_weathered_granite_slab
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

enum class BackgroundPattern(
    override val uiText: UiText,
    override val drawableResourceId: DrawableResource?,
    val patternName: String?
) : StringResourceEnumerable<BackgroundPattern> {

    DESTROYED_STONE_TILE(UiText.StringResourceId(Res.string.enum_background_destroyed_stone_tile), null, "DESTROYED_STONE_TILE"),
    DISTRESSED_TRAVERTINE_SLAB(UiText.StringResourceId(Res.string.enum_background_distressed_travertine_slab), null, "DISTRESSED_TRAVERTINE_SLAB"),
    DISTRESSED_WHITE_GRANIT(UiText.StringResourceId(Res.string.enum_background_distressed_white_granit), null, "DISTRESSED_WHITE_GRANIT"),
    STONE_WITH_MOSS(UiText.StringResourceId(Res.string.enum_background_stone_with_moss), null, "STONE_WITH_MOSS"),
    WEATHERED_GRANITE_SLAB(UiText.StringResourceId(Res.string.enum_background_weathered_granite_slab), null, "WEATHERED_GRANITE_SLAB"),
    NONE(UiText.StringResourceId(Res.string.enum_background_none), null, null),
    ;

    companion object : KeyFactory<BackgroundPattern> {

        override val options: List<Triple<BackgroundPattern, UiText?, DrawableResource?>> = entries.map { e -> Triple(e, e.uiText, e.drawableResourceId) }

        override fun fromString(value: String?): BackgroundPattern {
            return value?.let { v -> BackgroundPattern.valueOf(v) } ?: NONE
        }

        override fun fromValue(value: Any?): BackgroundPattern? {
            return when (value) {
                is String -> fromString(value)
                is BackgroundPattern -> value
                else -> null
            }
        }

        override fun stringValue(value: Any?): String? {
            return (value as? BackgroundPattern)?.name
        }
    }
}
