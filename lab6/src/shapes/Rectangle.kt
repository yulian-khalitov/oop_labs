package shapes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Rectangle")
class Rectangle(var width: Double, var height: Double) : Shape {
    init {
        if (!doesExist) throw IllegalStateException("Rectangle with width=$width and height=$height cannot exist")
    }
    override val area: Double
        get() = width * height

    override val perimeter: Double
        get() = 2 * (width + height)

    override val doesExist: Boolean
        get() = width > 0 && height > 0

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Rectangle ||
                setOf(height, width) != setOf(other.height, other.width)) return false
        return true
    }

    override fun hashCode(): Int {
        return setOf(height, width).hashCode()
    }

    override fun toString(): String {
        return "Rectangle($height, $width)"
    }
}