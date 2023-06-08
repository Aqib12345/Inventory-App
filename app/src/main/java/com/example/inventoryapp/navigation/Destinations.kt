package com.example.inventoryapp.navigation

interface Destinations {
    val route: String
    val title: String
}

object HomeScreenDestination: Destinations{
    override val route: String = "home"
    override val title: String = "Inventory App"
}

object AddScreenDestination: Destinations{
    override val route: String = "entry"
    override val title: String = "Add Item"
}

object DetailDestination: Destinations{
    override val route: String = "details"
    override val title: String = "Item Details"

    const val itemId = "item_id"
    val routeWithArg = "$route/{$itemId}"
}

object EditDestination: Destinations{
    override val route: String = "edit"
    override val title: String = "Edit Item"

    const val itemId = "item_id"
    val routeWithArg = "$route/{$itemId}"
}
