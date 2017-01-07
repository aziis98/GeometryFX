package com.aziis98.kgeometry.primitive

import com.aziis98.kgeometry.GeometricSpace
import com.aziis98.kgeometry.fillCircle
import com.aziis98.kgeometry.strokeCircle
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Point2D
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.transform.Affine

/**
 * Created by aziis98 on 06/01/2017.
 */

abstract class Primitive {
    abstract fun render(gc: GraphicsContext, space: GeometricSpace, isSelected: Boolean = false)
}

class Point(position: Point2D) : Primitive() {

    constructor(x: Double, y: Double) : this(Point2D(x, y))

    val positionProperty = SimpleObjectProperty(position)
    var position: Point2D
        get() = positionProperty.get()
        set(value) = positionProperty.set(value)

    override fun render(gc: GraphicsContext, space: GeometricSpace, isSelected: Boolean) {
        val screenPosition = space.affine.transform(position)

        gc.apply {

            if (isSelected) {
                fill = Color.ORANGE
            }
            else {
                fill = Color.BLACK
            }

            fillCircle(screenPosition.x, screenPosition.y, 3.0)

            if (space.mouseSpacePos.distance(position) < 5.0) {
                lineWidth = 1.0
                stroke = Color.RED
                strokeCircle(screenPosition.x, screenPosition.y, 5.0)
            }

        }
    }
}

