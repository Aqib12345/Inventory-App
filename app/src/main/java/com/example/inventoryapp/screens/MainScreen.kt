package com.example.inventoryapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.inventoryapp.R
import com.example.inventoryapp.navigation.HomeScreenDestination

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun MainScreen(modifier: Modifier = Modifier,
                onAddButtonClicked: () -> Unit,
                onItemClick: (Int) -> Unit,
                viewModel: MainScreenViewModel) {

    val homeUiState = viewModel.homeUiState.collectAsState()
    Scaffold(modifier = modifier,
        topBar = {InventoryTopBar(title = HomeScreenDestination.title, hasBackButton = false)} ,
        floatingActionButton = {
            FloatingActionButton(onClick = onAddButtonClicked,
                shape = CircleShape) {
                Icon(
                    painter = painterResource(id = R.drawable.add_fab),
                    contentDescription = "Add Button"
                )
            }
        },
        bottomBar = {
            FooterRow(state = homeUiState.value, modifier = Modifier.padding(bottom = 10.dp))
        }
    ) {
        LazyColumn(Modifier.padding(it)){
            item {HeaderRow(isSortClicked = homeUiState.value.isSortedByName, onClickSort = viewModel::onClickSort)}
            items(homeUiState.value.itemList){item ->
                ItemRow(item = item, onItemClick = onItemClick)
            }

        }
    }

}

@Composable
fun FooterRow(modifier: Modifier = Modifier, state: HomeUiState) {
    Card(modifier = modifier.padding(horizontal = 5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )) {
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {

            Icon(
                painter = painterResource(id = R.drawable.total),
                contentDescription = "Sort Icon",
                modifier = Modifier
                    .weight(0.1f)
                    .padding(horizontal = 10.dp),
                tint = Color.Magenta
            )
            Text(text = "Total Inventory Value", modifier = Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
            Text(text = " Rs: ${state.totalInventoryValue}", modifier = Modifier.weight(0.4f), fontWeight = FontWeight.Bold)

        }
    }
}

@Composable
fun HeaderRow(modifier: Modifier = Modifier, isSortClicked: Boolean = false, onClickSort: () -> Unit) {
    Card(modifier = modifier.padding(horizontal = 5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )) {
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id =
                if (isSortClicked)
                    R.drawable.sort_clicked
                else
                    R.drawable.sort_unclicked),
                contentDescription = "Sort Icon",
                modifier = Modifier
                    .weight(0.1f)
                    .padding(horizontal = 10.dp)
                    .clickable {
                        onClickSort()
                    }
                    .hoverable(
                        interactionSource = remember { MutableInteractionSource() },
                        enabled = true
                    ),
                tint = Color.Magenta
            )
            Text(text = "Item Description", modifier = Modifier.weight(0.4f))
            Text(
                text = "Price",
                modifier = Modifier.weight(0.25f),
                textAlign = TextAlign.End
            )
            Text(
                text = "Quantity",
                modifier = Modifier
                    .weight(0.25f)
                    .padding(end = 8.dp),
                textAlign = TextAlign.End
            )

        }
    }

}

@Composable
fun ItemRow(
    modifier: Modifier = Modifier,
    item: com.example.inventoryapp.models.Item,
    onItemClick: (Int) -> Unit
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable {
            onItemClick(item.id)
        }
        .padding(horizontal = 8.dp, vertical = 16.dp)
        ,
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.inventory),
            contentDescription = "Inventory Icon",
            modifier = Modifier
                .weight(0.1f)
                .padding(horizontal = 10.dp)
        )
        Text(text = item.name, modifier = Modifier.weight(0.4f))
        Text(
            text = item.price.toString(),
            modifier = Modifier.weight(0.25f),
            textAlign = TextAlign.End
        )
        Text(
            text = item.quantity.toString(),
            modifier = Modifier
                .weight(0.25f)
                .padding(end = 8.dp),
            textAlign = TextAlign.End
        )

    }
    Divider()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryTopBar(
    modifier: Modifier = Modifier,
    title: String,
    hasBackButton: Boolean,
    onBackClicked: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title)},
        navigationIcon = {
            if (hasBackButton)
                IconButton(onClick = onBackClicked){
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Icon")
                }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        modifier = modifier
    )
}