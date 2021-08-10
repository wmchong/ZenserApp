package com.example.zenserapp.ui.sell

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SellViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is sell fragment"
    }
    val text: LiveData<String> = _text
}