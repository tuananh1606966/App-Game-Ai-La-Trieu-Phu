package com.nghiemtuananh.baitapappgameailatrieuphut3h

import android.widget.Button
import androidx.databinding.BindingAdapter

object CommonApp {
    var level = 0
    var checkEnd = false
    var checkCallHelp = false
    var check5050Help = false
    var checkSwitchQuestion = false
    var checkPeopleHelp = false

    @JvmStatic
    @BindingAdapter("cash")
    fun cash(view: Button, level: Int) {
        if (level == 1) {
            view.setText("200,000")
        }
        if (level == 2) {
            view.setText("400,000")
        }
        if (level == 3) {
            view.setText("600,000")
        }
        if (level == 4) {
            view.setText("1,000,000")
        }
        if (level == 5) {
            view.setText("2,000,000")
        }
        if (level == 6) {
            view.setText("3,000,000")
        }
        if (level == 7) {
            view.setText("6,000,000")
        }
        if (level == 8) {
            view.setText("10,000,000")
        }
        if (level == 9) {
            view.setText("14,000,000")
        }
        if (level == 10) {
            view.setText("22,000,000")
        }
        if (level == 11) {
            view.setText("30,000,000")
        }
        if (level == 12) {
            view.setText("40,000,000")
        }
        if (level == 13) {
            view.setText("60,000,000")
        }
        if (level == 14) {
            view.setText("85,000,000")
        }
        if (level == 15) {
            view.setText("150,000,000")
        }
    }
}