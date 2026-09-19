package de.visualdigits.graffitomat.presentation.style

import androidx.compose.ui.graphics.Color
import de.visualdigits.common.domain.model.color.ColorPalette
import de.visualdigits.common.domain.model.color.PaletteColor

val TextColor = Color(0xFF000000)

val SpotColor = Color(0xFFC6030F)

val BackgroundColor = Color(0xFF000000)

val ContainerDisabled = Color(0xFF777777)

val ContentDisabled = Color(0xFF000000)


val COLOR_PALETTE_GRAFFITY = ColorPalette(
    colors = listOf(
        PaletteColor("red", Color(0xffff0000)),
        PaletteColor("green", Color(0xff00ff00)),
        PaletteColor("blue", Color(0xff0000ff)),
        PaletteColor("yellow", Color(0xffffff00)),
        PaletteColor("cyan", Color(0xff00ffff)),
        PaletteColor("magenta", Color(0xffff00ff)),
        PaletteColor("purple", Color(0xff6600ff)),
        PaletteColor("orange", Color(0xffff7700)),
        PaletteColor("lime", Color(0xFFB3FF00)),
        PaletteColor("sky", Color(0xFF7AC9FE)),
        PaletteColor("black", Color(0xff000000)),
        PaletteColor("white", Color(0xffffffff)),
        PaletteColor("none", null),
    )
)

val COLOR_PALETTE_BACKGROUND = ColorPalette(
    colors = listOf(
        PaletteColor("black", Color(0xff000000)),
        PaletteColor("white", Color(0xffffffff)),
        PaletteColor("none", null),
    )
)
