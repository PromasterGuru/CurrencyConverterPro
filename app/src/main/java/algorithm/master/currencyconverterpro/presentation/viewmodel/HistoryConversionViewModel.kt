package algorithm.master.currencyconverterpro.presentation.viewmodel

import algorithm.master.currencyconverterpro.domain.model.history.RateModel
import algorithm.master.currencyconverterpro.domain.usecase.GetHistoryConversionsUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Created by promasterguru on 15/11/2022.
 */
class HistoryConversionViewModel
    (private val historyConversionsUseCase: GetHistoryConversionsUseCase) : BaseViewModel() {
    private val _historyConversions = MutableLiveData<List<RateModel>>()
    val historyConversions: LiveData<List<RateModel>>
        get() = _historyConversions

    fun getRealTimeConversions(
        endDate: String,
        startDate: String,
        base: String,
        symbols: String,
    ) {
        viewModelScope.launch {
            _error.value = null
            _fetching.value = false
            historyConversionsUseCase.execute(
                endDate,
                startDate,
                base,
                symbols,
                _historyConversions::setValue,
                _error::setValue,
                _fetching::setValue
            )
        }
    }
}
