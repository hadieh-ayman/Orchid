package com.example.project.views.Screens.User

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.model.room.entity.Order
import com.example.project.viewModels.OrderViewModel


@Composable
fun OrderFilter(navController: NavController) {
    OrderFilteredApp(navController)
}

@Composable
fun OrderFilteredApp(navController: NavController) {

    val ordersViewModel =
        viewModel<OrderViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val allOrders: List<Order> by ordersViewModel.orders.collectAsState()
    var orders : MutableList<Order> = mutableListOf<Order>()

}