package com.example.base_app.ui.widget.pattern

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import com.example.base_app.R
import com.example.base_app.data.preferences.SharedPreferences

class PatternLockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var stageState: PatternViewStageState = PatternViewStageState.FIRST

    private var touchedPointX: Float = 0.0f
    private var touchedPointY: Float = 0.0f
    private var minCount = 4
    private var maxCount = 9
    private var markedDotList = mutableListOf<Dot>()
    private var initialDotList = mutableListOf<Dot>()
    private var state: PatternViewState = PatternViewState.Initial
    private var attrIsDotAnimate = true
    private var onChangeStateListener: ((state: PatternViewState) -> Unit)? = null
    private var countDownTimer: CountDownTimer? = null
    private var stagePasswords = linkedMapOf<PatternViewStageState, String>()
    private var sharePreferences: SharedPreferences
    private var oldData = ""

    @ColorInt
    private var attrDotColor = Color.DKGRAY

    @ColorInt
    private var attrLineColor = Color.DKGRAY

    @ColorInt
    private var attrErrorDotColor = Color.RED

    @ColorInt
    private var attrErrorLineColor = Color.RED

    @ColorInt
    private var attrSuccessDotColor = Color.BLUE

    @ColorInt
    private var attrSuccessLineColor = Color.BLUE

    @DrawableRes
    private var attrSuccessBackground = R.drawable.bg_num_pin_lock_success

    @DrawableRes
    private var attrErrorBackground = R.drawable.bg_num_pin_lock_error

    @DrawableRes
    private var attNormalBackground = R.drawable.bg_num_pin_lock

    private val rect = Rect()
    private val dotNumberKeyArray = arrayOf(
        arrayOf("1", "2", "3"),
        arrayOf("4", "5", "6"),
        arrayOf("7", "8", "9")
    )
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeWidth = 12f
        color = Color.DKGRAY
    }

    init {
        sharePreferences = SharedPreferences(context)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PatternLockView,
            0,
            0
        ).apply {
            try {
                attrIsDotAnimate =
                    getBoolean(R.styleable.PatternLockView_patternLock_isAnimate, true)
                attrDotColor = getColor(R.styleable.PatternLockView_patternLock_lineColor, Color.DKGRAY)

                attrLineColor = getColor(R.styleable.PatternLockView_patternLock_lineColor, Color.DKGRAY)

                attrSuccessDotColor =
                    getColor(R.styleable.PatternLockView_patternLock_successDotColor, Color.BLUE)
                attrSuccessLineColor =
                    getColor(R.styleable.PatternLockView_patternLock_successLineColor, Color.BLUE)
                attrErrorDotColor =
                    getColor(R.styleable.PatternLockView_patternLock_errorDotColor, Color.RED)
                attrErrorLineColor =
                    getColor(R.styleable.PatternLockView_patternLock_errorLineColor, Color.RED)
            } finally {
                recycle()
            }
        }

        orientation = VERTICAL
        paint.color = attrLineColor

        drawPatternView()
        setWillNotDraw(false)
    }

    fun setDotColor(color: Int){
        this.attrDotColor = color
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        addInitialData()

        if (state is PatternViewState.Error) {
            updateViewState(state)
        }

        drawLine(canvas)
        if (state is PatternViewState.Error) {
            countDownTimer = object : CountDownTimer(1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                }

                override fun onFinish() {
                    reset()
                }
            }.start()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { motionEvent ->
            touchedPointX = motionEvent.x
            touchedPointY = motionEvent.y

            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (state is PatternViewState.Success) {
                        return false
                    }

                    reset()

                    if (isTouchedDot(touchedPointX, touchedPointY)) {
                        state = PatternViewState.Started
                        onChangeStateListener?.invoke(state)
                        invalidate()
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (markedDotList.size != 0 && markedDotList.size >= minCount) {
                        when (stageState) {
                            PatternViewStageState.FIRST -> {
                                if (oldData == ""){
                                    stagePasswords[PatternViewStageState.FIRST] = getDrawnPatternKey()
                                    state = PatternViewState.Success(
                                        attrSuccessDotColor,
                                        attrSuccessLineColor,
                                        attrSuccessBackground
                                    )
                                }else{
                                    if(getDrawnPatternKey() != oldData){
                                        state = PatternViewState.Error(
                                            attrErrorDotColor,
                                            attrErrorLineColor,
                                            attrErrorBackground
                                        )
                                    }else{
                                        stagePasswords[PatternViewStageState.FIRST] = getDrawnPatternKey()
                                        state = PatternViewState.Success(
                                            attrSuccessDotColor,
                                            attrSuccessLineColor,
                                            attrSuccessBackground
                                        )
                                    }
                                }
                            }
                            PatternViewStageState.SECOND -> {
                                stagePasswords[PatternViewStageState.SECOND] = getDrawnPatternKey()
                                state =
                                    if (stagePasswords[PatternViewStageState.FIRST] != stagePasswords[PatternViewStageState.SECOND]) {
                                        PatternViewState.Error(
                                            attrErrorDotColor,
                                            attrErrorLineColor,
                                            attrErrorBackground
                                        )
                                    } else {
                                        PatternViewState.Success(
                                            attrSuccessDotColor,
                                            attrSuccessLineColor,
                                            attrSuccessBackground
                                        )
                                    }
                            }
                        }
                        updateViewState(state)
                        onChangeStateListener?.invoke(state)
                    } else if (markedDotList.size != 0) {
                        state = PatternViewState.Error(attrErrorDotColor, attrErrorLineColor, attrErrorBackground)
                        onChangeStateListener?.invoke(state)
                    }

                    invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
                    if (state == PatternViewState.Started && markedDotList.size != maxCount) {
                        isTouchedDot(touchedPointX, touchedPointY)
                        invalidate()
                    }
                }
            }

            return true
        }

        return false
    }

    private fun drawPatternView(
        rowSize: Int = 3,
        columnSize: Int = 3,
        layoutParams: ViewGroup.LayoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            weight = 1f
            gravity = Gravity.CENTER
        },
        nodeKeys: Array<Array<String>> = dotNumberKeyArray
    ) {
        for (rowIndex in 0 until rowSize) {
            createRow(this@PatternLockView, layoutParams).apply {
                for (columnIndex in 0 until columnSize) {
                    createRow(this, LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        weight = 1f
                        gravity = Gravity.CENTER
                    }).run {
                        createColumn(this, nodeKeys[rowIndex][columnIndex])
                    }
                }
            }
        }
    }

    private fun createRow(view: LinearLayout, layoutParams: ViewGroup.LayoutParams): LinearLayout {
        view.apply {
            addView(LinearLayout(context).apply {
                this.layoutParams = layoutParams
                this.gravity = Gravity.CENTER
            })
        }

        return view.getChildAt(view.childCount - 1) as LinearLayout
    }

    private fun createColumn(view: LinearLayout, nodeKey: String) {
        val marginLeft = context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._12sdp)
        val marginRight = context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._12sdp)
        val marginTop = context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._12sdp)
        val marginBottom = context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._12sdp)
        view.apply {
            addView(
                DotView(context).apply {
                    (layoutParams as MarginLayoutParams).setMargins(marginLeft, marginTop, marginRight, marginBottom)
                    setDotViewColor(attrDotColor)
                    setKey(nodeKey)
                }
            )
            (layoutParams as MarginLayoutParams).setMargins(
                context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._12sdp),
                context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._12sdp),
                context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._12sdp),
                context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._12sdp),
            )
            val mParams = layoutParams
            mParams.height = context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._82sdp)
            layoutParams = mParams
            setBackgroundResource(R.drawable.bg_num_pin_lock)
        }
    }

    private fun addInitialData() {
        if (initialDotList.size != 0) return

        forEachIndexed { rowIndex, view ->
            (view as? ViewGroup)?.forEachIndexed { columnIndex, viewGroup ->
                (viewGroup as? ViewGroup)?.forEach { nodeView ->
                    if (nodeView !is DotView) return

                    nodeView.getLocalVisibleRect(rect)

                    offsetDescendantRectToMyCoords(nodeView, rect)

                    initialDotList.add(
                        Dot(
                            rowIndex = rowIndex.toFloat(),
                            columnIndex = columnIndex.toFloat(),
                            leftPoint = rect.left.toFloat(),
                            rightPoint = rect.right.toFloat(),
                            topPoint = rect.top.toFloat(),
                            bottomPoint = rect.bottom.toFloat(),
                            key = nodeView.key
                        )
                    )
                }
            }
        }
    }

    private fun drawLine(canvas: Canvas?) {
        markedDotList.forEachIndexed { index, _ ->
            if (index + 1 < markedDotList.size) {
                canvas?.drawLine(
                    (markedDotList[index].rightPoint + markedDotList[index].leftPoint) / 2.toFloat(),
                    ((markedDotList[index].bottomPoint.toFloat()) + (markedDotList[index].topPoint.toFloat())) / 2,
                    (markedDotList[index + 1].rightPoint + markedDotList[index + 1].leftPoint) / 2.toFloat(),
                    ((markedDotList[index + 1].bottomPoint.toFloat()) + (markedDotList[index + 1].topPoint.toFloat())) / 2,
                    paint
                )
            }
        }

        if (state is PatternViewState.Started) {
            canvas?.drawLine(
                (markedDotList.last().rightPoint + markedDotList.last().leftPoint) / 2.toFloat(),
                ((markedDotList.last().bottomPoint.toFloat()) + (markedDotList.last().topPoint.toFloat())) / 2,
                touchedPointX, touchedPointY, paint
            )
        }
    }

    private fun isTouchedDot(pointX: Float, pointY: Float): Boolean {
        val touchedDot = getTouchedDotByPosition(pointX, pointY) ?: return false

        if (isDotSelected(touchedDot)) return false

        markedDotList.takeIf { it.size != 0 }?.last()?.let { lastTouchedDot ->
            val rowIndex = (lastTouchedDot.rowIndex + touchedDot.rowIndex) / 2
            val columnIndex =
                (lastTouchedDot.columnIndex + touchedDot.columnIndex) / 2

            getDotWithIndex(rowIndex, columnIndex)?.let { previousDot ->
                if (isDotSelected(previousDot).not()) {
                    selectDotView(previousDot)
                }
            }
        }

        if (markedDotList.size != maxCount) {
            selectDotView(touchedDot)
        }

        return true
    }

    private fun getTouchedDotByPosition(pointX: Float, pointY: Float) = initialDotList.firstOrNull {
        ((it.leftPoint) <= pointX && (it.topPoint) <= pointY)
                && ((it.rightPoint) >= pointX && (it.bottomPoint) >= pointY)
    }

    private fun isDotSelected(dot: Dot) =
        markedDotList.firstOrNull { markedDot ->
            markedDot.rowIndex == dot.rowIndex && markedDot.columnIndex == dot.columnIndex
        } != null

    private fun getDotWithIndex(rowIndex: Float, columnIndex: Float) = initialDotList.firstOrNull {
        it.rowIndex == rowIndex && it.columnIndex == columnIndex
    }

    private fun selectDotView(selectedDot: Dot) {
        markedDotList.add(selectedDot)
        if (attrIsDotAnimate){
            (((getChildAt(selectedDot.rowIndex.toInt()) as? ViewGroup)
                ?.getChildAt(selectedDot.columnIndex.toInt()) as? ViewGroup)
                ?.getChildAt(0) as? DotView)
                ?.animateDotView()
        }
    }

    private fun updateViewState(
        state: PatternViewState
    ) {
        @ColorInt val dotColor: Int
        @ColorInt val lineColor: Int
        @DrawableRes val backgroundRes: Int

        when (state) {
            is PatternViewState.Success -> {
                dotColor = state.dotColor
                lineColor = state.lineColorInt
                backgroundRes = state.dotBackgroundColor
            }
            is PatternViewState.Error -> {
                dotColor = state.dotColor
                lineColor = state.lineColorInt
                backgroundRes = state.dotBackgroundColor
            }
            else -> {
                dotColor = attrDotColor
                lineColor = attrLineColor
                backgroundRes = R.drawable.bg_num_pin_lock
            }
        }

        paint.color = lineColor

        markedDotList.forEach { dot ->
            (((this.getChildAt(dot.rowIndex.toInt()) as? ViewGroup)
                ?.getChildAt(dot.columnIndex.toInt()) as? ViewGroup)
                ?.getChildAt(0) as? DotView)?.let {
                it.setDotViewColor(dotColor)
            }
            ((this.getChildAt(dot.rowIndex.toInt()) as? ViewGroup)
                ?.getChildAt(dot.columnIndex.toInt()) as? ViewGroup)?.let {
                it.setBackgroundResource(backgroundRes)
            }
        }
    }

    private fun getDrawnPatternKey() =
        markedDotList.map { it.key }.joinToString("")

    fun reset() {
        state = PatternViewState.Initial
        updateViewState(state)
        markedDotList.clear()
        countDownTimer?.cancel()
        countDownTimer = null

        invalidate()
    }

    fun setOldData(s: String){
        oldData = s
    }

    fun getPassword(stageState: PatternViewStageState) = stagePasswords[stageState]

    fun setOnChangeStateListener(listener: (state: PatternViewState) -> Unit) {
        onChangeStateListener = listener
    }
}
