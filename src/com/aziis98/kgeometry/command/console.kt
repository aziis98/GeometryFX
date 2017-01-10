package com.aziis98.kgeometry.command

import com.aziis98.kgeometry.rendering.RenderManager
import com.aziis98.kgeometry.toMathString

/**
 * Created by aziis98 on 10/01/2017.
 */

data class ConsoleCommand(val command: String) : ICommand

class ConsoleCommandHandler(manager: RenderManager) : CommandHandler(manager) {
    lateinit var command: String

    init {
        manager.consoleDialog.inputText {
            command = it
            runCommand()
            complete()
        }
    }

    fun runCommand() {
        println("Command: $command")

        when {
            command.startsWith(":") -> when (command) {
                ":dump-math" -> println(manager.space.toMathString())
            }
        }

    }

    override fun finalize(): ICommand {
        return ConsoleCommand(command)
    }
}