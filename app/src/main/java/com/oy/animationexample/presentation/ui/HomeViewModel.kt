package com.oy.animationexample.presentation.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor()
    : ViewModel() {

    val isSelected = mutableStateOf(false)

    fun onSelected(selected:Boolean){
//        Log.d("APPDEBUG", "onSelected: ${selected}")
        setSelect(selected)
    }

    private fun setSelect(selected:Boolean){
        this.isSelected.value = selected
    }
}