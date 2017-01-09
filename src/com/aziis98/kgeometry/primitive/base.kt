package com.aziis98.kgeometry.primitive

import com.aziis98.kgeometry.GeometricSpace
import com.aziis98.kgeometry.fillCircle
import com.aziis98.kgeometry.*
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
    fun distance(point: Point2D) = distance(point.x, point.y)

    abstract fun distance(x: Double, y: Double): Double
}

class Point(space: GeometricSpace, val xProperty: SimpleDoubleProperty, val yProperty: SimpleDoubleProperty) : Primitive(space) {

    constructor(space: GeometricSpace, x: Double, y: Double) : this(space, SimpleDoubleProperty(x), SimpleDoubleProperty(y))

    var x: Double by xProperty
    var y: Double by yProperty

    val position: Point2D
        get() = Point2D(x, y)

    override fun distance(x: Double, y: Double): Double {
        return position.distance(x, y)
    }

    override fun toString() = "Point($x, $y)"
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

    var a by aProperty
    var b by bProperty
    var c by cProperty

    override fun distance(x: Double, y: Double): Double {
        return Math.abs(a * x + b * y + c) / Math.sqrt(a * a + b * b)
    }

    override fun toString() = "Line( ($a)x + ($b)y + ($c) = 0 )"
}

