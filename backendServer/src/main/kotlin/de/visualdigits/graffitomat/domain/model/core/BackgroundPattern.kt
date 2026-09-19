package de.visualdigits.graffitomat.domain.model.core

import com.github.weisj.jsvg.attributes.filter.BlendMode
import com.github.weisj.jsvg.nodes.filter.BlendModeComposite
import de.visualdigits.graffitomat.domain.util.resize
import java.awt.Color
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import kotlin.math.roundToInt

enum class BackgroundPattern(
    val resource: String
) {
    DESTROYED_STONE_TILE("backgrounds/DestroyedStoneTile.jpg"),
    DISTRESSED_TRAVERTINE_SLAB("backgrounds/DistressedTravertineSlab.jpg"),
    DISTRESSED_WHITE_GRANIT("backgrounds/DistressedWhiteGranit.jpg"),
    STONE_WITH_MOSS("backgrounds/StoneWithMoss.jpg"),
    WEATHERED_GRANITE_SLAB("backgrounds/WeatheredGraniteSlab.jpg"),
    ;

    fun load(
        height: Int,
        tint: Color? = null
    ): BufferedImage {
        return loadImage(resource, height, tint)
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
    val resourceAsStream = Thread.currentThread().contextClassLoader.getResourceAsStream(resource) ?: error("Background not found: $resource")
    val image = ImageIO.read(resourceAsStream)
    val aspect = image.width.toDouble() / image.height
    val width = (aspect * height).roundToInt()

    val resizedImage = image.resize(width, height)

    val g2d = resizedImage.createGraphics()
    tint?.let { color ->
        g2d.composite = BlendModeComposite.create(BlendMode.Multiply)
        g2d.color = color
        g2d.fillRect(0, 0, resizedImage.width, resizedImage.height)
    }
    g2d.dispose()

    return resizedImage
}
