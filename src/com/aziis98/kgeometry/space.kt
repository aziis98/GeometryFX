package com.aziis98.kgeometry

import com.aziis98.kgeometry.primitive.Point
import com.aziis98.kgeometry.primitive.Primitive
import javafx.geometry.Point2D
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.paint.Color
import javafx.scene.transform.Affine
import javafx.scene.transform.Scale
import javafx.scene.transform.TransformChangedEvent
import javafx.scene.transform.Translate
import kotlin.concurrent.thread

/**
 * Created by aziis98 on 06/01/2017.
 */

class GeometricSpace(val canvas: Canvas) {
    val primitives = mutableListOf<Primitive>()
//    val selected = mutableSetOf<Primitive>()
//
//    private var mousePos = Point2D(0.0, 0.0)
//
//    val translation = Translate(0.0, 0.0)
//    val zoom = Scale(1.0, 1.0, 0.0, 0.0)
//
//    val affine = Affine().apply {
//        val validateAffine: (TransformChangedEvent) -> Unit = {
//            setToIdentity()
//            appendTranslation(canvas.width / 2, canvas.height / 2)
//            append(translation)
//            append(zoom)
//        }
//
//        translation.addEventHandler(TransformChangedEvent.TRANSFORM_CHANGED, validateAffine)
//        zoom.addEventHandler(TransformChangedEvent.TRANSFORM_CHANGED, validateAffine)
//
//        setToIdentity()
//        appendTranslation(canvas.width / 2, canvas.height / 2)
//    }
//
//    val mouseSpacePos: Point2D
//        get() = affine.inverseTransform(mousePos)
//
//    init {
//        primitives += Point(0.0, 0.0)
//        primitives += Point(Math.random() * 100, Math.random() * 100)
//        primitives += Point(Math.random() * 100, Math.random() * 100)
//
//        canvas.addEventHandler(MouseEvent.MOUSE_MOVED) {
//            mousePos = Point2D(it.x, it.y)
//        }
//
//        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED) {
//            val newMousePos = Point2D(it.x, it.y)
//            val delta = newMousePos.subtract(mousePos)
//
//            translation.x += delta.x
//            translation.y += delta.y
//
//            mousePos = newMousePos
//        }
//
//        canvas.addEventHandler(ScrollEvent.ANY) {
//            val zoomFactor = it.deltaY * 0.1
//            zoom.x += zoomFactor
//            zoom.y += zoomFactor
//
//            zoom.x = zoom.x.coerceIn(0.1, 10.0)
//            zoom.y = zoom.y.coerceIn(0.1, 10.0)
//        }
//
//        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED) {
//            if (it.button == MouseButton.SECONDARY) {
//                println("ContextMenu")
//            }
//        }
//
//        thread(isDaemon = true) {
//            while (true) {
//                update()
//                Thread.sleep(1)
//            }
//        }
//    }
//
//    var currentCommand: ICommand? = null
//    fun handle(command: ICommand) {
//        command.init(this)
//        currentCommand = command
//    }

//    fun render(gc: GraphicsContext) {
//        gc.save()
//
//        primitives.forEach {
//            it.render(gc, this, it in selected)
//        }
//
//        currentCommand?.renderOverlay(gc, this)
//
//        gc.restore()
//    }
//
//    fun update() {
//        currentCommand = currentCommand?.handle(this)
//    }
}

//interface ICommand {
//    fun init(space: GeometricSpace) {
//
//    }
//    fun handle(space: GeometricSpace): ICommand? {
//        return null
//    }
//    fun renderOverlay(gc: GraphicsContext, space: GeometricSpace) {
//
//    }
//}
//
//data class CreatePoint(val position: Point2D) : ICommand {
//    override fun init(space: GeometricSpace) {
//        space.primitives += Point(space.affine.inverseTransform(position))
//    }
//}
//
//data class CreateCircle(val position: Point2D) : ICommand {
//    override fun renderOverlay(gc: GraphicsContext, space: GeometricSpace) {
//        gc.lineWidth = 1.0
//        gc.stroke = Color.LIGHTGRAY
//        gc.strokeCircle(position.x, position.y, position.distance(space.mouseSpacePos))
//    }
//}