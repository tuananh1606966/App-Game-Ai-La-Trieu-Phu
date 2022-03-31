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
        level = CommonApp.level
        binding.root.startAnimation(anim)
        listQuestion = requireArguments().getSerializable("listQuestion") as ArrayList<Question>
        inter = activity as IActivityAndInGameFragment
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
                mediaPlayerBG!!.stop()
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
                Toast.makeText(activity, "Bạn đã sử dụng sự trợ giúp này rồi", Toast.LENGTH_LONG)
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
                Toast.makeText(activity, "Bạn đã sử dụng sự trợ giúp này rồi", Toast.LENGTH_LONG)
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
                Toast.makeText(activity, "Bạn đã sử dụng sự trợ giúp này rồi", Toast.LENGTH_LONG)
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
                Toast.makeText(activity, "Bạn đã sử dụng sự trợ giúp này rồi", Toast.LENGTH_LONG)
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
                txtMoney.setText(getMoneySave(levelSave))
            }
            false -> {
                if (levelSave >= 10) {
                    txtMoney.setText("22,000,000 VNĐ")
                } else if (levelSave < 10 && levelSave >= 5) {
                    txtMoney.setText("2,000,000 VNĐ")
                } else {
                    txtMoney.setText("000,000 VNĐ")
                }
            }
        }
        btnHuy.setOnClickListener {
            dialog.dismiss()
            inter.onBackPress()
        }
        btnSave.setOnClickListener {
            when (stopGame) {
                true -> {
                    if (edtTen.text.toString().trim().equals("")) {
                        name = "Vô danh"
                    } else {
                        name = edtTen.text.toString().trim()
                    }
                    money = getMoneySave(levelSave)
                    inter.saveHighScore(name, money, levelSave)
                }
                false -> {
                    if (edtTen.text.toString().trim().equals("")) {
                        name = "Vô danh"
                    } else {
                        name = edtTen.text.toString().trim()
                    }
                    if (levelSave >= 10) {
                        money = "22,000,000 VNĐ"
                    } else if (levelSave < 10 && levelSave >= 5) {
                        money = "2,000,000 VNĐ"
                    } else {
                        money = "000,000 VNĐ"
                    }
                    inter.saveHighScore(name, money, levelSave)
                }
            }
            dialog.dismiss()
            inter.onBackPress()
        }
        dialog.show()
    }

    private fun getMoneySave(level: Int): String {
        when (level) {
            0 -> return "000,000 VNĐ"
            1 -> return "200,000 VNĐ"
            2 -> return "400,000 VNĐ"
            3 -> return "600,000 VNĐ"
            4 -> return "1,000,000 VNĐ"
            5 -> return "2,000,000 VNĐ"
            6 -> return "3,000,000 VNĐ"
            7 -> return "6,000,000 VNĐ"
            8 -> return "10,000,000 VNĐ"
            9 -> return "14,000,000 VNĐ"
            10 -> return "22,000,000 VNĐ"
            11 -> return "30,000,000 VNĐ"
            12 -> return "40,000,000 VNĐ"
            13 -> return "60,000,000 VNĐ"
            14 -> return "85,000,000 VNĐ"
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
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_people_help)
        dialog.setCancelable(false)
        val window = dialog.window
        window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
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
                    } else if (level == 9){
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

    private fun getCurrentMpAnsA(): MediaPlayer {
        var mediaPlayer: MediaPlayer
        mediaPlayer = MediaPlayer.create(activity, R.raw.ans_a)
        return mediaPlayer
    }

    private fun getCurrentMpAnsB(): MediaPlayer {
        var mediaPlayer: MediaPlayer
        mediaPlayer = MediaPlayer.create(activity, R.raw.ans_b)
        return mediaPlayer
    }

    private fun getCurrentMpAnsC(): MediaPlayer {
        var mediaPlayer: MediaPlayer
        mediaPlayer = MediaPlayer.create(activity, R.raw.ans_c2)
        return mediaPlayer
    }

    private fun getCurrentMpAnsD(): MediaPlayer {
        var mediaPlayer: MediaPlayer
        mediaPlayer = MediaPlayer.create(activity, R.raw.ans_d)
        return mediaPlayer
    }

    private fun getCurrentMpFalseA(): MediaPlayer {
        var mediaPlayer: MediaPlayer
        mediaPlayer = MediaPlayer.create(activity, R.raw.lose_a)
        return mediaPlayer
    }

    private fun getCurrentMpFalseB(): MediaPlayer {
        var mediaPlayer: MediaPlayer
        mediaPlayer = MediaPlayer.create(activity, R.raw.lose_b)
        return mediaPlayer
    }

    private fun getCurrentMpFalseC(): MediaPlayer {
        var mediaPlayer: MediaPlayer
        mediaPlayer = MediaPlayer.create(activity, R.raw.lose_c)
        return mediaPlayer
    }

    private fun getCurrentMpFalseD(): MediaPlayer {
        var mediaPlayer: MediaPlayer
        mediaPlayer = MediaPlayer.create(activity, R.raw.lose_d)
        return mediaPlayer
    }

    private fun getCurrentMpTrueA(): MediaPlayer {
        var mediaPlayer: MediaPlayer
        mediaPlayer = MediaPlayer.create(activity, R.raw.true_a)
        return mediaPlayer
    }

    private fun getCurrentMpTrueB(): MediaPlayer {
        var mediaPlayer: MediaPlayer
        mediaPlayer = MediaPlayer.create(activity, R.raw.true_b)
        return mediaPlayer
    }

    private fun getCurrentMpTrueC(): MediaPlayer {
        var mediaPlayer: MediaPlayer
        mediaPlayer = MediaPlayer.create(activity, R.raw.true_c)
        return mediaPlayer
    }

    private fun getCurrentMpTrueD(): MediaPlayer {
        var mediaPlayer: MediaPlayer
        mediaPlayer = MediaPlayer.create(activity, R.raw.true_d2)
        return mediaPlayer
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
        } else if (level > 4 && level <= 9){
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
                    binding.tvTime.setText(it)
                },
                {},
                {
                    mediaPlayerTimeOut!!.start()
                    CommonApp.checkEnd = true
                    mediaPlayerBG!!.stop()
                    muteAll()
                    showDialogSaveScore(false)
                }
            )
    }

    override fun onBackPressForFragment() {
        inter.onBackPress()
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
    }

    override fun onStop() {
        super.onStop()
        mediaPlayerBG?.setVolume(0f, 0f)
        mediaPlayerTimeOut?.setVolume(0f, 0f)
        muteAll()
    }

    override fun onDestroy() {
        super.onDestroy()
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
    }

    private fun muteAll() {
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
    }
}