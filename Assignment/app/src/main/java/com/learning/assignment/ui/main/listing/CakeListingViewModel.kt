package com.learning.assignment.ui.main.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.learning.domain.interactor.GetAllCakesUseCase
import com.learning.domain.model.Cakes
import com.learning.domain.model.OrderType
import com.learning.domain.model.Result
import com.learning.assignment.ui.main.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CakeListingViewModel @Inject constructor(
    private val getAllCakesUseCase: GetAllCakesUseCase
) : BaseViewModel() {

    private var sort: OrderType = OrderType.Ascending

    private val _cakes: MutableLiveData<List<Cakes>> by lazy {
        MutableLiveData<List<Cakes>>()
    }
    private val _error: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val cakes: LiveData<List<Cakes>> get() = _cakes
    val error: LiveData<String> get() = _error
    fun loadData() {
        viewModelScope.launch {
            showLoader()
            val result = getAllCakesUseCase.execute(GetAllCakesUseCase.Params(sort))
            hideLoader()
            when (result) {
                is Result.Success<List<Cakes>> -> {
                    val allAds = result.data
                    if (allAds.isNotEmpty()) {
                        _cakes.postValue(allAds)
                    }
                }
                is Result.Error -> {
                    result.exception.message?.let {
                        _error.postValue(it)
                        showToast(it)
                    }
                }
            }
        }
    }

    fun sort() {
        sort = when (sort) {
            OrderType.Ascending -> {
                OrderType.Descending
            }
            OrderType.Descending -> {
                OrderType.Ascending
            }
        }
        val currentList = _cakes.value!!
        val sortedList = when (sort) {
            OrderType.Ascending -> currentList.sortedBy { it.title }
            OrderType.Descending -> currentList.sortedByDescending { it.title }
        }
        _cakes.postValue(sortedList)
    }

}