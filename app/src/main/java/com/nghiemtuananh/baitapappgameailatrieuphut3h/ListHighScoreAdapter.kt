package com.nghiemtuananh.baitapappgameailatrieuphut3h

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nghiemtuananh.baitapappgameailatrieuphut3h.databinding.ItemHighScoreBinding
import com.nghiemtuananh.baitapappgameailatrieuphut3h.interfacee.IAdapterHighScore

class ListHighScoreAdapter(var inter: IAdapterHighScore): RecyclerView.Adapter<ListHighScoreAdapter.ListHighScoreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHighScoreViewHolder {
        val binding = ItemHighScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListHighScoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListHighScoreViewHolder, position: Int) {
        val highScore = inter.getItem(position)
        holder.binding.data = highScore
    }

    override fun getItemCount(): Int {
        return inter.getCount()
    }

    inner class ListHighScoreViewHolder(var binding: ItemHighScoreBinding): RecyclerView.ViewHolder(binding.root)
}