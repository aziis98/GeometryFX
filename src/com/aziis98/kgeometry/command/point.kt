package com.aziis98.kgeometry.command

import com.aziis98.kgeometry.primitive.Point
import com.aziis98.kgeometry.primitive.createPoint
import com.aziis98.kgeometry.rendering.RenderManager
import javafx.geometry.Point2D

/**
 * Created by aziis98 on 09/01/2017.
 */

data class PointCommand(val point: Point) : ICommand

class PointCommandHandler(manager: RenderManager) : CommandHandler(manager), IClickListener {
    lateinit var point: Point

    override fun mouseClick(spacePosition: Point2D) {
        point = manager.space.createPoint(spacePosition.x, spacePosition.y)
        manager.space.primitives += point
        complete()
    }

    override fun finalize() = PointCommand(point)
}