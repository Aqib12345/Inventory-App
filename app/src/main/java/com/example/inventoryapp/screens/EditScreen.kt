package com.example.inventoryapp.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.inventoryapp.navigation.EditDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(editScreenViewModel: EditScreenViewModel,
    onUpdateButtonClicked: () -> Unit,
    navigateBack: () -> Unit) {
    val state = editScreenViewModel.itemState
    Scaffold(
        topBar = {
            InventoryTopBar(title = EditDestination.title, hasBackButton = true, onBackClicked = navigateBack)
        }
    ) {
        AddScreenEntryBody(
            value = state, onUiStateChanged = editScreenViewModel::updateState, onButtonClicked = onUpdateButtonClicked,
            buttonText = "Update Data",
            modifier = Modifier.padding(it))
    }



}