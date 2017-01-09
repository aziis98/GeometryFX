package com.aziis98.kgeometry

import javafx.beans.property.Property
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.canvas.GraphicsContext
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

class DelegatedFXProperty<T>(val property: Property<T>) : ReadWriteProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return this.property.value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.property.value = value
    }
}


fun fx(doubleProperty: SimpleDoubleProperty): DelegatedFXProperty<Double> {
    return DelegatedFXProperty(doubleProperty.asObject())
}

fun fx(booleanProperty: SimpleBooleanProperty): DelegatedFXProperty<Boolean> {
    return DelegatedFXProperty(booleanProperty.asObject())
}

fun <T> fx(property: Property<T>): DelegatedFXProperty<T> {
    return DelegatedFXProperty(property)
}