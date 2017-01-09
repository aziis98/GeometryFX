package com.aziis98.kgeometry.rendering

import com.aziis98.kgeometry.fillCircle
import com.aziis98.kgeometry.primitive.Line
import com.aziis98.kgeometry.primitive.Point
import com.aziis98.kgeometry.primitive.Primitive
import com.aziis98.kgeometry.strokeCircle
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import kotlin.reflect.KClass

/**
 * Created by aziis98 on 09/01/2017.
 */

data class PrimitiveAttributes(val selected: Boolean, val hovered: Boolean)

abstract class PrimitiveRenderer<T : Primitive>(val primitiveClass: KClass<T>, val manager: RenderManager) {
    abstract fun render(gc: GraphicsContext, primitive: T, attributes: PrimitiveAttributes)
}

class PointRenderer(manager: RenderManager) : PrimitiveRenderer<Point>(Point::class, manager) {
    override fun render(gc: GraphicsContext, primitive: Point, attributes: PrimitiveAttributes) {
        val screenPosition = manager.affine.transform(primitive.x, primitive.y)

        gc.apply {

            if (attributes.selected) {
                fill = Color.ORANGE
            }
            else {
                fill = Color.BLACK
            }

            fillCircle(screenPosition.x, screenPosition.y, 3.0)

            if (attributes.hovered) {
                lineWidth = 1.0
                stroke = Color.RED
                strokeCircle(screenPosition.x, screenPosition.y, 5.0)
            }
        }
    }
}

class LineRenderer(manager: RenderManager) : PrimitiveRenderer<Line>(Line::class, manager) {
    override fun render(gc: GraphicsContext, primitive: Line, attributes: PrimitiveAttributes) {
//        gc.apply {
//            val spaceX1 = space.affine.inverseTransform(0.0, 0.0).x
//            val screenY1 = - (a * spaceX1 + c) / b
//
//            val spaceX2 = space.affine.inverseTransform(gc.canvas.width, 0.0).x
//            val screenY2 = - (a * spaceX2 + c) / b
//
//            if (attributes.selected) {
//                fill = Color.ORANGE
//            }
//            else {
//                fill = Color.BLACK
//            }
//
//            if (b != 0.0) {
//                assert(screenY1.isFinite() && screenY2.isFinite())
//                gc.strokeLine(0.0, screenY1, gc.canvas.width, screenY2)
//            }
//            else {
//                val screenX = -(c / a)
//                gc.strokeLine(screenX, 0.0, screenX, gc.canvas.height)
//            }
//        }
    }
}