package com.aziis98.kgeometry

import com.aziis98.kgeometry.primitive.Line
import com.aziis98.kgeometry.primitive.Line2Point
import com.aziis98.kgeometry.primitive.Point
import com.aziis98.kgeometry.primitive.Primitive

/**
 * Created by aziis98 on 10/01/2017.
 */

fun GeometricSpace.toMathString(): String {
    return primitives.mapIndexed { i, primitive -> primitive.toMathString(i) }.joinToString("\n\n")
}

fun Primitive.toMathString(index: Int = 0): String {
    return when (this) {
        is Point -> {
            "x_$index, y_$index"
        }
        is Line2Point -> {
            val i1 = space.primitives.indexOf(p1)
            val i2 = space.primitives.indexOf(p2)
            """
            line_$index through (x_$i1, y_$i1) and (x_$i2, y_$i2):
            ($a)x + ($b)y + ($c) = 0
            """.trimMargin()
        }
        is Line -> {
            "line_$index: ($a)x + ($b)y + ($c) = 0"
        }
        else     -> "/*$this*/"
    }
}