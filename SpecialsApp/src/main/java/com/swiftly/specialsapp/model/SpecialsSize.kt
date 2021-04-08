package com.swiftly.specialsapp.model

import android.util.DisplayMetrics
import android.view.ViewGroup
import androidx.annotation.DimenRes

class SpecialsSize(item: Special, screenWidth: Int, canvasUnit: Int?) {
    @DimenRes var width: Int = ViewGroup.LayoutParams.MATCH_PARENT
    @DimenRes var height: Int = ViewGroup.LayoutParams.WRAP_CONTENT

    init {
        var pixelsPerCanvasUnit: Int = 0

        // Compute pixels per canvas unit.  Note that we may not have gotten
        // the DisplayMetrics or the service didn't return a valid canvasUnit.
        if (screenWidth != 0 && canvasUnit != null && canvasUnit != 0)
            pixelsPerCanvasUnit = screenWidth / canvasUnit

        // Compute width and height in pixel for this special.  If we had some
        // missing or invalid canvas unit data, we'll stick with the defaults
        // provided by SpecialsSize.
        if (item.width != null && pixelsPerCanvasUnit != 0)
            width = pixelsPerCanvasUnit * item.width!!
        if (item.height != null && pixelsPerCanvasUnit != 0)
            height = pixelsPerCanvasUnit * item.height!!
    }
}