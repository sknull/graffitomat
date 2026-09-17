package de.visualdigits.graffitomat.data.graffitomat.util

import de.visualdigits.graffitomat.domain.model.core.GraffitiFont
import de.visualdigits.graffitomat.domain.model.core.GraffitiPattern
import de.visualdigits.graffitomat.domain.util.createGraffitiImage
import org.tw.pi.framebuffer.core.AbstractFrameBuffer
import org.tw.pi.framebuffer.core.AbstractFrameBufferApplication
import java.awt.Color
import java.lang.Thread.sleep
import kotlin.math.min
import kotlin.math.roundToInt

class TestApp(
    frameBuffer: AbstractFrameBuffer
) : AbstractFrameBufferApplication(frameBuffer, false) {

    override fun runApplication() {
        val g2d = frameBuffer.graphics
        g2d.color = Color.BLACK
        g2d.fillRect(0, 0, frameBuffer.width, frameBuffer.height)

        val graffito = createGraffitiImage(
            text = "GRAFFITOMAT",
            fontSize = 200,
            pattern = GraffitiPattern.SKYLINE,
            patternColor = Color.YELLOW,
            patternHeightFactor = 0.7f,
            drawBehind = false,
            topDotsColor = Color.GREEN,
            midDotsColor = null,
            bottomDotsColor = null,
            graffitiFont = GraffitiFont.JRAOT,
            baseColor = Color.RED,
            outlineColor = Color.BLUE,
            backgroundColor = Color.BLACK
        )
        val graffitoWidth = graffito.width
        val graffitoHeight = graffito.height
        val frameBufferWidth = frameBuffer.width
        val frameBufferHeight = frameBuffer.height
        val offsetY = ((frameBufferHeight - graffitoHeight) / 2.0).roundToInt()
        if (graffitoWidth > frameBufferWidth) {
            (0 until graffitoWidth step 2).forEach { x ->
                g2d.fillRect(0, 0, frameBuffer.width, frameBuffer.height)
                val rest = graffitoWidth - x
                val frame = graffito.getSubimage(x, 0, min(frameBufferWidth, rest), graffitoHeight)
                g2d.drawImage(frame, 0, offsetY, null)
                frameBuffer.update()
                sleep(1000 / 30)
            }
            g2d.dispose()
        } else {
            g2d.drawImage(graffito, 0, 0, null)
            g2d.dispose()
            frameBuffer.update()
        }
    }
}
