package algorithm.master.currencyconverterpro.domain.usecase

import algorithm.master.currencyconverterpro.domain.model.ExceptionModel
import algorithm.master.currencyconverterpro.domain.model.converter.ConverterCurrencyModel
import algorithm.master.currencyconverterpro.domain.repository.CurrencyRepository
import java.util.*

/**
 * Created by promasterguru on 15/11/2022.
 */
class ConvertCurrencyUseCase(private val currencyRepository: CurrencyRepository) {
    suspend fun execute(
        to: String,
        from: String,
        amount: Double,
        date: String,
        onSuccess: (ConverterCurrencyModel) -> Unit,
        onFailure: (ExceptionModel) -> Unit,
        onComplete: (Boolean) -> Unit
    ) {
        currencyRepository.convertCurrency(to, from, amount, date, onSuccess, onFailure, onComplete)
    }
}
