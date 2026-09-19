package de.visualdigits.graffitomat.domain.model.core

import java.awt.Font
import java.awt.GraphicsEnvironment

enum class GraffitiFont(
    val resource: String,
    val fontSize: Int,
) {
    AANOTHERTAG("fonts/aAnotherTag.ttf", 250),
    ADRIP1("fonts/adrip1.ttf", 180),
    AEROSOLDIERDRIP("fonts/AerosoldierDrip.otf", 160),
    FATWANDALS("fonts/FatWandals.ttf", 160),
    FATWANDALSELEMENT("fonts/FatWandalsElement.ttf", 160),
    HESORDER("fonts/Hesorder.ttf", 160),
    JRAOT("fonts/Jraot-Regular.ttf", 140),
    JUNKYSTYLEDRIP("fonts/JunkyStyle-Drip.ttf", 160),
    SPARTICAL("fonts/SparticalGraffiti.otf", 160),
    SPLATINK("fonts/Splatink.otf", 160),
    ;

    fun load(): Font {
        return loadFont(resource, fontSize)
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
