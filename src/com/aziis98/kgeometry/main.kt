package com.aziis98.kgeometry

import javafx.animation.AnimationTimer
import javafx.application.Application
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
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Stage

/**
 * Created by aziis98 on 06/01/2017.
 */

class KGeometry : Application() {

    val at = object : AnimationTimer() {
        override fun handle(now: Long) {
            val gc = canvas.graphicsContext2D

            gc.apply {
                clearRect(0.0, 0.0, canvas.width, canvas.height)

                space.render(this)
            }
        }
    }

    var lastMousePos = Point2D(0.0, 0.0)
    val canvas = Canvas(1000.0, 800.0).apply {
        addEventHandler(MouseEvent.MOUSE_CLICKED) {
            if (it.button == MouseButton.SECONDARY) {
                lastMousePos = Point2D(it.x, it.y)
                contextMenu.show(this, it.screenX, it.screenY)
            }
        }
    }

    val space = GeometricSpace(canvas)
    val contextMenu: ContextMenu = createContextMenu(this)

    override fun start(primaryStage: Stage) {
        at.start()

        primaryStage.title = "KGeometry"
        primaryStage.scene = Scene(Pane(canvas).apply {
            canvas.widthProperty().bind(widthProperty())
            canvas.heightProperty().bind(heightProperty())

            style = "-fx-background-color: #fff"
        })
        primaryStage.show()
    }

}

fun main(args: Array<String>) {
    Application.launch(KGeometry::class.java, *args)
}