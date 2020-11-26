package shapes

class Rectangle(var width: Double, var height: Double) : Shape {
    override val area: Double
        get() {
            if (!doesExist)
                throw IllegalStateException("Rectangle with width=$width and height=$height cannot exist")
            return width * height
        }

    override val perimeter: Double
        get() {
            if (!doesExist)
                throw IllegalStateException("Rectangle with width=$width and height=$height cannot exist")
            return 2 * (width + height)
        }

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