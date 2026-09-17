package de.visualdigits.graffitomat.domain.util

import java.awt.Color
import java.lang.Integer.decode
import java.lang.Integer.toHexString

fun String.toAwtColor(): Color = Color(decode(if (startsWith("#") || startsWith("0x")) this else "#$this"))

fun Color.hex(): String = toHexString(this.rgb).padStart(6, '0')
