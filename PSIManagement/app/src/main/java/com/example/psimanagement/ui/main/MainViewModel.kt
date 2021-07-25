package com.example.psimanagement.ui.main

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.psimanagement.network.MarsApi
import com.example.psimanagement.network.MarsPhoto
import kotlinx.coroutines.launch

enum class MarsApiStatus { LOADING, ERROR, DONE }

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var user: String? ="nullUSER"

    fun setUser(aa: String){
        user=aa
    }

    fun getUser(): String? {
        return user
    }

    private var _backingPropertyTest = "backingPropertyTest"
    val backingPropertyTest: String
        get() = _backingPropertyTest

    private val _mutableLiveDataTest = MutableLiveData<String>()
    val mutableLiveDataTest: LiveData<String>
        get() = _mutableLiveDataTest


    private val _quantity = MutableLiveData<Int>(0)
    val quantity: LiveData<Int> = _quantity

    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
    }

    private val _marStatusTest =MutableLiveData<MarsApiStatus>()
    val marStatusTest: LiveData<MarsApiStatus> = _marStatusTest
//        get() = _marStatusTest
//    private val _status = MutableLiveData<MarsApiStatus>()
//
//    val status: LiveData<MarsApiStatus> = _status


    private val _photos = MutableLiveData<MarsPhoto>()
    val photos: LiveData<MarsPhoto> = _photos

    init {
        getMarsPhotos()
    }


    private fun getMarsPhotos() {


        viewModelScope.launch {
            _marStatusTest.value = MarsApiStatus.LOADING
            try {
//                val listResult = MarsApi.retrofitService.getPhotos()
//                _marStatusTest.value = "Success: ${listResult.size} Mars photos retrieved"
//                如果要收字串
//                _marStatusTest.value = listResult

                _photos.value = MarsApi.retrofitService.getPhotos()[0]
//                _marStatusTest.value = "   First Mars image URL : ${_photos.value!!.imgSrcUrl}"
                _marStatusTest.value = MarsApiStatus.DONE
            } catch (e: Exception) {
                _marStatusTest.value = MarsApiStatus.ERROR
//                _marStatusTest.value = "Failure: ${e.message}"
            }
        }


    }


}