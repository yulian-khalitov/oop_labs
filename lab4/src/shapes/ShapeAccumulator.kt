package shapes

class ShapeAccumulator {
    val shapes = mutableListOf<Shape>()

    fun add(shape: Shape) = shapes.add(shape)

    fun addAll(collection: Collection<Shape>) = shapes.addAll(collection)

    val maxAreaShape
        get() = shapes.maxByOrNull{ shape -> shape.area }!!

    val minAreaShape
        get() = shapes.minByOrNull{ shape -> shape.area }!!

    val maxPerimeterShape
        get() = shapes.maxByOrNull{ shape -> shape.perimeter }!!

    val minPerimeterShape
        get() = shapes.minByOrNull{ shape -> shape.perimeter }!!

    val totalArea
        get() = shapes.sumByDouble { it.area }

    val totalPerimeter
        get() = shapes.sumByDouble { it.perimeter }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is ShapeAccumulator ||
                shapes != other.shapes) return false
        return true
    }

    override fun hashCode() = shapes.hashCode()

    inline fun forEach(action: (Shape) -> Unit) = shapes.forEach { action(it) }
}