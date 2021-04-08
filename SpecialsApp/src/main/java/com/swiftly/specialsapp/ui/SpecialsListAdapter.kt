package com.swiftly.specialsapp.ui

import android.graphics.Paint
import android.util.DisplayMetrics
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
import com.swiftly.specialsapp.model.SpecialsSize

/**
 * A [RecyclerView.Adapter] to show the manager's specials according to the layout rules
 */
class SpecialsListAdapter(val list: SpecialsList, val displayMetrics: DisplayMetrics) :
    RecyclerView.Adapter<SpecialsListAdapter.SpecialsListViewHolder>() {

    private val rowLayout = list.computeRowLayout()
    private val screenWidth = displayMetrics.widthPixels

    class SpecialsListViewHolder(val viewGroup : ViewGroup) : RecyclerView.ViewHolder(viewGroup)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SpecialsListAdapter.SpecialsListViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val layout = inflater.inflate(R.layout.specials_row, viewGroup, false) as ViewGroup
        return SpecialsListViewHolder(layout)
    }

    override fun onBindViewHolder(viewHolder: SpecialsListViewHolder, position: Int) {
        val row = getItem(position)
        createSpecialsRow(row, viewHolder.viewGroup)
    }

    private fun createSpecialsRow(row: MutableList<Int>, viewGroup: ViewGroup) {
        if (row.isEmpty())
            return

        val inflater = LayoutInflater.from(viewGroup.context)

        // Remove all of the view as we are recycling
        viewGroup.removeAllViews()

        // Iterate over the row, inflating a data binding, and then add the view
        // to the ViewGroup
        for (i in row.indices) {
            val item = list.managerSpecials!![row[i]]
            val binding = SpecialsItemBinding.inflate(inflater)
            createSpecialsView(binding, item!!)
            viewGroup.addView(binding.root)
        }
    }

    fun getItem(position: Int) = rowLayout!![position]

    override fun getItemCount() =
        if (rowLayout == null)
            0
        else
            rowLayout.size

    override fun getItemViewType(position: Int) = position

    private fun createSpecialsView(binding: SpecialsItemBinding, item: Special) {
        // Perform data binding of the item and the size of the item
        binding.item = item
        binding.sizeInfo = SpecialsSize(item, screenWidth, list.canvasUnit)

        // Add strikethrough on the text for the original price
        val originalPriceView = binding.root.findViewById(R.id.original_price) as TextView
        originalPriceView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        // Use Glide to load the image
        val imageView = binding.root.findViewById(R.id.image) as ImageView
        Glide.with(imageView.context).load(item.imageUrl).into(imageView)
    }
}