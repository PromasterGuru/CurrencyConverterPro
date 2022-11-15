package algorithm.master.currencyconverterpro.domain.usecase

import algorithm.master.currencyconverterpro.domain.model.Exception
import algorithm.master.currencyconverterpro.domain.repository.CurrencyRepository

/**
 * Created by promasterguru on 15/11/2022.
 */
class GetAllAvailableCurrencyUseCase(private val currencyRepository: CurrencyRepository) {
    suspend fun execute(onSuccess: (List<String>) -> Unit, onFailure: (Exception) -> Unit) {
        currencyRepository.getAllAvailableCurrencies(onSuccess, onFailure)
    }
}
