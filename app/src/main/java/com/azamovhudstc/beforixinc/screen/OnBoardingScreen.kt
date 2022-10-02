package com.azamovhudstc.beforixinc.screen


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.azamovhudstc.beforixinc.R
import com.azamovhudstc.beforixinc.activity.OtpVerfiyActivity
import com.azamovhudstc.beforixinc.adapter.OnboardingViewPagerAdapter2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_onboarding_example4.*

class OnBoardingScreen : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager2
    private lateinit var btnCreateAccount: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_example4)

        btnCreateAccount = btn_create_account
        btnCreateAccount.setOnClickListener {
            startActivity(Intent(applicationContext, OtpVerfiyActivity::class.java))
            finish()
        }

        mViewPager = findViewById(R.id.viewPager)
        mViewPager.adapter = OnboardingViewPagerAdapter2(this, this)
        TabLayoutMediator(pageIndicator, mViewPager) { _, _ -> }.attach()
        mViewPager.offscreenPageLimit = 1
    }
}
