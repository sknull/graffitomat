package de.visualdigits.graffitomat.domain.model.createrequest

import de.visualdigits.common.domain.model.configuration.FieldKey

enum class RK : FieldKey<RK> {

    text,
    fontSize,
    pattern,
    patternColor,
    patternHeightFactor,
    drawBehind,
    topDotsColor,
    midDotsColor,
    bottomDotsColor,
    graffitiFont,
    baseColor,
    outlineColor,
    backgroundColor,
}
