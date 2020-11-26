package shapes

import kotlin.math.sqrt

class Triangle(var a: Double, var b: Double, var c: Double) : Shape {
    override val area: Double
        get() {
            if (!doesExist)
                throw IllegalStateException("Triangle with a=$a, b=$b, c=$c cannot exist")
            return sqrt(perimeter * (perimeter - a) * (perimeter - b) * (perimeter - c))
        }

    override val perimeter: Double
        get() {
            if (!doesExist)
                throw IllegalStateException("Triangle with a=$a, b=$b, c=$c cannot exist")
            return a + b + c
        }

    override val doesExist: Boolean
        get() = a > 0 && b > 0 && c > 0 && a + b > c && b + c > a && a + c > b

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Triangle ||
                setOf(a, b, c) != setOf(other.a, other.b, other.c)) return false
        return true
    }

    override fun hashCode(): Int {
        return setOf(a, b, c).hashCode()
    }

    override fun toString(): String {
        return "Triangle($a, $b, $c)"
    }
}