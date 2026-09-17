package de.visualdigits.graffitomat.domain.model.core

import java.awt.Font
import java.awt.GraphicsEnvironment

enum class GraffitiFont(
    val baseFontResource: String,
    val outlineFontResource: String? = null,
    val baseFontOffsetX: Int = 0,
    val baseFontOffsetY: Int = 0,
    val characterTracking: Float = 0.0f,
) {
    JRAOT(
        baseFontResource = "fonts/Jraot-Regular.ttf",
        outlineFontResource = "fonts/Jraot-Outline.ttf",
        baseFontOffsetX = 5,
        baseFontOffsetY = 4,
        characterTracking = -0.3f
    ),

    SPARTICAL(
        baseFontResource = "fonts/SparticalGraffiti.otf",
        outlineFontResource = "fonts/SparticalGraffitiLine.otf",
        baseFontOffsetX = 1,
        baseFontOffsetY = 1
    )
    ;

    fun load(fontSize: Int): Pair<Font, Font?> {
        return Pair(
            loadFont(baseFontResource, fontSize),
            outlineFontResource?.let { off -> loadFont(off, fontSize)}
        )
    }
}

/**
 * Loads the given [fontResource], registers it and returns the font.
 */
private fun loadFont(
    fontResource: String,
    fontSize: Int
): Font {
    val resourceAsStream = Thread.currentThread().contextClassLoader.getResourceAsStream(fontResource) ?: error("Font not found: $fontResource")
    return resourceAsStream.use { fontResource ->
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        val font = Font.createFont(Font.TRUETYPE_FONT, fontResource)
        ge.registerFont(font)
        font.deriveFont(Font.PLAIN, fontSize.toFloat())
    }
}
