package com.nghiemtuananh.baitapappgameailatrieuphut3h.basefragment

import androidx.fragment.app.Fragment
import com.nghiemtuananh.baitapappgameailatrieuphut3h.baseactivity.BaseActivity

open class BaseFragment: Fragment() {
    open fun onBackPressForFragment() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).onBackRoot()
        }
    }
}