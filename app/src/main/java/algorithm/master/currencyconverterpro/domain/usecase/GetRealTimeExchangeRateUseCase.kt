package algorithm.master.currencyconverterpro.domain.usecase

import algorithm.master.currencyconverterpro.domain.model.ExceptionModel
import algorithm.master.currencyconverterpro.domain.model.history.RateModel
import algorithm.master.currencyconverterpro.domain.repository.CurrencyRepository
import javax.inject.Inject

/**
 * Created by promasterguru on 15/11/2022.
 */
class GetRealTimeExchangeRateUseCase
@Inject constructor(private val currencyRepository: CurrencyRepository) {
    suspend fun execute(
        symbols: String,
        base: String,
        onSuccess: (List<RateModel>) -> Unit,
        onFailure: (ExceptionModel) -> Unit,
        onComplete: (Boolean) -> Unit,
    ) {
        currencyRepository.getRealTimeExchangeRate(symbols, base, onSuccess, onFailure, onComplete)
    }
}
