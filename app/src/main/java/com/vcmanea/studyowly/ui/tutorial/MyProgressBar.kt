package com.vcmanea.studyowly.ui.tutorial

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class MyProgressBar : View {
    private var currentElement = 1

    //height and width of the view
    private var viewWidth=0
    private var viewHeight=0
    //spaces between the segments length
    private val eachSpaceWidth = 8
    //segments and spaces between the segments
    private var segmentsNumber=0
    private var spacesNumber=0
    //total size of spaces
    private var spacesLengthSum=0
    //total size for segments
    private var segmentsLengthSum=0
    //each segment
    private var eachSegmentWidth=0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    private fun initDimensions() {
        //-10 because the last element seems to be smaller if not, some width is lost during the calculations
        viewWidth = width
        viewHeight = height
        spacesLengthSum = spacesNumber * eachSpaceWidth
        segmentsLengthSum = viewWidth - spacesLengthSum
        if (segmentsNumber > 0)
            eachSegmentWidth = segmentsLengthSum / segmentsNumber
//        Log.d("onSizeChanged", "view w ${viewWidth} ")
//        Log.d("onSizeChanged", "view h ${height} ")
//        Log.d("onSizeChanged", "segmentsNumber ${segmentsNumber} ")
//        Log.d("onSizeChanged", "spacesNumber:  ${spacesNumber} ")
//        Log.d("onSizeChanged", "spacesLengthSum:  ${spacesLengthSum} ")
//        Log.d("onSizeChanged", "segmentsLengthSum:  ${segmentsLengthSum} ")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        initDimensions()
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //function to create dots
        createSegment(canvas)
    }

    private fun createSegment(canvas: Canvas?) {
        //PAINTS
        val greenPaint = Paint()
        greenPaint.color = Color.parseColor("#2e7d32")
        val grayPaint = Paint()
        grayPaint.color = Color.parseColor("#DDDDDD")

        //y0,y1 will be always the same
        val y0 = 0f
        val y1 = viewHeight.toFloat()


        for (i in 0 until segmentsNumber) {

            // basically (X0) will be 0 for the beginning
            val x0 = (eachSpaceWidth + eachSegmentWidth) * i
            //due this formula the last element will be shorter, because after the last element there won't be any space
            val x1 = ((eachSpaceWidth + eachSegmentWidth) * (i + 1)) - eachSpaceWidth

            val rectF = RectF(x0.toFloat(), y0, x1.toFloat(), y1)
            // GREEN PAINT
            if (i < currentElement) {
                canvas?.drawRect(rectF, greenPaint)
            }
            // GRAY PAINT
            else {
                canvas?.drawRect(rectF, grayPaint)
            }
//            Log.d("createSegment", "x0= ${x0}, x1= ${x1}")
        }
    }

    fun setListSize(size: Int) {

        if (size > 0) {
            segmentsNumber = size
            spacesNumber = size - 1
        }
        initDimensions()
    }

    fun setProgressFromHere(position: Int) {
        currentElement = position + 1
        invalidate()
    }
}