package ru.netology.custom_view.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import ru.netology.custom_view.R
import ru.netology.custom_view.R.styleable
import ru.netology.custom_view.utils.Utils
import kotlin.math.min
import kotlin.random.Random

class StatsView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private var radius = 0F
    private var center = PointF(0F, 0F)
    private var oval = RectF(0F, 0F, 0F, 0F)

    private var lineWidth = Utils.dp(context, 5F).toFloat()
    private var fontSize = Utils.dp(context, 40F).toFloat()
    private var colors = emptyList<Int>()
    private var sumValues: Float = 0F
    private var colorOfEmptySpace = ContextCompat.getColor(context, R.color.light_grey)

    init {
        context.withStyledAttributes(attrs, styleable.StatsView) {
            lineWidth = getDimension(styleable.StatsView_lineWidth, lineWidth)
            fontSize = getDimension(styleable.StatsView_fontSize, fontSize)
            colors = listOf(
                    getColor(
                            styleable.StatsView_color1,
                            randomColor()
                    ),
                    getColor(
                            styleable.StatsView_color2,
                            randomColor()
                    ),
                    getColor(
                            styleable.StatsView_color3,
                            randomColor()
                    ),
                    getColor(
                            styleable.StatsView_color4,
                            randomColor()
                    ),
                    getColor(
                            styleable.StatsView_color5,
                            randomColor()
                    ),
                    getColor(
                            styleable.StatsView_color6,
                            randomColor()
                    ),
                    getColor(
                            styleable.StatsView_color7,
                            randomColor()
                    ),
                    getColor(
                            styleable.StatsView_color8,
                            randomColor()
                    ),
                    getColor(
                            styleable.StatsView_color9,
                            randomColor()
                    ),
                    getColor(
                            styleable.StatsView_color10,
                            randomColor()
                    )
            )
        }
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = lineWidth
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = fontSize
    }

    var data: List<Float> = emptyList()
        set(value) {
            field = value
            sumValues = data.sum()
            invalidate()
        }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = min(w, h) / 2F - lineWidth / 2
        center = PointF(w / 2F, h / 2F)
        oval = RectF(
                center.x - radius, center.y - radius,
                center.x + radius, center.y + radius
        )
    }

    override fun onDraw(canvas: Canvas) {
        if (data.isEmpty()) {
            return
        }

        var startFrom = -90F
        paint.color = colorOfEmptySpace
        canvas.drawCircle(center.x, center.y, radius, paint)

        for ((index, datum) in data.withIndex()) {

            val angle = if (sumValues > 100F) {
                360F * (datum / sumValues)
            } else {
                (datum * 3.6F)
            }
            paint.color = colors.getOrNull(index) ?: randomColor()
            canvas.drawArc(oval, startFrom, angle, false, paint)
            paint.color = colors[0]
            canvas.drawArc(oval, -90F, 1F, false, paint)

            startFrom += angle
        }

        canvas.drawText(
                "%.2f%%".format(
                        if (sumValues > 100F) {
                            100F
                        } else {
                            sumValues
                        }
                ),
                center.x,
                center.y + textPaint.textSize / 4,
                textPaint
        )
    }

    private fun randomColor() = Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())
}

