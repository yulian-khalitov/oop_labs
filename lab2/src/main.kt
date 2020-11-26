import shapes.*
import kotlin.math.min
import kotlin.random.Random

fun main() {
    val shapes = mutableListOf(
        Circle(Random.nextDouble(0.0, 10.0)),
        Triangle(3.0, 4.0, 5.0), //hardcoded because of triangle's specific existence conditions
        Square(Random.nextDouble(0.0, 10.0)),
        Rectangle(
                Random.nextDouble(0.0, 10.0),
                Random.nextDouble(0.0, 10.0)
        )
    )
    var totalArea = 0.0
    var totalPerimeter = 0.0

    println("Shapes:")
    shapes.forEach { shape ->
        println(shape)
        totalArea += shape.area
        totalPerimeter += shape.perimeter
    }

    println("Total area: $totalArea")
    println("Total pertimeter: $totalPerimeter")

    val minAreaShape = shapes.minByOrNull{ shape -> shape.area }
    val maxAreaShape = shapes.maxByOrNull { shape -> shape.area }
    val minPerimeterShape = shapes.minByOrNull{ shape -> shape.perimeter }
    val maxPerimeterShape = shapes.maxByOrNull { shape -> shape.perimeter}

    println("Minimal area shape: $minAreaShape with ${minAreaShape!!.area}")
    println("Maximum area shape: $maxAreaShape with ${maxAreaShape!!.area}")
    println("Minimal perimeter shape: $minPerimeterShape with ${minPerimeterShape!!.perimeter}")
    println("Maximum perimeter shape: $maxPerimeterShape with ${maxPerimeterShape!!.perimeter}")
}