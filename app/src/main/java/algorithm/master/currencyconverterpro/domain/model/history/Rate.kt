package algorithm.master.currencyconverterpro.domain.model.history

import java.util.*

/**
 * Created by promasterguru on 15/11/2022.
 */
data class Rate(
    val base: String, val to: String, val amount: Double, val date: Date
)
