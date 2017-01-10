package com.aziis98.kgeometry.rendering

import com.aziis98.kgeometry.ConsoleDialog
import com.aziis98.kgeometry.GeometricSpace
import com.aziis98.kgeometry.Worker
import com.aziis98.kgeometry.command.CommandHandler
import com.aziis98.kgeometry.command.ICommand
import com.aziis98.kgeometry.command.IClickListener
import com.aziis98.kgeometry.command.getNearest
import com.aziis98.kgeometry.position
import com.aziis98.kgeometry.primitive.*
import javafx.animation.AnimationTimer
import javafx.beans.property.SimpleDoubleProperty
import javafx.geometry.Point2D
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.transform.Affine
import javafx.scene.transform.Scale
import javafx.scene.transform.TransformChangedEvent
import javafx.scene.transform.Translate
import kotlin.concurrent.thread
import kotlin.reflect.KClass

/**
 * Created by aziis98 on 09/01/2017.
 */

class RenderManager(val canvas: Canvas, val consoleDialog: ConsoleDialog) {

    val space = GeometricSpace()
    val renderers = mutableMapOf<KClass<out Primitive>, PrimitiveRenderer<out Primitive>>()

    val selected = mutableSetOf<Primitive>()
    val nearestMap = NearestMapWorker(this)

    var commandHandler: com.aziis98.kgeometry.command.CommandHandler? = null
    val commands = mutableListOf<ICommand>()

    fun registerRenderer(primitiveRenderer: PrimitiveRenderer<*>) {
        renderers.put(primitiveRenderer.primitiveClass, primitiveRenderer)
    }

    val translation = Translate(0.0, 0.0)
    val scale = Scale(1.0, 1.0, 0.0, 0.0)
    val zoom = SimpleDoubleProperty(1.0)

    val affine = Affine().apply {
        val listener: () -> Unit = {
            setToIdentity()
            appendTranslation(canvas.width / 2, canvas.height / 2)
            append(scale)
            append(translation)
        }

        translation.addEventHandler(TransformChangedEvent.TRANSFORM_CHANGED) { listener() }

        scale.xProperty().bind(zoom)
        scale.yProperty().bind(zoom)
        zoom.addListener { _, _, _ -> listener() }

        canvas.widthProperty().addListener { _, _, _ -> listener() }
        canvas.heightProperty().addListener { _, _, _ -> listener() }

        // init
        setToIdentity()
        appendTranslation(canvas.width / 2, canvas.height / 2)
        append(scale)
        append(translation)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Primitive> getRenderer(primitive: T) = renderers.filter { it.key.isInstance(primitive) }.values.firstOrNull() as PrimitiveRenderer<T>?

    var draggingPoint: Point? = null

    init {
        registerRenderers()
        setupCanvas()

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED) {
            draggingPoint = getNearest<Point>()
        }
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED) {
            draggingPoint = null
        }

        val p1 = space.createPoint(0.0, 0.0)
        val p2 = space.createPoint(100.0, 0.0)

        space.primitives += p1
        space.primitives += p2
        space.primitives += space.createPoint(0.0, 100.0)

        space.primitives += space.createLine(p1, p2)
    }


    private fun setupCanvas() {
        object : AnimationTimer() {
            override fun handle(now: Long) {
                render(canvas.graphicsContext2D)
            }
        }.start()

        var mousePrevPos: Point2D? = null
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED) { e ->
            nearestMap.push(Point2D(e.x, e.y))

            mousePrevPos = e.position
        }

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED) { e ->
            nearestMap.push(Point2D(e.x, e.y))

            if (draggingPoint != null) {
                draggingPoint?.let {
                    it.x += (e.x - (mousePrevPos?.x ?: e.x)) / zoom.get()
                    it.y += (e.y - (mousePrevPos?.y ?: e.y)) / zoom.get()
                }
            }
            else {
                translation.x += (e.x - (mousePrevPos?.x ?: e.x)) / zoom.get()
                translation.y += (e.y - (mousePrevPos?.y ?: e.y)) / zoom.get()
            }

            mousePrevPos = e.position
        }

        canvas.addEventHandler(ScrollEvent.ANY) {
            val zoomSpeed = 0.05
            zoom.set(zoom.get() + it.deltaY * zoomSpeed)
            zoom.set(zoom.get().coerceIn(0.1, 10.0))
        }

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED) { e ->
            val handler = commandHandler
            if (e.button == MouseButton.PRIMARY && handler != null) {
                if (handler is IClickListener)
                    handler.mouseClick(affine.inverseTransform(e.position))
            }

            if (e.button == MouseButton.SECONDARY) {
                println("ContextMenu!")
            }
        }

        canvas.addEventHandler(KeyEvent.KEY_PRESSED) {
            if (it.code == KeyCode.T) {
                consoleDialog.inputText { println("console: $it") }
            }
        }
    }


    fun render(gc: GraphicsContext) {
        gc.font = Font.font("Cutive Mono", 14.0)
        gc.clearRect(0.0, 0.0, canvas.width, canvas.height)

        renderPrimitives(gc)

        commandHandler?.let {

            it.render(gc)

            if (it.completed) {
                commands += it.finalize()
                commandHandler = null
            }

        }

//        gc.fill = Color.BLACK
//        gc.fillText("nearest: $nearestMap", 10.0, 10.0)
//        gc.fillText("affine: $affine", 10.0, 30.0)
    }


    private fun renderPrimitives(gc: GraphicsContext) {
        space.primitives.forEach {
            val firstEntry = nearestMap.get().entries.firstOrNull()
            val attributes = PrimitiveAttributes(
                    commandHandler == null && it in selected,
                    it == firstEntry?.key && firstEntry.value < 7.0
            )
            gc.save()
            getRenderer(it)?.render(gc, it, attributes) ?: error("No renderer for primitive: $it")
            gc.restore()
        }
    }

    fun handleCommand(commandHandler: CommandHandler) {
        this.commandHandler = commandHandler
    }
}

class NearestMapWorker(val manager: RenderManager) : Worker<Point2D, LinkedHashMap<Primitive, Double>>(LinkedHashMap()) {
    override fun work(input: Point2D): LinkedHashMap<Primitive, Double> {
        val mousePosition = input
//        Thread.sleep(500)

        // Points are scored more than other primitives
        return manager.space.primitives
                .map { it to it.distance(manager.affine.inverseTransform(mousePosition)) }  // List<Pair<Primitive, *Score*>>
                .map { if (it.first !is Point) it.first to it.second + 3.0 else it }        // Adjusting scores
                .sortedBy { it.second }
                .associateTo(LinkedHashMap()) { it }
    }

    override fun toString() = get().mapValues { it.value.toInt() }.toString()
}

// Init stuff

fun RenderManager.registerRenderers() {
    registerRenderer(PointRenderer(this))
    registerRenderer(LineRenderer(this))
}