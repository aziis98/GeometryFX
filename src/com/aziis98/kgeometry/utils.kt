package com.aziis98.kgeometry

import javafx.scene.canvas.GraphicsContext

/**
 * Created by aziis98 on 06/01/2017.
 */


fun GraphicsContext.fillCircle(x: Double, y: Double, radius: Double) =
        fillOval(x - radius, y - radius, radius * 2, radius * 2)

fun GraphicsContext.strokeCircle(x: Double, y: Double, radius: Double) =
        strokeOval(x - radius, y - radius, radius * 2, radius * 2)

fun Number.map(srcFrom: Number, srcTo: Number, outFrom: Number, outTo: Number)
        = (this.toDouble() - srcFrom.toDouble()) / (srcTo.toDouble() - srcFrom.toDouble()) * (outTo.toDouble() - outFrom.toDouble()) + outFrom.toDouble()