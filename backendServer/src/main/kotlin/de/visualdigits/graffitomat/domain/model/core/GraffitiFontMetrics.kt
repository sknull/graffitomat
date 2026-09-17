package de.visualdigits.graffitomat.domain.model.core

import java.awt.font.FontRenderContext

data class GraffitiFontMetrics(
    val offsetX: Int,
    val canvasWidth: Int,
    val canvasHeight: Int,
    val baseX: Double,
    val baseY: Double,
    val baseWidth: Int,
    val baseHeight: Int,
    val outlineX: Double?,
    val outlineY: Double?,
    val frc: FontRenderContext?
)
