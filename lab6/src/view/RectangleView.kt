package view
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TextField
import shapes.Rectangle
import tornadofx.*

class RectangleView : View("Create Rectangle") {
    var heightField: TextField by singleAssign()
    var widthField: TextField by singleAssign()
    val status = SimpleStringProperty("")

    override val root = form {
        fieldset {
            field("Height") {
                heightField = textfield()
            }
            field("Width") {
                widthField = textfield()
            }
        }
        label(status)
        hbox {
            button("CANCEL") {
                action {
                    close()
                }
            }
            button("OK") {
                action {
                    try {
                        val rectangle = Rectangle(heightField.text.toDouble(), widthField.text.toDouble())
                        find<MainView>().apply {
                            shapes.add(rectangle)
                            ShapeSerial.save(shapes)
                        }
                        close()
                    } catch (e: Exception) {
                        when (e) {
                            is NumberFormatException -> {
                                status.value = "Not a number"
                            }
                            is IllegalStateException -> {
                                status.value = "Invalid params"
                            }
                            else -> throw e
                        }
                    }
                }
            }
        }
    }
}