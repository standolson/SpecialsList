package com.swiftly.specialsapp.model

/**
 * Describes the list of manager's specials as returned by the service
 */
class SpecialsList {
    var canvasUnit: Int? = null
    var managerSpecials: List<Special?>? = null

    /**
     * Generate a list of lists which represent the indicies of the [managerSpecials]
     * for each row based on the rules of using [canvasUnit]
     */
    public fun computeRowLayout() : MutableList<MutableList<Int>>? {
        if (managerSpecials == null || canvasUnit == null)
            return null

        var rowLayout = mutableListOf<MutableList<Int>>()

        // Iterate over all of the specials and create an array for each line
        // containing the indicies of the specials on that line.
        //
        // As part of this process, throw out any special that is missing or
        // has no width
        var rowTotalWidth = 0
        var currentRow = mutableListOf<Int>()
        for (i in managerSpecials!!.indices) {
            val item = managerSpecials!![i]
            if (item == null || item.width == null)
                continue

            if (rowTotalWidth + item.width!! > canvasUnit!!) {
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

        return rowLayout
    }
}
