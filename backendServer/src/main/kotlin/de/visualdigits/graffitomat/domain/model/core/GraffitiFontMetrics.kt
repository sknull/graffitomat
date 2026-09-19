package de.visualdigits.graffitomat.domain.model.core

import java.awt.font.FontRenderContext

data class GraffitiFontMetrics(
    val offsetX: Int,
    val x: Double,
    val y: Double,
    val width: Int,
    val height: Int,
    val frc: FontRenderContext?
)
