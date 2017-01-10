package com.aziis98.kgeometry

import com.aziis98.kgeometry.rendering.RenderManager
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.ContextMenu
import javafx.scene.control.CustomMenuItem
import javafx.scene.control.MenuItem
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.scene.layout.Pane
import javafx.scene.text.Font


/**
 * Created by aziis98 on 06/01/2017.
 */

class KGeometry : Application() {

    val canvasPane = CanvasPane(1000.0, 800.0)

    val canvas = canvasPane.canvas.apply {
        addEventHandler(MouseEvent.MOUSE_CLICKED) {
            if (it.button == MouseButton.SECONDARY) {
                contextMenu.show(this, it.screenX, it.screenY)
            }
        }

        isFocusTraversable = true
    }
    val consoleField = TextField().apply {
        style = "-fx-background-color: #eee;"
        font = Font.font("monospace", 15.0)
    }

    val manager = RenderManager(canvas, ConsoleDialog(consoleField))
    val contextMenu: ContextMenu = createContextMenu(manager)

    override fun start(primaryStage: Stage) {
        primaryStage.title = "KGeometry"
        val root = BorderPane()

        root.center = canvasPane
        root.bottom = consoleField

        root.style = "-fx-background: white;"

        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

}

fun main(args: Array<String>) {
    Application.launch(KGeometry::class.java, *args)
}

class ConsoleDialog(val textField: TextField) {

    var shown: Boolean
        get() = textField.visibleProperty().get()
        set(value) = textField.visibleProperty().set(value)

    var theCallback: ((String) -> Unit)? = null

    init {
        textField.apply {
            managedProperty().bind(visibleProperty())

            addEventHandler(KeyEvent.KEY_PRESSED) {
                if (it.code == KeyCode.ENTER) {
                    val text = textField.text
                    theCallback?.invoke(text)
                    theCallback = null
                    shown = false
                }
                if (it.code == KeyCode.ESCAPE) {
                    shown = false
                    theCallback = null
                }
            }
        }

        shown = false
    }

    fun inputText(message: String = "", callback: (String) -> Unit) {
//        println("[Showing the console]")
        if (!shown) {
            shown = true
            theCallback = callback
            textField.text = message
            Platform.runLater {
                textField.requestFocus()
                textField.positionCaret(textField.text.length)
            }
        }
    }
}

class CanvasPane(width: Double, height: Double) : Pane() {

    val canvas: Canvas

    init {
        canvas = Canvas(width, height)
        children.add(canvas)
    }

    override fun layoutChildren() {
        val x = snappedLeftInset()
        val y = snappedTopInset()
        val w = snapSize(width) - x - snappedRightInset()
        val h = snapSize(height) - y - snappedBottomInset()
        canvas.layoutX = x
        canvas.layoutY = y
        canvas.width = w
        canvas.height = h
    }
}