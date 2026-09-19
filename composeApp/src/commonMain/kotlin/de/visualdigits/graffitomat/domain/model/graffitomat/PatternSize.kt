package de.visualdigits.graffitomat.domain.model.graffitomat

import de.visualdigits.common.domain.model.configuration.keyfactory.KeyFactory
import de.visualdigits.common.domain.model.ui.StringResourceEnumerable
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.enum_patternsize_full
import de.visualdigits.compose.resources.enum_patternsize_half
import de.visualdigits.compose.resources.enum_patternsize_none
import de.visualdigits.compose.resources.enum_patternsize_quarter
import de.visualdigits.compose.resources.enum_patternsize_three_quarters
import org.jetbrains.compose.resources.DrawableResource

enum class PatternSize(
    override val uiText: UiText,
    override val drawableResourceId: DrawableResource?,
    val factor: Float?
) : StringResourceEnumerable<PatternSize> {

    FACTOR_FULL(UiText.StringResourceId(Res.string.enum_patternsize_full), null, 1.0f),
    FACTOR_THREE_QUARTERS(UiText.StringResourceId(Res.string.enum_patternsize_three_quarters), null, 0.75f),
    FACTOR_HALF(UiText.StringResourceId(Res.string.enum_patternsize_half), null, 0.5f),
    FACTOR_QUARTER(UiText.StringResourceId(Res.string.enum_patternsize_quarter), null, 0.25f)
    ;

    companion object : KeyFactory<PatternSize> {

        override val options: List<Triple<PatternSize, UiText?, DrawableResource?>> = PatternSize.entries.map { e -> Triple(e, e.uiText, e.drawableResourceId) }

        override fun fromString(value: String?): PatternSize? {
            return value?.let { v -> PatternSize.valueOf(v) }
        }

        override fun fromValue(value: Any?): PatternSize? {
            return when (value) {
                is String -> fromString(value)
                is PatternSize -> value
                else -> null
            }
        }

        override fun stringValue(value: Any?): String? {
            return (value as? PatternSize)?.name
        }
    }
}
