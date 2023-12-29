package com.example.base_app.ui.frags.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.base_app.data.model.GameModel
import com.example.base_app.databinding.ItemGameBinding
import org.apache.commons.lang3.StringEscapeUtils
import java.nio.charset.Charset

class GameAdapter(private val items: List<GameModel>) : Adapter<GameAdapter.GameViewHolder>() {


    class GameViewHolder(private val binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binds(item: GameModel) {
            Glide.with(binding.gameImage)
                .load(item.images)
                .into(binding.gameImage)
//            Log.d("namnamnam 1",   .unescapeJava(item.description))
            binding.gameNameTextView.text = String(item.name.toByteArray(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"))
            binding.gameDescriptionTextView.text =  String(item.description.toByteArray(Charset.forName("ISO-8859-1")), Charset.forName("UTF-8"))
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