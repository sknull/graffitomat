package de.visualdigits.graffitomat.domain.model.createrequest

import de.visualdigits.common.domain.model.configuration.FieldKey

enum class RK : FieldKey<RK> {

    text,
    graffitiFont,
    characterTracking,
    pattern,
    patternColor,
    patternHeightFactor,
    drawBehind,
    topDotsColor,
    midDotsColor,
    bottomDotsColor,
    baseColor,
    outlineColor,
    outlineWidth,
    backgroundColor,
    backgroundPattern
}
