package algorithm.master.currencyconverterpro.presentation.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by promasterguru on 15/11/2022.
 */
fun getDate(now: Long): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(now)
}

fun getPastDate(daysAgo: Int): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
    return getDate(calendar.time.time)
}

fun parseDate(strDate: String): Date? {
    return try {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(strDate)
    } catch (ex: Exception) {
        null
    }
}
