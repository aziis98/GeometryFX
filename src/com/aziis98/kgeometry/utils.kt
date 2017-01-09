package com.aziis98.kgeometry

import javafx.beans.property.Property
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Point2D
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.MouseEvent
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by aziis98 on 06/01/2017.
 */


fun GraphicsContext.fillCircle(x: Double, y: Double, radius: Double) =
        fillOval(x - radius, y - radius, radius * 2, radius * 2)

fun GraphicsContext.strokeCircle(x: Double, y: Double, radius: Double) =
        strokeOval(x - radius, y - radius, radius * 2, radius * 2)

fun Number.map(srcFrom: Number, srcTo: Number, outFrom: Number, outTo: Number)
        = (this.toDouble() - srcFrom.toDouble()) / (srcTo.toDouble() - srcFrom.toDouble()) * (outTo.toDouble() - outFrom.toDouble()) + outFrom.toDouble()

operator fun SimpleBooleanProperty.getValue(thisRef: Any?, property: KProperty<*>): Boolean {
    return get()
}

operator fun SimpleBooleanProperty.setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
    set(value)
}

operator fun SimpleDoubleProperty.getValue(thisRef: Any?, property: KProperty<*>): Double {
    return get()
}

operator fun SimpleDoubleProperty.setValue(thisRef: Any?, property: KProperty<*>, value: Double) {
    set(value)
}

operator fun <T> SimpleObjectProperty<T>.getValue(thisRef: Any?, property: KProperty<*>): T {
    return get()
}

operator fun <T> SimpleObjectProperty<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    set(value)
}

val MouseEvent.position: Point2D
    get() = Point2D(x, y)