package com.pubg.sb.pubgassist.widget.irregular

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

/**
 *  @author XY on 2019/8/27
 *  @apiNote 不规则View
 */
class IrRegularView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                              defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val mPaint by lazy {
        Paint()
    }

    private var mWidth = 0
    private var mHeight = 0
    private val mRegions = arrayListOf<Region>()//存放4部分的图图像信息

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        init()
    }

    private fun init() {
        //初始化画笔
        mPaint.also {
            it.isAntiAlias = true
            it.style = Paint.Style.FILL
            it.color = Color.RED
            it.strokeWidth = 0f
        }


        val innerRadius = mWidth / 4f - 10//内圆半径
        val outerRadius = mWidth / 2f - 10//外圆半径
        val gapDegreeInner = 10 / (2 * innerRadius * Math.PI) * 360//缝隙旋转的角度
        val gapDegreeOuter = 10 / (2 * outerRadius * Math.PI) * 360//缝隙旋转的角度

        //分为4个部分，每次画1/4,之后再旋转90°画下一次
        for (i in 0..3) {
            val path = Path()


            path.addArc(RectF(-innerRadius, -innerRadius, innerRadius, innerRadius),
                    (-135f + gapDegreeInner).toFloat(), (90f - gapDegreeInner).toFloat())
            path.lineTo((outerRadius * cos(degree2Radians(-(45 + gapDegreeOuter)))).toFloat(),
                    (outerRadius * sin(degree2Radians(-(45 + gapDegreeOuter)))).toFloat())
            path.addArc(RectF(-outerRadius, -outerRadius, outerRadius, outerRadius),
                    (-45f - gapDegreeOuter).toFloat(), -(90f - gapDegreeOuter).toFloat())
            path.lineTo(-(innerRadius * cos(degree2Radians(-(45 + gapDegreeInner)))).toFloat(),
                    (innerRadius * sin(degree2Radians(-(45 + gapDegreeInner)))).toFloat())

            path.transform(Matrix().also {
                it.setRotate((i * 90).toFloat())
            })

            val region = Region().also {
                it.setPath(path, Region((-outerRadius).toInt(), (-outerRadius).toInt(),
                        outerRadius.toInt(), outerRadius.toInt()))
            }

            region.translate(mWidth / 2, mHeight / 2)//讲坐标轴平移到中心
            mRegions.add(region)
        }

        //添加中心的圆
        val pathCircleCenter = Path()
        pathCircleCenter.addCircle(0f, 0f, innerRadius - 15, Path.Direction.CW)
        val regionCircleCenter = Region().also {
            it.setPath(pathCircleCenter, Region((-innerRadius).toInt(), (-innerRadius).toInt(),
                    innerRadius.toInt(), innerRadius.toInt()))
        }
        regionCircleCenter.translate(mWidth / 2, mHeight / 2)
        mRegions.add(regionCircleCenter)


    }

    /**
     * 角度转化为弧度
     */
    private fun degree2Radians(degree: Double): Double {
        return degree * Math.PI / 180
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        canvas?.save()
//        canvas?.translate(mWidth / 2f, mHeight / 2f)

        mRegions.forEach {
            canvas?.drawPath(it.boundaryPath, mPaint)
        }
//        canvas?.restore()
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN || event?.action == MotionEvent.ACTION_UP) {
            mRegions.forEachIndexed { index, region ->
                if (region.contains(event.x.toInt(), event.y.toInt())) {
                    setTag(id, index)
                    return super.onTouchEvent(event)
                }
            }
        }
        return false
    }
}
