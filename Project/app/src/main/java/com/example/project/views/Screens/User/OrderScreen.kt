package com.example.project.views.Screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.model.room.entity.CartItem
import com.example.project.model.room.entity.Order
import com.example.project.model.room.entity.Product
import com.example.project.viewModels.AccountViewModel
import com.example.project.viewModels.CartItemViewModel
import com.example.project.viewModels.OrderViewModel
import com.example.project.viewModels.ProductViewModel
import com.example.project.views.Navigation.Screens

//import com.example.project.views.Navigation.BottomBarScreen

@Composable
fun ViewOrdersScreen(navController: NavController) {
    AllOrdersApp(navController)
}

@Composable
fun AllOrdersApp(navController: NavController) {
    val orderViewModel =
        viewModel<OrderViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val accountViewModel =
        viewModel<AccountViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val currentAccount = accountViewModel.getCurrentAccount()
    orderViewModel.getAllAccountOrders(currentAccount.accountId)
    val orders: List<Order> by orderViewModel.orders.collectAsState()
    orderViewModel.getAllAccountOrders(currentAccount.accountId)
    Row(
        modifier = Modifier.padding(top = 10.dp),
        horizontalArrangement = Arrangement.Start,
    ) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(
                modifier = Modifier
                    .size(30.dp),
                imageVector = Icons.Filled.ArrowBack,
                tint = Color(0xFFE13646),
                contentDescription = "Arrow icon"
            )
        }
        Row(
            modifier = Modifier
                //.padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top,
        ) {
//           
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        Text(
            "All Orders",
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp),
            color = Color(0xFFE13646),
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
        Text(
            "Total No Of Orders: ${orders.size}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start=5.dp),
            color = Color.Black,
            fontSize = 13.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start=10.dp,end=10.dp)
                .fillMaxWidth(),
            //scaffoldState = scaffoldState
        ) {
            LazyColumn(modifier = Modifier
                .height(600.dp)
                .fillMaxWidth()
                .padding(top = 0.dp),
                // content padding
                contentPadding = PaddingValues(
                    start = 0.dp,
                    end = 0.dp,
                    bottom = 2.dp
                ),
                content = {
                    items(orders.size) { index ->
                        ViewCard(orders[index])
                    }
                }
            )
        }
    }
}

@Composable
fun ViewCard(order: Order) {
    val orderViewModel =
        viewModel<OrderViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val cartItemViewModel =
        viewModel<CartItemViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    cartItemViewModel.getAllCartItemsEver()
    val cartitems: List<CartItem> by cartItemViewModel.allCartItems.collectAsState()

    Card(
        border = BorderStroke(2.dp, Color(0xFFE13646)),
        backgroundColor = Color(0xFFE2B4B1),
        modifier = Modifier
            .padding(top = 20.dp, start = 15.dp,end=15.dp)
//            .clickable(onClick = { navController.navigate(Screens.ProductInfo.route) }),
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
                Column() {
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
                                text = "Ordered: ${order.datePlaced}",
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
                    Text(
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(start=8.dp,top = 1.dp),
                        text = "Total: ${String.format("%.2f", order.total)}",
                        color = Color(0xFFE13646),
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    Modifier
                        .align(Alignment.TopCenter)
                        .width(0.dp)
                        .padding(start = 40.dp,top=20.dp),
                )

                Column(
                    modifier = Modifier.padding(start = 50.dp, top = 90.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Card(
                        modifier = Modifier
                            .padding(top = 50.dp),
                        elevation = 4.dp,
                        shape = RoundedCornerShape(30.dp),
                        backgroundColor =
                        Color(0xFFE13646),
                    ) {
                        Text(
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(10.dp),
                            text = "Processed",
                            color =
                            Color.White,
                        )
                    }
                    Divider(
                        color = Color.Red,
                        modifier = Modifier
                            .height(50.dp)
                            .width(5.dp)
                    )
                    Card(
                        modifier = Modifier
                            .padding(top = 0.dp),
                        elevation = 4.dp,
                        shape = RoundedCornerShape(30.dp),
                        backgroundColor = if (order.status.equals(
                                "Processed"
                            )
                        ) {
                            Color.DarkGray
                        } else {
                            Color(0xFFE13646)
                        },
                    ) {
                        Text(
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(10.dp),
                            text = "Shipped",
                            color = if (order.status.equals("Processed")) {
                                Color.Black
                            } else {
                                Color.White
                            },
                        )
                    }
//                    Box(
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .width(1.dp)
//                            .background(Color(0xFFE13646))
//                    )
                    Divider(
                        color = Color.Red,
                        modifier = Modifier
                            .height(50.dp)
                            .width(5.dp)
                    )
                    Card(
                        modifier = Modifier
                            .padding(top = 0.dp),
                        elevation = 4.dp,
                        shape = RoundedCornerShape(30.dp),
                        backgroundColor = if (order.status.equals(
                                "Processed"
                            ) || order.status.equals("Shipped")
                        ) {
                            Color.DarkGray
                        } else {
                            Color(0xFFE13646)
                        },
                    ) {
                        Text(
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(10.dp),
                            text = "Delivered",
                            color = if (order.status.equals("Processed") || order.status.equals(
                                    "Shipped"
                                )
                            ) {
                                Color.Black
                            } else {
                                Color.White
                            },
                        )
                    }
                    Spacer(modifier = Modifier.padding(bottom = 50.dp))
                }

                Card(
                    modifier = Modifier.padding(start = 280.dp, top = 130.dp),
                    backgroundColor = Color(0xFFE2B4B1), elevation = 0.dp
                ) {
                    if (order.status == "Processed") {
                        IconButton(onClick = {
                            orderViewModel.deleteOrder(order)
                            orderViewModel.getAllAccountOrders(order.account)
                            for (c in cartitems){
                                if(c.order.equals(order.orderId)){
                                    cartItemViewModel.deleteCartItem(c)
                                    cartItemViewModel.getAllCartItemsEver()
                                }
                            }
                        }
                        ) {
                            Icon(

                                modifier = Modifier
                                    .size(95.dp)
                                    .padding(top = 50.dp, end = 30.dp),
                                imageVector = Icons.Filled.Delete,
                                tint = Color(0xFFE13646),
                                contentDescription = "Delete icon"
                            )

                        }
                    }
                }
            }

        }
    }
}


//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.project.views.Navigation.Screens
//import java.text.SimpleDateFormat
//import java.util.*
//
//
//@Composable
//fun OrderScreen(navController: NavController) {
//    OrderApp(navController)
//}
//
//@Composable
//fun OrderApp(navController: NavController) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(20.dp)
//    ) {}
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(20.dp)
//    ) {
//        IconButton(onClick = { navController.navigateUp() }) {
//            Icon(
//
//                modifier = Modifier
//                    .size(30.dp),
//                imageVector = Icons.Filled.ArrowBack,
//                tint = Color(0xFFE13646),
//                contentDescription = "Arrow icon"
//            )
//        }
//        Text(
//            "All Orders",
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(20.dp),
//            fontSize = 25.sp,
//            textAlign = TextAlign.Start,
//            fontWeight = FontWeight.Bold,
//        )
//
//        OrderBar(navController)
//        OrderBar(navController)
//        OrderBar(navController)
//        Divider(thickness = 1.dp, color = Color.Black)
//    }
//
//}
//
//@Composable
//fun OrderBar(navController: NavController) {
//    Divider(thickness = 1.dp, color = Color.Black)
//    val sdf = SimpleDateFormat("dd-MM-yyyy")
//    val currentDateAndTime = sdf.format(Date())
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(20.dp)
//    ) {
//        Text(
//            modifier = Modifier
//                .fillMaxWidth(),
//            textAlign = TextAlign.End,
//            fontSize = 10.sp,
//            text = currentDateAndTime,
//            color = Color.Gray
//        )
//        Text(
//            "Order #82784027",
//            modifier = Modifier
//                .fillMaxWidth(),
//            fontSize = 15.sp,
//            textAlign = TextAlign.Start,
//            color = Color.Gray,
//        )
//
//        Status()
//        Column(
//            modifier = Modifier
//                .fillMaxWidth(),
//            horizontalAlignment = Alignment.End,
//            verticalArrangement = Arrangement.Bottom
//        )
//        {
//            Button(modifier = Modifier
//                .width(80.dp)
//                .height(25.dp),
//                colors = ButtonDefaults.buttonColors(Color(0xFFE13646)),
//                onClick = {
//                    navController.navigate(Screens.Tracking.route)
//                }) {
//                Text(
//                    "Track",
//                    fontSize = 8.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White
//                )
//            }
//        }
//
//    }
//
//}
//
//@Composable
//fun Status() {
//    Spacer(modifier = Modifier.height(4.dp))
//    CircledColoredLabel(shape = CircleShape)
//}
//
//@Composable
//fun CircledColoredLabel(shape: RoundedCornerShape) {
//    val status: String = "Confirmed";
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentSize(Alignment.BottomStart),
//        verticalAlignment = Alignment.Bottom,
//    ) {
//
//        if (status == "Confirmed") {
//            Box(
//                modifier = Modifier
//                    .size(10.dp)
//                    .clip(shape)
//                    .background(Color.Green)
//            )
//            Text(
//                "Confirmed",
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
//            )
//        } else if (status == "Processed") {
//            Box(
//                modifier = Modifier
//                    .size(10.dp)
//                    .clip(shape)
//                    .background(Color.Yellow)
//            )
//            Text(
//                "Processed",
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
//            )
//        } else if (status == "Shipped") {
//            Box(
//                modifier = Modifier
//                    .size(10.dp)
//                    .clip(shape)
//                    .background(Color.Blue)
//            )
//            Text(
//                "Shipped",
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
//            )
//        } else if (status == "Delivered") {
//            Box(
//                modifier = Modifier
//                    .size(10.dp)
//                    .clip(shape)
//                    .background(Color.Red)
//            )
//            Text(
//                "Delivered",
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
//            )
//        }
//    }
//}
