package algorithm.master.currencyconverterpro.domain.usecase

import algorithm.master.currencyconverterpro.domain.model.ExceptionModel
import algorithm.master.currencyconverterpro.domain.model.history.RateModel
import algorithm.master.currencyconverterpro.domain.repository.CurrencyRepository
import javax.inject.Inject

/**
 * Created by promasterguru on 15/11/2022.
 */
class GetHistoryConversionsUseCase
@Inject constructor(private val currencyRepository: CurrencyRepository) {
    suspend fun execute(
        endDate: String,
        startDate: String,
        base: String,
        symbols: String,
        onSuccess: (List<RateModel>) -> Unit,
        onFailure: (ExceptionModel) -> Unit,
        onComplete: (Boolean) -> Unit
    ) {
        currencyRepository.getLatestConversions(
            endDate, startDate, base, symbols, onSuccess, onFailure, onComplete
        )
    }
}
