package de.visualdigits.graffitomat.data.mapper

import androidx.compose.ui.graphics.Color
import de.visualdigits.common.domain.model.configuration.keyfactory.BooleanEnum
import de.visualdigits.common.domain.util.toWebColorShort
import de.visualdigits.graffitomat.data.model.CreateGraffitoRequestDto
import de.visualdigits.graffitomat.domain.model.createrequest.CreateRequestForm
import de.visualdigits.graffitomat.domain.model.createrequest.RK
import de.visualdigits.graffitomat.domain.model.graffitomat.BackgroundPattern
import de.visualdigits.graffitomat.domain.model.graffitomat.CharacterTracking
import de.visualdigits.graffitomat.domain.model.graffitomat.GraffitiFont
import de.visualdigits.graffitomat.domain.model.graffitomat.GraffitiPattern
import de.visualdigits.graffitomat.domain.model.graffitomat.OutlineWidth
import de.visualdigits.graffitomat.domain.model.graffitomat.PatternSize

fun CreateRequestForm.toCreateGraffitoRequestDto(): CreateGraffitoRequestDto {
    return CreateGraffitoRequestDto(
        text = get<String>(RK.text) ?: "?",
        graffitiFont = get<GraffitiFont>(RK.graffitiFont) ?: GraffitiFont.JRAOT,
        characterTracking = get<CharacterTracking>(RK.characterTracking)?.tracking ?: -0.3f,
        pattern = get<GraffitiPattern>(RK.pattern)?.patternName,
        patternColor = get<Color>(RK.patternColor)?.toWebColorShort() ?: "#00ffff",
        patternHeightFactor = get<PatternSize>(RK.patternHeightFactor)?.factor ?: 0.7f,
        drawBehind = get<BooleanEnum>(RK.drawBehind)?.booleanValue ?: false,
        topDotsColor = get<Color>(RK.topDotsColor)?.toWebColorShort(),
        midDotsColor = get<Color>(RK.midDotsColor)?.toWebColorShort(),
        bottomDotsColor = get<Color>(RK.bottomDotsColor)?.toWebColorShort(),
        baseColor = get<Color>(RK.baseColor)?.toWebColorShort() ?: "#0000ff",
        outlineColor = get<Color>(RK.outlineColor)?.toWebColorShort() ?: "#ffff00",
        outlineWidth = get<OutlineWidth>(RK.outlineWidth)?.width ?: 3.0f,
        backgroundColor = get<Color>(RK.backgroundColor)?.toWebColorShort(),
        backgroundPattern = get<BackgroundPattern>(RK.backgroundPattern)?.patternName
    )
}
