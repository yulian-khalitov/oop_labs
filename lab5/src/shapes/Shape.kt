package shapes

import kotlinx.serialization.Polymorphic

@Polymorphic
interface Shape {
    val area : Double
    val perimeter : Double
    val doesExist : Boolean
}