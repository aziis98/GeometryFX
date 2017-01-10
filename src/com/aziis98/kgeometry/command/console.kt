package com.aziis98.kgeometry.command

import com.aziis98.kgeometry.rendering.RenderManager
import com.aziis98.kgeometry.toMathString

/**
 * Created by aziis98 on 10/01/2017.
 */

data class ConsoleCommand(val command: String) : ICommand

class ConsoleCommandHandler(manager: RenderManager, commandPrefix: String = "") : CommandHandler(manager) {
    lateinit var command: String

    init {
        manager.consoleDialog.inputText(commandPrefix) {
            command = it
            runCommand()
            complete()
        }
    }

    fun runCommand() {
//        println("Command: \"$command\"")

        when {
            command.startsWith(":") -> when (command) {
                ":dump-math" -> println(manager.space.toMathString())

                ":point" -> manager.handleCommand(PointCommandHandler(manager))
                ":line" -> manager.handleCommand(LineCommandHandler(manager))

                else -> System.err.println("[Warning] Ignored unknown command: \"$command\"")
            }
        }

    }

    override fun finalize(): ICommand {
        return ConsoleCommand(command)
    }
}