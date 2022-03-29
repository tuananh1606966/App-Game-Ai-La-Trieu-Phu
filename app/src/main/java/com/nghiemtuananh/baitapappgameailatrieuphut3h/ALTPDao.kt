package com.nghiemtuananh.baitapappgameailatrieuphut3h

import android.content.Context
import android.content.res.AssetManager
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream

class ALTPDao {
    private val mContext: Context
    private var mSqlite: SQLiteDatabase? = null
    constructor(mContext: Context) {
        this.mContext = mContext
        copyDatabase()
    }

    private fun copyDatabase() {
        val path = Environment.getDataDirectory().path + File.separator + "data" + File.separator + mContext.packageName + File.separator + "Question"
        if (File(path).exists()) {
            return
        }
        val assetsManager: AssetManager = mContext.assets
        val input = assetsManager.open("Question")

        val out = FileOutputStream(path)
        input.copyTo(out)
        input.close()
        out.close()
    }

    private fun openDatabase() {
        val path = Environment.getDataDirectory().path + File.separator + "data" +
                File.separator + mContext.packageName + File.separator + "Question"
        if (mSqlite != null && mSqlite!!.isOpen) {
            return
        }
        mSqlite = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    private fun closeDatabase() {
        if (mSqlite != null && mSqlite!!.isOpen) {
            mSqlite!!.close()
            mSqlite = null
        }
    }

    fun query15Question(): ArrayList<Question> {
        openDatabase()

        var listQuestion: ArrayList<Question> = arrayListOf()
        val query = "select * from (select * from Question order by random()) as qti group by level"
        val cur: Cursor = mSqlite!!.rawQuery(query, null)
        cur.moveToFirst()

        val indexQuestion = cur.getColumnIndex("question")
        val indexLevel = cur.getColumnIndex("level")
        val indexCaseA = cur.getColumnIndex("casea")
        val indexCaseB = cur.getColumnIndex("caseb")
        val indexCaseC = cur.getColumnIndex("casec")
        val indexCaseD = cur.getColumnIndex("cased")
        val indexTrueCase = cur.getColumnIndex("truecase")
        while (!cur.isAfterLast) {
            val question = cur.getString(indexQuestion)
            val level = cur.getInt(indexLevel)
            val caseA = cur.getString(indexCaseA)
            val caseB = cur.getString(indexCaseB)
            val caseC = cur.getString(indexCaseC)
            val caseD = cur.getString(indexCaseD)
            val trueCase = cur.getInt(indexTrueCase)

            cur.moveToNext()
            listQuestion.add(Question(question, level, caseA, caseB, caseC, caseD, trueCase))
        }

        return listQuestion
        closeDatabase()
    }

    fun queryNewQuestion(level: Int): Question {
        openDatabase()

        val query = "SELECT * FROM Question WHERE level = $level order by random() limit 1"
        val cur: Cursor = mSqlite!!.rawQuery(query, null)
        cur.moveToFirst()
        val indexQuestion = cur.getColumnIndex("question")
        val indexLevel = cur.getColumnIndex("level")
        val indexCaseA = cur.getColumnIndex("casea")
        val indexCaseB = cur.getColumnIndex("caseb")
        val indexCaseC = cur.getColumnIndex("casec")
        val indexCaseD = cur.getColumnIndex("cased")
        val indexTrueCase = cur.getColumnIndex("truecase")

        val question = cur.getString(indexQuestion)
        val level = cur.getInt(indexLevel)
        val caseA = cur.getString(indexCaseA)
        val caseB = cur.getString(indexCaseB)
        val caseC = cur.getString(indexCaseC)
        val caseD = cur.getString(indexCaseD)
        val trueCase = cur.getInt(indexTrueCase)

        return Question(question, level, caseA, caseB, caseC, caseD, trueCase)

        closeDatabase()
    }
}