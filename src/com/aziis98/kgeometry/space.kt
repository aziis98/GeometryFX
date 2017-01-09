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

class GeometricSpace {
    val primitives = mutableListOf<Primitive>()

    fun getNearestPrimitive(x: Double, y: Double) {

    }
}