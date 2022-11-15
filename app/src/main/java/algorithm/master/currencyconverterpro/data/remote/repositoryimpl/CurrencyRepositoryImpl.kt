package algorithm.master.currencyconverterpro.data.remote.repositoryimpl

import algorithm.master.currencyconverterpro.data.remote.api.CurrencyService
import algorithm.master.currencyconverterpro.data.util.ResponseWrapper
import algorithm.master.currencyconverterpro.data.util.resolve
import algorithm.master.currencyconverterpro.data.util.safeApiCall
import algorithm.master.currencyconverterpro.domain.model.ExceptionModel
import algorithm.master.currencyconverterpro.domain.model.converter.ConverterCurrencyModel
import algorithm.master.currencyconverterpro.domain.model.history.RateModel
import algorithm.master.currencyconverterpro.domain.repository.CurrencyRepository

/**
 * Created by promasterguru on 15/11/2022.
 */
class CurrencyRepositoryImpl(private val currencyService: CurrencyService) : CurrencyRepository {
    override suspend fun convertCurrency(
        to: String,
        from: String,
        amount: Double,
        data: String,
        onSuccess: (ConverterCurrencyModel) -> Unit,
        onFailure: (ExceptionModel) -> Unit
    ) {
        when (val response =
            safeApiCall { currencyService.convertCurrency(to, from, amount, data) }) {
            is ResponseWrapper.Success -> {
                onSuccess(response.data.toCurrencyModel())
            }
            is ResponseWrapper.Failure -> onFailure(response.error.resolve())
        }
    }

    override suspend fun getAllAvailableCurrencies(
        onSuccess: (List<String>) -> Unit, onFailure: (ExceptionModel) -> Unit
    ) {
        when (val response = safeApiCall { currencyService.getAllAvailableCurrencies() }) {
            is ResponseWrapper.Success -> {
                response.data.toCurrencySymbols()
            }
            is ResponseWrapper.Failure -> onFailure(response.error.resolve())
        }
    }

    override suspend fun getRealTimeExchangeRate(
        symbols: String,
        base: String,
        onSuccess: (List<RateModel>) -> Unit,
        onFailure: (ExceptionModel) -> Unit
    ) {
        when (val response =
            safeApiCall { currencyService.getRealTimeExchangeRate(symbols, base) }) {
            is ResponseWrapper.Success -> {
                response.data.toRates()
            }
            is ResponseWrapper.Failure -> onFailure(response.error.resolve())
        }
    }

    override suspend fun getLatestConversions(
        endDate: String,
        startDate: String,
        base: String,
        symbols: String,
        onSuccess: (List<RateModel>) -> Unit,
        onFailure: (ExceptionModel) -> Unit
    ) {
        when (val response =
            safeApiCall {
                currencyService.getLatestConversions(
                    endDate,
                    startDate,
                    base,
                    symbols
                )
            }) {
            is ResponseWrapper.Success -> {
                response.data.toRates()
            }
            is ResponseWrapper.Failure -> onFailure(response.error.resolve())
        }
    }
}
