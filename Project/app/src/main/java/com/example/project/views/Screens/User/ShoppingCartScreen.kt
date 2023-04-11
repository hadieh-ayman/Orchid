package com.example.project.views.Screens

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.project.R
import com.example.project.model.room.entity.*
import com.example.project.viewModels.*
import com.example.project.views.Navigation.Screens
import kotlinx.coroutines.launch
import kotlinx.datetime.*


@Composable
fun ShoppingCartScreen(navController: NavController) {
    ShowShoppingScreen(navController)
}

@Composable
fun ShowShoppingScreen(navController: NavController) {
    val cartItemViewModel =
        viewModel<CartItemViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val productViewModel =
        viewModel<ProductViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val accountViewModel =
        viewModel<AccountViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val voucherViewModel =
        viewModel<VoucherViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val orderViewModel =
        viewModel<OrderViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val toastContext = LocalContext.current
    val currentAccount: Account by accountViewModel.account.collectAsState()
    cartItemViewModel.getAllCartItems(currentAccount.accountId)
    val cartItems: List<CartItem> by cartItemViewModel.cartItems.collectAsState()
    val vouchers: List<Voucher> by voucherViewModel.vouchers.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var voucherState by remember { mutableStateOf("") }
    var total by remember { mutableStateOf("0") }
    var totalDouble = total.toDouble()
    var discountedTotal by remember { mutableStateOf("0") }
    var discountedTotalDouble = discountedTotal.toDouble()

    cartItems.forEach() { c ->
        val products: List<Product> by productViewModel.products.collectAsState()
        var product = Product( "", 0.0, "", 0.0, 0, 0.0, 0, "","")
        products.forEach() { p ->
            if (p.productId.equals(c.product)) {
                product = p
            }
        }
        totalDouble += (c.quantity * c.price)
        discountedTotalDouble += (c.price * (1-product.discount)) * c.quantity
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .fillMaxWidth(),
        //scaffoldState = scaffoldState
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable(onClick = { navController.navigateUp() })
                    .size(30.dp)
                    .weight(1f),
                tint = Color(0xFFE13646),
            )
            Text(
                "Shopping Cart",
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 10.dp),
                // modifier = Modifier,
                color = Color.Black,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_outline_shopping_cart_24),
                contentDescription = "Cart",
                modifier = Modifier
                    .size(20.dp)
                    .weight(1f),
                tint = Color(0xFFE13646),
            )
        }
        Spacer(modifier = Modifier.padding(top=20.dp).height(2.dp))
        if (cartItems.isNotEmpty()) {
            LazyColumn(modifier = Modifier
                .height(480.dp)
                .fillMaxWidth(),
                // content padding
                contentPadding = PaddingValues(
                    start = 0.dp,
                    top = 16.dp,
                    end = 0.dp,
                    bottom = 2.dp
                ),
                content = {
                    items(cartItems) { c ->
                        CartCard(c, navController)
                    }
                }
            )
        } else {
            Column(
                modifier = Modifier.height(500.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (cartItems.isEmpty()) {
                    Image(
                        modifier = Modifier
                            .size(200.dp)
                            .padding(start = 30.dp),
                        contentDescription = null,
                        painter = painterResource(R.drawable.emptycart),
                    )
                    Text(
                        "Shopping Cart is Empty!",
                        modifier = Modifier.padding(top = 10.dp, start = 30.dp),
                        color = Color.Black,
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }

        Card(
            backgroundColor = Color(0xFFFFB7B7).copy(alpha = 0.6f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = 5.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Total: " + String.format("%.2f", totalDouble),
                    fontSize = 17.sp,
                    fontWeight = Bold,
                    style = TextStyle(textDecoration = TextDecoration.LineThrough),
                    color = Color(0xFF000000),
                )
                Text(
                    text = "Discounted Total: " + String.format("%.2f", discountedTotalDouble),
                    fontSize = 17.sp,
                    fontWeight = Bold,
                    color = Color(0xFFE13646),
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = voucherState,
                    onValueChange = {
                        voucherState = it
                    },
                    placeholder = { Text("Voucher Code") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFFE13646),
                        unfocusedBorderColor = Color(0xFFDF4F5C)
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = {
                        val datePlaced: LocalDate =
                            Clock.System.todayAt(TimeZone.currentSystemDefault())
                        val estDelivery = datePlaced.plus(1, DateTimeUnit.MONTH)

                        var voucherExists = false
                        vouchers.forEach() { v ->
                            if (v.voucherCode.equals(voucherState)) {
                                voucherExists = true
                            }
                        }

                        if (voucherExists || voucherState == "") {
                            var order = Order(
                                "",
                                "Processed",
                                datePlaced.toString(),
                                estDelivery.toString(),
                                discountedTotalDouble,
                                "",
                                currentAccount.accountId,
                                "",
                                voucherState
                            )
                            voucherViewModel.getVoucher(voucherState)

                            orderViewModel.addOrder(order)
                            navController.navigate(Screens.Payment.route)
                        } else {
                            scope.launch {
                                Toast.makeText(
                                    toastContext,
                                    "Voucher does not exist!",
                                    Toast.LENGTH_SHORT
                                ).show();
                            }
                            voucherState = ""
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE13646))
                ) {
                    Text(
                        textAlign = TextAlign.Center, text = "CHECKOUT", fontSize = 19.sp,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun CartCard(
    cartItem: CartItem,
    navController: NavController,
    cartItemViewModel: CartItemViewModel = viewModel(LocalContext.current as ComponentActivity),
    productViewModel: ProductViewModel = viewModel(LocalContext.current as ComponentActivity)
) {

    val products: List<Product> by productViewModel.products.collectAsState()
    var currentProduct = Product("", 0.0, "", 0.0, 0, 0.0, 0, "", "")
    products.forEach() { p ->
        if (p.productId.equals(cartItem.product)) {
            currentProduct = p
        }
    }

    var quantity by remember { mutableStateOf(cartItem.quantity.toString()) }
    var quantityInt = quantity.toInt()

    Card(
        backgroundColor = Color(0xFFFACDCD).copy(alpha = 0.6f),
        modifier = Modifier
            .padding(top = 20.dp, start = 8.dp)
            .clickable(onClick = { navController.navigate(Screens.ProductInfo.route) }),
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(currentProduct.image),
                contentDescription = "null",
                modifier = Modifier
                    .size(80.dp)
                    .padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 10.dp)
            )
            Column(
                modifier = Modifier.padding(top = 15.dp, start = 10.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.width(200.dp),
                    text = currentProduct.productName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                if(currentProduct.discount > 0) {
                    Text(
                        text = "Old Price: " + String.format("%.2f", cartItem.price) + " QAR",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(textDecoration = TextDecoration.LineThrough),
                        color = Color(0xFF000000),
                    )
                    Text(
                        text = "New Price: " + String.format("%.2f", cartItem.price * (1-currentProduct.discount)) + " QAR",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE13646),
                    )
                } else {
                    Text(
                        text = "Price: " + String.format("%.2f", cartItem.price) + " QAR",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE13646),
                    )
                }
                Text(
                    text = "Color: " + cartItem.color,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE13646),
                )
                Text(
                    text = "Size: " + cartItem.size,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE13646),
                )
            }
            Column() {
                Row() {
//                    Card(
//                        modifier = Modifier
//                            //.height(10.dp)
//                            .width(18.dp)
//                            .padding(top = 30.dp),
//                        backgroundColor = Color(0xFF8A8A8A).copy(alpha = 0.6f),
//                        shape = RoundedCornerShape(9.dp),
//                    ) {
//                        Text(
//                            text = "-",
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 18.sp,
//                            modifier = Modifier.clickable(onClick = {
//                                if (cartItem.quantity in 2..10) {
//                                    quantityInt--
//                                    cartItem.quantity--
//                                }
//                            })
//                        )
//                    }
                    Text(
                        modifier = Modifier.padding(top = 30.dp),
                        text = cartItem.quantity.toString() + " Units",
                        fontSize = 18.sp
                    )
//                    Card(
//                        modifier = Modifier
//                            .padding(top = 30.dp)
//                            .width(18.dp),
//                        backgroundColor = Color(0xFF8A8A8A).copy(alpha = 0.6f),
//                        shape = RoundedCornerShape(9.dp),
//                    ) {
//                        Text(
//                            text = "+",
//                            fontSize = 18.sp,
//                            modifier = Modifier.clickable(onClick = {
//                                if (cartItem.quantity in 1..9) {
//                                    quantityInt++
//                                    cartItem.quantity++
//                                }
//                            })
//
//                        )
//                    }
                }
                IconButton(onClick = {
                    cartItemViewModel.deleteCartItem(cartItem)
                }) {
                    Icon(
                        modifier = Modifier
                            .size(35.dp),
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "delete icon"
                    )
                }
            }
        }
    }
}