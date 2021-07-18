package com.example.psimanagement.ui.main

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
    //到第4點而已,先來睡了

}