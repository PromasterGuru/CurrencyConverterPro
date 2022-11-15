package algorithm.master.currencyconverterpro.presentation.viewmodel

import algorithm.master.currencyconverterpro.domain.model.history.RateModel
import algorithm.master.currencyconverterpro.domain.usecase.GetRealTimeExchangeRateUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by promasterguru on 15/11/2022.
 */
class RealTimeConversionViewModel
@Inject constructor(private val realTimeConversionsUseCase: GetRealTimeExchangeRateUseCase) :
    BaseViewModel() {
    private val _realTimeConversions = MutableLiveData<List<RateModel>>()
    val realTimeConversions: LiveData<List<RateModel>>
        get() = _realTimeConversions

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
