package de.visualdigits.graffitomat.domain.model.graffitomat

import androidx.compose.ui.graphics.Color

data class CreateGraffitoRequest(
    val text: String,
    val fontSize: Int,
    val pattern: GraffitiPattern? = null,
    val patternColor: Color? = null,
    val patternHeightFactor: Float = 1.0f,
    val drawBehind: Boolean = false,
    val topDotsColor: Color? = null,
    val midDotsColor: Color? = null,
    val bottomDotsColor: Color? = null,
    val graffitiFont: GraffitiFont,
    val baseColor: Color? = null,
    val outlineColor: Color? = null,
    val backgroundColor: Color? = null,
)
