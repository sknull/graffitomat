package de.visualdigits.graffitomat.presentation.service

import de.visualdigits.graffitomat.domain.model.core.GraffitiFont
import de.visualdigits.graffitomat.domain.model.core.GraffitiPattern
import de.visualdigits.graffitomat.domain.model.dto.CreateGraffitoRequestDto
import de.visualdigits.graffitomat.domain.util.createGraffitiImage
import de.visualdigits.graffitomat.presentation.model.FrameBufferComponent
import de.visualdigits.kotlin.extensions.toPixelMatrix
import jakarta.servlet.http.HttpServletResponse
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
import org.springframework.web.bind.annotation.RequestParam
import org.tw.pi.framebuffer.inmemory.BufferedImageFrameBuffer
import java.awt.Color
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import kotlin.math.min
import kotlin.math.roundToInt

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
        fontSize: Int = 150,
        pattern: GraffitiPattern? = GraffitiPattern.SKYLINE,
        patternColor: Color? = null,
        patternHeightFactor: Float = 1.0f,
        drawBehind: Boolean = false,
        topDotsColor: Color? = null,
        midDotsColor: Color? = null,
        bottomDotsColor: Color? = null,
        graffitiFont: GraffitiFont = GraffitiFont.JRAOT,
        baseColor: Color? = null,
        outlineColor: Color? = null,
        backgroundColor: Color? = null,
    ): ByteArray {
         val graffito = createGraffitiImage(
            text = text,
            fontSize = fontSize,
            pattern = pattern,
            patternColor = patternColor,
            patternHeightFactor = patternHeightFactor,
            drawBehind = drawBehind,
            topDotsColor = topDotsColor,
            midDotsColor = midDotsColor,
            bottomDotsColor = bottomDotsColor,
            graffitiFont = graffitiFont,
            baseColor = baseColor,
            outlineColor = outlineColor,
            backgroundColor = backgroundColor
        )
        ByteArrayOutputStream().use { baos ->
            // "png" ist meistens sicherer bei ImageIO und erhält Transparenzen
            ImageIO.write(graffito, "png", baos)
            return baos.toByteArray()
        }
    }

    private suspend fun renderLoop(
        request: CreateGraffitoRequestDto
    ) {
        val frameBuffer = frameBufferComponent.getFrameBuffer()
        val g2d = frameBuffer.graphics

        g2d.color = Color.BLACK
        g2d.fillRect(0, 0, frameBuffer.width, frameBuffer.height)

        val graffito = createGraffitiImage(
            text = request.text,
            fontSize = request.fontSize,
            pattern = request.pattern,
            patternColor = request.patternColor,
            patternHeightFactor = request.patternHeightFactor,
            drawBehind = request.drawBehind,
            topDotsColor = request.topDotsColor,
            midDotsColor = request.midDotsColor,
            bottomDotsColor = request.bottomDotsColor,
            graffitiFont = request.graffitiFont,
            baseColor = request.baseColor,
            outlineColor = request.outlineColor,
            backgroundColor = request.backgroundColor
        )

        val graffitoWidth = graffito.width
        val graffitoHeight = graffito.height
        val frameBufferWidth = frameBuffer.width
        val frameBufferHeight = frameBuffer.height
        val offsetY = ((frameBufferHeight - graffitoHeight) / 2.0).roundToInt()

        if (frameBuffer is BufferedImageFrameBuffer) {
            g2d.fillRect(0, 0, frameBuffer.width, frameBuffer.height)
            val frame = graffito.getSubimage(0, 0, min(frameBufferWidth, graffitoWidth), graffitoHeight)
            g2d.drawImage(frame, 0, offsetY, null)
            println(frameBuffer.image.toPixelMatrix(targetHeight = 40))
        } else {
            for (x in 0 until graffitoWidth step 2) {
                currentCoroutineContext().ensureActive()

                g2d.fillRect(0, 0, frameBuffer.width, frameBuffer.height)
                val rest = graffitoWidth - x
                val frame = graffito.getSubimage(x, 0, min(frameBufferWidth, rest), graffitoHeight)
                g2d.drawImage(frame, 0, offsetY, null)

                frameBuffer.update()

                delay(1000L / 30L)
            }
        }

        g2d.dispose()
    }
}
