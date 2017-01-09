package com.aziis98.kgeometry.rendering

import com.aziis98.kgeometry.GeometricSpace
import com.aziis98.kgeometry.Worker
import com.aziis98.kgeometry.position
import com.aziis98.kgeometry.primitive.Point
import com.aziis98.kgeometry.primitive.Primitive
import com.aziis98.kgeometry.primitive.createPoint
import javafx.animation.AnimationTimer
import javafx.beans.property.SimpleDoubleProperty
import javafx.geometry.Point2D
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
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

class RenderManager(val canvas: Canvas) {

    val space = GeometricSpace()
    val renderers = mutableMapOf<KClass<out Primitive>, PrimitiveRenderer<out Primitive>>()

    val selected = mutableSetOf<Primitive>()
    val nearestMap = NearestMapWorker(this)

    fun registerRenderer(primitiveRenderer: PrimitiveRenderer<*>) {
        renderers.put(primitiveRenderer.primitiveClass, primitiveRenderer)
    }

    val translation = Translate(0.0, 0.0)
    val scale = Scale(1.0, 1.0)
    val zoom = SimpleDoubleProperty(1.0)

    val affine = Affine().apply {
        val listener: () -> Unit = {
            setToIdentity()
            appendTranslation(canvas.width / 2, canvas.height / 2)
            append(translation)
            append(scale)
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
        append(translation)
        append(scale)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Primitive> getRenderer(primitive: T) = renderers[primitive::class] as PrimitiveRenderer<T>?


    init {
        registerRenderers()
        setupCanvas()

        space.primitives += space.createPoint(0.0, 0.0)
        space.primitives += space.createPoint(100.0, 0.0)
        space.primitives += space.createPoint(0.0, 100.0)
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

            translation.x += e.x - (mousePrevPos?.x ?: e.x)
            translation.y += e.y - (mousePrevPos?.y ?: e.y)

            mousePrevPos = e.position
        }

        canvas.addEventHandler(ScrollEvent.ANY) {
            val zoomSpeed = 0.05
            zoom.set(zoom.get() + it.deltaY * zoomSpeed)
            zoom.set(zoom.get().coerceIn(0.1, 10.0))
        }
    }


    fun render(gc: GraphicsContext) {
        gc.clearRect(0.0, 0.0, canvas.width, canvas.height)

        renderPrimitives(gc)

        gc.font = Font.font("Cutive Mono", 14.0)
        gc.fill = Color.BLACK
        gc.fillText("nearest: $nearestMap", 10.0, 10.0)
        gc.fillText("affine: $affine", 10.0, 30.0)
    }


    private fun renderPrimitives(gc: GraphicsContext) {
        space.primitives.forEach {
            val firstEntry = nearestMap.get().entries.firstOrNull()
            val attributes = PrimitiveAttributes(it in selected, it == firstEntry?.key && firstEntry.value < 7.0)
            getRenderer(it)?.render(gc, it, attributes) ?: error("No renderer for primitive: $it")
        }
    }
}

class NearestMapWorker(val manager: RenderManager) : Worker<Point2D, Map<Primitive, Double>>(mapOf()) {
    override fun work(input: Point2D): Map<Primitive, Double> {
        val mousePosition = input
//        Thread.sleep(500)

        return manager.space.primitives
                .map { it to it.distance(manager.affine.inverseTransform(mousePosition)) }
                .sortedBy { it.second }
                .associateTo(LinkedHashMap()) { it }
    }

    override fun toString(): String {
        return get().mapValues { it.value.toInt() }.toString()
    }
}

// Init stuff

fun RenderManager.registerRenderers() {
    registerRenderer(PointRenderer(this))
    registerRenderer(LineRenderer(this))
}