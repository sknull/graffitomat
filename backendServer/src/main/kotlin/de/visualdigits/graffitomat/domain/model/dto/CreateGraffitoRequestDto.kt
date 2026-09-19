package de.visualdigits.graffitomat.domain.model.dto

import de.visualdigits.graffitomat.domain.model.core.BackgroundPattern
import de.visualdigits.graffitomat.domain.model.core.GraffitiFont
import de.visualdigits.graffitomat.domain.model.core.GraffitiPattern
import de.visualdigits.graffitomat.domain.serializer.ColorSerializer
import kotlinx.serialization.Serializable
import java.awt.Color

@Serializable
data class CreateGraffitoRequestDto(
    val text: String,
    val graffitiFont: GraffitiFont,
    val characterTracking: Float = -0.3f,
    val pattern: GraffitiPattern? = null,
    @Serializable(with = ColorSerializer::class) val patternColor: Color? = null,
    val patternHeightFactor: Float = 1.0f,
    val drawBehind: Boolean = false,
    @Serializable(with = ColorSerializer::class) val topDotsColor: Color? = null,
    @Serializable(with = ColorSerializer::class) val midDotsColor: Color? = null,
    @Serializable(with = ColorSerializer::class) val bottomDotsColor: Color? = null,
    @Serializable(with = ColorSerializer::class) val baseColor: Color? = null,
    @Serializable(with = ColorSerializer::class) val outlineColor: Color? = null,
    val outlineWidth: Float = 3.0f,
    @Serializable(with = ColorSerializer::class) val backgroundColor: Color? = null,
    val backgroundPattern: BackgroundPattern? = null,
)
