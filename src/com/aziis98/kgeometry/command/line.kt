package com.aziis98.kgeometry.command

import com.aziis98.kgeometry.primitive.Line
import com.aziis98.kgeometry.primitive.Point
import com.aziis98.kgeometry.primitive.createLine
import com.aziis98.kgeometry.rendering.RenderManager
import javafx.geometry.Point2D

/**
 * Created by aziis98 on 09/01/2017.
 */

data class LineCommand(val line: Line) : ICommand

class LineCommandHandler(manager: RenderManager) : CommandHandler(manager), IClickListener {
    var state = 0

    lateinit var a: Point
    lateinit var b: Point

    lateinit var line: Line

    override fun mouseClick(spacePosition: Point2D) {
        when (state) {
            0 -> {
                val pt = manager.getNearest<Point>()
                if (pt != null) {
                    a = pt
                    state = 1
                }
            }
            1 -> {
                val pt = manager.getNearest<Point>()
                if (pt != null) {
                    b = pt
                    state = 2

                    line = manager.space.createLine(a, b)
                    manager.space.primitives += line

                    complete()
                }
            }
        }
    }

    override fun finalize(): ICommand {
        return LineCommand(line)
    }
}