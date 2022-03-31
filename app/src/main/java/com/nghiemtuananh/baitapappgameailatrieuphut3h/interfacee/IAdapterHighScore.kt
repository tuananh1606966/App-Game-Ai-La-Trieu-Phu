package com.nghiemtuananh.baitapappgameailatrieuphut3h.interfacee

import com.nghiemtuananh.baitapappgameailatrieuphut3h.HighScore

interface IAdapterHighScore {
    fun getCount(): Int
    fun getItem(position: Int): HighScore
}