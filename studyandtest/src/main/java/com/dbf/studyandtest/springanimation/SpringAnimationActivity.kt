package com.dbf.studyandtest.springanimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.RadioGroup
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.dbf.common.myutils.MyLog
import com.dbf.studyandtest.R
import kotlinx.android.synthetic.main.activity_spring_animation.*

class SpringAnimationActivity : AppCompatActivity(), DynamicAnimation.OnAnimationUpdateListener,
    DynamicAnimation.OnAnimationEndListener, RadioGroup.OnCheckedChangeListener,
    View.OnTouchListener {
    companion object {
        val TAG = "SpringAnimationActivity"
    }

    private lateinit var springAnimationX: SpringAnimation
    private lateinit var springAnimationY: SpringAnimation
    private lateinit var springAnimationZ: SpringAnimation
    private lateinit var springForce: SpringForce
    private var myVelocity: Float = 0f
    private var animationEndPosition = 200f //要创建的弹簧的最终位置。
    private var startValue = 0f //动画起点值

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spring_animation)
        springAnimationX = SpringAnimation(testSpringAnimationImgv1,
            DynamicAnimation.TRANSLATION_X,
            animationEndPosition)
        springAnimationY = SpringAnimation(testSpringAnimationImgv2,
            DynamicAnimation.TRANSLATION_Y,
            animationEndPosition)
        springAnimationZ = SpringAnimation(testSpringAnimationImgv3,
            DynamicAnimation.TRANSLATION_Z,
            animationEndPosition)
        testSpringAnimationImgv1.setOnTouchListener(this)
        testSpringAnimationImgv2.setOnTouchListener(this)
        testSpringAnimationImgv3.setOnTouchListener(this)
        bouncyRg.setOnCheckedChangeListener(this)
        stiffnessRg.setOnCheckedChangeListener(this)
        springAnimationX.addUpdateListener(this)
        springAnimationX.addEndListener(this)
        springAnimationY.addUpdateListener(this)
        springAnimationY.addEndListener(this)
        springAnimationZ.addUpdateListener(this)
        springAnimationZ.addEndListener(this)
        initSpringForce()
        animationConfigs()

    }

    override fun onAnimationUpdate(animation: DynamicAnimation<out DynamicAnimation<*>>?,
                                   value: Float,
                                   velocity: Float) {
    }

    override fun onAnimationEnd(animation: DynamicAnimation<out DynamicAnimation<*>>?,
                                canceled: Boolean,
                                value: Float,
                                velocity: Float) {
    }

    private fun animationConfigs() {
        animationConfig(springAnimationX)
        animationConfig(springAnimationY)
        animationConfig(springAnimationZ)
    }

    private fun animationConfig(animation: SpringAnimation) {
        animation.let {
            //                    it.setMaxValue(1000f)
            //                    it.setMinValue(0f)
            it.setStartValue(startValue)
            it.setStartVelocity(createVelocity())
            it.setSpring(springForce)
        }
    }

    private var BOUNCY = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY //阻尼系数
    private var STIFFNESS = SpringForce.STIFFNESS_MEDIUM //刚度
    private var staticPosition = 0f //弹簧静止的位置
    private fun initSpringForce() {
        springForce = SpringForce()
        springForce.setDampingRatio(BOUNCY) //设置阻尼系数
        springForce.setStiffness(STIFFNESS) //设置刚度
        springForce.setFinalPosition(staticPosition) //设置弹簧的静止位置。
    }

    private var COMPLEX_UNIT = TypedValue.COMPLEX_UNIT_DIP //滑动速度单位（dp或px等）
    private var slidingSpeed = 1000f //滑动速度
    private fun createVelocity(): Float {
        val pixelPerSecond: Float =
            TypedValue.applyDimension(COMPLEX_UNIT, slidingSpeed, resources.displayMetrics)
        var vt = VelocityTracker.obtain()
        vt.computeCurrentVelocity(pixelPerSecond.toInt())
        return vt.yVelocity
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        group?.let {
            when (it) {
                bouncyRg -> {
                    when (checkedId) {
                        R.id.bouncyrb1 -> {
                            BOUNCY = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
                        }
                        R.id.bouncyrb2 -> {
                            BOUNCY = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
                        }
                        R.id.bouncyrb3 -> {
                            BOUNCY = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                        }
                        R.id.bouncyrb4 -> {
                            BOUNCY = SpringForce.DAMPING_RATIO_NO_BOUNCY
                        }
                    }
                    initSpringForce()
                    animationConfigs()

                }
                stiffnessRg -> {
                    when (checkedId) {
                        R.id.stiffnessrb1 -> {
                            STIFFNESS = SpringForce.STIFFNESS_HIGH
                        }
                        R.id.stiffnessrb1 -> {
                            STIFFNESS = SpringForce.STIFFNESS_MEDIUM
                        }
                        R.id.stiffnessrb1 -> {
                            STIFFNESS = SpringForce.STIFFNESS_LOW
                        }
                        R.id.stiffnessrb1 -> {
                            STIFFNESS = SpringForce.STIFFNESS_VERY_LOW
                        }
                    }
                    initSpringForce()
                    animationConfigs()
                }
                else -> {
                }
            }
        }
    }

    var xDiffLeft = 0f
    var yDiffTop = 0f
    override fun onTouch(v: View, event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                xDiffLeft = event.rawX - v.x
                yDiffTop = event.rawY - v.y
            }
            MotionEvent.ACTION_MOVE -> {
                v.x = event.rawX - xDiffLeft
                v.y = event.rawY - yDiffTop
            }
            MotionEvent.ACTION_UP -> {
                when (v) {
                    testSpringAnimationImgv1 -> {
                        springAnimationX.animateToFinalPosition(500f)
                    }
                    testSpringAnimationImgv2 -> {
                        springAnimationY.animateToFinalPosition(500f)
                    }
                    testSpringAnimationImgv3 -> {
                        springAnimationZ.animateToFinalPosition(500f)
                    }
                }
            }
        }
        return true
    }
}
