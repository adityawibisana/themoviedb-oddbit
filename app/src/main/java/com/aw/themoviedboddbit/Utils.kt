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

        return retVal.trim()
    }

    fun minutesToHourMinute(minute: Int) : String {
        val hours = minute / 60
        val remainingMinute = minute - (60 * hours)

        val h = when (hours) {
            0 -> ""
            1 -> "1 hour"
            else -> {
                "$hours hours"
            }
        }

        val m = when (remainingMinute) {
            0 -> ""
            1 -> "1 minute"
            else -> {
                "$remainingMinute minutes"
            }
        }
        return "$h $m".trim()
    }
}