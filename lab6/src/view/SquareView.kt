package view
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TextField
import shapes.Square
import shapes.ShapeSerial
import tornadofx.*


class SquareView : View("Create Square") {
    var heightField: TextField by singleAssign()
    val status = SimpleStringProperty("")

    override val root = form {
        fieldset {
            field("Height") {
                heightField = textfield()
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
                        val square = Square(heightField.text.toDouble())
                        find<MainView>().apply {
                            shapes.add(square)
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