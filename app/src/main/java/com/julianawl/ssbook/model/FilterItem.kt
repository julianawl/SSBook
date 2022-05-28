package com.julianawl.ssbook.model

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import com.google.android.material.chip.Chip
import com.julianawl.ssbook.R

data class FilterItem(
    val genre: String
)

fun FilterItem.toChip(context: Context): Chip {
    val chip = LayoutInflater.from(context).inflate(R.layout.chip_choice, null, false) as Chip
    chip.setChipStrokeColorResource(android.R.color.darker_gray)
    chip.chipStrokeWidth = 1f
    chip.text = genre
    return chip
}
