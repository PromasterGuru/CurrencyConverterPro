package algorithm.master.currencyconverterpro.presentation.viewmodel

import algorithm.master.currencyconverterpro.domain.usecase.GetAllAvailableCurrencyUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by promasterguru on 15/11/2022.
 */
class AvailableCurrencyViewModel
@Inject constructor(private val availableCurrencyUseCase: GetAllAvailableCurrencyUseCase) :
    BaseViewModel() {
    private val _availableCurrencies = MutableLiveData<List<String>>()
    val availableCurrencies: LiveData<List<String>>
        get() = _availableCurrencies

    fun getAvailableCurrencies() {
        viewModelScope.launch {
            _error.value = null
            _fetching.value = true
            availableCurrencyUseCase.execute(
                _availableCurrencies::setValue, _error::setValue, _fetching::setValue
            )
        }
    }
}
