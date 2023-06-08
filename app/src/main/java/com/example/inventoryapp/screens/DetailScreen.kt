package com.example.inventoryapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventoryapp.R
import com.example.inventoryapp.navigation.DetailDestination
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    detailScreenViewModel: DetailScreenViewModel,
    navigateToEditScreen: (Int) -> Unit,
    onClickSellButton: (Int) -> Unit,
    navigateBack: () -> Unit
) {

    val  uiState = detailScreenViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    Scaffold(topBar = {
        InventoryTopBar(title = DetailDestination.title, hasBackButton = true, onBackClicked = navigateBack)
    },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditScreen(uiState.value.id)},
                shape = CircleShape) {
                Icon(imageVector = Icons.Default.Edit, contentDescription ="" )
            }
        }
    ) {
        DetailsScreenBody(
            modifier = Modifier.padding(it),
            itemUiState = uiState.value,
            onClickSellButton = onClickSellButton,
            onClickDeleteButton = {
                scope.launch {
                    detailScreenViewModel.deleteItem()
                    navigateBack()
                }
            }
        )


    }
}

@Composable
fun DetailsScreenBody(modifier: Modifier =  Modifier,
                      itemUiState: UiState,
                    onClickSellButton: (Int)-> Unit,
                    onClickDeleteButton: () -> Unit) {
    var deleteConfirmationReq by remember {
        mutableStateOf(false)
    }
    
    Column(modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Form(itemUiState = itemUiState, onUiStateChanged = {}, enabled = false)
        InventoryButton(
            onClick = { onClickSellButton(itemUiState.id) },
            text = "Sell Item",
            icon = R.drawable.sell_icon,
            isEnabled = itemUiState.isButtonEnabled
        )
        InventoryButton(
            onClick = { deleteConfirmationReq = true },
            text = "Delete",
            icon = R.drawable.delete_icon,
            isEnabled = true
        )
    }
    
    if (deleteConfirmationReq){
        DeleteConfirmationDialog(
            onDismissClicked = {
            deleteConfirmationReq = false
        }, onDeleteClicked = {
            deleteConfirmationReq = false
            onClickDeleteButton()
        })
    }

}

@Composable
fun DeleteConfirmationDialog(
    modifier: Modifier = Modifier,
    onDismissClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        title = { Text(text = "Attention")},
        text = { Text(text = "Are you sure you want to delete the item")},
        dismissButton = {
            TextButton(onClick = onDismissClicked) {
                Text(text = "No")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteClicked) {
                Text(text = "Yes")
            }
        },
        modifier = modifier.padding(20.dp),
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        tonalElevation = 5.dp
    )
}