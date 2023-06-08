package com.example.inventoryapp.screens

import com.example.inventoryapp.models.Item

data class UiState(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
    val isButtonEnabled: Boolean = false
)

data class HomeUiState(
    val itemList: List<Item> = emptyList(),
    val isSortedByName: Boolean = false,
    val totalInventoryValue: Double = 0.0
)

fun UiState.toItem(): Item {
    return Item(
        id =id,
        name = name,
        price = price.toDoubleOrNull() ?: 0.0,
        quantity = quantity.toIntOrNull()?: 0
    )
}

fun Item.toUiState(actionEnabled: Boolean = false) : UiState{
    return UiState(
        id = id,
        name = name,
        price = price.toString(),
        quantity = quantity.toString(),
        isButtonEnabled = actionEnabled
    )
}

fun UiState.isValid(): Boolean {
    return (name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank())
}



