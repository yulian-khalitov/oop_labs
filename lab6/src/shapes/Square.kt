package shapes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Square")
class Square(var height: Double) : Shape {
    init {
        if (!doesExist) throw IllegalStateException("Square with height=$height cannot exist")
    }
    override val area: Double
        get() = height * height

    override val perimeter: Double
        get() = 4 * height

    override val doesExist: Boolean
        get() = height > 0

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Square ||
                height != other.height) return false
        return true
    }

    override fun hashCode(): Int {
        return height.hashCode()
    }

    override fun toString(): String {
        return "Square($height)"
    }
}