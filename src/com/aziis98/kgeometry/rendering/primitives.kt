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
        gc.apply {
            if (attributes.selected) {
                stroke = Color.ORANGE
            }
            else if (attributes.hovered) {
                stroke = Color.GRAY
            }
            else {
                stroke = Color.BLACK
            }


            if (primitive.b != 0.0) {
                val spaceX1 = manager.affine.inverseTransform(0.0, 0.0).x
                val spaceY1 = - (primitive.a * spaceX1 + primitive.c) / primitive.b

                val spaceX2 = manager.affine.inverseTransform(gc.canvas.width, gc.canvas.height).x
                val spaceY2 = - (primitive.a * spaceX2 + primitive.c) / primitive.b

                val screen1 = manager.affine.transform(spaceX1, spaceY1)
                val screen2 = manager.affine.transform(spaceX2, spaceY2)

                gc.strokeLine(screen1.x, screen1.y, screen2.x, screen2.y)
            }
            else {
                val spaceX = -(primitive.c / primitive.a)
                val screenX = manager.affine.transform(spaceX, 0.0).x

                gc.strokeLine(screenX, 0.0, screenX, gc.canvas.height)
            }
        }
    }
}