package de.visualdigits.graffitomat.domain.mapper

import de.visualdigits.common.domain.model.configuration.keyfactory.BooleanEnum
import de.visualdigits.graffitomat.domain.model.createrequest.CreateRequestForm
import de.visualdigits.graffitomat.domain.model.createrequest.RK
import de.visualdigits.graffitomat.domain.model.graffitomat.GraffitiFont
import de.visualdigits.graffitomat.domain.model.graffitomat.GraffitiPattern

fun CreateRequestForm.toQueryParams(): String {
    return listOf(
        "text" to (get<String>(RK.text) ?: "?"),
        "fontSize" to (get<Int>(RK.fontSize)?.toString() ?: "150"),
        "pattern" to (get<String>(RK.pattern) ?: GraffitiPattern.SKYLINE.name),
        "patternColor" to (get<String>(RK.patternColor)?.removePrefix("#") ?: "00ffff"),
        "patternHeightFactor" to (get<Double>(RK.patternHeightFactor)?.toString() ?: "0.7"),
        "drawBehind" to (get<BooleanEnum>(RK.drawBehind)?.booleanValue?.toString() ?: "false"),
        "topDotsColor" to (get<String>(RK.topDotsColor)?.removePrefix("#") ?: "00ff00"),
        "midDotsColor" to get<String>(RK.midDotsColor)?.removePrefix("#"),
        "bottomDotsColor" to get<String>(RK.bottomDotsColor)?.removePrefix("#"),
        "graffitiFont" to (get<String>(RK.graffitiFont) ?: GraffitiFont.JRAOT.name),
        "baseColor" to (get<String>(RK.baseColor)?.removePrefix("#") ?: "0000ff"),
        "outlineColor" to (get<String>(RK.outlineColor)?.removePrefix("#") ?: "ffff00"),
        "backgroundColor" to (get<String>(RK.backgroundColor)?.removePrefix("#") ?: "000000")
    ).filter { it.second != null }
        .joinToString("&") { "${it.first}=${it.second}" }
}
