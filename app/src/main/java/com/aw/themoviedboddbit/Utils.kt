package com.aw.themoviedboddbit

class Utils {
    fun listToStringCommas(list: List<String>?) : String {
        if (list == null || list.isEmpty()) {
            return "-"
        }

        var retVal = list[0]
        for (i in 1 until list.size) {
            retVal = "${retVal}, ${list[i]}"
        }

        return retVal
    }
}