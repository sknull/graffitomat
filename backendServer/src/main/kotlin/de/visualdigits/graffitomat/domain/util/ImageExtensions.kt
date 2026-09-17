package de.visualdigits.graffitomat.domain.util

import de.visualdigits.graffitomat.domain.model.core.GraffitiFontMetrics
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.font.TextLayout
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import kotlin.math.max
import kotlin.math.roundToInt

fun drawLetter(
    letter: Char,
    graffitiFontMetrics: GraffitiFontMetrics,

    patternImage: BufferedImage? = null,

    bottomDotsImage: BufferedImage? = null,
    midDotsImage: BufferedImage? = null,
    topDotsImage: BufferedImage? = null,

    baseFont: Font,
    baseFontOffsetX: Int,
    baseFontOffsetY: Int,
    baseColor: Color? = null,

    outlineFont: Font? = null,
    outlineColor: Color? = null,
): BufferedImage {
    val text = letter.toString()

    val baseWidth = graffitiFontMetrics.baseWidth

    val canvasWidth = graffitiFontMetrics.canvasWidth
    val canvasHeight = graffitiFontMetrics.canvasHeight
    val frameBuffer = BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB)
    val g: Graphics2D = frameBuffer.graphics as Graphics2D
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)

    // draw base font
    g.font = baseFont
    g.color = baseColor
    val xOffsetBase = ((-graffitiFontMetrics.baseX) + baseFontOffsetX)
    val yOffsetBase = ((-graffitiFontMetrics.baseY) + baseFontOffsetY)
    g.drawString(text, xOffsetBase.roundToInt(), yOffsetBase.roundToInt())

    // draw pattern (if any)
    if (patternImage != null) {
        val textLayout = TextLayout(text, baseFont, graffitiFontMetrics.frc)

        val textShape = textLayout.getOutline(AffineTransform.getTranslateInstance(xOffsetBase, yOffsetBase))

        val originalClip = g.clip
        g.clip = textShape


        val img = patternImage.getSubimage(graffitiFontMetrics.offsetX, 0, canvasWidth, patternImage.height)
        g.drawImage(img, 0, 0, null)

        bottomDotsImage?.also { pattern ->
            val img = pattern.getSubimage(graffitiFontMetrics.offsetX, 0, canvasWidth, pattern.height)
            g.drawImage(img, 0, 0, null)
        }

        midDotsImage?.also { pattern ->
            val img = pattern.getSubimage(graffitiFontMetrics.offsetX, 0, canvasWidth, pattern.height)
            g.drawImage(img, 0, 0, null)
        }

        topDotsImage?.also { pattern ->
            val img = pattern.getSubimage(graffitiFontMetrics.offsetX, 0, canvasWidth, pattern.height)
            g.drawImage(img, 0, 0, null)
        }

        g.clip = originalClip
    }

    // draw outline font (if any)
    if (outlineFont != null) {
        g.font = outlineFont
        g.color = outlineColor
        val xOffsetOutline = (-(graffitiFontMetrics.outlineX ?: 0.0)).roundToInt()
        val yOffsetOutline = (-(graffitiFontMetrics.outlineY ?: 0.0)).roundToInt()
        g.drawString(text, xOffsetOutline, yOffsetOutline)
    }

    return frameBuffer
}

fun graffitiFontMetrics(
    letter: Char,
    offsetX: Int,
    baseFont: Font,
    outlineFont: Font? = null,
): GraffitiFontMetrics {
    val text = letter.toString()

    val (frc, baseVisualBounds) = baseFont.visualBounds(text)
    val baseWidth = baseVisualBounds.width.roundToInt().coerceAtLeast(1)
    val baseHeight = baseVisualBounds.height.roundToInt().coerceAtLeast(1)

    val outlineResult = outlineFont?.visualBounds(text)
    val outlineWidth = outlineResult?.second?.width?.roundToInt()?.coerceAtLeast(1) ?: 0
    val outlineHeight = outlineResult?.second?.height?.roundToInt()?.coerceAtLeast(1) ?: 0

    val canvasWidth = max(baseWidth, outlineWidth)
    val canvasHeight = max(baseHeight, outlineHeight)

    return GraffitiFontMetrics(
        offsetX = offsetX,
        canvasWidth = canvasWidth,
        canvasHeight = canvasHeight,
        baseX = baseVisualBounds.x,
        baseY = baseVisualBounds.y,
        baseWidth = baseWidth,
        baseHeight = baseHeight,
        outlineX = outlineResult?.second?.x,
        outlineY = outlineResult?.second?.y,
        frc = frc
    )
}
