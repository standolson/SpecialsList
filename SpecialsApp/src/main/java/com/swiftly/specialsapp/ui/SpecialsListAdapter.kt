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

    private var rowLayout = mutableListOf<MutableList<Int>>()

    init {
        computeRowLayout()
    }

    private fun computeRowLayout() {
        rowLayout.clear()

        if (list.managerSpecials == null || list.canvasUnit == null)
            return

        // Iterate over all of the specials and create an array for each line
        // containing the indicies of the specials on that line.
        //
        // As part of this process, throw out any special that is missing or
        // has no width
        var rowTotalWidth = 0
        var currentRow = mutableListOf<Int>()
        for (i in list.managerSpecials!!.indices) {
            val item = list.managerSpecials!![i]
            if (item == null || item.width == null)
                continue

            if (rowTotalWidth + item.width!! > list.canvasUnit!!) {
                rowLayout.add(currentRow)
                currentRow = mutableListOf<Int>()
                rowTotalWidth = 0
            }
            rowTotalWidth += item.width!!
            currentRow.add(i)
        }

        // If there are any items left over, add them as a line now
        if (currentRow.size > 0)
            rowLayout.add(currentRow)
    }

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