package algorithm.master.currencyconverterpro.domain.usecase

import algorithm.master.currencyconverterpro.domain.model.ExceptionModel
import algorithm.master.currencyconverterpro.domain.repository.CurrencyRepository
import javax.inject.Inject

/**
 * Created by promasterguru on 15/11/2022.
 */
class GetAllAvailableCurrencyUseCase
@Inject constructor(private val currencyRepository: CurrencyRepository) {
    suspend fun execute(
        onSuccess: (List<String>) -> Unit,
        onFailure: (ExceptionModel) -> Unit,
        onComplete: (Boolean) -> Unit
    ) {
        currencyRepository.getAllAvailableCurrencies(onSuccess, onFailure, onComplete)
    }
}
