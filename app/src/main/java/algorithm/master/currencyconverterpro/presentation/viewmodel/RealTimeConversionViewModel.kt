package algorithm.master.currencyconverterpro.presentation.viewmodel

import algorithm.master.currencyconverterpro.domain.model.ExceptionModel
import algorithm.master.currencyconverterpro.domain.model.history.RateModel
import algorithm.master.currencyconverterpro.domain.usecase.GetRealTimeExchangeRateUseCase
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
class RealTimeConversionViewModel
@Inject constructor(private val realTimeConversionsUseCase: GetRealTimeExchangeRateUseCase) :
    ViewModel() {
    private val _realTimeConversions = MutableLiveData<List<RateModel>>()
    val realTimeConversions: LiveData<List<RateModel>>
        get() = _realTimeConversions

    private val _fetching = MutableLiveData(false)
    val fetching: LiveData<Boolean>
        get() = _fetching

    private val _error = MutableLiveData<ExceptionModel?>()
    val error: LiveData<ExceptionModel?>
        get() = _error

    fun getRealTimeConversions(
        symbols: String, base: String
    ) {
        viewModelScope.launch {
            _error.value = null
            _fetching.value = false
            realTimeConversionsUseCase.execute(
                symbols, base, _realTimeConversions::setValue, _error::setValue, _fetching::setValue
            )
        }
    }
}
