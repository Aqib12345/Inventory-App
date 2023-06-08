package com.example.inventoryapp.screens


import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.inventoryapp.R
import com.example.inventoryapp.navigation.AddScreenDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(addScreenViewModel: AddScreenViewModel, onAddButtonClicked: () -> Unit, navigateBack: () -> Unit) {

    Scaffold(
        topBar = {
            InventoryTopBar(title = AddScreenDestination.title, hasBackButton = true, onBackClicked = navigateBack)
        }
    ) {
        AddScreenEntryBody(Modifier.padding(it),
            value = addScreenViewModel.itemState,
            onUiStateChanged = addScreenViewModel::updateState,
            onButtonClicked = onAddButtonClicked
        )
    }



}

@Composable
fun AddScreenEntryBody(modifier: Modifier = Modifier,
    value: UiState,
    buttonText: String = "Add Data",
    onUiStateChanged: (uiState: UiState) -> Unit,
    onButtonClicked: () -> Unit) {
    Column(modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Form(
            value,
            onUiStateChanged = onUiStateChanged)

        InventoryButton(
            onClick = onButtonClicked,
            text = buttonText, icon = R.drawable.add_fab,
            isEnabled = value.isValid())
    }
}



@Composable
fun Form(
    itemUiState: UiState,
    onUiStateChanged: (uiState: UiState) -> Unit,
    enabled: Boolean = true
) {
    Column {
        InventoryTextField(
            icon = R.drawable.add_fab,
            label = "Item Name",
            isEnabled = enabled,
            value = itemUiState.name,
            onValueChange = {
                onUiStateChanged(
                    itemUiState.copy(
                        name = it
                    )
                )
            }
        )
        InventoryTextField(
                icon = R.drawable.add_fab,
        label = "Item Price",
            isEnabled = enabled,
        value = itemUiState.price,
        onValueChange = {
            onUiStateChanged(
                itemUiState.copy(
                    price = it
                )
            )
        },
            keyboardType = KeyboardType.Number
        )

        InventoryTextField(
            icon = R.drawable.add_fab,
            isEnabled = enabled,
            label = "Item Quantity",
            value = itemUiState.quantity,
            onValueChange = {
                onUiStateChanged(
                    itemUiState.copy(
                        quantity = it
                    )
                )
            },
            keyboardType = KeyboardType.Number
        )

        Divider()

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryTextField(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    placeholder: String = "",
    label: String,
    @DrawableRes icon: Int,
    singleLine: Boolean = true,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    
    OutlinedTextField(
        value = value, 
        onValueChange = {
        onValueChange(it)
    },
        enabled = isEnabled,
        singleLine = singleLine,
        placeholder = { Text(text = placeholder)},
        label = { Text(text = label)},
        leadingIcon = { Icon(painter = painterResource(id = icon), contentDescription = "Trailing Icon")},
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
    
}

@Composable
fun InventoryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isEnabled: Boolean = false,
    text: String,
    @DrawableRes icon: Int
) {
    Button(enabled = isEnabled,
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)) {
        Icon(painter = painterResource(id = icon),
            contentDescription = "Button Icon",
            modifier = modifier.size(ButtonDefaults.IconSize))
        Spacer(modifier = modifier.size(ButtonDefaults.IconSpacing))
        Text(text = text)
    }

}