package com.nghiemtuananh.baitapappgameailatrieuphut3h

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
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
    var mpAnsA: MediaPlayer? = null
    var mpAnsB: MediaPlayer? = null
    var mpAnsC: MediaPlayer? = null
    var mpAnsD: MediaPlayer? = null
    var mpFalseA: MediaPlayer? = null
    var mpFalseB: MediaPlayer? = null
    var mpFalseC: MediaPlayer? = null
    var mpFalseD: MediaPlayer? = null
    var mpTrueA: MediaPlayer? = null
    var mpTrueB: MediaPlayer? = null
    var mpTrueC: MediaPlayer? = null
    var mpTrueD: MediaPlayer? = null
    var mpAnsNow: MediaPlayer? = null
    var mpCongrat: MediaPlayer? = null
    var mediaPlayerBG: MediaPlayer? = null
    var mediaPlayerTimeOut: MediaPlayer? = null
    var mediaPlayerClick: MediaPlayer? = null
    var mediaPlayerCallHelp: MediaPlayer? = null
    var mediaPlayerPeopleHelp: MediaPlayer? = null
    var mediaPlayer5050: MediaPlayer? = null
    var mediaPlayerImportant: MediaPlayer? = null
    var mediaPlayerWinner: MediaPlayer? = null
    lateinit var anim: Animation
    var isSelected = false
    var time: Int = 30
    var level: Int = 0
    var listQuestion: ArrayList<Question> = arrayListOf()
    lateinit var inter: IActivityAndInGameFragment
    lateinit var binding: FragmentIngameBinding
    var dialogCallHelp: Dialog? = null
    var dialogPeopleHelp: Dialog? = null
    private lateinit var altpDao: ALTPDao
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentIngameBinding.inflate(inflater, container, false)
        anim = AnimationUtils.loadAnimation(binding.root.context, R.anim.anim_translate)
        level = CommonApp.level
        binding.root.startAnimation(anim)
        listQuestion = requireArguments().getSerializable("listQuestion") as ArrayList<Question>
        inter = activity as IActivityAndInGameFragment
        if (level == 14) {
            mediaPlayerImportant = MediaPlayer.create(activity, R.raw.important)
            mediaPlayerImportant!!.start()
        }
        mediaPlayerTimeOut = MediaPlayer.create(activity, R.raw.out_of_time)
        mediaPlayerBG = getMpBG()
        mediaPlayerBG!!.isLooping = true
        mediaPlayerBG!!.start()
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
                mpAnsA = getCurrentMpAnsA()
                mpAnsA!!.start()
                pauseTimeAndOffClickableAllButton()
                binding.ibtnSelectA.isVisible = true
                binding.ibtnSelectAAnim.isVisible = true
                binding.ibtnSelectAAnim.startAnimation(anim)
                if (level >= 5) {
                    mpAnsA!!.setOnCompletionListener {
                        mpAnsNow = getMpNow()
                        mpAnsNow!!.start()
                        mpAnsNow!!.setOnCompletionListener {
                            checkSelectA()
                        }
                    }
                } else {
                    checkSelectA()
                }
            }
            binding.ibtnCaseBNormal -> {
                mpAnsB = getCurrentMpAnsB()
                mpAnsB!!.start()
                pauseTimeAndOffClickableAllButton()
                binding.ibtnSelectB.isVisible = true
                binding.ibtnSelectBAnim.isVisible = true
                binding.ibtnSelectBAnim.startAnimation(anim)
                if (level >= 5) {
                    mpAnsB!!.setOnCompletionListener {
                        mpAnsNow = getMpNow()
                        mpAnsNow!!.start()
                        mpAnsNow!!.setOnCompletionListener {
                            checkSelectB()
                        }
                    }
                } else {
                    checkSelectB()
                }
            }
            binding.ibtnCaseCNormal -> {
                mpAnsC = getCurrentMpAnsC()
                mpAnsC!!.start()
                pauseTimeAndOffClickableAllButton()
                binding.ibtnSelectC.isVisible = true
                binding.ibtnSelectCAnim.isVisible = true
                binding.ibtnSelectCAnim.startAnimation(anim)
                if (level >= 5) {
                    mpAnsC!!.setOnCompletionListener {
                        mpAnsNow = getMpNow()
                        mpAnsNow!!.start()
                        mpAnsNow!!.setOnCompletionListener {
                            checkSelectC()
                        }
                    }
                } else {
                    checkSelectC()
                }
            }
            binding.ibtnCaseDNormal -> {
                mpAnsD = getCurrentMpAnsD()
                mpAnsD!!.start()
                pauseTimeAndOffClickableAllButton()
                binding.ibtnSelectD.isVisible = true
                binding.ibtnSelectDAnim.isVisible = true
                binding.ibtnSelectDAnim.startAnimation(anim)
                if (level >= 5) {
                    mpAnsD!!.setOnCompletionListener {
                        mpAnsNow = getMpNow()
                        mpAnsNow!!.start()
                        mpAnsNow!!.setOnCompletionListener {
                            checkSelectD()
                        }
                    }
                } else {
                    checkSelectD()
                }
            }
            binding.ibtnEndGame -> {
                mediaPlayerClick = MediaPlayer.create(activity, R.raw.touch_sound)
                mediaPlayerClick!!.start()
                stopAllMp()
                showDialogSaveScore(true)
                isSelected = true
            }
            binding.ibtnCallHelp -> {
                binding.ibtnCallHelp.isClickable = false
                CommonApp.checkCallHelp = true
                binding.ibtnDeleteCall.isVisible = true
                mediaPlayerClick = MediaPlayer.create(activity, R.raw.touch_sound)
                mediaPlayerClick!!.start()
                mediaPlayerCallHelp = MediaPlayer.create(activity, R.raw.help_call)
                mediaPlayerCallHelp!!.start()
                showDialogCallHelp()
            }
            binding.ibtnDeleteCall -> {
                Toast.makeText(activity, "B???n ???? s??? d???ng s??? tr??? gi??p n??y r???i", Toast.LENGTH_LONG)
                    .show()
            }
            binding.ibtn5050 -> {
                binding.ibtn5050.isClickable = false
                mediaPlayerClick = MediaPlayer.create(activity, R.raw.touch_sound)
                mediaPlayerClick!!.start()
                CommonApp.check5050Help = true
                binding.ibtnDelete5050.isVisible = true
                mediaPlayer5050 = MediaPlayer.create(activity, R.raw.sound5050)
                mediaPlayer5050!!.start()
                beforeDeleteTwoFalseCase()
            }
            binding.ibtnDelete5050 -> {
                Toast.makeText(activity, "B???n ???? s??? d???ng s??? tr??? gi??p n??y r???i", Toast.LENGTH_LONG)
                    .show()
            }
            binding.ibtnSwitchQuestion -> {
                binding.ibtnSwitchQuestion.isClickable = false
                mediaPlayerClick = MediaPlayer.create(activity, R.raw.touch_sound)
                mediaPlayerClick!!.start()
                CommonApp.checkSwitchQuestion = true
                binding.ibtnDeleteSwitchQuestion.isVisible = true
                switchQuestion()
            }
            binding.ibtnDeleteSwitchQuestion -> {
                Toast.makeText(activity, "B???n ???? s??? d???ng s??? tr??? gi??p n??y r???i", Toast.LENGTH_LONG)
                    .show()
            }
            binding.ibtnPeopleHelp -> {
                binding.ibtnPeopleHelp.isClickable = false
                mediaPlayerClick = MediaPlayer.create(activity, R.raw.touch_sound)
                mediaPlayerClick!!.start()
                CommonApp.checkPeopleHelp = true
                binding.ibtnDeletePeopleHelp.isVisible = true
                mediaPlayerPeopleHelp = MediaPlayer.create(activity, R.raw.khan_gia)
                mediaPlayerPeopleHelp!!.start()
                mediaPlayerPeopleHelp!!.setOnCompletionListener {
                    showDialogPeopleHelp()
                }
            }
            binding.ibtnDeletePeopleHelp -> {
                Toast.makeText(activity, "B???n ???? s??? d???ng s??? tr??? gi??p n??y r???i", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun showDialogSaveScore(stopGame: Boolean) {
        var name = ""
        var money = ""
        val levelSave = listQuestion[level].level - 1
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_save_score)
        dialog.setCancelable(false)
        val edtTen = dialog.findViewById<EditText>(R.id.edt_name)
        val btnSave = dialog.findViewById<Button>(R.id.btn_save)
        val btnHuy = dialog.findViewById<Button>(R.id.btn_huy_save)
        val txtMoney = dialog.findViewById<TextView>(R.id.tv_money_over)
        when (stopGame) {
            true -> {
                txtMoney.text = getMoneySave(levelSave)
            }
            false -> {
                if (levelSave >= 10) {
                    txtMoney.text = "22,000,000 VN??"
                } else if (levelSave < 10 && levelSave >= 5) {
                    txtMoney.text = "2,000,000 VN??"
                } else {
                    txtMoney.text = "000,000 VN??"
                }
            }
        }
        btnHuy.setOnClickListener {
            dialog.dismiss()
            offDialogHelp()
            inter.onBackPress(false)
        }
        btnSave.setOnClickListener {
            when (stopGame) {
                true -> {
                    if (edtTen.text.toString().trim().equals("")) {
                        name = "V?? danh"
                    } else {
                        name = edtTen.text.toString().trim()
                    }
                    money = getMoneySave(levelSave)
                    inter.saveHighScore(name, money, levelSave)
                }
                false -> {
                    if (edtTen.text.toString().trim().equals("")) {
                        name = "V?? danh"
                    } else {
                        name = edtTen.text.toString().trim()
                    }
                    if (levelSave >= 10) {
                        money = "22,000,000 VN??"
                    } else if (levelSave < 10 && levelSave >= 5) {
                        money = "2,000,000 VN??"
                    } else {
                        money = "000,000 VN??"
                    }
                    inter.saveHighScore(name, money, levelSave)
                }
            }
            dialog.dismiss()
            offDialogHelp()
            inter.onBackPress(false)
        }
        dialog.show()
    }

    private fun offDialogHelp() {
        if (dialogCallHelp != null && dialogCallHelp!!.isShowing) {
            dialogCallHelp!!.dismiss()
        }
        if (dialogPeopleHelp != null && dialogPeopleHelp!!.isShowing) {
            dialogPeopleHelp!!.dismiss()
        }
    }

    private fun getMoneySave(level: Int): String {
        when (level) {
            0 -> return "000,000 VN??"
            1 -> return "200,000 VN??"
            2 -> return "400,000 VN??"
            3 -> return "600,000 VN??"
            4 -> return "1,000,000 VN??"
            5 -> return "2,000,000 VN??"
            6 -> return "3,000,000 VN??"
            7 -> return "6,000,000 VN??"
            8 -> return "10,000,000 VN??"
            9 -> return "14,000,000 VN??"
            10 -> return "22,000,000 VN??"
            11 -> return "30,000,000 VN??"
            12 -> return "40,000,000 VN??"
            13 -> return "60,000,000 VN??"
            14 -> return "85,000,000 VN??"
        }
        return ""
    }

    @SuppressLint("CheckResult")
    private fun beforeDeleteTwoFalseCase() {
        Observable.create<Void> {
            SystemClock.sleep(3000)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {},
                {},
                {
                    deleteTwoFalseCase()
                }
            )
    }

    private fun checkSelectA() {
        if (listQuestion[level].trueCase == 1) {
            mpTrueA = getCurrentMpTrueA()
            actionWin(binding.ibtnCaseATrue, binding.ibtnCaseATrueAnim, mpTrueA)
        } else {
            actionLose(binding.ibtnCaseAFalse, binding.ibtnCaseAFalseAnim)
        }
    }

    private fun checkSelectB() {
        if (listQuestion[level].trueCase == 2) {
            mpTrueB = getCurrentMpTrueB()
            actionWin(binding.ibtnCaseBTrue, binding.ibtnCaseBTrueAnim, mpTrueB)
        } else {
            actionLose(binding.ibtnCaseBFalse, binding.ibtnCaseBFalseAnim)
        }
    }

    private fun checkSelectC() {
        if (listQuestion[level].trueCase == 3) {
            mpTrueC = getCurrentMpTrueC()
            actionWin(binding.ibtnCaseCTrue, binding.ibtnCaseCTrueAnim, mpTrueC)
        } else {
            actionLose(binding.ibtnCaseCFalse, binding.ibtnCaseCFalseAnim)
        }
    }

    private fun checkSelectD() {
        if (listQuestion[level].trueCase == 4) {
            mpTrueD = getCurrentMpTrueD()
            actionWin(binding.ibtnCaseDTrue, binding.ibtnCaseDTrueAnim, mpTrueD)
        } else {
            actionLose(binding.ibtnCaseDFalse, binding.ibtnCaseDFalseAnim)
        }
    }

    private fun showDialogPeopleHelp() {
        val random = Random
        var progressA = 0.0
        var progressB = 0.0
        var progressC = 0.0
        var progressD = 0.0
        when (listQuestion[level].trueCase) {
            1 -> {
                progressA = 64.8 + random.nextDouble(11.2)
                progressB = 5.0 + random.nextDouble(6.0)
                progressC = 5.0 + random.nextDouble(6.0)
                progressD = 100 - progressA - progressB - progressC
            }
            2 -> {
                progressB = 64.8 + random.nextDouble(11.2)
                progressA = 5.0 + random.nextDouble(6.0)
                progressC = 5.0 + random.nextDouble(6.0)
                progressD = 100 - progressA - progressB - progressC
            }
            3 -> {
                progressC = 64.8 + random.nextDouble(11.2)
                progressB = 5.0 + random.nextDouble(6.0)
                progressA = 5.0 + random.nextDouble(6.0)
                progressD = 100 - progressA - progressB - progressC
            }
            4 -> {
                progressD = 64.8 + random.nextDouble(11.2)
                progressB = 5.0 + random.nextDouble(6.0)
                progressC = 5.0 + random.nextDouble(6.0)
                progressA = 100 - progressD - progressB - progressC
            }
        }
        progressA = Math.round(progressA * 100.0) / 100.0
        progressB = Math.round(progressB * 100.0) / 100.0
        progressC = Math.round(progressC * 100.0) / 100.0
        progressD = Math.round(progressD * 100.0) / 100.0
        setDialogPeopleHelp(progressA, progressB, progressC, progressD)
    }

    private fun setDialogPeopleHelp(
        progressA: Double,
        progressB: Double,
        progressC: Double,
        progressD: Double,
    ) {
        dialogPeopleHelp = Dialog(requireContext())
        dialogPeopleHelp!!.setContentView(R.layout.dialog_people_help)
        dialogPeopleHelp!!.setCancelable(false)
        val window = dialogPeopleHelp!!.window
        window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val txtProgressA = dialogPeopleHelp!!.findViewById<TextView>(R.id.tv_progress_a)
        val txtProgressB = dialogPeopleHelp!!.findViewById<TextView>(R.id.tv_progress_b)
        val txtProgressC = dialogPeopleHelp!!.findViewById<TextView>(R.id.tv_progress_c)
        val txtProgressD = dialogPeopleHelp!!.findViewById<TextView>(R.id.tv_progress_d)
        val progressViewA = dialogPeopleHelp!!.findViewById<ProgressView>(R.id.progress_a)
        val progressViewB = dialogPeopleHelp!!.findViewById<ProgressView>(R.id.progress_b)
        val progressViewC = dialogPeopleHelp!!.findViewById<ProgressView>(R.id.progress_c)
        val progressViewD = dialogPeopleHelp!!.findViewById<ProgressView>(R.id.progress_d)
        val btnThanks = dialogPeopleHelp!!.findViewById<Button>(R.id.btn_thanks)

        txtProgressA.text = "$progressA %"
        txtProgressB.text = "$progressB %"
        txtProgressC.text = "$progressC %"
        txtProgressD.text = "$progressD %"

        progressViewA.progress = progressA.toFloat()
        progressViewB.progress = progressB.toFloat()
        progressViewC.progress = progressC.toFloat()
        progressViewD.progress = progressD.toFloat()

        btnThanks.setOnClickListener {
            dialogPeopleHelp!!.dismiss()
        }
        dialogPeopleHelp!!.show()
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
                            binding.tvCaseA.text = ""
                            binding.ibtnCaseANormal.isClickable = false
                        }
                        2 -> {
                            binding.tvCaseB.text = ""
                            binding.ibtnCaseBNormal.isClickable = false
                        }
                        3 -> {
                            binding.tvCaseC.text = ""
                            binding.ibtnCaseCNormal.isClickable = false
                        }
                        4 -> {
                            binding.tvCaseD.text = ""
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
        dialogCallHelp = Dialog(requireActivity())
        dialogCallHelp!!.setContentView(R.layout.dialog_call_help)
        dialogCallHelp!!.setCancelable(false)
        val txtOk = dialogCallHelp!!.findViewById<TextView>(R.id.tv_ok)
        val txtAnwser = dialogCallHelp!!.findViewById<TextView>(R.id.tv_anwser)
        val ivMessi = dialogCallHelp!!.findViewById<ImageView>(R.id.iv_messi)
        val ivRonaldo = dialogCallHelp!!.findViewById<ImageView>(R.id.iv_ronaldo)
        val ivBigRonaldo = dialogCallHelp!!.findViewById<ImageView>(R.id.iv_big_ronaldo)
        val ivCongVinh = dialogCallHelp!!.findViewById<ImageView>(R.id.iv_congvinh)
        val ivNeymar = dialogCallHelp!!.findViewById<ImageView>(R.id.iv_neymar)
        val ivSuarez = dialogCallHelp!!.findViewById<ImageView>(R.id.iv_suarez)
        ivMessi.setOnClickListener {
            if (!checkCalled) {
                txtAnwser.text = "????p ??n c???a Messi l??: $trueCase"
                checkCalled = true
            } else {
                Toast.makeText(activity, "B???n ???? g???i ??i???n r???i", Toast.LENGTH_LONG).show()
            }
        }
        ivRonaldo.setOnClickListener {
            if (!checkCalled) {
                txtAnwser.text = "????p ??n c???a Ronaldo l??: $trueCase"
                checkCalled = true
            } else {
                Toast.makeText(activity, "B???n ???? g???i ??i???n r???i", Toast.LENGTH_LONG).show()
            }
        }
        ivBigRonaldo.setOnClickListener {
            if (!checkCalled) {
                txtAnwser.text = "????p ??n c???a Big Ronaldo l??: $trueCase"
                checkCalled = true
            } else {
                Toast.makeText(activity, "B???n ???? g???i ??i???n r???i", Toast.LENGTH_LONG).show()
            }
        }
        ivCongVinh.setOnClickListener {
            if (!checkCalled) {
                txtAnwser.text = "????p ??n c???a C??ng Vinh l??: $trueCase"
                checkCalled = true
            } else {
                Toast.makeText(activity, "B???n ???? g???i ??i???n r???i", Toast.LENGTH_LONG).show()
            }
        }
        ivNeymar.setOnClickListener {
            if (!checkCalled) {
                txtAnwser.text = "????p ??n c???a Neymar l??: $trueCase"
                checkCalled = true
            } else {
                Toast.makeText(activity, "B???n ???? g???i ??i???n r???i", Toast.LENGTH_LONG).show()
            }
        }
        ivSuarez.setOnClickListener {
            if (!checkCalled) {
                txtAnwser.text = "????p ??n c???a Suarez l??: $trueCase"
                checkCalled = true
            } else {
                Toast.makeText(activity, "B???n ???? g???i ??i???n r???i", Toast.LENGTH_LONG).show()
            }
        }
        txtOk.setOnClickListener {
            dialogCallHelp!!.dismiss()
        }
        dialogCallHelp!!.show()
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
                            mpFalseA = getCurrentMpFalseA()
                            mpFalseA!!.start()
                            binding.ibtnCaseATrue.isVisible = true
                            binding.ibtnCaseATrueAnim.isVisible = true
                            binding.ibtnCaseATrueAnim.startAnimation(anim)
                        }
                        2 -> {
                            mpFalseB = getCurrentMpFalseB()
                            mpFalseB!!.start()
                            binding.ibtnCaseBTrue.isVisible = true
                            binding.ibtnCaseBTrueAnim.isVisible = true
                            binding.ibtnCaseBTrueAnim.startAnimation(anim)
                        }
                        3 -> {
                            mpFalseC = getCurrentMpFalseC()
                            mpFalseC!!.start()
                            binding.ibtnCaseCTrue.isVisible = true
                            binding.ibtnCaseCTrueAnim.isVisible = true
                            binding.ibtnCaseCTrueAnim.startAnimation(anim)
                        }
                        4 -> {
                            mpFalseD = getCurrentMpFalseD()
                            mpFalseD!!.start()
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
                    mediaPlayerBG!!.stop()
                    showDialogSaveScore(false)
                }
            )
    }

    @SuppressLint("CheckResult")
    private fun actionWin(ibtnTrue: ImageButton, ibtnTrueAnim: ImageButton, mp: MediaPlayer?) {
        Observable.create<Void> {
            SystemClock.sleep(5000)
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {},
                {},
                {
                    mp!!.start()
                    ibtnTrue.isVisible = true
                    ibtnTrueAnim.isVisible = true
                    ibtnTrueAnim.startAnimation(anim)
                    if (level == 4) {
                        mp.setOnCompletionListener {
                            mpCongrat = getMpCongra()
                            mpCongrat!!.start()
                            mpCongrat!!.setOnCompletionListener {
                                nextLevel()
                            }
                        }
                    } else if (level == 9) {
                        mp.setOnCompletionListener {
                            mpCongrat = getMpCongra()
                            mpCongrat!!.start()
                            mpCongrat!!.setOnCompletionListener {
                                nextLevel()
                            }
                        }
                    } else {
                        nextLevel()
                    }
                }
            )
    }

    @SuppressLint("CheckResult")
    private fun nextLevel() {
        Observable.create<String> {
            if (level == 14) {
                SystemClock.sleep(2000)
                it.onNext(null.toString())
            } else {
                SystemClock.sleep(3000)
                it.onComplete()
            }
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mediaPlayerWinner = MediaPlayer.create(activity, R.raw.best_player)
                    mediaPlayerWinner!!.start()
                    mediaPlayerBG!!.stop()
                    showDialogSaveScoreWinner()
                },
                {},
                {
                    if (!CommonApp.checkEnd) {
                        CommonApp.level++
                        inter.nextLevel()
                    }
                }
            )
    }

    private fun showDialogSaveScoreWinner() {
        var name = ""
        var money = ""
        val levelSave = 15
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_save_score_winner)
        dialog.setCancelable(false)
        val edtTen = dialog.findViewById<EditText>(R.id.edt_name)
        val btnSave = dialog.findViewById<Button>(R.id.btn_save)
        val btnHuy = dialog.findViewById<Button>(R.id.btn_huy_save)
        val txtMoney = dialog.findViewById<TextView>(R.id.tv_money_over)
        btnHuy.setOnClickListener {
            dialog.dismiss()
            offDialogHelp()
            inter.onBackPress(true)
        }
        btnSave.setOnClickListener {
            name = if (edtTen.text.toString().trim() == "") {
                "V?? danh"
            } else {
                edtTen.text.toString().trim()
            }
            money = txtMoney.text.toString()
            inter.saveHighScore(name, money, levelSave)
            dialog.dismiss()
            offDialogHelp()
            inter.onBackPress(true)
        }
        dialog.show()
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

    private fun getCurrentMpAnsA(): MediaPlayer {
        return MediaPlayer.create(activity, R.raw.ans_a)
    }

    private fun getCurrentMpAnsB(): MediaPlayer {
        return MediaPlayer.create(activity, R.raw.ans_b)
    }

    private fun getCurrentMpAnsC(): MediaPlayer {
        return MediaPlayer.create(activity, R.raw.ans_c2)
    }

    private fun getCurrentMpAnsD(): MediaPlayer {
        return MediaPlayer.create(activity, R.raw.ans_d)
    }

    private fun getCurrentMpFalseA(): MediaPlayer {
        return MediaPlayer.create(activity, R.raw.lose_a)
    }

    private fun getCurrentMpFalseB(): MediaPlayer {
        return MediaPlayer.create(activity, R.raw.lose_b)
    }

    private fun getCurrentMpFalseC(): MediaPlayer {
        return MediaPlayer.create(activity, R.raw.lose_c)
    }

    private fun getCurrentMpFalseD(): MediaPlayer {
        return MediaPlayer.create(activity, R.raw.lose_d)
    }

    private fun getCurrentMpTrueA(): MediaPlayer {
        return MediaPlayer.create(activity, R.raw.true_a)
    }

    private fun getCurrentMpTrueB(): MediaPlayer {
        return MediaPlayer.create(activity, R.raw.true_b)
    }

    private fun getCurrentMpTrueC(): MediaPlayer {
        return MediaPlayer.create(activity, R.raw.true_c)
    }

    private fun getCurrentMpTrueD(): MediaPlayer {
        return MediaPlayer.create(activity, R.raw.true_d2)
    }

    private fun getMpCongra(): MediaPlayer? {
        var mediaPlayer: MediaPlayer? = null
        if (level == 4) {
            mediaPlayer = MediaPlayer.create(activity, R.raw.chuc_mung_vuot_moc_01_0)
        } else if (level == 9) {
            mediaPlayer = MediaPlayer.create(activity, R.raw.chuc_mung_vuot_moc_02_0)
        }
        return mediaPlayer
    }

    private fun getMpBG(): MediaPlayer {
        val mp: MediaPlayer
        if (level <= 4) {
            mp = MediaPlayer.create(activity, R.raw.background_music)
        } else if (level in 5..9) {
            mp = MediaPlayer.create(activity, R.raw.background_music_b)
        } else {
            mp = MediaPlayer.create(activity, R.raw.background_music_c)
        }
        return mp
    }

    private fun getMpNow(): MediaPlayer? {
        var mp: MediaPlayer? = null
        val random = Random
        val rd = random.nextInt(3)
        when (rd) {
            0 -> mp = MediaPlayer.create(activity, R.raw.ans_now1)
            1 -> mp = MediaPlayer.create(activity, R.raw.ans_now2)
            2 -> mp = MediaPlayer.create(activity, R.raw.ans_now3)
        }
        return mp
    }

    @SuppressLint("CheckResult")
    private fun timeCountDown() {
        Observable.create<String> {
            while (time > 0 && !isSelected) {
                SystemClock.sleep(1000)
                time--
                it.onNext(time.toString())
            }
            if (time == 0 && !isSelected) {
                it.onComplete()
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    binding.tvTime.text = it
                },
                {},
                {
                    mediaPlayerTimeOut?.start()
                    CommonApp.checkEnd = true
                    stopAllMp()
                    showDialogSaveScore(false)
                }
            )
    }

    override fun onBackPressForFragment() {
        inter.onBackPress(false)
        isSelected = true
    }

    override fun onStart() {
        super.onStart()
        mediaPlayerBG?.setVolume(1f, 1f)
        mediaPlayerTimeOut?.setVolume(1f, 1f)
        mpCongrat?.setVolume(1f, 1f)
        mpAnsA?.setVolume(1f, 1f)
        mpAnsB?.setVolume(1f, 1f)
        mpAnsC?.setVolume(1f, 1f)
        mpAnsD?.setVolume(1f, 1f)
        mpFalseA?.setVolume(1f, 1f)
        mpFalseB?.setVolume(1f, 1f)
        mpFalseC?.setVolume(1f, 1f)
        mpFalseD?.setVolume(1f, 1f)
        mpTrueA?.setVolume(1f, 1f)
        mpTrueB?.setVolume(1f, 1f)
        mpTrueC?.setVolume(1f, 1f)
        mpTrueD?.setVolume(1f, 1f)
        mpAnsNow?.setVolume(1f, 1f)
        mediaPlayerCallHelp?.setVolume(1f, 1f)
        mediaPlayerPeopleHelp?.setVolume(1f, 1f)
        mediaPlayer5050?.setVolume(1f, 1f)
        mediaPlayerImportant?.setVolume(1f, 1f)
        mediaPlayerWinner?.setVolume(1f, 1f)
    }

    override fun onStop() {
        super.onStop()
        mediaPlayerBG?.setVolume(0f, 0f)
        mediaPlayerTimeOut?.setVolume(0f, 0f)
        mpCongrat?.setVolume(0f, 0f)
        mpAnsA?.setVolume(0f, 0f)
        mpAnsB?.setVolume(0f, 0f)
        mpAnsC?.setVolume(0f, 0f)
        mpAnsD?.setVolume(0f, 0f)
        mpFalseA?.setVolume(0f, 0f)
        mpFalseB?.setVolume(0f, 0f)
        mpFalseC?.setVolume(0f, 0f)
        mpFalseD?.setVolume(0f, 0f)
        mpTrueA?.setVolume(0f, 0f)
        mpTrueB?.setVolume(0f, 0f)
        mpTrueC?.setVolume(0f, 0f)
        mpTrueD?.setVolume(0f, 0f)
        mpAnsNow?.setVolume(0f, 0f)
        mediaPlayerCallHelp?.setVolume(0f, 0f)
        mediaPlayerPeopleHelp?.setVolume(0f, 0f)
        mediaPlayer5050?.setVolume(0f, 0f)
        mediaPlayerImportant?.setVolume(0f, 0f)
        mediaPlayerWinner?.setVolume(0f, 0f)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayerBG?.release()
        mediaPlayerBG = null
        mediaPlayerTimeOut?.release()
        mediaPlayerTimeOut = null
        mpCongrat?.release()
        mpCongrat = null
        mpAnsA?.release()
        mpAnsA = null
        mpAnsB?.release()
        mpAnsB = null
        mpAnsC?.release()
        mpAnsC = null
        mpAnsD?.release()
        mpAnsD = null
        mpFalseA?.release()
        mpFalseA = null
        mpFalseB?.release()
        mpFalseB = null
        mpFalseC?.release()
        mpFalseC = null
        mpFalseD?.release()
        mpFalseD = null
        mpTrueA?.release()
        mpTrueA = null
        mpTrueB?.release()
        mpTrueB = null
        mpTrueC?.release()
        mpTrueC = null
        mpTrueD?.release()
        mpTrueD = null
        mpAnsNow?.release()
        mpAnsNow = null
        mpCongrat?.release()
        mpCongrat = null
        mediaPlayerTimeOut?.release()
        mediaPlayerTimeOut = null
        mediaPlayerClick?.release()
        mediaPlayerClick = null
        mediaPlayerCallHelp?.release()
        mediaPlayerCallHelp = null
        mediaPlayerPeopleHelp?.release()
        mediaPlayerPeopleHelp = null
        mediaPlayer5050?.release()
        mediaPlayer5050 = null
        mediaPlayerImportant?.release()
        mediaPlayerImportant = null
        mediaPlayerWinner?.release()
        mediaPlayerWinner = null
    }

    private fun stopAllMp() {
        mediaPlayerBG?.stop()
        mpCongrat?.stop()
        mpAnsA?.stop()
        mpAnsB?.stop()
        mpAnsC?.stop()
        mpAnsD?.stop()
        mpFalseA?.stop()
        mpFalseB?.stop()
        mpFalseC?.stop()
        mpFalseD?.stop()
        mpTrueA?.stop()
        mpTrueB?.stop()
        mpTrueC?.stop()
        mpTrueD?.stop()
        mpAnsNow?.stop()
        mediaPlayerCallHelp?.stop()
        mediaPlayerPeopleHelp?.stop()
        mediaPlayer5050?.stop()
        mediaPlayerImportant?.stop()
        mediaPlayerWinner?.stop()
    }
}