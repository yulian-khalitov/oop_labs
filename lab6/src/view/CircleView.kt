package view
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TextField
import shapes.Circle
import shapes.ShapeSerial
import tornadofx.*


class CircleView : View("Create Circle") {
    var radiusField: TextField by singleAssign()
    val status = SimpleStringProperty("")

    override val root = form {
        fieldset {
            field("Radius") {
                radiusField = textfield()
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
                        val circle = Circle(radiusField.text.toDouble())
                        find<MainView>().apply {
                            shapes.add(circle)
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