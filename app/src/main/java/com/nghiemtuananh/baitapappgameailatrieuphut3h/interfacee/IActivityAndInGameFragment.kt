package com.nghiemtuananh.baitapappgameailatrieuphut3h.interfacee

interface IActivityAndInGameFragment {
    fun nextLevel()
    fun onBackPress(isWinner: Boolean)
    fun saveHighScore(name: String, money: String, level: Int)
}