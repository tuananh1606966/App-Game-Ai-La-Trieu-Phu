package com.nghiemtuananh.baitapappgameailatrieuphut3h

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.view.isVisible
import com.nghiemtuananh.baitapappgameailatrieuphut3h.basefragment.BaseFragment
import com.nghiemtuananh.baitapappgameailatrieuphut3h.databinding.FragmentIngameBinding
import com.nghiemtuananh.baitapappgameailatrieuphut3h.interfacee.IActivityAndInGameFragment
import com.skydoves.progressview.ProgressView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.random.Random

class InGameFragment : BaseFragment(), View.OnClickListener {
    lateinit var anim: Animation
    var isSelected = false
    var time: Int = 30
    var level: Int = 0
    var listQuestion: ArrayList<Question> = arrayListOf()
    lateinit var inter: IActivityAndInGameFragment
    lateinit var binding: FragmentIngameBinding
    private lateinit var altpDao: ALTPDao
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentIngameBinding.inflate(inflater, container, false)
        anim = AnimationUtils.loadAnimation(binding.root.context, R.anim.anim_translate)
        binding.root.startAnimation(anim)
        listQuestion = requireArguments().getSerializable("listQuestion") as ArrayList<Question>
        inter = activity as IActivityAndInGameFragment
        if (CommonApp.checkCallHelp) {
            binding.ibtnDeleteCall.isVisible = true
        }
        if (CommonApp.check5050Help) {
            binding.ibtnDelete5050.isVisible = true
        }
        if (CommonApp.checkSwitchQuestion) {
            binding.ibtnDeleteSwitchQuestion.isVisible = true
        }
        if (CommonApp.checkPeopleHelp) {
            binding.ibtnDeletePeopleHelp.isVisible = true
        }
        level = CommonApp.level
        binding.data = listQuestion[level]
        setOnClickAllButton()
        timeCountDown()
        return binding.root
    }

    private fun setOnClickAllButton() {
        binding.ibtnCaseANormal.setOnClickListener(this)
        binding.ibtnCaseBNormal.setOnClickListener(this)
        binding.ibtnCaseCNormal.setOnClickListener(this)
        binding.ibtnCaseDNormal.setOnClickListener(this)
        binding.ibtnEndGame.setOnClickListener(this)
        binding.ibtnCallHelp.setOnClickListener(this)
        binding.ibtnDeleteCall.setOnClickListener(this)
        binding.ibtn5050.setOnClickListener(this)
        binding.ibtnDelete5050.setOnClickListener(this)
        binding.ibtnSwitchQuestion.setOnClickListener(this)
        binding.ibtnDeleteSwitchQuestion.setOnClickListener(this)
        binding.ibtnPeopleHelp.setOnClickListener(this)
        binding.ibtnDeletePeopleHelp.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        anim = AnimationUtils.loadAnimation(activity, R.anim.anim_blink)
        when (p0) {
            binding.ibtnCaseANormal -> {
                pauseTimeAndOffClickableAllButton()
                binding.ibtnSelectA.isVisible = true
                binding.ibtnSelectAAnim.isVisible = true
                binding.ibtnSelectAAnim.startAnimation(anim)
                if (listQuestion[level].trueCase == 1) {
                    actionWin(binding.ibtnCaseATrue, binding.ibtnCaseATrueAnim)
                } else {
                    actionLose(binding.ibtnCaseAFalse, binding.ibtnCaseAFalseAnim)
                }
            }
            binding.ibtnCaseBNormal -> {
                pauseTimeAndOffClickableAllButton()
                binding.ibtnSelectB.isVisible = true
                binding.ibtnSelectBAnim.isVisible = true
                binding.ibtnSelectBAnim.startAnimation(anim)
                if (listQuestion[level].trueCase == 2) {
                    actionWin(binding.ibtnCaseBTrue, binding.ibtnCaseBTrueAnim)
                } else {
                    actionLose(binding.ibtnCaseBFalse, binding.ibtnCaseBFalseAnim)
                }
            }
            binding.ibtnCaseCNormal -> {
                pauseTimeAndOffClickableAllButton()
                binding.ibtnSelectC.isVisible = true
                binding.ibtnSelectCAnim.isVisible = true
                binding.ibtnSelectCAnim.startAnimation(anim)
                if (listQuestion[level].trueCase == 3) {
                    actionWin(binding.ibtnCaseCTrue, binding.ibtnCaseCTrueAnim)
                } else {
                    actionLose(binding.ibtnCaseCFalse, binding.ibtnCaseCFalseAnim)
                }
            }
            binding.ibtnCaseDNormal -> {
                pauseTimeAndOffClickableAllButton()
                binding.ibtnSelectD.isVisible = true
                binding.ibtnSelectDAnim.isVisible = true
                binding.ibtnSelectDAnim.startAnimation(anim)
                if (listQuestion[level].trueCase == 4) {
                    actionWin(binding.ibtnCaseDTrue, binding.ibtnCaseDTrueAnim)
                } else {
                    actionLose(binding.ibtnCaseDFalse, binding.ibtnCaseDFalseAnim)
                }
            }
            binding.ibtnEndGame -> {
                inter.onBackPress()
                isSelected = true
            }
            binding.ibtnCallHelp -> {
                CommonApp.checkCallHelp = true
                binding.ibtnDeleteCall.isVisible = true
                showDialogCallHelp()
            }
            binding.ibtnDeleteCall -> {
                Toast.makeText(activity, "Bạn đã sử dụng sự trợ giúp này rồi", Toast.LENGTH_LONG)
                    .show()
            }
            binding.ibtn5050 -> {
                CommonApp.check5050Help = true
                binding.ibtnDelete5050.isVisible = true
                deleteTwoFalseCase()
            }
            binding.ibtnDelete5050 -> {
                Toast.makeText(activity, "Bạn đã sử dụng sự trợ giúp này rồi", Toast.LENGTH_LONG)
                    .show()
            }
            binding.ibtnSwitchQuestion -> {
                CommonApp.checkSwitchQuestion = true
                binding.ibtnDeleteSwitchQuestion.isVisible = true
                switchQuestion()
            }
            binding.ibtnDeleteSwitchQuestion -> {
                Toast.makeText(activity, "Bạn đã sử dụng sự trợ giúp này rồi", Toast.LENGTH_LONG)
                    .show()
            }
            binding.ibtnPeopleHelp -> {
                CommonApp.checkPeopleHelp = true
                binding.ibtnDeletePeopleHelp.isVisible = true
                showDialogPeopleHelp()
            }
            binding.ibtnDeletePeopleHelp -> {
                Toast.makeText(activity, "Bạn đã sử dụng sự trợ giúp này rồi", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun showDialogPeopleHelp() {
        val random = Random
        var progressA = 0
        var progressB = 0
        var progressC = 0
        var progressD = 0
        when (listQuestion[level].trueCase) {
            1 -> {
                progressA = 65 + random.nextInt(11)
                progressB = 5 + random.nextInt(6)
                progressC = 5 + random.nextInt(6)
                progressD = 100 - progressA - progressB - progressC
            }
            2 -> {
                progressB = 65 + random.nextInt(11)
                progressA = 5 + random.nextInt(6)
                progressC = 5 + random.nextInt(6)
                progressD = 100 - progressA - progressB - progressC
            }
            3 -> {
                progressC = 65 + random.nextInt(11)
                progressB = 5 + random.nextInt(6)
                progressA = 5 + random.nextInt(6)
                progressD = 100 - progressA - progressB - progressC
            }
            4 -> {
                progressD = 65 + random.nextInt(11)
                progressB = 5 + random.nextInt(6)
                progressC = 5 + random.nextInt(6)
                progressA = 100 - progressD - progressB - progressC
            }
        }
        setDialogPeopleHelp(progressA, progressB, progressC, progressD)
    }

    private fun setDialogPeopleHelp(progressA: Int, progressB: Int, progressC: Int, progressD: Int) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_people_help)
        dialog.setCancelable(false)
        val window = dialog.window
        window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val txtProgressA = dialog.findViewById<TextView>(R.id.tv_progress_a)
        val txtProgressB = dialog.findViewById<TextView>(R.id.tv_progress_b)
        val txtProgressC = dialog.findViewById<TextView>(R.id.tv_progress_c)
        val txtProgressD = dialog.findViewById<TextView>(R.id.tv_progress_d)
        val progressViewA = dialog.findViewById<ProgressView>(R.id.progress_a)
        val progressViewB = dialog.findViewById<ProgressView>(R.id.progress_b)
        val progressViewC = dialog.findViewById<ProgressView>(R.id.progress_c)
        val progressViewD = dialog.findViewById<ProgressView>(R.id.progress_d)
        val btnThanks = dialog.findViewById<Button>(R.id.btn_thanks)

        txtProgressA.setText("$progressA %")
        txtProgressB.setText("$progressB %")
        txtProgressC.setText("$progressC %")
        txtProgressD.setText("$progressD %")

        progressViewA.progress = progressA.toFloat()
        progressViewB.progress = progressB.toFloat()
        progressViewC.progress = progressC.toFloat()
        progressViewD.progress = progressD.toFloat()

        btnThanks.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun switchQuestion() {
        altpDao = ALTPDao(requireContext())
        val newQuestion = altpDao.queryNewQuestion(listQuestion[level].level)
        binding.data = newQuestion
        listQuestion[level] = newQuestion
    }

    @SuppressLint("CheckResult")
    private fun deleteTwoFalseCase() {
        var checkCount = 0
        var indexDelete2 = -1
        Observable.create<Int> {
            var random = Random
            while (checkCount != 2) {
                val indexDelete = random.nextInt(4) + 1
                if (indexDelete != listQuestion[level].trueCase && indexDelete != indexDelete2) {
                    indexDelete2 = indexDelete
                    it.onNext(indexDelete)
                    checkCount++
                }
            }
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        1 -> {
                            binding.tvCaseA.setText("")
                            binding.ibtnCaseANormal.isClickable = false
                        }
                        2 -> {
                            binding.tvCaseB.setText("")
                            binding.ibtnCaseBNormal.isClickable = false
                        }
                        3 -> {
                            binding.tvCaseC.setText("")
                            binding.ibtnCaseCNormal.isClickable = false
                        }
                        4 -> {
                            binding.tvCaseD.setText("")
                            binding.ibtnCaseDNormal.isClickable = false
                        }
                    }
                },
                {},
                {}
            )
    }

    private fun showDialogCallHelp() {
        var trueCase: String = ""
        when (listQuestion[level].trueCase) {
            1 -> trueCase = "A"
            2 -> trueCase = "B"
            3 -> trueCase = "C"
            4 -> trueCase = "D"
        }
        var checkCalled = false
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.dialog_call_help)
        dialog.setCancelable(false)
        val txtOk = dialog.findViewById<TextView>(R.id.tv_ok)
        val txtAnwser = dialog.findViewById<TextView>(R.id.tv_anwser)
        val ivMessi = dialog.findViewById<ImageView>(R.id.iv_messi)
        val ivRonaldo = dialog.findViewById<ImageView>(R.id.iv_ronaldo)
        val ivBigRonaldo = dialog.findViewById<ImageView>(R.id.iv_big_ronaldo)
        val ivCongVinh = dialog.findViewById<ImageView>(R.id.iv_congvinh)
        val ivNeymar = dialog.findViewById<ImageView>(R.id.iv_neymar)
        val ivSuarez = dialog.findViewById<ImageView>(R.id.iv_suarez)
        ivMessi.setOnClickListener {
            if (!checkCalled) {
                txtAnwser.setText("Đáp án của Messi là: $trueCase")
                checkCalled = true
            } else {
                Toast.makeText(activity, "Bạn đã gọi điện rồi", Toast.LENGTH_LONG).show()
            }
        }
        ivRonaldo.setOnClickListener {
            if (!checkCalled) {
                txtAnwser.setText("Đáp án của Ronaldo là: $trueCase")
                checkCalled = true
            } else {
                Toast.makeText(activity, "Bạn đã gọi điện rồi", Toast.LENGTH_LONG).show()
            }
        }
        ivBigRonaldo.setOnClickListener {
            if (!checkCalled) {
                txtAnwser.setText("Đáp án của Big Ronaldo là: $trueCase")
                checkCalled = true
            } else {
                Toast.makeText(activity, "Bạn đã gọi điện rồi", Toast.LENGTH_LONG).show()
            }
        }
        ivCongVinh.setOnClickListener {
            if (!checkCalled) {
                txtAnwser.setText("Đáp án của Công Vinh là: $trueCase")
                checkCalled = true
            } else {
                Toast.makeText(activity, "Bạn đã gọi điện rồi", Toast.LENGTH_LONG).show()
            }
        }
        ivNeymar.setOnClickListener {
            if (!checkCalled) {
                txtAnwser.setText("Đáp án của Neymar là: $trueCase")
                checkCalled = true
            } else {
                Toast.makeText(activity, "Bạn đã gọi điện rồi", Toast.LENGTH_LONG).show()
            }
        }
        ivSuarez.setOnClickListener {
            if (!checkCalled) {
                txtAnwser.setText("Đáp án của Suarez là: $trueCase")
                checkCalled = true
            } else {
                Toast.makeText(activity, "Bạn đã gọi điện rồi", Toast.LENGTH_LONG).show()
            }
        }
        txtOk.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    @SuppressLint("CheckResult")
    private fun actionLose(ibtnFalse: ImageButton, ibtnFalseAnim: ImageButton) {
        Observable.create<Void> {
            SystemClock.sleep(5000)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {},
                {},
                {
                    ibtnFalse.isVisible = true
                    ibtnFalseAnim.isVisible = true
                    ibtnFalseAnim.startAnimation(anim)
                    when (listQuestion[level].trueCase) {
                        1 -> {
                            binding.ibtnCaseATrue.isVisible = true
                            binding.ibtnCaseATrueAnim.isVisible = true
                            binding.ibtnCaseATrueAnim.startAnimation(anim)
                        }
                        2 -> {
                            binding.ibtnCaseBTrue.isVisible = true
                            binding.ibtnCaseBTrueAnim.isVisible = true
                            binding.ibtnCaseBTrueAnim.startAnimation(anim)
                        }
                        3 -> {
                            binding.ibtnCaseCTrue.isVisible = true
                            binding.ibtnCaseCTrueAnim.isVisible = true
                            binding.ibtnCaseCTrueAnim.startAnimation(anim)
                        }
                        4 -> {
                            binding.ibtnCaseDTrue.isVisible = true
                            binding.ibtnCaseDTrueAnim.isVisible = true
                            binding.ibtnCaseDTrueAnim.startAnimation(anim)
                        }
                    }
                    endGame()
                }
            )
    }

    @SuppressLint("CheckResult")
    private fun endGame() {
        Observable.create<Void> {
            SystemClock.sleep(3000)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {},
                {},
                {
                    if (!CommonApp.checkEnd) {
                        inter.onBackPress()
                        isSelected = true
                    }
                }
            )
    }

    @SuppressLint("CheckResult")
    private fun actionWin(ibtnTrue: ImageButton, ibtnTrueAnim: ImageButton) {
        Observable.create<Void> {
            SystemClock.sleep(5000)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {},
                {},
                {
                    ibtnTrue.isVisible = true
                    ibtnTrueAnim.isVisible = true
                    ibtnTrueAnim.startAnimation(anim)
                    nextLevel()
                }
            )
    }

    @SuppressLint("CheckResult")
    private fun nextLevel() {
        Observable.create<Void> {
            SystemClock.sleep(3000)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {},
                {},
                {
                    if (!CommonApp.checkEnd) {
                        CommonApp.level++
                        inter.nextLevel()
                    }
                }
            )
    }

    private fun pauseTimeAndOffClickableAllButton() {
        isSelected = true
        binding.ibtnCaseANormal.isClickable = false
        binding.ibtnCaseBNormal.isClickable = false
        binding.ibtnCaseCNormal.isClickable = false
        binding.ibtnCaseDNormal.isClickable = false
        binding.ibtn5050.isClickable = false
        binding.ibtnCallHelp.isClickable = false
        binding.ibtnEndGame.isClickable = false
        binding.ibtnPeopleHelp.isClickable = false
        binding.ibtnSwitchQuestion.isClickable = false
        binding.ibtnDelete5050.isClickable = false
        binding.ibtnDeleteCall.isClickable = false
        binding.ibtnDeleteSwitchQuestion.isClickable = false
        binding.ibtnDeletePeopleHelp.isClickable = false
    }

    @SuppressLint("CheckResult")
    private fun timeCountDown() {
        Observable.create<String> {
            while (time > 0 && !isSelected) {
                SystemClock.sleep(1000)
                time--
                it.onNext(time.toString())
            }
            if (time == 0) {
                it.onComplete()
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    binding.tvTime.setText(it)
                },
                {},
                {
                    inter.onBackPress()
                }
            )
    }

    override fun onBackPressForFragment() {
        inter.onBackPress()
        isSelected = true
    }
}