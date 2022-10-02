package com.azamovhudstc.beforixinc.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.azamovhudstc.beforixinc.R
import com.azamovhudstc.beforixinc.fragment.OnboardingFragment2



class OnboardingViewPagerAdapter2(
    fragmentActivity: FragmentActivity,
    private val context: Context
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnboardingFragment2.newInstance(
                context.resources.getString(R.string.heading_one),
                context.resources.getString(R.string.desc_one),

                R.raw.welcome
            )
            1 -> OnboardingFragment2.newInstance(
                context.resources.getString(R.string.heading_two),
                context.resources.getString(R.string.desc_two),
                R.raw.privacy
            )
            else -> OnboardingFragment2.newInstance(
                context.resources.getString(R.string.heading_three),
                context.resources.getString(R.string.desc_three),
                R.raw.transfer
            )
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}
