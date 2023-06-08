package com.example.inventoryapp.screens


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventoryapp.data.Repository
import com.example.inventoryapp.models.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(repository: Repository): ViewModel() {
//    val homeUiState : StateFlow<HomeUiState> = repository.getAllItems().map {
//        HomeUiState(it)
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5000),
//        initialValue = HomeUiState()
//    )

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    private val _defaultUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())

    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    private fun calculateTotalInventoryValue(items: List<Item>): Double{
        var total = 0.0
        for (item in items){
            total += item.price * item.quantity
        }
        return total
    }

    init {
        repository.getAllItems()
            .map { items ->
                HomeUiState(items, isSortedByName = false,
                totalInventoryValue = calculateTotalInventoryValue(items))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomeUiState()
            )
            .onEach {
                _homeUiState.value = it
                _defaultUiState.value = it
            }
            .launchIn(viewModelScope)
    }

    fun onClickSort(){
        val currentState = homeUiState.value
        if (!homeUiState.value.isSortedByName){
            val sortedItems = currentState.itemList.toMutableList().sortedBy {
                it.name.lowercase()
            }
            val updatedState = currentState.copy(
                itemList = sortedItems,
                isSortedByName = true
            )
            _homeUiState.value = updatedState
        }

        else{
            _homeUiState.value = _defaultUiState.value
        }
    }
}