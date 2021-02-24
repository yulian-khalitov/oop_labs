package view

import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import shapes.*
import tornadofx.*


class MainView : View("Shapes App") {
    val selectedReading = SimpleObjectProperty<Shape>()
    val shapes = FXCollections.observableArrayList(ShapeSerial.load())

    override val root = hbox {
        listview(shapes) {
            bindSelected(selectedReading)
        }
        vbox {
            hbox {
                button("Move Down") {
                    useMaxWidth = true
                    action {
                        val shape = selectedReading.value
                        val index = shapes.indexOf(shape)
                        if (index >= 0 && index < shapes.size - 1) {
                            shapes.swap(index, index + 1)
                            ShapeSerial.save(shapes)
                        }
                    }
                }
                button("Move Up") {
                    useMaxWidth = true
                    action {
                        val shape = selectedReading.value
                        val index = shapes.indexOf(shape)
                        if (index > 0 && index < shapes.size) {
                            shapes.swap(index, index - 1)
                            ShapeSerial.save(shapes)
                        }

                    }
                }
            }
            button("Remove") {
                useMaxWidth = true
                action {
                    val shape = selectedReading.value
                    val index = shapes.indexOf(shape)
                    if (index >= 0 && index < shapes.size) {
                        shapes.remove(shape)
                        ShapeSerial.save(shapes)
                    }
                }
            }
            button("Create Rectangle") {
                useMaxWidth = true
                action {
                    openInternalWindow<RectangleView>()
                }
            }
            button("Create Circle") {
                useMaxWidth = true
                action {
                    openInternalWindow<CircleView>()
                }
            }
            button("Create Square") {
                useMaxWidth = true
                action {
                    openInternalWindow<SquareView>()
                }
            }
            button("Create Triangle") {
                useMaxWidth = true
                action {
                    openInternalWindow<TriangleView>()
                }
            }
        }
    }
}