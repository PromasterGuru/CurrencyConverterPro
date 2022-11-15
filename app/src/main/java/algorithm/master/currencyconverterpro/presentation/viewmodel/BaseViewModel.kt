package algorithm.master.currencyconverterpro.presentation.viewmodel

import algorithm.master.currencyconverterpro.domain.model.ExceptionModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by promasterguru on 15/11/2022.
 */
open class BaseViewModel : ViewModel() {

    val _fetching = MutableLiveData(false)
    val fetching: LiveData<Boolean>
        get() = _fetching

    val _error = MutableLiveData<ExceptionModel?>()
    val error: LiveData<ExceptionModel?>
        get() = _error

}
