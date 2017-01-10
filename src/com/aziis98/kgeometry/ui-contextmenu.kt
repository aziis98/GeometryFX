package com.aziis98.kgeometry

import com.aziis98.kgeometry.command.ConsoleCommandHandler
import com.aziis98.kgeometry.command.LineCommandHandler
import com.aziis98.kgeometry.command.PointCommandHandler
import com.aziis98.kgeometry.rendering.RenderManager
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem

/**
 * Created by aziis98 on 06/01/2017.
 */

fun createContextMenu(manager: RenderManager): ContextMenu {
    return ContextMenu(
            MenuItem("Point").apply {
                setOnAction {
                    manager.handleCommand(PointCommandHandler(manager))
                }
            },
            MenuItem("Line").apply {
                setOnAction {
                    manager.handleCommand(LineCommandHandler(manager))
                }
            },
            MenuItem("Command...").apply {
                setOnAction {
                    manager.handleCommand(ConsoleCommandHandler(manager))
                }
            }
    )
}