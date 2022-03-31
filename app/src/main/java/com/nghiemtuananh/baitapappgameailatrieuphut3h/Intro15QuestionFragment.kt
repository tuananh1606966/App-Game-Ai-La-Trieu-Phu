package com.nghiemtuananh.baitapappgameailatrieuphut3h

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import com.nghiemtuananh.baitapappgameailatrieuphut3h.basefragment.BaseFragment
import com.nghiemtuananh.baitapappgameailatrieuphut3h.databinding.FragmentLevelBinding
import com.nghiemtuananh.baitapappgameailatrieuphut3h.interfacee.IActivityAndIntroGame
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Intro15QuestionFragment : BaseFragment() {
    lateinit var inter: IActivityAndIntroGame
    lateinit var animIntro: Animation
    var mediaPlayerClick: MediaPlayer? = null
    var mediaPlayerRule: MediaPlayer? = null
    lateinit var binding: FragmentLevelBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLevelBinding.inflate(inflater, container, false)
        inter = activity as IActivityAndIntroGame
        animIntro = AnimationUtils.loadAnimation(binding.root.context, R.anim.anim_translate)
        binding.root.startAnimation(animIntro)
        val anim = AnimationUtils.loadAnimation(activity, R.anim.anim_rotate)
        binding.ivBgCircle15QuestionAnim.startAnimation(anim)
        mediaPlayerRule = MediaPlayer.create(activity, R.raw.reday)
        mediaPlayerRule!!.start()
        mediaPlayerClick = MediaPlayer.create(activity, R.raw.touch_sound)
        loadQuestionImportant()
        binding.tvStart.setOnClickListener {
            mediaPlayerClick!!.start()
            mediaPlayerRule!!.pause()
            showDialog()
        }
        mediaPlayerRule!!.setOnCompletionListener {
            showDialog()
        }
        return binding.root
    }

    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_ready)
        dialog.setCancelable(false)
        val btnReady = dialog.findViewById<Button>(R.id.btn_san_sang)
        val btnCancel = dialog.findViewById<Button>(R.id.btn_huy_bo)
        btnReady.setOnClickListener {
            inter.clickReady()
            dialog.dismiss()
        }
        btnCancel.setOnClickListener {
            inter.clickCancel()
            dialog.dismiss()
        }
        dialog.show()
    }

    @SuppressLint("CheckResult")
    private fun loadQuestionImportant() {
        Observable.create<Int> {
            SystemClock.sleep(4500)
            it.onNext(1)
            SystemClock.sleep(1000)
            it.onNext(2)
            SystemClock.sleep(500)
            it.onNext(3)
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        1 -> {
                            binding.tvLevel5.setBackgroundColor(Color.parseColor("#3F51B5"))
                        }
                        2 -> {
                            binding.tvLevel10.setBackgroundColor(Color.parseColor("#3F51B5"))
                            binding.tvLevel5.setBackgroundColor(Color.TRANSPARENT)
                        }
                        3 -> {
                            binding.tvLevel15.setBackgroundColor(Color.parseColor("#3F51B5"))
                            binding.tvLevel10.setBackgroundColor(Color.TRANSPARENT)
                        }
                    }
                },
                {},
                {}
            )
    }

    override fun onStop() {
        super.onStop()
        mediaPlayerRule?.setVolume(0f, 0f)
    }

    override fun onStart() {
        super.onStart()
        mediaPlayerRule?.setVolume(1f, 1f)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayerRule?.release()
        mediaPlayerRule = null
        mediaPlayerClick?.release()
        mediaPlayerClick = null
    }
}