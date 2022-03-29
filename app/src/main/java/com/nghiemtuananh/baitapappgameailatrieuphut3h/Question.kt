package com.nghiemtuananh.baitapappgameailatrieuphut3h

import java.io.Serializable

data class Question(val question: String, val level: Int, val caseA: String, val caseB: String, val caseC: String, val caseD: String, val trueCase: Int): Serializable
