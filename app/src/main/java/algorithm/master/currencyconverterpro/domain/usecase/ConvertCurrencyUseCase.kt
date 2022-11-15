package algorithm.master.currencyconverterpro.domain.usecase

import algorithm.master.currencyconverterpro.domain.model.ExceptionModel
import algorithm.master.currencyconverterpro.domain.model.converter.ConverterCurrencyModel
import algorithm.master.currencyconverterpro.domain.repository.CurrencyRepository
import javax.inject.Inject

/**
 * Created by promasterguru on 15/11/2022.
 */
class ConvertCurrencyUseCase
@Inject constructor(private val currencyRepository: CurrencyRepository) {
    suspend fun execute(
        to: String,
        from: String,
        amount: Float,
        date: String,
        onSuccess: (ConverterCurrencyModel) -> Unit,
        onFailure: (ExceptionModel) -> Unit,
        onComplete: (Boolean) -> Unit
    ) {
        currencyRepository.convertCurrency(to, from, amount, date, onSuccess, onFailure, onComplete)
    }
}
