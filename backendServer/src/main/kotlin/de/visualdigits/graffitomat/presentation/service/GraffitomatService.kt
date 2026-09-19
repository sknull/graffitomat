package de.visualdigits.graffitomat.presentation.service

import de.visualdigits.graffitomat.domain.model.core.BackgroundPattern
import de.visualdigits.graffitomat.domain.model.core.GraffitiFont
import de.visualdigits.graffitomat.domain.model.core.GraffitiPattern
import de.visualdigits.graffitomat.domain.model.dto.CreateGraffitoRequestDto
import de.visualdigits.graffitomat.domain.util.createGraffitiImage
import de.visualdigits.graffitomat.domain.util.createPatternImage
import de.visualdigits.graffitomat.presentation.model.FrameBufferComponent
import de.visualdigits.kotlin.extensions.toPixelMatrix
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.tw.pi.framebuffer.inmemory.BufferedImageFrameBuffer
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
@Service
class GraffitomatService(
    private val frameBufferComponent: FrameBufferComponent
) {
    
    private val log = LoggerFactory.getLogger(javaClass)

    @OptIn(DelicateCoroutinesApi::class)
    private val renderContext = newSingleThreadContext("FramebufferRenderThread")
    private val renderScope = CoroutineScope(renderContext + SupervisorJob())

    private val queue = Channel<CreateGraffitoRequestDto>(Channel.UNLIMITED)

    init {
        renderScope.launch(renderContext) {
            for (jobData in queue) {
                try {
                    log.info("Startingprocessing for job: ${jobData.text}")
                    renderLoop(jobData)
                    log.info("Finished processing job: ${jobData.text}")
                } catch (e: Exception) {
                    log.error("Error while processing job", e)
                }
            }
        }
    }

    fun queueGraffitiAnimation(request: CreateGraffitoRequestDto) {
        val success = queue.trySend(request).isSuccess
        if (success) {
            log.info("Graffito for '${request.text}' queued successfully.")
        }
    }

    fun renderGraffito(
        text: String,
        characterTracking: Float = -0.3f,
        pattern: GraffitiPattern? = null,
        patternColor: Color? = null,
        patternHeightFactor: Float = 1.0f,
        drawBehind: Boolean = false,
        topDotsColor: Color? = null,
        midDotsColor: Color? = null,
        bottomDotsColor: Color? = null,
        graffitiFont: GraffitiFont = GraffitiFont.JRAOT,
        baseColor: Color? = null,
        outlineColor: Color? = null,
        outlineWidth: Float = 3.0f,
        backgroundColor: Color? = null,
        backgroundPattern: BackgroundPattern? = null,
    ): ByteArray {
        log.info("preview $text")
         val graffito = createGraffitiImage(
             text = text,
             graffitiFont = graffitiFont,
             characterTracking = characterTracking,
             pattern = pattern,
             baseColor = baseColor,
             patternColor = patternColor,
             patternHeightFactor = patternHeightFactor,
             drawBehind = drawBehind,
             topDotsColor = topDotsColor,
             midDotsColor = midDotsColor,
             bottomDotsColor = bottomDotsColor,
             outlineColor = outlineColor,
             outlineWidth = outlineWidth
        )
        val graffitoWidth = graffito.width
        val graffitoHeight = graffito.height

        val frameBufferWidth = graffitoWidth + 60
        val frameBufferHeight = 240
        val frameBuffer = BufferedImage(frameBufferWidth, frameBufferHeight, BufferedImage.TYPE_INT_ARGB)
        val g2d = frameBuffer.graphics as Graphics2D
        renderBackground(
            frameBufferWidth = frameBufferWidth,
            frameBufferHeight = frameBufferHeight,
            backgroundPattern = backgroundPattern,
            backgroundColor = backgroundColor,
            g2d = g2d
        )
        val yOffset = ((240 - graffitoHeight) / 2.0).roundToInt()
        g2d.drawImage(graffito, 30, yOffset, null)

        g2d.dispose()

        ImageIO.write(frameBuffer, "jpg", File("e:/temp/graffito.jpg"))

        ByteArrayOutputStream().use { baos ->
            // "png" ist meistens sicherer bei ImageIO und erhält Transparenzen
            ImageIO.write(frameBuffer, "png", baos)
            return baos.toByteArray()
        }
    }

    private suspend fun renderLoop(
        request: CreateGraffitoRequestDto
    ) {
        log.info("Producing: $request")

        val frameBuffer = frameBufferComponent.getFrameBuffer()
        val frameBufferWidth = frameBuffer.width
        val frameBufferHeight = frameBuffer.height
        val g2d = frameBuffer.graphics

        val graffito = createGraffitiImage(
            text = request.text,
            graffitiFont = request.graffitiFont,
            characterTracking = request.characterTracking,
            pattern = request.pattern,
            baseColor = request.baseColor,
            patternColor = request.patternColor,
            patternHeightFactor = request.patternHeightFactor,
            drawBehind = request.drawBehind,
            topDotsColor = request.topDotsColor,
            midDotsColor = request.midDotsColor,
            bottomDotsColor = request.bottomDotsColor,
            outlineColor = request.outlineColor,
            outlineWidth = request.outlineWidth
        )

        val graffitoWidth = graffito.width
        val graffitoHeight = graffito.height
        val offsetY = ((frameBufferHeight - graffitoHeight) / 2.0).roundToInt()

        if (frameBuffer is BufferedImageFrameBuffer) {
            println(graffito.toPixelMatrix(targetHeight = 40))
        } else {
            for (x in 0 until graffitoWidth step 2) {
                currentCoroutineContext().ensureActive()

                renderBackground(
                    frameBufferWidth = frameBufferWidth,
                    frameBufferHeight = frameBufferHeight,
                    backgroundPattern = request.backgroundPattern,
                    backgroundColor = request.backgroundColor,
                    g2d = g2d
                )
                val rest = graffitoWidth - x
                val frame = graffito.getSubimage(x, 0, min(frameBufferWidth, rest), graffitoHeight)
                g2d.drawImage(frame, 0, offsetY, null)

                frameBuffer.update()

                delay((1000 / 30.0).milliseconds)
            }
        }

        g2d.dispose()
    }

    private fun renderBackground(
        frameBufferWidth: Int,
        frameBufferHeight: Int,
        backgroundPattern: BackgroundPattern?,
        backgroundColor: Color?,
        g2d: Graphics2D
    ) {
        if (backgroundPattern != null) {
            val img = backgroundPattern.load(frameBufferHeight, backgroundColor)
            val backgroundImage = createPatternImage(img, frameBufferWidth, frameBufferHeight)
            g2d.drawImage(backgroundImage, 0, 0, null)
        } else if (backgroundColor != null) {
            g2d.color = backgroundColor
            g2d.fillRect(0, 0, frameBufferWidth, frameBufferHeight)
        }
    }
}
