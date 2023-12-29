package com.example.base_app.ui.intro.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.base_app.databinding.ItemIntroActivityBinding
import com.example.base_app.data.model.IntroModel

class IntroductionAdapter: Adapter<IntroductionAdapter.IntroductionViewHolder>() {
    private val items = ArrayList<IntroModel>()

    fun applyData(items: ArrayList<IntroModel>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class IntroductionViewHolder(private val binding: ItemIntroActivityBinding): ViewHolder(binding.root){
        fun binds(item: IntroModel){
            binding.titleImageIntro.text = item.title
            binding.contentImgIntro.text = item.content
            binding.imageViewCarouselItem.setImageResource(item.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroductionViewHolder {
        return IntroductionViewHolder(ItemIntroActivityBinding.inflate( LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: IntroductionViewHolder, position: Int) {
        holder.binds(items[position])
    }

}