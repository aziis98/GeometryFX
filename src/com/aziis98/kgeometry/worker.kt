package com.aziis98.kgeometry

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingDeque
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import kotlin.concurrent.thread

/**
 * Created by aziis98 on 09/01/2017.
 */

abstract class Worker<T, out R>(initialReturnValue: R) {

    var queue = LinkedBlockingDeque<T>()

    private var lastInput: T? = null
    private var lastValue: R = initialReturnValue

    init {
        thread(isDaemon = true) {
            while (true) {
                val input = queue.take()
                lastValue = work(input)
            }
        }
    }

    fun push(value: T) {
        lastInput = value
        queue.push(value)
    }

    fun get() = lastValue
    fun getLastInput() = lastInput

    abstract fun work(input: T): R
}