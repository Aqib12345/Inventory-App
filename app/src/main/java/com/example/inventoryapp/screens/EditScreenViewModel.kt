package com.example.inventoryapp.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventoryapp.data.Repository
import com.example.inventoryapp.navigation.DetailDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditScreenViewModel @Inject constructor(private val repository: Repository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val itemId = checkNotNull(savedStateHandle.get<Int>(DetailDestination.itemId))

    var itemState by mutableStateOf(UiState())

    init {
        viewModelScope.launch {
            repository.getItemById(itemId).filterNotNull().map {
                it.toUiState()
            }.collect{
                itemState = it
            }
        }


    }

    fun updateState(state: UiState) {
        itemState = state.copy(
            isButtonEnabled = state.isValid()
        )
    }

    fun updateItem() {
        viewModelScope.launch {
            repository.updateItem(item = itemState.toItem())
        }
    }
}