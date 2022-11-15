package algorithm.master.currencyconverterpro.presentation.viewmodel

import algorithm.master.currencyconverterpro.domain.model.ExceptionModel
import algorithm.master.currencyconverterpro.domain.usecase.GetAllAvailableCurrencyUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by promasterguru on 15/11/2022.
 */
@HiltViewModel
class AvailableCurrencyViewModel
@Inject constructor(private val availableCurrencyUseCase: GetAllAvailableCurrencyUseCase) :
    ViewModel() {
    private val _availableCurrencies = MutableLiveData<List<String>>()
    val availableCurrencies: LiveData<List<String>>
        get() = _availableCurrencies

    private val _fetching = MutableLiveData(false)
    val fetching: LiveData<Boolean>
        get() = _fetching

    private val _error = MutableLiveData<ExceptionModel?>()
    val error: LiveData<ExceptionModel?>
        get() = _error

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
