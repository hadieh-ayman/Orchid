package com.example.project.views.Screens

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.model.room.entity.*
import com.example.project.viewModels.*
import com.example.project.views.Navigation.Screens

@Composable
fun Payment(navController: NavController) {
    PaymentApp(navController)
}

@Composable
fun PaymentApp(navController: NavController) {
    val cartItemViewModel =
        viewModel<CartItemViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val cardViewModel =
        viewModel<CardViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val accountViewModel =
        viewModel<AccountViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val orderViewModel =
        viewModel<OrderViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val voucherViewModel =
        viewModel<VoucherViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val addressViewModel =
        viewModel<AddressViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val productViewModel =
        viewModel<ProductViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    var toastContext = LocalContext.current

    val currentOrder: Order by orderViewModel.order.collectAsState()
    val currentAccount: Account by accountViewModel.account.collectAsState()
    cartItemViewModel.getAllCartItems(currentAccount.accountId)
    addressViewModel.getAllAddresses(currentAccount.accountId)
    val cartItems: List<CartItem> by cartItemViewModel.cartItems.collectAsState()
    val accountAddresses: List<Address> by addressViewModel.addresses.collectAsState()
    val currentAddress: Address by addressViewModel.address.collectAsState()
    cardViewModel.getAllCards(currentAccount.accountId)
    val currentCard: CreditCard by cardViewModel.card.collectAsState()
    val accountCards: List<CreditCard> by cardViewModel.cards.collectAsState()
    val currentVoucher: Voucher by voucherViewModel.voucher.collectAsState()
    if (currentVoucher.voucherCode == "")
        currentVoucher.percentage == 0.0

    val savedCard = remember { mutableStateOf(false) }
    val newCard = remember { mutableStateOf(false) }
    val savedAdd = remember { mutableStateOf(false) }
    val newAdd= remember { mutableStateOf(false) }
    val cashDelivery = remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf("") }
    val savedAddress = remember { mutableStateOf(false) }
    val newAddress = remember { mutableStateOf(false) }

//    var newTotal = currentOrder.total * (1 - currentVoucher.percentage)

    var total by remember { mutableStateOf("0") }
    var totalDouble = total.toDouble()

    cartItems.forEach() { c ->
        val products: List<Product> by productViewModel.products.collectAsState()
        var product = Product("", 0.0, "", 0.0, 0, 0.0, 0, "", "")
        products.forEach() { p ->
            if (p.productId.equals(c.product)) {
                product = p
            }
        }
        totalDouble += (c.price * (1-product.discount)) * c.quantity
    }


    var newTotal = totalDouble * (1 - currentVoucher.percentage)

    TopAppBar(
        backgroundColor = Color.White,
        contentColor = Color.White
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    imageVector = Icons.Filled.ArrowBack,
                    tint = Color(0xFFE13646),
                    contentDescription = "arrow icon"
                )
            }
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
            .verticalScroll(rememberScrollState()),

        ) {
        Text(
            text = "Choose your payment method",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier.padding(top = 100.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (accountCards.isEmpty()) {
            } else {
                Button(
                    modifier = Modifier
                        .width(360.dp)
                        .height(80.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (savedCard.value) Color(0xFFFF9422) else Color(
                            0xFFE9B9B6
                        )
                    ),
                    onClick = {
                        if (savedCard.value) {
                        } else {
                            savedCard.value = !savedCard.value
                            newCard.value = !savedCard.value
                            cashDelivery.value = !savedCard.value
                            selectedType = "Card"
                        }
                    },
                ) {
                    Icon(
                        modifier = Modifier
                            .size(30.dp),
                        imageVector = Icons.Filled.CheckCircle,
                        tint = Color(0xFFE13646),
                        contentDescription = "Saved Card icon"
                    )
                    Text("Saved Card", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (savedCard.value) {
                    LazyRow(modifier = Modifier.fillMaxWidth(), content = {
                        items(accountCards) { c ->
                            CardCard(c = c)
                            cardViewModel.getCard(c.cardId)
                        }
                    })
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            Button(
                modifier = Modifier
                    .width(360.dp)
                    .height(80.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (newCard.value) Color(0xFFFF9422) else Color(0xFFE9B9B6)
                ),
                onClick = {
                    navController.navigate(Screens.NewCard.route)
                    if (newCard.value) {
                    } else {
                        newCard.value = !newCard.value
                        savedCard.value = !newCard.value
                        cashDelivery.value = !newCard.value
                        selectedType = "NCard"
                    }
                },
            ) {
                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    imageVector = Icons.Filled.AddCircle,
                    tint = Color(0xFFE13646),
                    contentDescription = "New Card icon"
                )
                Text("New Card", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                modifier = Modifier
                    .width(360.dp)
                    .height(80.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (cashDelivery.value) Color(0xFFFF9422) else Color(
                        0xFFE9B9B6
                    )
                ),
                onClick = {
                    if (cashDelivery.value) {
                    } else {
                        cashDelivery.value = !cashDelivery.value
                        newCard.value = !cashDelivery.value
                        savedCard.value = !cashDelivery.value
                        selectedType = "Cash"
                    }
                },
            ) {
                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    imageVector = Icons.Filled.ShoppingCart,
                    tint = Color(0xFFE13646),
                    contentDescription = "Cash icon"
                )
                Text("Cash on delivery", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Delivery Address",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (accountAddresses.isEmpty()) {
                } else {
                    Button(
                        modifier = Modifier
                            .width(360.dp)
                            .height(80.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (savedAdd.value) Color(0xFFFF9422) else Color(
                                0xFFE9B9B6
                            )
                        ),
                        onClick = {
                            if (savedAdd.value) {
                            } else {
                                savedAdd.value = !savedAdd.value
                                newAdd.value = !savedAdd.value
                            }
                        },
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp),
                            imageVector = Icons.Filled.CheckCircle,
                            tint = Color(0xFFE13646),
                            contentDescription = "Saved Address"
                        )
                        Text("Saved Address", fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    if (savedAdd.value) {
                        LazyRow(modifier = Modifier.fillMaxWidth(), content = {
                            items(accountAddresses) { a ->
                                AddrCard(a)
                                addressViewModel.getAddress(a.addressId)
                            }
                        })
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                Button(
                    modifier = Modifier
                        .width(360.dp)
                        .height(80.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (newAdd.value) Color(0xFFFF9422) else Color(0xFFE9B9B6)
                    ),
                    onClick = {
                        navController.navigate(Screens.AddAddress.route)
                        if (newAdd.value) {
                        } else {
                            newAdd.value = !newAdd.value
                            savedAdd.value = !newAdd.value
                        }
                    },
                ) {
                    Icon(
                        modifier = Modifier
                            .size(30.dp),
                        imageVector = Icons.Filled.AddCircle,
                        tint = Color(0xFFE13646),
                        contentDescription = "New Address icon"
                    )
                    Text("New Address", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    modifier = Modifier
                        .width(360.dp)
                        .height(100.dp),
                    shape = RoundedCornerShape(15.dp),
                    elevation = 5.dp
                ) {
                    if(currentAddress.city!="") {
                        Column(
                            modifier = Modifier.background(color = Color(0xFFE9D2B6)),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = currentAddress.addressLine,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                            )
                            Text(
                                text = currentAddress.city + ", " + currentAddress.country,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                            )
                            Text(
                                text = "Zipcode: " + currentAddress.zipcode,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .width(360.dp),
                shape = RoundedCornerShape(15.dp),
                elevation = 5.dp
            ) {
                Column(
                    modifier = Modifier.background(color = Color(0xFFE9B9B6)),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Receipt:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "Subtotal: " + String.format("%.2f", totalDouble),
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "Voucher discount: " + (currentVoucher.percentage * 100) + " %",
                        fontSize = 20.sp,
                        color = Color(0xFFE13646),
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Total: " + String.format("%.2f", newTotal),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "Payment type: $selectedType",
                        fontSize = 20.sp,
                    )
                    if (selectedType.equals("Card")) {
                        Text(
                            text = "Card: ${currentCard.cardNo}",
                            fontSize = 20.sp,
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Button(modifier = Modifier
            .width(200.dp)
            .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFE13646)),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                if(currentAccount.accountType.equals("admin")){
                    Toast.makeText(toastContext, "Only Users Can Place Orders!", Toast.LENGTH_SHORT)
                        .show();
                }
                else if (currentCard.cardHolder.isEmpty() && (selectedType == "" || selectedType == "Card")) {
                    Toast.makeText(toastContext, "Choose method of payment!", Toast.LENGTH_SHORT)
                        .show();
                }else if(currentAddress.city=="")
                {
                    Toast.makeText(toastContext, "Choose Address!", Toast.LENGTH_SHORT)
                    .show();
                } else {
                    currentOrder.status = "Processed"
                    currentOrder.total = newTotal
                    currentOrder.paymentMode = selectedType



                    if (currentCard.cardHolder.isEmpty()) {
                        currentOrder.card = ""
                    } else {
                        currentOrder.card = currentCard.cardId
                    }
                    orderViewModel.setCurrentOrder(currentOrder)
                    orderViewModel.updateOrder(currentOrder)

                    cartItems.forEach() { c ->
                        if(c.checkedOut == false){
                            c.order = currentOrder.orderId
                        }
                        c.checkedOut = true
                        cartItemViewModel.updateCartItem(c)
                    }

                    cardViewModel.setCard(CreditCard("", "", "", "", "", ""))
                    navController.navigate(Screens.ThankYou.route)
                }
            }) {
            Text("Place Order", color = Color.White, fontSize = 20.sp)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
    }
}

@Composable
fun CardCard(
    c: CreditCard,
) {
    val cardViewModel =
        viewModel<CardViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    Card(
        backgroundColor = Color(0xFFFACDCD).copy(alpha = 0.6f),
        modifier = Modifier
            .width(300.dp)
            .padding(10.dp)
            .clickable(onClick = { cardViewModel.setCard(c) }),
    )
    {
        Column {
            Text(
                text = c.cardNo,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            Text(
                text = "Exp: " + c.expDate,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE13646),
            )
            Text(
                text = c.cardHolder,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE13646),
            )

        }
    }
}

@Composable
fun AddrCard(
    c: Address,
) {
    val addressViewModel =
        viewModel<AddressViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    Card(
        backgroundColor = Color(0xFFFACDCD).copy(alpha = 0.6f),
        modifier = Modifier
            .width(300.dp)
            .padding(10.dp)
            .clickable(onClick = { addressViewModel.setAddress(c) }),
    )
    {
        Column {
            Text(
                text = c.addressLine,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            Text(
                text = c.city + ", " + c.country,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE13646),
            )
            Text(
                text = "Zipcode: " + c.zipcode,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE13646),
            )

        }
    }
}
