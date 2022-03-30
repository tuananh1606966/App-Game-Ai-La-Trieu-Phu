package com.nghiemtuananh.baitapappgameailatrieuphut3h

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.nghiemtuananh.baitapappgameailatrieuphut3h.R
import com.nghiemtuananh.baitapappgameailatrieuphut3h.basefragment.BaseFragment
import com.nghiemtuananh.baitapappgameailatrieuphut3h.databinding.FragmentLoadGameBinding

class LoadGameFragment: BaseFragment() {
    lateinit var binding: FragmentLoadGameBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoadGameBinding.inflate(inflater, container, false)
        val anim = AnimationUtils.loadAnimation(activity, R.anim.anim_rotate_load_game)
        binding.ivLoadGame.startAnimation(anim)
        return binding.root
    }
}