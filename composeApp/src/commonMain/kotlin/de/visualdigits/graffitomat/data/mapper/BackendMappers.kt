package de.visualdigits.graffitomat.data.mapper

import de.visualdigits.common.domain.model.configuration.keyfactory.BooleanEnum
import de.visualdigits.graffitomat.data.model.CreateGraffitoRequestDto
import de.visualdigits.graffitomat.domain.model.createrequest.CreateRequestForm
import de.visualdigits.graffitomat.domain.model.createrequest.RK
import de.visualdigits.graffitomat.domain.model.graffitomat.GraffitiFont
import de.visualdigits.graffitomat.domain.model.graffitomat.GraffitiPattern

fun CreateRequestForm.toCreateGraffitoRequestDto(): CreateGraffitoRequestDto {
    return CreateGraffitoRequestDto(
        text = get<String>(RK.text) ?: "?",
        fontSize = get<Int>(RK.fontSize) ?: 150,
        pattern = get<String>(RK.pattern)?.let { p -> GraffitiPattern.valueOf(p) } ?: GraffitiPattern.SKYLINE,
        patternColor = get<String>(RK.patternColor) ?: "#00ffff",
        patternHeightFactor = get<Double>(RK.patternHeightFactor)?.toFloat() ?: 0.7f,
        drawBehind = get<BooleanEnum>(RK.drawBehind)?.booleanValue ?: false,
        topDotsColor = get<String>(RK.topDotsColor) ?: "#00ff00",
        midDotsColor = get<String>(RK.midDotsColor),
        bottomDotsColor = get<String>(RK.bottomDotsColor),
        graffitiFont = get<String>(RK.graffitiFont)?.let { f -> GraffitiFont.valueOf(f) } ?: GraffitiFont.JRAOT,
        baseColor = get<String>(RK.baseColor) ?: "#0000ff",
        outlineColor = get<String>(RK.outlineColor) ?: "#ffff00",
        backgroundColor = get<String>(RK.backgroundColor) ?: "#000000"
    )
}
