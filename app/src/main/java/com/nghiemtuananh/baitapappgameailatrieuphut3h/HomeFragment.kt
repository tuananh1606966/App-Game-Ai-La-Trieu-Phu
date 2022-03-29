package com.nghiemtuananh.baitapappgameailatrieuphut3h

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.nghiemtuananh.baitapappgameailatrieuphut3h.basefragment.BaseFragment
import com.nghiemtuananh.baitapappgameailatrieuphut3h.databinding.FragmentHomeBinding
import com.nghiemtuananh.baitapappgameailatrieuphut3h.interfacee.IActivityAndHomeFragment

class HomeFragment: BaseFragment(), View.OnClickListener {
    lateinit var binding: FragmentHomeBinding
    lateinit var inter: IActivityAndHomeFragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val anim = AnimationUtils.loadAnimation(container!!.context, R.anim.anim_rotate)
        binding.ivBgCircleAnim.startAnimation(anim)
        binding.ibtnPalyNonPress.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(p0: View?) {
        inter = activity as IActivityAndHomeFragment
        when (p0) {
            binding.ibtnPalyNonPress -> {
                inter.onClickNowPlay()
                binding.ibtnPalyPress.isVisible = true
            }
        }
    }
}