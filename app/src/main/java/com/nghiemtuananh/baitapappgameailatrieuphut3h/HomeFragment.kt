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
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nghiemtuananh.baitapappgameailatrieuphut3h.basefragment.BaseFragment
import com.nghiemtuananh.baitapappgameailatrieuphut3h.databinding.FragmentHomeBinding
import com.nghiemtuananh.baitapappgameailatrieuphut3h.interfacee.IActivityAndHomeFragment
import com.nghiemtuananh.baitapappgameailatrieuphut3h.interfacee.IAdapterHighScore
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeFragment : BaseFragment(), View.OnClickListener, IAdapterHighScore {
    var listHighScore: ArrayList<HighScore> = arrayListOf()
    var mediaPlayerBG: MediaPlayer? = null
    var mediaClick: MediaPlayer? = null
    lateinit var binding: FragmentHomeBinding
    lateinit var inter: IActivityAndHomeFragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        inter = activity as IActivityAndHomeFragment
        val anim = AnimationUtils.loadAnimation(container!!.context, R.anim.anim_rotate)
        binding.ivBgCircleAnim.startAnimation(anim)
        binding.ibtnPalyNonPress.setOnClickListener(this)
        binding.ibtnHightScoreNonPress.setOnClickListener(this)
        mediaClick = MediaPlayer.create(activity, R.raw.touch_sound)
        mediaPlayerBG = MediaPlayer.create(activity, R.raw.bgmusic)
        mediaPlayerBG!!.isLooping = true
        mediaPlayerBG!!.start()
//        listHighScore = inter.getListHighScore()
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onClick(p0: View?) {
        when (p0) {
            binding.ibtnPalyNonPress -> {
                mediaClick!!.start()
                inter.onClickNowPlay()
                binding.ibtnPalyPress.isVisible = true
            }
            binding.ibtnHightScoreNonPress -> {
                listHighScore = inter.getListHighScore()
                mediaClick!!.start()
                binding.ibtnHightScorePress.isVisible = true
                Observable.create<Void> {
                    SystemClock.sleep(500)
                    it.onComplete()
                }.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {},
                        {},
                        {
                            binding.ibtnHightScorePress.isVisible = false
                        }
                    )
                showDialogHighScore()
            }
        }
    }

    private fun showDialogHighScore() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_high_score)
        val rcv = dialog.findViewById<RecyclerView>(R.id.rcv_list_high_score)
        val btnOk = dialog.findViewById<Button>(R.id.btn_ok_high_score)
        rcv.adapter = ListHighScoreAdapter(this)
        rcv.layoutManager = LinearLayoutManager(requireContext())
        rcv.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        btnOk.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun getCount(): Int {
        return listHighScore.size
    }

    override fun getItem(position: Int): HighScore {
        return listHighScore[position]
    }

    override fun onStart() {
        super.onStart()
        mediaPlayerBG?.setVolume(1f, 1f)
    }

    override fun onStop() {
        super.onStop()
        mediaPlayerBG?.setVolume(0f, 0f)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaClick?.release()
        mediaClick = null
        mediaPlayerBG?.release()
        mediaPlayerBG = null
    }
}