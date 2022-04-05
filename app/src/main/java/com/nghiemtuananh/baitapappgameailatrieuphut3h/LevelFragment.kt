package com.nghiemtuananh.baitapappgameailatrieuphut3h

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.nghiemtuananh.baitapappgameailatrieuphut3h.basefragment.BaseFragment
import com.nghiemtuananh.baitapappgameailatrieuphut3h.databinding.FragmentLevelBinding

class LevelFragment: BaseFragment() {
    var mediaPlayer: MediaPlayer? = null
    lateinit var binding: FragmentLevelBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLevelBinding.inflate(inflater, container, false)
        val level = requireArguments().getInt("level")
        val animTranslate = AnimationUtils.loadAnimation(binding.root.context, R.anim.anim_translate)
        binding.root.startAnimation(animTranslate)
        when (level) {
            1 -> {
                binding.ivLevel1.isVisible = true
                mediaPlayer = MediaPlayer.create(activity, R.raw.ques1)
                mediaPlayer!!.start()
                binding.tvStart.isGone = true
            }
            2 -> {
                binding.ivLevel2.isVisible = true
                mediaPlayer = MediaPlayer.create(activity, R.raw.ques2)
                mediaPlayer!!.start()
                binding.tvStart.isGone = true
            }
            3 -> {
                binding.ivLevel3.isVisible = true
                mediaPlayer = MediaPlayer.create(activity, R.raw.ques3)
                mediaPlayer!!.start()
                binding.tvStart.isGone = true
            }
            4 -> {
                binding.ivLevel4.isVisible = true
                mediaPlayer = MediaPlayer.create(activity, R.raw.ques4)
                mediaPlayer!!.start()
                binding.tvStart.isGone = true
            }
            5 -> {
                binding.ivLevel5.isVisible = true
                mediaPlayer = MediaPlayer.create(activity, R.raw.ques5)
                mediaPlayer!!.start()
                binding.tvStart.isGone = true
            }
            6 -> {
                binding.ivLevel6.isVisible = true
                mediaPlayer = MediaPlayer.create(activity, R.raw.ques6)
                mediaPlayer!!.start()
                binding.tvStart.isGone = true
            }
            7 -> {
                binding.ivLevel7.isVisible = true
                mediaPlayer = MediaPlayer.create(activity, R.raw.ques7)
                mediaPlayer!!.start()
                binding.tvStart.isGone = true
            }
            8 -> {
                binding.ivLevel8.isVisible = true
                mediaPlayer = MediaPlayer.create(activity, R.raw.ques8)
                mediaPlayer!!.start()
                binding.tvStart.isGone = true
            }
            9 -> {
                binding.ivLevel9.isVisible = true
                mediaPlayer = MediaPlayer.create(activity, R.raw.ques9)
                mediaPlayer!!.start()
                binding.tvStart.isGone = true
            }
            10 -> {
                binding.ivLevel10.isVisible = true
                mediaPlayer = MediaPlayer.create(activity, R.raw.ques10)
                mediaPlayer!!.start()
                binding.tvStart.isGone = true
            }
            11 -> {
                binding.ivLevel11.isVisible = true
                mediaPlayer = MediaPlayer.create(activity, R.raw.ques11)
                mediaPlayer!!.start()
                binding.tvStart.isGone = true
            }
            12 -> {
                binding.ivLevel12.isVisible = true
                mediaPlayer = MediaPlayer.create(activity, R.raw.ques12)
                mediaPlayer!!.start()
                binding.tvStart.isGone = true
            }
            13 -> {
                binding.ivLevel13.isVisible = true
                mediaPlayer = MediaPlayer.create(activity, R.raw.ques13)
                mediaPlayer!!.start()
                binding.tvStart.isGone = true
            }
            14 -> {
                binding.ivLevel14.isVisible = true
                mediaPlayer = MediaPlayer.create(activity, R.raw.ques14)
                mediaPlayer!!.start()
                binding.tvStart.isGone = true
            }
            15 -> {
                binding.ivLevel15.isVisible = true
                mediaPlayer = MediaPlayer.create(activity, R.raw.ques15)
                mediaPlayer!!.start()
                binding.tvStart.isGone = true
            }
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        mediaPlayer?.setVolume(1f, 1f)
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.setVolume(0f, 0f)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onBackPressForFragment() {
    }
}