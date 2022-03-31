package com.nghiemtuananh.baitapappgameailatrieuphut3h.interfacee

import com.nghiemtuananh.baitapappgameailatrieuphut3h.HighScore

interface IActivityAndHomeFragment {
    fun onClickNowPlay()
    fun getListHighScore(): ArrayList<HighScore>
}