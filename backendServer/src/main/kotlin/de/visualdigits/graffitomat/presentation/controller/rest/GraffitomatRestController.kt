package de.visualdigits.graffitomat.presentation.controller.rest

import de.visualdigits.graffitomat.domain.model.core.BackgroundPattern
import de.visualdigits.graffitomat.domain.model.core.GraffitiFont
import de.visualdigits.graffitomat.domain.model.core.GraffitiPattern
import de.visualdigits.graffitomat.domain.model.dto.CreateGraffitoRequestDto
import de.visualdigits.graffitomat.domain.util.toAwtColor
import de.visualdigits.graffitomat.presentation.service.GraffitomatService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1")
class GraffitomatRestController(
    private val graffitomatService: GraffitomatService
) {

    @GetMapping("preview")
    fun previewGraffito(
        @RequestParam("text") text: String,
        @RequestParam("graffitiFont") graffitiFont: GraffitiFont = GraffitiFont.JRAOT,
        @RequestParam("characterTracking", required = false) characterTracking: Float = -0.3f,
        @RequestParam("pattern", required = false) pattern: GraffitiPattern? = null,
        @RequestParam("patternColor", required = false) patternColor: String? = null,
        @RequestParam("patternHeightFactor", required = false) patternHeightFactor: Float = 1.0f,
        @RequestParam("drawBehind", required = false) drawBehind: Boolean = false,
        @RequestParam("topDotsColor", required = false) topDotsColor: String? = null,
        @RequestParam("midDotsColor", required = false) midDotsColor: String? = null,
        @RequestParam("bottomDotsColor", required = false) bottomDotsColor: String? = null,
        @RequestParam("baseColor", required = false) baseColor: String? = null,
        @RequestParam("outlineColor", required = false) outlineColor: String? = null,
        @RequestParam("outlineWidth", required = false) outlineWidth: Float = 3.0f,
        @RequestParam("backgroundColor", required = false) backgroundColor: String? = null,
        @RequestParam("backgroundPattern", required = false) backgroundPattern: BackgroundPattern? = null
    ): ResponseEntity<ByteArray> {
        val imageBytes  = graffitomatService.renderGraffito(
            text = text,
            characterTracking = characterTracking,
            pattern = pattern,
            patternColor = patternColor?.toAwtColor(),
            patternHeightFactor = patternHeightFactor,
            drawBehind = drawBehind,
            topDotsColor = topDotsColor?.toAwtColor(),
            midDotsColor = midDotsColor?.toAwtColor(),
            bottomDotsColor = bottomDotsColor?.toAwtColor(),
            graffitiFont = graffitiFont,
            baseColor = baseColor?.toAwtColor(),
            outlineColor = outlineColor?.toAwtColor(),
            outlineWidth = outlineWidth,
            backgroundColor = backgroundColor?.toAwtColor(),
            backgroundPattern = backgroundPattern,
        )

        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(imageBytes)
    }

    @PostMapping("create")
    fun createGraffito(
        @RequestBody request: CreateGraffitoRequestDto,
    ) {
        graffitomatService.queueGraffitiAnimation(request)
    }
}
