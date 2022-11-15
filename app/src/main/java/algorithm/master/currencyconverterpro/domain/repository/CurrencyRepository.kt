package algorithm.master.currencyconverterpro.domain.repository

import algorithm.master.currencyconverterpro.domain.model.ExceptionModel
import algorithm.master.currencyconverterpro.domain.model.converter.ConverterCurrencyModel
import algorithm.master.currencyconverterpro.domain.model.history.RateModel
import java.util.*

/**
 * Created by promasterguru on 15/11/2022.
 */
interface CurrencyRepository {
    suspend fun convertCurrency(
        to: String,
        from: String,
        amount: Double,
        data: String,
        onSuccess: (ConverterCurrencyModel) -> Unit,
        onFailure: (ExceptionModel) -> Unit,
        onComplete: (Boolean) -> Unit
    )

    suspend fun getAllAvailableCurrencies(
        onSuccess: (List<String>) -> Unit,
        onFailure: (ExceptionModel) -> Unit,
        onComplete: (Boolean) -> Unit
    )

    suspend fun getRealTimeExchangeRate(
        symbols: String,
        base: String,
        onSuccess: (List<RateModel>) -> Unit,
        onFailure: (ExceptionModel) -> Unit,
        onComplete: (Boolean) -> Unit
    )

    suspend fun getLatestConversions(
        endDate: String,
        startDate: String,
        base: String,
        symbols: String,
        onSuccess: (List<RateModel>) -> Unit,
        onFailure: (ExceptionModel) -> Unit,
        onComplete: (Boolean) -> Unit
    )
}
