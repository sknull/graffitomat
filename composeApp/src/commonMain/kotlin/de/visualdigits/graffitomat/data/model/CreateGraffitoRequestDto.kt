package de.visualdigits.graffitomat.data.model

import de.visualdigits.graffitomat.domain.model.graffitomat.GraffitiFont
import kotlinx.serialization.Serializable

@Serializable
data class CreateGraffitoRequestDto(
    val text: String,
    val graffitiFont: GraffitiFont,
    val characterTracking: Float = -0.3f,
    val pattern: String? = null,
    val patternColor: String? = null,
    val patternHeightFactor: Float = 1.0f,
    val drawBehind: Boolean = false,
    val topDotsColor: String? = null,
    val midDotsColor: String? = null,
    val bottomDotsColor: String? = null,
    val baseColor: String? = null,
    val outlineColor: String? = null,
    val outlineWidth: Float = 3.0f,
    val backgroundColor: String? = null,
    val backgroundPattern: String? = null,
)
