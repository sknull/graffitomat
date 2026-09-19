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
    graffitiFont: GraffitiFont,
    characterTracking: Float = 0.0f,
    pattern: GraffitiPattern? = null,
    baseColor: Color? = null,
    patternColor: Color? = null,
    patternHeightFactor: Float = 1.0f,
    drawBehind: Boolean = false,
    topDotsColor: Color? = null,
    midDotsColor: Color? = null,
    bottomDotsColor: Color? = null,
    outlineColor: Color? = null,
    outlineWidth: Float = 3.0f,
): BufferedImage {
    val font = graffitiFont.load()

    var offsetX = 0
    val factor = 1.0f + characterTracking.coerceIn(-1.0f, 1.0f)
    val metrics = text.map { letter ->
        val metric = graffitiFontMetrics(letter, offsetX, font)
        offsetX += (metric.width * factor).roundToInt()
        metric
    }

    if (metrics.isEmpty()) return BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)

    val frameWidth = metrics.last().let { m -> m.offsetX + m.width }

    val canvasWidth = metrics.sumOf { it.width }
    val canvasHeight = metrics.maxOf { it.height }
    val frameBuffer = BufferedImage(frameWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB)
    val g2d: Graphics2D = frameBuffer.graphics as Graphics2D
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)

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

    text.toCharArray()
        .let { chars -> if (drawBehind) chars.reversed() else chars.toList() }
        .zip(metrics.let { m -> if (drawBehind) m.reversed() else m })
        .forEach { (letter, graffitiFontMetrics) ->
            val letterImage = drawLetter(
                letter = letter,
                font = font,
                outlineWidth = outlineWidth,
                graffitiFontMetrics = graffitiFontMetrics,
                patternImage = patternImage,
                bottomDotsImage = bottomDotsImage,
                midDotsImage = midDotsImage,
                topDotsImage = topDotsImage,
                baseColor = baseColor,
                outlineColor = outlineColor
            )
            g2d.drawImage(letterImage, graffitiFontMetrics.offsetX, ((canvasHeight - letterImage.height) / 2.0).roundToInt(), null)
        }

    return frameBuffer
}
