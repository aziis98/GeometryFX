package com.aziis98.kgeometry

import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem

/**
 * Created by aziis98 on 06/01/2017.
 */

fun createContextMenu(application: KGeometry): ContextMenu {
    return ContextMenu(
            MenuItem("Point").apply {
                setOnAction {
//                    application.space.handle(CreatePoint(application.lastMousePos))
                }
            },
            MenuItem("Circle").apply {
                setOnAction {
//                    application.space.handle(CreateCircle(application.lastMousePos))
                }
            }
    )
}