package com.jskaleel.fte.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.animation.*
import androidx.appcompat.app.AppCompatActivity
import com.jskaleel.fte.MainActivity
import com.jskaleel.fte.R
import kotlinx.android.synthetic.main.activity_splash.*
import java.lang.ref.WeakReference

class SplashActivity : AppCompatActivity() {
    private val sleepDuration: Long = 2000
    private var activityDestroyed = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val scaleAnim = ScaleAnimation(
            0f, 1f,
            0f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        val alphaAnim = AlphaAnimation(0f, 1f)
        val animSet = AnimationSet(true)
        animSet.addAnimation(scaleAnim)
        animSet.addAnimation(alphaAnim)

        animSet.interpolator = OvershootInterpolator()
        animSet.duration = 600
        llSplashLogo.startAnimation(animSet)

        val mHandler = FTEHandler(this@SplashActivity)
        mHandler.sendEmptyMessageDelayed(1, sleepDuration)
    }

    private class FTEHandler internal constructor(splashScreen: SplashActivity) : Handler() {
        internal var splash: WeakReference<SplashActivity> = WeakReference(splashScreen)

        override fun handleMessage(msg: Message) {
            val activity = splash.get()
            if (activity != null && msg.what == 1 && !activity.activityDestroyed) {
                activity.checkForLocalBooks()
            }
        }
    }

    private fun checkForLocalBooks() {
        txtLoading.visibility = View.VISIBLE
        progressLoader.visibility = View.VISIBLE


        progressLoader.progress = 100
        startNextActivity()
    }

    private fun startNextActivity() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        this@SplashActivity.finish()
    }

    override fun onDestroy() {
        activityDestroyed = true
        super.onDestroy()
    }
}