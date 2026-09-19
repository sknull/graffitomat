package de.visualdigits.graffitomat.domain.util

import de.visualdigits.graffitomat.domain.model.core.GraffitiFontMetrics
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.font.TextLayout
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import kotlin.math.roundToInt

fun drawLetter(
    letter: Char,
    font: Font,
    outlineWidth: Float,
    graffitiFontMetrics: GraffitiFontMetrics,

    patternImage: BufferedImage? = null,

    bottomDotsImage: BufferedImage? = null,
    midDotsImage: BufferedImage? = null,
    topDotsImage: BufferedImage? = null,

    baseColor: Color? = null,

    outlineColor: Color? = null
): BufferedImage {
    val text = letter.toString()

    val canvasWidth = graffitiFontMetrics.width
    val canvasHeight = graffitiFontMetrics.height
    val frameBuffer = BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB)
    val g2d: Graphics2D = frameBuffer.graphics as Graphics2D
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)

    val xOffsetBase = ((-graffitiFontMetrics.x))
    val yOffsetBase = ((-graffitiFontMetrics.y))
    val textLayout = TextLayout(text, font, graffitiFontMetrics.frc)
    val textShape = textLayout.getOutline(AffineTransform.getTranslateInstance(xOffsetBase, yOffsetBase))

    // draw base font
    g2d.font = font
    g2d.color = baseColor
    g2d.drawString(text, xOffsetBase.roundToInt(), yOffsetBase.roundToInt())

    // draw pattern (if any)
    if (patternImage != null) {
        val originalClip = g2d.clip
        g2d.clip = textShape

        val img = patternImage.getSubimage(graffitiFontMetrics.offsetX, 0, canvasWidth, patternImage.height)
        g2d.drawImage(img, 0, 0, null)

        bottomDotsImage?.also { pattern ->
            val img = pattern.getSubimage(graffitiFontMetrics.offsetX, 0, canvasWidth, pattern.height)
            g2d.drawImage(img, 0, 0, null)
        }

        midDotsImage?.also { pattern ->
            val img = pattern.getSubimage(graffitiFontMetrics.offsetX, 0, canvasWidth, pattern.height)
            g2d.drawImage(img, 0, 0, null)
        }

        topDotsImage?.also { pattern ->
            val img = pattern.getSubimage(graffitiFontMetrics.offsetX, 0, canvasWidth, pattern.height)
            g2d.drawImage(img, 0, 0, null)
        }

        g2d.clip = originalClip
    }

    // draw outline
    if (outlineColor != null && outlineWidth > 0.0f) {
        g2d.color = outlineColor
        g2d.stroke = BasicStroke(outlineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
        g2d.draw(textShape)
    }
    g2d.dispose()

    return frameBuffer
}

fun graffitiFontMetrics(
    letter: Char,
    offsetX: Int,
    font: Font,
): GraffitiFontMetrics {
    val (frc, visualBounds) = font.visualBounds(letter.toString())

    return GraffitiFontMetrics(
        offsetX = offsetX,
        x = visualBounds.x,
        y = visualBounds.y,
        width = visualBounds.width.roundToInt().coerceAtLeast(1),
        height = visualBounds.height.roundToInt().coerceAtLeast(1),
        frc = frc
    )
}


/**
 * Repeats the given [pattern] with the given [width].
 */
fun createPatternImage(
    pattern: BufferedImage,
    width: Int,
    height: Int,
): BufferedImage {
    val img = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val g2d: Graphics2D = img.graphics as Graphics2D

    var x = 0
    while (x < width) {
        g2d.drawImage(pattern, x, height - pattern.height, null)
        x += pattern.width
    }
    g2d.dispose()

    return img
}

fun BufferedImage.resize(targetWidth: Int, targetHeight: Int): BufferedImage {
    val resizedImage = BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB)
    val g2d = resizedImage.createGraphics()
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g2d.drawImage(this, 0, 0, targetWidth, targetHeight, null)
    g2d.dispose()

    return resizedImage
}
