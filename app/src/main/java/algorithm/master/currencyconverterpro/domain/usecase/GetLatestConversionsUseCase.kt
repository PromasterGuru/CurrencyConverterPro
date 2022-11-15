package algorithm.master.currencyconverterpro.domain.usecase

import algorithm.master.currencyconverterpro.domain.model.ExceptionModel
import algorithm.master.currencyconverterpro.domain.model.history.RateModel
import algorithm.master.currencyconverterpro.domain.repository.CurrencyRepository
import java.util.*

/**
 * Created by promasterguru on 15/11/2022.
 */
class GetLatestConversionsUseCase(private val currencyRepository: CurrencyRepository) {
    suspend fun execute(
        endDate: Date,
        startDate: Date,
        base: String,
        symbols: String,
        onSuccess: (List<RateModel>) -> Unit,
        onFailure: (ExceptionModel) -> Unit
    ) {
        currencyRepository.getLatestConversions(
            endDate, startDate, base, symbols, onSuccess, onFailure
        )
    }
}
