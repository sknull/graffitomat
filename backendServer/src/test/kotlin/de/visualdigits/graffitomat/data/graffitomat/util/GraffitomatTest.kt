package de.visualdigits.graffitomat.data.graffitomat.util

import org.junit.jupiter.api.Test
import org.tw.pi.framebuffer.swing.SwingFrameBuffer

class GraffitomatTest {

    @Test
    fun test() {
        val frameBuffer = SwingFrameBuffer(320, 240, 2.0, 30, "graffitOmat", null)
        val testApp = TestApp(frameBuffer)
        testApp.run()

        while(true) {
            Thread.sleep(1000)
        }
    }
}
