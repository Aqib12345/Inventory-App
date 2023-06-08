package com.example.inventoryapp.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventoryapp.data.Repository
import com.example.inventoryapp.navigation.DetailDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(private val repository: Repository,
    savedStateHandle: SavedStateHandle): ViewModel(
) {
    val id = checkNotNull( savedStateHandle.get<Int>(DetailDestination.itemId))
    val uiState: StateFlow<UiState> = repository.getItemById(id).filterNotNull().map {item ->
        item.toUiState(
            actionEnabled = item.quantity > 0
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        UiState()
    )

    fun sellItem(){
        viewModelScope.launch {
            val currentItem = uiState.value.toItem()
            if (currentItem.quantity > 0){
                repository.updateItem(currentItem.copy(
                    quantity = currentItem.quantity - 1
                ))
            }
        }
    }
    suspend fun deleteItem(){
        repository.deleteItem(uiState.value.toItem())
    }

}