package com.nghiemtuananh.baitapappgameailatrieuphut3h

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.nghiemtuananh.baitapappgameailatrieuphut3h.baseactivity.BaseActivity
import com.nghiemtuananh.baitapappgameailatrieuphut3h.baseactivity.LoadGameFragment
import com.nghiemtuananh.baitapappgameailatrieuphut3h.databinding.ActivityMainBinding
import com.nghiemtuananh.baitapappgameailatrieuphut3h.interfacee.IActivityAndHomeFragment
import com.nghiemtuananh.baitapappgameailatrieuphut3h.interfacee.IActivityAndInGameFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : BaseActivity(), IActivityAndHomeFragment, IActivityAndInGameFragment {
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
        listQuestion = altpDao.query15Question()
    }

    @SuppressLint("CheckResult")
    override fun onClickNowPlay() {
        Observable.create<String?> {
            it.onNext(null.toString())
            SystemClock.sleep(7500)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    val loadGameFragment = LoadGameFragment()
                    fragmentTransaction.replace(R.id.fl_content, loadGameFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                },
                {},
                {
                    CommonApp.level = 0
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    val inGameFragment = InGameFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("listQuestion", listQuestion)
                    inGameFragment.arguments = bundle
                    fragmentTransaction.replace(R.id.fl_content, inGameFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                    CommonApp.checkEnd = false
                }
            )
    }

    override fun nextLevel() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        val inGameFragment = InGameFragment()
        val bundle = Bundle()
        bundle.putSerializable("listQuestion", listQuestion)
        inGameFragment.arguments = bundle
        fragmentTransaction.replace(R.id.fl_content, inGameFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onBackPress() {
        CommonApp.checkEnd = true
        CommonApp.check5050Help = false
        CommonApp.checkCallHelp = false
        CommonApp.checkSwitchQuestion = false
        CommonApp.checkPeopleHelp = false
        altpDao = ALTPDao(this)
        listQuestion = altpDao.query15Question()
        Toast.makeText(this, "Game over!!!", Toast.LENGTH_LONG).show()
        fragmentManager.popBackStack("aaa", 0)
    }
}