package com.example.base_app.ui.frags.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.base_app.data.model.GameModel
import com.example.base_app.databinding.ItemGameBinding

class GameAdapter(private val items: List<GameModel>) : Adapter<GameAdapter.GameViewHolder>() {


    class GameViewHolder(private val binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binds(item: GameModel) {
            Log.d("namnamnam 1", item.name)
            binding.gameNameTextView.text = item.name
            binding.gameDescriptionTextView.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            ItemGameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.binds(items[position])
    }

}