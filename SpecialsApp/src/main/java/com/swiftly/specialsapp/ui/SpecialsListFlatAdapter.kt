package com.swiftly.specialsapp.ui

import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.swiftly.specialsapp.R
import com.swiftly.specialsapp.databinding.SpecialsItemBinding
import com.swiftly.specialsapp.model.Special
import com.swiftly.specialsapp.model.SpecialsList
import com.swiftly.specialsapp.model.SpecialsSize

/**
 * A [RecyclerView.Adapter] to show the manager's specials, one per row
 */
class SpecialsListFlatAdapter(val list: SpecialsList, val displayMetrics: DisplayMetrics) :
    RecyclerView.Adapter<SpecialsListFlatAdapter.SpecialsListFlatViewHolder>() {

    private val screenWidth = displayMetrics.widthPixels

    class SpecialsListFlatViewHolder(val binding: SpecialsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SpecialsListFlatViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = SpecialsItemBinding.inflate(inflater)
        return SpecialsListFlatViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: SpecialsListFlatViewHolder, position: Int) {
        val item = getItem(position)
        createSpecialsView(viewHolder.binding, item)
    }

    fun getItem(position: Int) = list.managerSpecials!![position]!!

    override fun getItemCount() =
        if (list.managerSpecials == null)
            0
        else
            list.managerSpecials!!.size

    private fun createSpecialsView(binding: SpecialsItemBinding, item: Special) {
        // Generate a SpecialsSize that fits the full width of the display
        // but wraps the height so as to display all content
        val size = SpecialsSize(item, screenWidth, list.canvasUnit)
        size.width = screenWidth
        size.height = ViewGroup.LayoutParams.WRAP_CONTENT

        binding.item = item
        binding.sizeInfo = size

        // Add strikethrough on the text for the original price
        val originalPriceView = binding.root.findViewById(R.id.original_price) as TextView
        originalPriceView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        // Use Glide to load the image
        val imageView = binding.root.findViewById(R.id.image) as ImageView
        Glide.with(imageView.context).load(item.imageUrl).into(imageView)
    }
}

@BindingAdapter("android:layout_width")
fun setLayoutWidth(view: ViewGroup, @DimenRes width: Int) {
    val layoutParams = view.layoutParams
    layoutParams.width = width
    view.layoutParams = layoutParams
}

@BindingAdapter("android:layout_height")
fun setLayoutHeight(view: ViewGroup, @DimenRes height: Int) {
    val layoutParams = view.layoutParams
    layoutParams.height = height
    view.layoutParams = layoutParams
}
