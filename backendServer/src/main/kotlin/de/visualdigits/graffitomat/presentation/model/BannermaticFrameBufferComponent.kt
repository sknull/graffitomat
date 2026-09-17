package de.visualdigits.graffitomat.presentation.model

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.tw.pi.framebuffer.core.AbstractFrameBuffer
import org.tw.pi.framebuffer.inmemory.BufferedImageFrameBuffer

@Profile("local")
@Component
class BannermaticFrameBufferComponent : FrameBufferComponent {

    override fun getFrameBuffer(): AbstractFrameBuffer {
        return BufferedImageFrameBuffer(180, 40)
    }
}
