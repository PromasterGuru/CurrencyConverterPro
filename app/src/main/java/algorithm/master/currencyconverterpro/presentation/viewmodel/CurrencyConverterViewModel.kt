package algorithm.master.currencyconverterpro.presentation.viewmodel

import algorithm.master.currencyconverterpro.domain.model.ExceptionModel
import algorithm.master.currencyconverterpro.domain.model.converter.ConverterCurrencyModel
import algorithm.master.currencyconverterpro.domain.usecase.ConvertCurrencyUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Created by promasterguru on 15/11/2022.
 */
class CurrencyConverterViewModel
    (private val converterUserCase: ConvertCurrencyUseCase) : ViewModel() {
    private val _currencyModel = MutableLiveData<ConverterCurrencyModel>()
    val currencyModel: LiveData<ConverterCurrencyModel>
        get() = _currencyModel

    private val _fetching = MutableLiveData(false)
    val fetching: LiveData<Boolean>
        get() = _fetching

    private val _error = MutableLiveData<ExceptionModel?>()
    val error: LiveData<ExceptionModel?>
        get() = _error

    fun convertCurrency(
        to: String, from: String, amount: Double, date: String
    ) {
        viewModelScope.launch {
            _error.value = null
            _fetching.value = true
            converterUserCase.execute(
                to,
                from,
                amount,
                date,
                _currencyModel::setValue,
                _error::setValue,
                _fetching::setValue
            )
        }
    }
}
