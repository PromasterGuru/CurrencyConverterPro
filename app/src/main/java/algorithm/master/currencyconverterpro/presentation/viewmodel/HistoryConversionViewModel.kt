package algorithm.master.currencyconverterpro.presentation.viewmodel

import algorithm.master.currencyconverterpro.domain.model.ExceptionModel
import algorithm.master.currencyconverterpro.domain.model.history.RateModel
import algorithm.master.currencyconverterpro.domain.usecase.GetHistoryConversionsUseCase
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
class HistoryConversionViewModel
@Inject constructor(private val historyConversionsUseCase: GetHistoryConversionsUseCase) :
    ViewModel() {
    private val _historyConversions = MutableLiveData<List<RateModel>>()
    val historyConversions: LiveData<List<RateModel>>
        get() = _historyConversions

    private val _fetching = MutableLiveData(false)
    val fetching: LiveData<Boolean>
        get() = _fetching

    private val _error = MutableLiveData<ExceptionModel?>()
    val error: LiveData<ExceptionModel?>
        get() = _error

    fun getHistoryRateConversions(
        startDate: String,
        endDate: String,
        base: String,
        symbols: String,
    ) {
        viewModelScope.launch {
            _error.value = null
            _fetching.value = false
            historyConversionsUseCase.execute(
                startDate,
                endDate,
                base,
                symbols,
                _historyConversions::setValue,
                _error::setValue,
                _fetching::setValue
            )
        }
    }
}
