package com.example.base_app.utils

import android.content.Context
import com.example.base_app.R

object EmojiManager {
    private lateinit var emojisList: MutableList<String>

    fun initialize(context: Context) {
        emojisList = getEmojis(context)
    }

    private fun getEmojis(context: Context): MutableList<String> {
        val emojiArray = context.resources.getStringArray(R.array.photo_editor_emoji)
        val emojis = mutableListOf<String>()
        for (emojiCode in emojiArray) {
            val codePoint = Integer.parseInt(emojiCode.substring(2), 16)
            val emoji = String(Character.toChars(codePoint))
            emojis.add(emoji)
        }
        return emojis
    }

    fun getEmojisList(): MutableList<String> {
        if (!::emojisList.isInitialized) {
            throw IllegalStateException("Emojis list is not initialized. Call initialize() first.")
        }
        return emojisList
    }
}