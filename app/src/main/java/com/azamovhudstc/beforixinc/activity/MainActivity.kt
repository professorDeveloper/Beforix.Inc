package com.azamovhudstc.beforixinc.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.azamovhudstc.beforixinc.R
import com.azamovhudstc.beforixinc.fragment.HomeFragment
import com.azamovhudstc.beforixinc.fragment.PremuimFragment
import com.azamovhudstc.beforixinc.fragment.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private  var fragment: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment()).commit()
            bottom_navigation.setItemSelected(R.id.home_menu)


        }
        bottom_navigation.setOnItemSelectedListener { id ->
            when (id) {
                R.id.home_menu -> {
                    fragment = HomeFragment()
                }
                R.id.profile_menu -> {
                    fragment = ProfileFragment()
                }
                R.id.stars_menu -> {
                    fragment = PremuimFragment()
                }
            }
            fragment!!.let {
                supportFragmentManager.beginTransaction().replace(R.id.frame, fragment!!).commit()
            }
        }
    }
}