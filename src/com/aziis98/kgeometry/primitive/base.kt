package com.aziis98.kgeometry.primitive

import com.aziis98.kgeometry.GeometricSpace
import com.aziis98.kgeometry.fillCircle
import com.aziis98.kgeometry.fx
import com.aziis98.kgeometry.strokeCircle
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Point2D
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.transform.Affine

/**
 * Created by aziis98 on 06/01/2017.
 */

abstract class Primitive(val space: GeometricSpace) {

    val selectedProperty = SimpleBooleanProperty(false)
    var selected by fx(selectedProperty)

    abstract fun render(gc: GraphicsContext)

}

class Point(space: GeometricSpace, val xProperty: SimpleDoubleProperty, val yProperty: SimpleDoubleProperty) : Primitive(space) {

    constructor(space: GeometricSpace, x: Double, y: Double) : this(space, SimpleDoubleProperty(x), SimpleDoubleProperty(y))

    var x: Double by fx(xProperty)
    var y: Double by fx(yProperty)

    override fun render(gc: GraphicsContext) {
        val screenPosition = space.affine.transform(x, y)

        gc.apply {

            if (selected) {
                fill = Color.ORANGE
            }
            else {
                fill = Color.BLACK
            }

            fillCircle(screenPosition.x, screenPosition.y, 3.0)

            if (space.mouseSpacePos.distance(x, y) < 5.0) {
                lineWidth = 1.0
                stroke = Color.RED
                strokeCircle(screenPosition.x, screenPosition.y, 5.0)
            }

        }
    }
}

/**
 * A line described by the equation ax+by+c=0.
 * y = - (ax + c) / b
 * x = - (by + c) / a
 */
class Line(space: GeometricSpace,
           val aProperty: SimpleDoubleProperty,
           val bProperty: SimpleDoubleProperty,
           val cProperty: SimpleDoubleProperty) : Primitive(space) {

    constructor(space: GeometricSpace, a: Double, b: Double, c: Double) : this(space, SimpleDoubleProperty(a), SimpleDoubleProperty(b), SimpleDoubleProperty(c))

    var a by fx(aProperty)
    var b by fx(bProperty)
    var c by fx(cProperty)

    override fun render(gc: GraphicsContext) {
        gc.apply {
            val spaceX1 = space.affine.inverseTransform(0.0, 0.0).x
            val screenY1 = - (a * spaceX1 + c) / b

            val spaceX2 = space.affine.inverseTransform(gc.canvas.width, 0.0).x
            val screenY2 = - (a * spaceX2 + c) / b

            if (selected) {
                fill = Color.ORANGE
            }
            else {
                fill = Color.BLACK
            }

            if (b != 0.0) {
                assert(screenY1.isFinite() && screenY2.isFinite())
                gc.strokeLine(0.0, screenY1, gc.canvas.width, screenY2)
            }
            else {
                val screenX = -(c / a)
                gc.strokeLine(screenX, 0.0, screenX, gc.canvas.height)
            }
        }
    }

}

