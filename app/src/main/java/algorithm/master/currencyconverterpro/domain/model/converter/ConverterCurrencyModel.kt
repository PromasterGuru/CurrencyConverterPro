package algorithm.master.currencyconverterpro.domain.model.converter

/**
 * Created by promasterguru on 15/11/2022.
 */
data class ConverterCurrencyModel(
    val amount: Float,
    val from: String,
    val to: String,
    val rate: Float,
    val date: String,
    val result: Float
)
