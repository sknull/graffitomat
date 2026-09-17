package de.visualdigits.graffitomat.presentation.model

import org.tw.pi.framebuffer.core.AbstractFrameBuffer
import org.tw.pi.framebuffer.swing.SwingFrameBuffer

class SwingFrameBufferComponent : FrameBufferComponent {

    override fun getFrameBuffer(): AbstractFrameBuffer {
        return SwingFrameBuffer(320, 240, 2.0, 30, "graffitOmat", null)
    }
}
