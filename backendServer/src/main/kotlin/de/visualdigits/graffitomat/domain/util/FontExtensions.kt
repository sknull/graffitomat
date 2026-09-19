package de.visualdigits.graffitomat.domain.util

import java.awt.Font
import java.awt.Graphics2D
import java.awt.font.FontRenderContext
import java.awt.geom.Rectangle2D
import java.awt.image.BufferedImage

/**
 * Returns the glyph metrics for the given [text] rendered with this font.
 */
fun Font.visualBounds(
    text: String
): Pair<FontRenderContext, Rectangle2D> {
    val frameBufferTemp = BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
    val g2d: Graphics2D = frameBufferTemp.graphics as Graphics2D
    val frc = g2d.fontRenderContext
    val glyphVector = createGlyphVector(frc, text)
    val visualBounds = glyphVector.visualBounds
    g2d.dispose()

    return Pair(frc, visualBounds)
}
