package de.visualdigits.graffitomat.domain.model.graffitomat

import de.visualdigits.common.domain.model.configuration.keyfactory.KeyFactory
import de.visualdigits.common.domain.model.ui.StringResourceEnumerable
import de.visualdigits.common.domain.model.ui.UiText
import org.jetbrains.compose.resources.DrawableResource

enum class GraffitiFont(
    override val uiText: UiText,
    override val drawableResourceId: DrawableResource?,
) : StringResourceEnumerable<GraffitiFont> {

    AANOTHERTAG(UiText.DynamicString("AANOTHERTAG"), null),
    ADRIP1(UiText.DynamicString("ADRIP1"), null),
    AEROSOLDIERDRIP(UiText.DynamicString("AEROSOLDIERDRIP"), null),
    FATWANDALS(UiText.DynamicString("FATWANDALS"), null),
    FATWANDALSELEMENT(UiText.DynamicString("FATWANDALSELEMENT"), null),
    HESORDER(UiText.DynamicString("HESORDER"), null),
    JRAOT(UiText.DynamicString("JRAOT"), null),
    JUNKYSTYLEDRIP(UiText.DynamicString("JUNKYSTYLEDRIP"), null),
    SPARTICAL(UiText.DynamicString("SPARTICAL"), null),
    SPLATINK(UiText.DynamicString("SPLATINK"), null),
    ;

    companion object : KeyFactory<GraffitiFont> {

        override val options: List<Triple<GraffitiFont, UiText?, DrawableResource?>> = GraffitiFont.entries.map { e -> Triple(e, e.uiText, e.drawableResourceId) }

        override fun fromString(value: String?): GraffitiFont? {
            return value?.let { v -> GraffitiFont.valueOf(v) }
        }

        override fun fromValue(value: Any?): GraffitiFont? {
            return when (value) {
                is String -> fromString(value)
                is GraffitiFont -> value
                else -> null
            }
        }

        override fun stringValue(value: Any?): String? {
            return (value as? GraffitiFont)?.name
        }
    }
}
