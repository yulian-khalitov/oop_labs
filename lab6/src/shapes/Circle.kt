package shapes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.PI

@Serializable
@SerialName("Circle")
class Circle(var radius: Double) : Shape {
    init {
        if (!doesExist) throw IllegalStateException("Circle with radius=$radius cannot exist")
    }
    override val area: Double
        get() = PI * radius * radius

    override val perimeter: Double
        get() = 2 * PI * radius

    override val doesExist: Boolean
        get() = radius > 0

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Circle ||
                radius != other.radius) return false
        return true
    }

    override fun hashCode(): Int {
        return radius.hashCode()
    }

    override fun toString(): String {
        return "Circle($radius)"
    }
}