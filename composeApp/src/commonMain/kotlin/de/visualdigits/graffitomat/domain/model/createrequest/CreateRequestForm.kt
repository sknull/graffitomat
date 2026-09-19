package de.visualdigits.graffitomat.domain.model.createrequest

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.visualdigits.common.domain.model.configuration.AbstractConfiguration
import de.visualdigits.common.domain.model.configuration.ColorPaletteFieldDescriptor
import de.visualdigits.common.domain.model.configuration.EnumFieldDescriptor
import de.visualdigits.common.domain.model.configuration.StringFieldDescriptor
import de.visualdigits.common.domain.model.configuration.keyfactory.BooleanEnum
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.group_background
import de.visualdigits.compose.resources.group_colors
import de.visualdigits.compose.resources.group_text
import de.visualdigits.compose.resources.label_backgroundColor
import de.visualdigits.compose.resources.label_background_image
import de.visualdigits.compose.resources.label_baseColor
import de.visualdigits.compose.resources.label_bottomDotsColor
import de.visualdigits.compose.resources.label_characterTracking
import de.visualdigits.compose.resources.label_drawBehind
import de.visualdigits.compose.resources.label_graffitiFont
import de.visualdigits.compose.resources.label_language
import de.visualdigits.compose.resources.label_midDotsColor
import de.visualdigits.compose.resources.label_outlineColor
import de.visualdigits.compose.resources.label_outlineWidth
import de.visualdigits.compose.resources.label_pattern
import de.visualdigits.compose.resources.label_patternColor
import de.visualdigits.compose.resources.label_patternHeightFactor
import de.visualdigits.compose.resources.label_text
import de.visualdigits.compose.resources.label_topDotsColor
import de.visualdigits.graffitomat.domain.model.graffitomat.BackgroundPattern
import de.visualdigits.graffitomat.domain.model.graffitomat.CharacterTracking
import de.visualdigits.graffitomat.domain.model.graffitomat.GraffitiFont
import de.visualdigits.graffitomat.domain.model.graffitomat.GraffitiPattern
import de.visualdigits.graffitomat.domain.model.graffitomat.OutlineWidth
import de.visualdigits.graffitomat.domain.model.graffitomat.PatternSize
import de.visualdigits.graffitomat.domain.model.type.Language
import de.visualdigits.graffitomat.presentation.style.COLOR_PALETTE_BACKGROUND
import de.visualdigits.graffitomat.presentation.style.COLOR_PALETTE_GRAFFITY
import de.visualdigits.graffitomat.presentation.style.ShapesGap

class CreateRequestForm: AbstractConfiguration<CreateRequestForm, RK>() {

    init {
        initialize(DESCRIPTORS)
    }

    companion object {
        val colorPaletteWidth = 450.dp

        val DESCRIPTORS = listOf(
            StringFieldDescriptor(
                key = RK.text,
                group = UiText.StringResourceId(Res.string.group_text),
                label = UiText.StringResourceId(Res.string.label_text),
                width = 900.dp + ShapesGap * 2
            ),

            EnumFieldDescriptor(
                fieldClass = GraffitiFont::class,
                key = RK.graffitiFont,
                group = UiText.StringResourceId(Res.string.group_text),
                label =  UiText.StringResourceId(Res.string.label_graffitiFont),
                options = { _, _ -> GraffitiFont.options },
                keyFactory = GraffitiFont,
                default = GraffitiFont.JRAOT
            ),
            EnumFieldDescriptor(
                fieldClass = BooleanEnum::class,
                key = RK.drawBehind,
                group = UiText.StringResourceId(Res.string.group_text),
                label =  UiText.StringResourceId(Res.string.label_drawBehind),
                options = { _, _ -> BooleanEnum.options },
                keyFactory = BooleanEnum,
                default = BooleanEnum.FALSE
            ),
            EnumFieldDescriptor(
                fieldClass = CharacterTracking::class,
                key = RK.characterTracking,
                group = UiText.StringResourceId(Res.string.group_text),
                label =  UiText.StringResourceId(Res.string.label_characterTracking),
                options = { _, _ -> CharacterTracking.options },
                keyFactory = CharacterTracking,
                default = CharacterTracking.TRACKING_MEDIUM
            ),
            EnumFieldDescriptor(
                fieldClass = OutlineWidth::class,
                key = RK.outlineWidth,
                group = UiText.StringResourceId(Res.string.group_text),
                label =  UiText.StringResourceId(Res.string.label_outlineWidth),
                options = { _, _ -> OutlineWidth.options },
                keyFactory = OutlineWidth,
                default = OutlineWidth.WIDTH_MEDIUM
            ),

            EnumFieldDescriptor(
                fieldClass = GraffitiPattern::class,
                key = RK.pattern,
                group = UiText.StringResourceId(Res.string.group_text),
                label =  UiText.StringResourceId(Res.string.label_pattern),
                options = { _, _ -> GraffitiPattern.options },
                keyFactory = GraffitiPattern,
                default = GraffitiPattern.SKYLINE
            ),
            EnumFieldDescriptor(
                fieldClass = PatternSize::class,
                key = RK.patternHeightFactor,
                group = UiText.StringResourceId(Res.string.group_text),
                label =  UiText.StringResourceId(Res.string.label_patternHeightFactor),
                options = { _, _ -> PatternSize.options },
                keyFactory = PatternSize,
                default = PatternSize.FACTOR_THREE_QUARTERS
            ),

            ColorPaletteFieldDescriptor(
                key = RK.baseColor,
                group = UiText.StringResourceId(Res.string.group_colors),
                label = UiText.StringResourceId(Res.string.label_baseColor),
                width = colorPaletteWidth,
                colorPalette = COLOR_PALETTE_GRAFFITY,
                default = Color(0xff0000ff)
            ),
            ColorPaletteFieldDescriptor(
                key = RK.patternColor,
                group = UiText.StringResourceId(Res.string.group_colors),
                label = UiText.StringResourceId(Res.string.label_patternColor),
                width = colorPaletteWidth,
                colorPalette = COLOR_PALETTE_GRAFFITY,
                default = Color(0xff00ffff)
            ),
            ColorPaletteFieldDescriptor(
                key = RK.outlineColor,
                group = UiText.StringResourceId(Res.string.group_colors),
                label = UiText.StringResourceId(Res.string.label_outlineColor),
                width = colorPaletteWidth,
                colorPalette = COLOR_PALETTE_GRAFFITY,
                default = Color(0xffffff00)
            ),

            ColorPaletteFieldDescriptor(
                key = RK.backgroundColor,
                group = UiText.StringResourceId(Res.string.group_colors),
                label = UiText.StringResourceId(Res.string.label_backgroundColor),
                colorPalette = COLOR_PALETTE_BACKGROUND,
                default = null
            ),

            ColorPaletteFieldDescriptor(
                key = RK.topDotsColor,
                group = UiText.StringResourceId(Res.string.group_colors),
                label = UiText.StringResourceId(Res.string.label_topDotsColor),
                width = colorPaletteWidth,
                colorPalette = COLOR_PALETTE_GRAFFITY,
                default = Color(0xff00ff00)
            ),
            ColorPaletteFieldDescriptor(
                key = RK.midDotsColor,
                group = UiText.StringResourceId(Res.string.group_colors),
                label = UiText.StringResourceId(Res.string.label_midDotsColor),
                width = colorPaletteWidth,
                colorPalette = COLOR_PALETTE_GRAFFITY,
                default = null
            ),
            ColorPaletteFieldDescriptor(
                key = RK.bottomDotsColor,
                group = UiText.StringResourceId(Res.string.group_colors),
                label = UiText.StringResourceId(Res.string.label_bottomDotsColor),
                width = colorPaletteWidth,
                colorPalette = COLOR_PALETTE_GRAFFITY,
                default = null
            ),

            EnumFieldDescriptor(
                fieldClass = BackgroundPattern::class,
                key = RK.backgroundPattern,
                group = UiText.StringResourceId(Res.string.group_colors),
                label =  UiText.StringResourceId(Res.string.label_background_image),
                options = { _, _ -> BackgroundPattern.options },
                keyFactory = BackgroundPattern,
                default = BackgroundPattern.DISTRESSED_WHITE_GRANIT
            ),
        )
    }

    override fun createInstance(newValues: Map<RK, Any?>): CreateRequestForm {
        return CreateRequestForm().initialize(DESCRIPTORS, newValues)
    }
}
