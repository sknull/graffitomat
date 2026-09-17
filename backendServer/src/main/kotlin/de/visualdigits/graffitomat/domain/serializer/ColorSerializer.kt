package de.visualdigits.graffitomat.domain.serializer

import de.visualdigits.graffitomat.domain.util.hex
import de.visualdigits.graffitomat.domain.util.toAwtColor
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.awt.Color

class ColorSerializer : KSerializer<Color> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "Color",
        PrimitiveKind.STRING
    )

    override fun deserialize(decoder: Decoder): Color {
        return decoder.decodeString().toAwtColor()
    }

    override fun serialize(encoder: Encoder, value: Color) {
        encoder.encodeString(value.hex())
    }
}
