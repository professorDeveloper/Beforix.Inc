package com.azamovhudstc.beforixinc.screen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.azamovhudstc.beforixinc.R
import com.azamovhudstc.beforixinc.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private val firebase =FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val pref = applicationContext.getSharedPreferences("MyPref", 0) // 0 - for private mode

        val string = pref.getString("key", null);
        if (firebase.currentUser == null) {
            Handler().postDelayed( {
                startActivity(Intent(applicationContext, OnBoardingScreen::class.java))
                finish()

            }, 6150)
        }else{
            Handler().postDelayed({
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()

            }, 6150)
        }
    }
}