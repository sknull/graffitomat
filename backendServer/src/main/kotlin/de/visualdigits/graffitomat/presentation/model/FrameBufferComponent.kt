package de.visualdigits.graffitomat.presentation.model

import org.tw.pi.framebuffer.core.AbstractFrameBuffer

interface FrameBufferComponent {

    fun getFrameBuffer(): AbstractFrameBuffer
}
