import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import shapes.*
import kotlin.random.Random
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.modules.subclass
import kotlinx.serialization.modules.polymorphic
import java.io.File

fun jsonFormat() = Json {
    serializersModule = SerializersModule {
        polymorphic(Shape::class) {
            subclass(Circle::class)
            subclass(Rectangle::class)
            subclass(Square::class)
            subclass(Triangle::class)
        }
    }
}

fun deserializeShapes(format: Json, jsonData: String) : List<Shape> {
    return format.decodeFromString(ListSerializer(PolymorphicSerializer(Shape::class)), jsonData)
}

fun serializeShapes(format: Json, shapes: List<Shape>) : String{
    return format.encodeToString(ListSerializer(PolymorphicSerializer(Shape::class)), shapes)
}

fun main() {
    val format = jsonFormat()
    val shapes = mutableListOf<Shape>()
    val file = File("shapes.json")

    if (file.exists()) {
        val shapesJson: String
        try {
            shapesJson = file.readText()
        } catch (ex: Exception) {
            println("Error while reading data from file: ${ex.message}")
            return
        }
        val shapesFromFile = deserializeShapes(format, shapesJson)
        shapes.addAll(shapesFromFile)
        println("Read data: $shapes")
    } else if (!file.createNewFile()) {
        println("Cannot create shapes.json file!")
        return
    }

    val newShapes =  mutableListOf(
            Circle(Random.nextDouble(0.0, 10.0)),
            Triangle(3.0, 4.0, 5.0),
            Square(Random.nextDouble(0.0, 10.0)),
            Rectangle(Random.nextDouble(0.0, 10.0), Random.nextDouble(0.0, 10.0))
    )
    shapes.addAll(newShapes)
    val shapesJson = serializeShapes(format, shapes)
    try {
        file.writeText(shapesJson)
    } catch (ex: Exception) {
        println("Error while saving serialized data to file: ${ex.message}")
    }
    println("Saved data: $shapes")

}
