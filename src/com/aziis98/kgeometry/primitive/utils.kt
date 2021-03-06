package com.aziis98.kgeometry.primitive

import com.aziis98.kgeometry.GeometricSpace
import com.aziis98.kgeometry.*
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.value.ObservableValue
import javafx.geometry.Point2D

/**
 * Created by aziis98 on 08/01/2017.
 */

// Factories

fun GeometricSpace.createPoint(x: Double, y: Double): Point {
    return Point(this, x, y)
}

/**
 * x (y1 - y2) + y (x2 - x1) + x1 y2 - x2 y1 = 0
 */
fun GeometricSpace.createLine(p1: Point, p2: Point): Line {
//    val aProp = SimpleDoubleProperty(0.0)
//    var a: Double by aProp
//
//    val bProp = SimpleDoubleProperty(0.0)
//    var b: Double by bProp
//
//    val cProp = SimpleDoubleProperty(0.0)
//    var c: Double by cProp
//
//    val listener: (ObservableValue<out Number>, Number, Number) -> Unit = { observable, oldValue, newValue ->
//        a = p1.y - p2.y
//        b = p2.x - p1.x
//        c = p1.x * p2.y - p2.x * p1.y
//    }
//
//    p1.xProperty.addListener(listener)
//    p1.yProperty.addListener(listener)
//
//    p2.xProperty.addListener(listener)
//    p2.yProperty.addListener(listener)

    return Line2Point(this, p1, p2)
}


