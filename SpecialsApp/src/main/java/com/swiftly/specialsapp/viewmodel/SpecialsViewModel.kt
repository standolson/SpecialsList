package com.swiftly.specialsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swiftly.specialsapp.model.ScreenState
import com.swiftly.specialsapp.model.SpecialsList
import com.swiftly.specialsapp.util.ResponseData
import com.swiftly.specialsapp.util.observeOnce
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SpecialsViewModel @Inject constructor(
    private val repository: SpecialsRepository
) : ViewModel() {

    var screenState = MutableLiveData<ScreenState>()

    var specialsItems = MutableLiveData<SpecialsList>()

    var error = MutableLiveData<String>()

    public fun loadSpecials() : SpecialsViewModel {
        viewModelScope.launch {
            repository.getSpecialsList().observeOnce { onRepositoryUpdated(it) }
        }
        return this
    }

    private fun onRepositoryUpdated(response: ResponseData<SpecialsList>) {
        if (response.hasStatusMessage()) {
            error.value = response.errorStatus
            screenState.value = ScreenState.ERROR
        }
        else {
            specialsItems.value = response.data
            screenState.value = ScreenState.READY
        }
    }
}
