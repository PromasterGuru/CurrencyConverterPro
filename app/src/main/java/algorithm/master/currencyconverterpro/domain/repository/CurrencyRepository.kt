package algorithm.master.currencyconverterpro.domain.repository

import algorithm.master.currencyconverterpro.domain.model.Exception
import algorithm.master.currencyconverterpro.domain.model.converter.ConverterCurrencyModel
import algorithm.master.currencyconverterpro.domain.model.history.Rate
import java.util.*

/**
 * Created by promasterguru on 15/11/2022.
 */
interface CurrencyRepository {
    suspend fun convertCurrency(
        to: String,
        from: String,
        amount: Int,
        data: Date,
        onSuccess: (ConverterCurrencyModel) -> Unit,
        onFailure: (Exception) -> Unit
    )

    suspend fun getAllAvailableCurrencies(
        onSuccess: (List<String>) -> Unit, onFailure: (Exception) -> Unit
    )

    suspend fun getRealTimeExchangeRate(
        symbols: String,
        base: String,
        onSuccess: (List<Rate>) -> Unit,
        onFailure: (Exception) -> Unit
    )

    suspend fun getLatestConversions(
        endDate: Date,
        startDate: Date,
        base: String,
        symbols: String,
        onSuccess: (List<Rate>) -> Unit,
        onFailure: (Exception) -> Unit
    )
}
