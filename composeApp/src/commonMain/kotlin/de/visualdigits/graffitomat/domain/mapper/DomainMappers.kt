package de.visualdigits.graffitomat.domain.mapper

import androidx.compose.ui.graphics.Color
import de.visualdigits.common.domain.model.configuration.keyfactory.BooleanEnum
import de.visualdigits.common.domain.util.toWebColorShort
import de.visualdigits.graffitomat.domain.model.createrequest.CreateRequestForm
import de.visualdigits.graffitomat.domain.model.createrequest.RK
import de.visualdigits.graffitomat.domain.model.graffitomat.BackgroundPattern
import de.visualdigits.graffitomat.domain.model.graffitomat.CharacterTracking
import de.visualdigits.graffitomat.domain.model.graffitomat.GraffitiFont
import de.visualdigits.graffitomat.domain.model.graffitomat.GraffitiPattern
import de.visualdigits.graffitomat.domain.model.graffitomat.OutlineWidth
import de.visualdigits.graffitomat.domain.model.graffitomat.PatternSize

fun CreateRequestForm.toQueryParams(): String {
    val patternHeightFactor = get<PatternSize>(RK.patternHeightFactor)?.factor ?: 0.7
    val filter = listOf(
        "text" to (get<String>(RK.text) ?: "?"),
        "characterTracking" to (get<CharacterTracking>(RK.characterTracking)?.tracking?.toString() ?: "-0.3"),
        "pattern" to get<GraffitiPattern>(RK.pattern)?.patternName,
        "patternColor" to (get<Color>(RK.patternColor)?.toWebColorShort()?.removePrefix("#") ?: "00ffff"),
        "patternHeightFactor" to patternHeightFactor.toString(),
        "drawBehind" to (get<BooleanEnum>(RK.drawBehind)?.booleanValue?.toString() ?: "false"),
        "topDotsColor" to get<Color>(RK.topDotsColor)?.toWebColorShort()?.removePrefix("#"),
        "midDotsColor" to get<Color>(RK.midDotsColor)?.toWebColorShort()?.removePrefix("#"),
        "bottomDotsColor" to get<Color>(RK.bottomDotsColor)?.toWebColorShort()?.removePrefix("#"),
        "graffitiFont" to (get<GraffitiFont>(RK.graffitiFont)?.name ?: GraffitiFont.JRAOT.name),
        "baseColor" to (get<Color>(RK.baseColor)?.toWebColorShort()?.removePrefix("#") ?: "0000ff"),
        "outlineColor" to (get<Color>(RK.outlineColor)?.toWebColorShort()?.removePrefix("#") ?: "ffff00"),
        "outlineWidth" to (get<OutlineWidth>(RK.outlineWidth)?.width?.toString() ?: "3.0"),
        "backgroundColor" to get<Color>(RK.backgroundColor)?.toWebColorShort()?.removePrefix("#"),
        "backgroundPattern" to get<BackgroundPattern>(RK.backgroundPattern)?.patternName
    ).filter { it.second != null }
    return filter
        .joinToString("&") { "${it.first}=${it.second}" }
}
