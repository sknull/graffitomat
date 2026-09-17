package de.visualdigits.graffitomat.data.model

import de.visualdigits.graffitomat.domain.model.graffitomat.GraffitiFont
import de.visualdigits.graffitomat.domain.model.graffitomat.GraffitiPattern
import kotlinx.serialization.Serializable

@Serializable
data class CreateGraffitoRequestDto(
    val text: String,
    val fontSize: Int,
    val pattern: GraffitiPattern? = null,
    val patternColor: String? = null,
    val patternHeightFactor: Float = 1.0f,
    val drawBehind: Boolean = false,
    val topDotsColor: String? = null,
    val midDotsColor: String? = null,
    val bottomDotsColor: String? = null,
    val graffitiFont: GraffitiFont,
    val baseColor: String? = null,
    val outlineColor: String? = null,
    val backgroundColor: String? = null,
)
