package com.example.base_app.ui.language.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.base_app.R
import com.example.base_app.data.model.Language
import com.example.base_app.databinding.ItemLanguageBinding
import com.example.base_app.extension.onClickListener

class LanguageAdapter: Adapter<LanguageAdapter.LanguageViewHolder>(){
    private val languages = ArrayList<Language>()
    private var posSelected = 0
    private var onEventLanguageListener: IOnEventLanguageListener? = null

    fun applyData(languages: ArrayList<Language>){
        this.languages.clear()
        this.languages.addAll(languages)
        notifyDataSetChanged()
    }

    fun setPosSelected(pos: Int){
        posSelected = pos
        notifyDataSetChanged()
    }

    fun applyEvent(onEventLanguageListener: IOnEventLanguageListener){
        this.onEventLanguageListener = onEventLanguageListener
    }

    inner class LanguageViewHolder(private val binding: ItemLanguageBinding): ViewHolder(binding.root){
        fun binds(language: Language, isSelected: Boolean, onItemSelected:(Language)->Unit){
            if (isSelected){
                binding.imgChecked.setImageResource(R.drawable.ic_lang_checked)
                binding.viewContainer.setBackgroundResource(R.drawable.bg_language_selected)
            }else{
                binding.viewContainer.setBackgroundResource(R.drawable.bg_language_normal)
                binding.imgChecked.setImageResource(R.drawable.ic_lang_not_checked)
            }
            binding.imgFlag.setImageResource(language.resId)
            binding.tvLang.text = language.name
            binding.viewContainer.onClickListener {
                onItemSelected(language)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        return LanguageViewHolder(ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = languages.size

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.binds(languages[position], posSelected == position){
            onEventLanguageListener?.onClickLanguage(it)
            posSelected = holder.adapterPosition
            notifyDataSetChanged()
        }
    }

    interface IOnEventLanguageListener{
        fun onClickLanguage(language: Language)
    }
}