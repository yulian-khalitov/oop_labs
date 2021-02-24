package view
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TextField
import shapes.Triangle
import tornadofx.*

class TriangleView : View("Create Triangle") {
    var aField: TextField by singleAssign()
    var bField: TextField by singleAssign()
    var cField: TextField by singleAssign()

    val status = SimpleStringProperty("")

    override val root = form {
        fieldset {
            field("Side A") {
                aField = textfield()
            }
            field("Side B") {
                bField = textfield()
            }
            field("Side C") {
                cField = textfield()
            }
        }
        label(status)
        hbox {
            button("CANCEL") {
                action {
                    clearView()
                    close()
                }
            }
            button("OK") {
                action {
                    try {
                        val triangle = Triangle(
                            aField.text.toDouble(),
                            bField.text.toDouble(),
                            cField.text.toDouble()
                            )
                        find<MainView>().apply {
                            shapes.add(triangle)
                            ShapeSerial.save(shapes)
                        }
                        clearView()
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

    fun clearView() {
        aField.clear()
        bField.clear()
        cField.clear()
        status.value = ""
    }
}