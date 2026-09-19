package de.visualdigits.graffitomat.domain.model.core

import com.github.weisj.jsvg.attributes.ViewBox
import com.github.weisj.jsvg.parser.SVGLoader
import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Component
import java.awt.image.BufferedImage
import kotlin.math.roundToInt

enum class GraffitiPattern(
    val resource: String
) {
    
    CIRCLES("patterns/pattern-circles.svg"),
    CIRCLES_WITH_HORIZONTAL_LINES("patterns/pattern-circles-with-horizontal-lines.svg"),
    CIRCLES_WITH_VERTICAL_LINES("patterns/pattern-circles-with-vertical-lines.svg"),
    CIRCLES_TOP("patterns/pattern-circles-top.svg"),
    CIRCLES_MID("patterns/pattern-circles-middle.svg"),
    CIRCLES_BOTTOM("patterns/pattern-circles-bottom.svg"),
    CUTS("patterns/pattern-cuts.svg"),
    DOTS("patterns/pattern-dots.svg"),
    DOTS_FULL("patterns/pattern-dots-full.svg"),
    FIRE("patterns/pattern-fire.svg"),
    SKYLINE("patterns/pattern-skyline.svg"),
    WAVES("patterns/pattern-waves.svg"),
    WAVES_WITH_LINE("patterns/pattern-waves-with-line.svg")
    ;

    fun load(
        height: Int,
        color: Color
    ): BufferedImage {
        return loadImage(resource, height, color)
    }
}

/**
 * Loads the given svg file using jsvg into a buffered image [file] and returns the image.
 * The image width will be calculated from svg aspect and the given [height].
 * You can also specify an optional [tint] color to override the original svg color.
 */
private fun loadImage(
    resource: String,
    height: Int,
    tint: Color? = null
): BufferedImage {
    val resourceAsStream = Thread.currentThread().contextClassLoader.getResourceAsStream(resource) ?: error("SVG not found: $resource")
    val svgDocument = SVGLoader().load(resourceAsStream) ?: error("Invalid SVG file")
    val size = svgDocument.size()
    val aspect = size.width / size.height
    val width = (aspect * height).roundToInt()

    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val g = image.createGraphics()

    svgDocument.render(null as Component?, g, ViewBox(width.toFloat(), height.toFloat()))

    tint?.let { color ->
        // SrcIn sorgt dafür, dass NUR dort gezeichnet wird, wo das SVG bereits Pixel hinterlassen hat
        g.composite = AlphaComposite.SrcIn
        g.color = color
        g.fillRect(0, 0, width, height)
    }

    g.dispose()

    return image
}
