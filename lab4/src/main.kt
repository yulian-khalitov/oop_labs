import shapes.*
import kotlin.math.min
import kotlin.random.Random

fun main() {
    val shapeAccumulator = ShapeAccumulator()
    shapeAccumulator.add( Triangle(3.0, 4.0, 5.0))
    shapeAccumulator.addAll(mutableListOf(
            Circle(Random.nextDouble(0.0, 10.0)),
            Square(Random.nextDouble(0.0, 10.0)),
            Rectangle(Random.nextDouble(0.0, 10.0), Random.nextDouble(0.0, 10.0))
    ))

    println("Shapes:")
    shapeAccumulator.forEach { println(it) }

    println("Total area: ${shapeAccumulator.totalArea}")
    println("Total perimeter: ${shapeAccumulator.totalPerimeter}")

    val minAreaShape = shapeAccumulator.minAreaShape
    val maxAreaShape = shapeAccumulator.maxAreaShape
    val minPerimeterShape = shapeAccumulator.minPerimeterShape
    val maxPerimeterShape = shapeAccumulator.maxPerimeterShape

    println("Minimal area shape: $minAreaShape with ${minAreaShape.area}")
    println("Maximum area shape: $maxAreaShape with ${maxAreaShape.area}")
    println("Minimal perimeter shape: $minPerimeterShape with ${minPerimeterShape.perimeter}")
    println("Maximum perimeter shape: $maxPerimeterShape with ${maxPerimeterShape.perimeter}")
}