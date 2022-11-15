package algorithm.master.currencyconverterpro.domain.model.converter

/**
 * Created by promasterguru on 15/11/2022.
 */
data class ConverterCurrencyModel(
    val amount: Double,
    val from: String,
    val to: String,
    val rate: String,
    val date: String,
    val result: Double
)
