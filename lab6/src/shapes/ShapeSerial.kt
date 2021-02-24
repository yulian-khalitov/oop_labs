package shapes

import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.io.File


object ShapeSerial {
    val json = Json {
        serializersModule = SerializersModule {
            polymorphic(Shape::class) {
                subclass(Circle::class)
                subclass(Rectangle::class)
                subclass(Square::class)
                subclass(Triangle::class)
            }
        }
    }
    val filename = "shapes.json"

    fun serialize(shapes: List<Shape>) : String{
        return json.encodeToString(ListSerializer(PolymorphicSerializer(Shape::class)), shapes)
    }

    fun deserialize(jsonData: String) : List<Shape> {
        return json.decodeFromString(ListSerializer(PolymorphicSerializer(Shape::class)), jsonData)
    }

    fun save(shapes: List<Shape>) {
        val shapesJson = serialize(shapes)
        val file = File(filename)
        file.writeText(shapesJson)
    }

    fun load() : MutableList<Shape> {
        val shapes = mutableListOf<Shape>()
        val file = File(filename)
        if (file.exists()) {
            try {
                val shapesJson = file.readText()
                val shapesFromFile = deserialize(shapesJson)
                shapes.addAll(shapesFromFile)
            } catch (ex: Exception) {
                println("Error while reading data from file: ${ex.message}")
            }
        }
        return shapes
    }
}