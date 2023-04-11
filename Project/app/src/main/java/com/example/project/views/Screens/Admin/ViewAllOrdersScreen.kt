package com.example.project.views.Screens

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.R
import com.example.project.model.room.entity.Category
import com.example.project.model.room.entity.Order
import com.example.project.model.room.entity.Product
import com.example.project.model.room.entity.Review
import com.example.project.viewModels.AccountViewModel
import com.example.project.viewModels.CategoryViewModel
import com.example.project.viewModels.OrderViewModel
import com.example.project.viewModels.ProductViewModel
import com.example.project.views.Navigation.Screens
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun ViewAllOrdersScreen(navController: NavController) {
    ViewOrdersApp(navController)
}

@Composable
fun ViewOrdersApp(navController: NavController) {
    var orderList = mutableListOf<Order>()
    val orderViewModel =
        viewModel<OrderViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val orders: List<Order> by orderViewModel.allOrders.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Row(
        ) {
            IconButton(onClick = { navController.navigateUp()  }) {
                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    imageVector = Icons.Filled.ArrowBack,
                    tint = Color(0xFFE13646),
                    contentDescription = "back icon"
                )
            }
        }
        Text(
            "All Orders",
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            color = Color(0xFFE13646),
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )

        var statusField = StatusOptions()

        orders.forEach() { o ->
            if (statusField == "All") {
                orderList.add(o)
            } else if (o.status.equals(statusField)) {
                orderList.add(o)
            }
        }

        LazyColumn(modifier = Modifier
            .height(510.dp)
            .fillMaxWidth()
            .padding(top = 20.dp),
            contentPadding = PaddingValues(
                start = 0.dp,
                end = 0.dp,
                bottom = 2.dp
            ),
            content = {
                items(orderList.size) { index ->
                    OrderCard(orderList[index])
                }
            }
        )
    }
}

@Composable
fun OrderCard(order: Order) {
    val orderViewModel =
        viewModel<OrderViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    var selectedText by remember { mutableStateOf(order.status) }

    Card(
        border = BorderStroke(2.dp,Color(0xFFE13646)),
        backgroundColor = Color(0xFFE2B4B1),
        modifier = Modifier
            .padding(top = 20.dp, start = 8.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .width(10.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.TopStart),
                    text = "Order #${order.orderId}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            fontSize = 15.sp,
                            modifier = Modifier
                                .padding(10.dp),
                            text = "Order Date: ${order.datePlaced}",
                            color = Color(0xFFE13646),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            fontSize = 15.sp,
                            modifier = Modifier
                                .padding(10.dp),
                            text = "ETA: ${order.estDelivery}",
                            color = Color(0xFFE13646),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp),
                        text = " ",
                        fontSize = 15.sp,
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp),
                        text = "Status: ${order.status}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                    )
                    Text(
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(top=10.dp),
                        text = "Total: ${String.format("%.2f", order.total)}",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    var expanded by remember { mutableStateOf(false) }
                    val suggestions = listOf("Processed", "Shipped", "Delivered")

                    var textfieldSize by remember { mutableStateOf(Size.Zero) }

                    val icon = if (expanded)
                        Icons.Filled.KeyboardArrowUp
                    else
                        Icons.Filled.KeyboardArrowDown

                    Column() {
                        OutlinedTextField(
                            value = selectedText,
                            onValueChange = { selectedText },
                            modifier = Modifier
                                .width(300.dp)
                                .height(60.dp)
                                .onGloballyPositioned { coordinates ->
                                    //This value is used to assign to the DropDown the same width
                                    textfieldSize = coordinates.size.toSize()
                                },
                            label = { Text("Change Status") },
                            trailingIcon = {
                                Icon(icon, "contentDescription",
                                    Modifier.clickable { expanded = !expanded })
                            }
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                        ) {
                            suggestions.forEach { label ->
                                DropdownMenuItem(onClick = {
                                    selectedText = label
                                    expanded = false
                                }) {
                                    Text(text = label)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    Button(
                    onClick = {
                        order.status = selectedText
                        orderViewModel.updateOrder(order)
                              //navController.navigate(Screens.ProductInfo.route)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE13646))
                    ) {
                    Text(
                        text = "Update Status",
                        color = Color.White,
                        modifier = Modifier.padding(
                            horizontal = 60.dp
                        )
                    )
                }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}




@Composable
fun StatusOptions(): String {
    Spacer(modifier = Modifier.height(16.dp))

    Text("Filter/Sort + edit orders by status", color = Color.Black,fontWeight = FontWeight.Bold,
        fontSize = 18.sp, modifier = Modifier.padding(bottom=10.dp,start=8.dp))

    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("All", "Processed", "Shipped", "Delivered")
    var selectedText by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column() {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textfieldSize = coordinates.size.toSize()
                },
            label = { Text("Select Status") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label
                    expanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
    return selectedText
}






@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowStatusDropDown(): String {
    val options = listOf("All", "Processed", "Shipped", "Delivered")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            modifier = Modifier
                .width(150.dp)
                .height(50.dp),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text("Status", fontSize = 12.sp) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(Color(0xFF000000))
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
    return selectedOptionText
}

