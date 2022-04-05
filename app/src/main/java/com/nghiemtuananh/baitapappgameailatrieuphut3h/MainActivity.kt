package com.nghiemtuananh.baitapappgameailatrieuphut3h

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.nghiemtuananh.baitapappgameailatrieuphut3h.baseactivity.BaseActivity
import com.nghiemtuananh.baitapappgameailatrieuphut3h.databinding.ActivityMainBinding
import com.nghiemtuananh.baitapappgameailatrieuphut3h.interfacee.IActivityAndHomeFragment
import com.nghiemtuananh.baitapappgameailatrieuphut3h.interfacee.IActivityAndInGameFragment
import com.nghiemtuananh.baitapappgameailatrieuphut3h.interfacee.IActivityAndIntroGame
import com.nghiemtuananh.baitapappgameailatrieuphut3h.interfacee.IAdapterHighScore
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : BaseActivity(), IActivityAndHomeFragment, IActivityAndInGameFragment,
    IActivityAndIntroGame {
    private var listHighScore: ArrayList<HighScore> = arrayListOf()
    private var listQuestion: ArrayList<Question> = arrayListOf()
    private val fragmentManager = supportFragmentManager
    private lateinit var altpDao: ALTPDao
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        altpDao = ALTPDao(this)
        initData()
        val fragmentTransaction = fragmentManager.beginTransaction()
        val homeFragment = HomeFragment()
        fragmentTransaction.add(R.id.fl_content, homeFragment)
        fragmentTransaction.addToBackStack("aaa")
        fragmentTransaction.commit()
    }

    private fun initData() {
        listHighScore = altpDao.queryListHighScore()
        listQuestion = altpDao.query15Question()
    }

    override fun onClickNowPlay() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        val intro15QuestionFragment = Intro15QuestionFragment()
        fragmentTransaction.replace(R.id.fl_content, intro15QuestionFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    @SuppressLint("CheckResult")
    override fun clickReady() {
        Observable.create<Int> {
            it.onNext(1)
            SystemClock.sleep(7000)
            it.onNext(2)
            SystemClock.sleep(3000)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        1 -> {
                            val fragmentTransaction = fragmentManager.beginTransaction()
                            val loadGameFragment = LoadGameFragment()
                            fragmentTransaction.replace(R.id.fl_content, loadGameFragment)
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        }
                        2 -> {
                            CommonApp.level = 0
                            CommonApp.checkEnd = false
                            openLevelFragment()
                        }
                    }
                },
                {},
                {
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    val inGameFragment = InGameFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("listQuestion", listQuestion)
                    inGameFragment.arguments = bundle
                    fragmentTransaction.replace(R.id.fl_content, inGameFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
            )
    }

    private fun openLevelFragment() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        val levelFragment = LevelFragment()
        val bundle = Bundle()
        bundle.putInt("level", listQuestion[CommonApp.level].level)
        levelFragment.arguments = bundle
        fragmentTransaction.replace(R.id.fl_content, levelFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun saveHighScore(name: String, money: String, level: Int) {
        altpDao.insertHighScore(name, money, level)
    }

    override fun clickCancel() {
        fragmentManager.popBackStack("aaa", 0)
    }

    @SuppressLint("CheckResult")
    override fun nextLevel() {
        Observable.create<String?> {
            it.onNext(null.toString())
            SystemClock.sleep(3000)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    openLevelFragment()
                },
                {},
                {
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    val inGameFragment = InGameFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("listQuestion", listQuestion)
                    inGameFragment.arguments = bundle
                    fragmentTransaction.replace(R.id.fl_content, inGameFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
            )
    }

    override fun getListHighScore(): ArrayList<HighScore> {
        listHighScore = altpDao.queryListHighScore()
        listHighScore.sortWith(compareBy({it.money.length}, {it.money}, {it.level}))
        listHighScore.reverse()
        return listHighScore
    }

    override fun onBackPress(isWinner: Boolean) {
        CommonApp.checkEnd = true
        CommonApp.check5050Help = false
        CommonApp.checkCallHelp = false
        CommonApp.checkSwitchQuestion = false
        CommonApp.checkPeopleHelp = false
        altpDao = ALTPDao(this)
        listQuestion = altpDao.query15Question()
        if (isWinner) {
            Toast.makeText(this, "Winner!!!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Game over!!!", Toast.LENGTH_SHORT).show()
        }
        fragmentManager.popBackStack("aaa", 0)
    }

    override fun onBackPressed() {
        val fragment = getCurrentFragment()
        if (fragment is HomeFragment) {
            finish()
        } else if (fragment is LoadGameFragment) {
            Toast.makeText(this, "Can't Back Press!", Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
    }
}