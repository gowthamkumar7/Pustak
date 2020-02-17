package com.gtm.pustak.kotlin

open class Utils {

    companion object {
        fun getTimeWithoutSeconds(createdTime: String?): String {

            if (createdTime != null) {
                var time = createdTime.split(" ").toMutableList()
                var timenew = time[2].split(":")
                time[2] = timenew[0] + ":" + timenew[1]
                var createdTime = time.get(0) + " " + time.get(1) + " " + time.get(2) + " " + time.get(3)

                return createdTime
            } else {
                return ""
            }
        }
    }


}