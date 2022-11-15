package algorithm.master.currencyconverterpro.domain.usecase

import algorithm.master.currencyconverterpro.domain.model.Exception
import algorithm.master.currencyconverterpro.domain.model.history.Rate
import algorithm.master.currencyconverterpro.domain.repository.CurrencyRepository

/**
 * Created by promasterguru on 15/11/2022.
 */
class GetRealTimeExchangeRateUseCase(private val currencyRepository: CurrencyRepository) {
    suspend fun execute(
        symbols: String,
        base: String,
        onSuccess: (List<Rate>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        currencyRepository.getRealTimeExchangeRate(symbols, base, onSuccess, onFailure)
    }
}
