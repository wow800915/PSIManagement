package com.example.psimanagement.ui.main

import android.text.Editable
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var user: String? =null

    fun setUser(aa: String){
        user=aa
    }

    fun getUser(): String? {
        return user
    }
}