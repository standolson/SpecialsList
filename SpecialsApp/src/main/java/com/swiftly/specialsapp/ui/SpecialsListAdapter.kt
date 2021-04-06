package com.swiftly.specialsapp.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.swiftly.specialsapp.R
import com.swiftly.specialsapp.databinding.SpecialsItemBinding
import com.swiftly.specialsapp.model.Special
import com.swiftly.specialsapp.model.SpecialsList

class SpecialsListAdapter(val list: SpecialsList) : RecyclerView.Adapter<SpecialsListAdapter.SpecialsListViewHolder>() {

    class SpecialsListViewHolder(val binding: SpecialsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SpecialsListViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = SpecialsItemBinding.inflate(inflater)
        return SpecialsListViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: SpecialsListViewHolder, position: Int) {
        val item = getItem(position)
        viewHolder.binding.item = item

        // Add strikethrough on the text for the original price
        val originalPriceView = viewHolder.itemView.findViewById(R.id.original_price) as TextView
        originalPriceView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        // Use Glide to load the image
        val imageView = viewHolder.itemView.findViewById(R.id.image) as ImageView
        Glide.with(imageView.context).load(item.imageUrl).into(imageView)
    }

    fun getItem(position: Int) : Special = list.managerSpecials!![position]!!

    override fun getItemCount(): Int =
        if (list.managerSpecials == null)
            0
        else
            list.managerSpecials!!.size
}