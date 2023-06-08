package com.example.inventoryapp.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventoryapp.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddScreenViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    var itemState by mutableStateOf(UiState())

    fun updateState(state: UiState) {
        itemState = state.copy(
            isButtonEnabled = state.isValid()
        )
    }

    fun addData() {
        viewModelScope.launch {
            repository.insertItem(
                itemState.toItem()
            )
        }
    }



}