package de.visualdigits.graffitomat.presentation.model

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.tw.pi.framebuffer.FrameBuffer
import org.tw.pi.framebuffer.core.AbstractFrameBuffer

@Profile("prod")
@Component
class SpiFrameBufferComponent : FrameBufferComponent {

    private val sharedFrameBuffer: AbstractFrameBuffer by lazy {
        FrameBuffer("/dev/fb1", 0, "/sys/class/backlight/fb_ili9341/bl_power")
    }

    override fun getFrameBuffer(): AbstractFrameBuffer {
        return sharedFrameBuffer
    }
}
