package com.nghiemtuananh.baitapappgameailatrieuphut3h.baseactivity

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.nghiemtuananh.baitapappgameailatrieuphut3h.HomeFragment
import com.nghiemtuananh.baitapappgameailatrieuphut3h.basefragment.BaseFragment

open class BaseActivity: AppCompatActivity() {
    fun getCurrentFragment(): Fragment? {
        for (fragment in supportFragmentManager.fragments) {
            if (fragment != null && fragment.isVisible) {
                return fragment
            }
        }
        return null
    }

    fun onBackRoot() {
        super.onBackPressed()
    }

    override fun onBackPressed() {
        val frag = getCurrentFragment()
        if (frag != null && frag is BaseFragment) {
            frag.onBackPressForFragment()
        } else {
            onBackRoot()
        }
    }
}