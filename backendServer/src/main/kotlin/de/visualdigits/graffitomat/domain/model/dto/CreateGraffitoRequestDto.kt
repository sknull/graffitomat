package de.visualdigits.graffitomat.domain.model.dto

import de.visualdigits.graffitomat.domain.model.core.GraffitiFont
import de.visualdigits.graffitomat.domain.model.core.GraffitiPattern
import de.visualdigits.graffitomat.domain.serializer.ColorSerializer
import kotlinx.serialization.Serializable
import java.awt.Color

@Serializable
data class CreateGraffitoRequestDto(
    val text: String,
    val fontSize: Int,
    val pattern: GraffitiPattern? = null,
    @Serializable(with = ColorSerializer::class) val patternColor: Color? = null,
    val patternHeightFactor: Float = 1.0f,
    val drawBehind: Boolean = false,
    @Serializable(with = ColorSerializer::class) val topDotsColor: Color? = null,
    @Serializable(with = ColorSerializer::class) val midDotsColor: Color? = null,
    @Serializable(with = ColorSerializer::class) val bottomDotsColor: Color? = null,
    val graffitiFont: GraffitiFont,
    @Serializable(with = ColorSerializer::class) val baseColor: Color? = null,
    @Serializable(with = ColorSerializer::class) val outlineColor: Color? = null,
    @Serializable(with = ColorSerializer::class) val backgroundColor: Color? = null,
)
