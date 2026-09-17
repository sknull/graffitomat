package de.visualdigits.graffitomat.domain.util

import de.visualdigits.graffitomat.domain.model.core.GraffitiFont
import de.visualdigits.graffitomat.domain.model.core.GraffitiPattern
import java.awt.Color
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import kotlin.math.roundToInt


fun createGraffitiImage(
    text: String,
    fontSize: Int,
    pattern: GraffitiPattern? = null,
    patternColor: Color? = null,
    patternHeightFactor: Float = 1.0f,
    drawBehind: Boolean = false,
    topDotsColor: Color? = null,
    midDotsColor: Color? = null,
    bottomDotsColor: Color? = null,
    graffitiFont: GraffitiFont,
    baseColor: Color? = null,
    outlineColor: Color? = null,
    backgroundColor: Color? = null,
): BufferedImage {
    val (baseFont, outlineFont) = graffitiFont.load(fontSize)

    var offsetX = 0
    val factor = 1.0f + graffitiFont.characterTracking.coerceIn(-1.0f, 1.0f)
    val metrics = text.map { letter ->
        val metrics = graffitiFontMetrics(letter, offsetX, baseFont, outlineFont)
        offsetX += (metrics.canvasWidth * factor).roundToInt()
        metrics
    }

    if (metrics.isEmpty()) return BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)

    val frameWidth = metrics.last().let { m -> m.offsetX + m.canvasWidth }

    val canvasWidth = metrics.sumOf { it.canvasWidth }
    val canvasHeight = metrics.maxOf { it.canvasHeight }
    val frameBuffer = BufferedImage(frameWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB)
    val g: Graphics2D = frameBuffer.graphics as Graphics2D
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)

    val patternImage = if (pattern != null && patternColor != null) {
        val img = pattern.load((canvasHeight * patternHeightFactor).roundToInt(), patternColor)
        createPatternImage(img, canvasWidth, canvasHeight)
    } else null

    val bottomDotsImage = bottomDotsColor?.let { color ->
        val img = GraffitiPattern.CIRCLES_BOTTOM.load(canvasHeight, color)
        createPatternImage(img, canvasWidth, canvasHeight)
    }

    val midDotsImage = midDotsColor?.let { color ->
        val img = GraffitiPattern.CIRCLES_MID.load(canvasHeight, color)
        createPatternImage(img, canvasWidth, canvasHeight)
    }

    val topDotsImage = topDotsColor?.let { color ->
        val img = GraffitiPattern.CIRCLES_TOP.load(canvasHeight, color)
        createPatternImage(img, canvasWidth, canvasHeight)
    }

    backgroundColor?.let { bg ->
        g.color = bg
        g.fillRect(0, 0, canvasWidth, canvasHeight)
    }

    text.toCharArray()
        .let { chars -> if (drawBehind) chars.reversed() else chars.toList() }
        .zip(metrics.let { m -> if (drawBehind) m.reversed() else m })
        .forEach { (letter, graffitiFontMetrics) ->
            val letterImage = drawLetter(
                letter = letter,
                graffitiFontMetrics = graffitiFontMetrics,
                patternImage = patternImage,
                bottomDotsImage = bottomDotsImage,
                midDotsImage = midDotsImage,
                topDotsImage = topDotsImage,
                baseFont = baseFont,
                baseFontOffsetX = graffitiFont.baseFontOffsetX,
                baseFontOffsetY = graffitiFont.baseFontOffsetY,
                baseColor = baseColor,
                outlineFont = outlineFont,
                outlineColor = outlineColor
            )
            g.drawImage(letterImage, graffitiFontMetrics.offsetX, ((canvasHeight - letterImage.height) / 2.0).roundToInt(), null)
        }

    return frameBuffer
}

/**
 * Repeats the given [pattern] with the given [width].
 */
private fun createPatternImage(
    pattern: BufferedImage,
    width: Int,
    height: Int,
): BufferedImage {
    val img = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val g: Graphics2D = img.graphics as Graphics2D

    val bgW = pattern.width
    val bgH = pattern.height

    var x = 0
    while (x < width) {
        g.drawImage(pattern, x, height - bgH, null)
        x += bgW
    }

    return img
}
