package com.swiftly.specialsapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swiftly.specialsapp.model.SpecialsList
import com.swiftly.specialsapp.util.ResponseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SpecialsViewModel @Inject constructor(
    private val repository: SpecialsRepository
) : ViewModel() {

    public fun loadSpecials() : SpecialsViewModel {
        viewModelScope.launch {
            repository.getSpecialsList().observeOnce { onRepositoryUpdated(it) }
        }
        return this
    }

    private fun onRepositoryUpdated(response: ResponseData<SpecialsList>) {
        if (response.hasStatusMessage())
            Log.e("TAG", "onRepositoryUpdated: status message = '" + response.resultStatus + "'")
        else {
            val list = response.data
            Log.e("TAG", "onRepositoryUpdated: data = " + list + "'")
        }
    }
}

fun <T> LiveData<T>.observeOnce(observer: (T) -> Unit) {
    observeForever(object: Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer(value)
        }
    })
}