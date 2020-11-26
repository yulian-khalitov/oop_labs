package shapes

import kotlin.math.PI

class Circle(var radius: Double) : Shape {
    override val area: Double
        get() {
            if (!doesExist)
                throw IllegalStateException("Circle with radius=$radius cannot exist")
            return PI * radius * radius
        }

    override val perimeter: Double
        get() {
            if (!doesExist)
                throw IllegalStateException("Circle with radius=$radius cannot exist")
            return 2 * PI * radius
        }

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