package com.example.project.views.Screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import coil.compose.rememberAsyncImagePainter
import com.example.project.model.room.entity.CartItem
import com.example.project.model.room.entity.Order
import com.example.project.model.room.entity.Product
import com.example.project.viewModels.CartItemViewModel
import com.example.project.viewModels.OrderViewModel
import com.example.project.viewModels.ProductViewModel
import com.example.project.views.Navigation.Screens


@Composable
fun TrackingScreen(navController: NavController) {
    TrackApp(navController)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackApp(navController: NavController) {
    val orderViewModel =
        viewModel<OrderViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val currentOrder: Order by orderViewModel.order.collectAsState()
    Row(
        modifier = Modifier
            .padding(top = 10.dp),
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

    }
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Text(
            "Order #${currentOrder.orderId}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp, 50.dp, 0.dp, 20.dp),
            color = Color.Black,
            fontSize = 25.sp,
            textAlign = TextAlign.Left,
        )
        Text(
            "Confirmed: ${currentOrder.datePlaced}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp, 0.dp, 0.dp, 10.dp),
            color = Color.LightGray,
            fontSize = 15.sp,
            textAlign = TextAlign.Left,
        )
        Text(
            "Estimated Delivery: ${currentOrder.estDelivery}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp, 0.dp, 0.dp, 10.dp),
            color = Color.LightGray,
            fontSize = 15.sp,
            textAlign = TextAlign.Left,
        )
        //TrackingTimeLine(navController)
        Spacer(modifier = Modifier.height(10.dp))
        OrderItems(navController)
    }

}

@Composable
fun OrderItems(navController: NavController) {
    val orderViewModel =
        viewModel<OrderViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val cartItemViewModel =
        viewModel<CartItemViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    cartItemViewModel.getAllCartItemsEver()
    var cartItemsList = mutableListOf<CartItem>()
    val currentOrder: Order by orderViewModel.order.collectAsState()
    val cartItems : List<CartItem> by cartItemViewModel.allCartItems.collectAsState()
    for(c in cartItems){
        if(c.order.equals(currentOrder.orderId)){
            cartItemsList.add(c)
        }
    }

    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                "Order Items",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Color(0xFFE13646)
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
        LazyColumn(
        modifier = Modifier
            .height(500.dp).padding(top=60.dp),
//            .fillMaxWidth(),
            // content padding
            contentPadding = PaddingValues(
                start = 0.dp,
                top = 16.dp,
                end = 0.dp,
                bottom = 2.dp
            ),
            content = {
                    items(cartItemsList.size) { i ->
                        CartProductCard(navController, cartItemsList[i], currentOrder)

                    }
                }
        )
            }
    }

@Composable
fun CartProductCard(navController: NavController,
              cartItem: CartItem, order: Order) {
    val productViewModel =
        viewModel<ProductViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val products: List<Product> by productViewModel.products.collectAsState()
    var product = Product("", 0.0, "", 0.0, 0, 0.0, 0, "", "")
    products.forEach() { p ->
        if (p.productId.equals(cartItem.product)) {
            product = p
        }
    }

    var total = cartItem.price*(1-product.discount)*cartItem.quantity

    Card(
        backgroundColor = Color(0xFFE2B4B1),
        modifier = Modifier
            .width(400.dp)
            .height(150.dp)
            .padding(all = 10.dp)
            .clickable(
                onClick = {}),
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(top = 15.dp, start = 10.dp, end = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(product.image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                )
            }

            Column(
                Modifier
                    .fillMaxSize()
                    .width(10.dp)
                    .padding(start = 10.dp, top = 10.dp)
            ) {
                Text(
//                    modifier = Modifier
//                        .padding(start = 40.dp, top = 9.dp),
//                    .align(Alignment.TopStart),
                    text = product.productName.take(30),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                )
//                Box(
//                    Modifier
//                        .align(Alignment.TopCenter)
//                        .width(0.dp),
//                    )
                Text(
//                    modifier = Modifier
//                        .padding(start = 40.dp, top = 25.dp),
//                    //.align(Alignment.TopStart),
                    text = "Order ID: " + order.orderId,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
//                    modifier = Modifier
//                        .padding(start = 40.dp, top = 25.dp),
//                    //.align(Alignment.TopStart),
                    text = "Date Ordered: " + order.datePlaced,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
//                    modifier = Modifier
//                        .padding(start = 40.dp, top = 40.dp),
//                    //.align(Alignment.TopStart),
                    text = "Count: " + cartItem.quantity,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE13646)
                )
                Text(
//                    modifier = Modifier
//                        .padding(start = 40.dp, top = 60.dp),
//                    //  .align(Alignment.TopStart),
                    text = "Total Amount: " + String.format("%.2f", total) + " QAR",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE13646)
                )
                Row(
//                    modifier = Modifier.padding(start = 210.dp, top = 20.dp),
//                    backgroundColor = Color(0xFFE2B4B1),
//                    elevation = 0.dp
                    /*shape = RoundedCornerShape(9.dp),*/
                ) {
                    Text(
                        text = "Status: ",
                        fontSize = 12.sp,
                    )
                    Text(
                        //modifier = Modifier.padding(top = 20.dp),
                        text = order.status,
                        color = Color(0xFF2196F3),
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}