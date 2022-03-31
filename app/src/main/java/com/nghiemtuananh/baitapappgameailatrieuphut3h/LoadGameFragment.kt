package com.nghiemtuananh.baitapappgameailatrieuphut3h

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.nghiemtuananh.baitapappgameailatrieuphut3h.R
import com.nghiemtuananh.baitapappgameailatrieuphut3h.basefragment.BaseFragment
import com.nghiemtuananh.baitapappgameailatrieuphut3h.databinding.FragmentLoadGameBinding

class LoadGameFragment: BaseFragment() {
    var mediaPlayer: MediaPlayer? = null
    lateinit var binding: FragmentLoadGameBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoadGameBinding.inflate(inflater, container, false)
        val anim = AnimationUtils.loadAnimation(activity, R.anim.anim_rotate_load_game)
        binding.ivLoadGame.startAnimation(anim)
        val animTranslate = AnimationUtils.loadAnimation(binding.root.context, R.anim.anim_translate)
        binding.root.startAnimation(animTranslate)
        mediaPlayer = MediaPlayer.create(activity, R.raw.gofind)
        mediaPlayer!!.start()
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

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}