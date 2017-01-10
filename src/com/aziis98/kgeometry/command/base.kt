package com.aziis98.kgeometry.command

import com.aziis98.kgeometry.primitive.Primitive
import com.aziis98.kgeometry.rendering.RenderManager
import javafx.geometry.Point2D
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

/**
 * Created by aziis98 on 09/01/2017.
 */

interface ICommand {

}

abstract class CommandHandler(val manager: RenderManager) {
    var completed = false
        private set

    protected fun complete() {
        completed = true
    }

    // TODO: abstract fun undo()

    open fun isSelectable(primitive: Primitive) = true

    open var info: String? = ""

    open fun render(gc: GraphicsContext) {
        gc.fill = Color.BLUEVIOLET
        info?.let {
            gc.fillText("$info", 10.0, 10.0)
        }
    }

    abstract fun finalize(): ICommand
}

interface IClickListener {
    fun mouseClick(spacePosition: Point2D)
}

// CommandHandler Helpers

inline fun <reified P : Primitive> RenderManager.getNearest(): P? {
    return nearestMap.get().toList().firstOrNull { it.first is P && it.second < 7.0 }?.first as P?
}