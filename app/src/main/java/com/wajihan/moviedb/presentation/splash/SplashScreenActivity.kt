package com.wajihan.moviedb.presentation.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wajihan.moviedb.R
import com.wajihan.moviedb.presentation.main.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        GlobalScope.launch {
            delay(3000)
            toNextActivity()
        }
    }

    private fun toNextActivity() {
        MainActivity.start(this@SplashScreenActivity)
        finish()
    }
}