package com.example.project.views.Screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.project.R
import com.example.project.model.room.entity.Account
import com.example.project.model.room.entity.CartItem
import com.example.project.model.room.entity.Product
import com.example.project.model.room.entity.Review
import com.example.project.viewModels.AccountViewModel
import com.example.project.viewModels.CartItemViewModel
import com.example.project.viewModels.ProductViewModel
import com.example.project.viewModels.ReviewViewModel
import com.example.project.views.Navigation.Screens

@Composable
fun ProductInfo(navController: NavController) {
    ProductPage(navController)
}

@Composable
fun ProductPage(navController: NavController) {
    var mExpanded by remember { mutableStateOf(false) }
    val qList = listOf(1,2,3,4,5,6,7,8,9,10)
    var qSelectedText by remember { mutableStateOf(1) }
    var qTextFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    var size by remember { mutableStateOf("M") }
    var color by remember { mutableStateOf("Black") }
    val reviewViewModel =
        viewModel<ReviewViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val productViewModel =
        viewModel<ProductViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    var currentCartItem: CartItem? = null
    val cartItemViewModel =
        viewModel<CartItemViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val currentProduct: Product by productViewModel.product.collectAsState()
    reviewViewModel.getReviewsOfProduct(currentProduct.productId)
    val accountViewModel =
        viewModel<AccountViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val currentAccount: Account by accountViewModel.account.collectAsState()

    val ProductView: Product by productViewModel.ProductView.collectAsState()
    cartItemViewModel.getAllCartItems(currentAccount.accountId)
    val cartItems: List<CartItem> by cartItemViewModel.cartItems.collectAsState()

    val productReview : List<Review> by reviewViewModel.ProductReviews.collectAsState()
    var total = 0.0
    for(review in productReview){
        total = total + review.rating
    }
    var average = 0.0
    if(productReview.size != 0){
        average = total/productReview.size}



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
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
        },
        content = { padding ->
            //Full Content
            Column(
                modifier = Modifier
                    .padding(padding)
                    .background(Color.White)
                    .padding(horizontal = 10.dp)
                    .verticalScroll(rememberScrollState()),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                //image
                Image(
                    painter = rememberAsyncImagePainter(currentProduct.image),
                    contentDescription = null,
                    modifier = Modifier.size(250.dp)
                )
                //title and reviews
                Row(
                    //modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    Column(modifier = Modifier.padding(end = 25.dp)) {
                        var discountedPrice = currentProduct.price * (1-currentProduct.discount)
                        var discountPercentage = currentProduct.discount *100
                        Text(
                            text = currentProduct.productName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color(0xFF505050)
                        )
                        if(currentProduct.discount > 0){
                            Text(
                                text = String.format("%.2f", currentProduct.price) + " QAR",
                                fontWeight = FontWeight.Bold,
                                style = TextStyle(textDecoration = TextDecoration.LineThrough),
                                fontSize = 28.sp
                            )
                            Text(
                                text = String.format("%.2f", discountedPrice) + " QAR",
                                fontWeight = FontWeight.Bold,
                                color = Color.Red,
                                fontSize = 30.sp
                            )
                            Text(
                                text = "%$discountPercentage",
                                fontWeight = FontWeight.Bold,
                                color = Color.Red,
                                fontSize = 20.sp
                            )
                        } else{
                            Text(
                                text = String.format("%.2f", currentProduct.price) + " QAR",
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp
                            )
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Row() {
                            if (Math.ceil(average) == Math.floor(average)) {
                                repeat(average.toInt()) {
                                    Icon(
                                        modifier = Modifier
                                            .size(30.dp),
                                        imageVector = Icons.Filled.Star,
                                        tint = Color(0xFFFFA230),
                                        contentDescription = "full star icon"
                                    )
                                }
                                repeat((5 - average).toInt()) {
                                    Icon(
                                        modifier = Modifier
                                            .size(30.dp),
                                        //imageVector = Icons.Filled.Star,
                                        painter = painterResource(id = R.drawable.star_outline),
                                        tint = Color(0xFFFFA230),
                                        contentDescription = "star icon"
                                    )
                                }

                            } else {
                                repeat(average.toInt()) {
                                    Icon(
                                        modifier = Modifier
                                            .size(30.dp),
                                        imageVector = Icons.Filled.Star,
                                        tint = Color(0xFFFFA230),
                                        contentDescription = "full star icon"
                                    )
                                }
                                Icon(
                                    modifier = Modifier
                                        .size(30.dp),
                                    painter = painterResource(id = R.drawable.star_half),
                                    tint = Color(0xFFFFA230),
                                    contentDescription = "half star icon"
                                )
                                repeat((5 - (average)).toInt()) {
                                    Icon(
                                        modifier = Modifier
                                            .size(30.dp),
                                        //imageVector = Icons.Filled.Star,
                                        painter = painterResource(id = R.drawable.star_outline),
                                        tint = Color(0xFFFFA230),
                                        contentDescription = "star icon"
                                    )
                                }
                            }
                        }
//                        Icon(
//                            modifier = Modifier
//                                .size(30.dp),
//                            imageVector = Icons.Filled.ArrowForward,
//                            contentDescription = "forward arrow icon"
//                        )
                        Text(
                            text = "See Reviews >",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 15.sp,
                            modifier = Modifier.clickable(onClick = { navController.navigate(Screens.Reviews.route) })
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                //color
                Column(
                    modifier = Modifier.padding(end = 230.dp)
                ) {
                    Row() {
                        Text(
                            text = "Color: ",
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                        Text(
                            text = "$color",
                            fontWeight = FontWeight.Normal,
                            fontSize = 25.sp
                        )
                    }
                    Row() {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_circle_24),
                            tint = Color.Black,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(
                                    horizontal = 2.dp
                                )
                                .clickable(onClick = { color = "Black" }),
                            contentDescription = "circle icon"
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_circle_24),
                            tint = Color.Red,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(
                                    horizontal = 2.dp
                                )
                                .clickable(onClick = { color = "Red" }),
                            contentDescription = "circle icon"
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_circle_24),
                            tint = Color.Blue,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(
                                    horizontal = 2.dp
                                )
                                .clickable(onClick = { color = "Blue" }),
                            contentDescription = "circle icon"
                        )
                    }

                }
                Spacer(modifier = Modifier.height(10.dp))
                //size
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row() {
                        Text(
                            text = "Size: ",
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                        Text(
                            text = "$size",
                            fontWeight = FontWeight.Normal,
                            fontSize = 25.sp
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(onClick = { /*TODO*/ }, shape = RoundedCornerShape(15.dp)) {
                            Text(
                                text = "XS",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .clickable(onClick = { size = "XS" })
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        OutlinedButton(onClick = { /*TODO*/ }, shape = RoundedCornerShape(15.dp)) {
                            Text(
                                text = "S",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .clickable(onClick = { size = "S" })
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        OutlinedButton(onClick = { /*TODO*/ }, shape = RoundedCornerShape(15.dp)) {
                            Text(
                                text = "M",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .clickable(onClick = { size = "M" })
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        OutlinedButton(onClick = { /*TODO*/ }, shape = RoundedCornerShape(15.dp)) {
                            Text(
                                text = "L",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .clickable(onClick = { size = "L" })
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        OutlinedButton(onClick = { /*TODO*/ }, shape = RoundedCornerShape(15.dp)) {
                            Text(
                                text = "XL",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .clickable(onClick = { size = "XL" })
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                //quantity
                Row {
                    Text(
                        text = "Quantity: ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                    Column(Modifier.padding(start = 10.dp)) {

                        OutlinedTextField(
                            value = "$qSelectedText",
                            onValueChange = { qSelectedText = it as Int },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    // This value is used to assign to
                                    // the DropDown the same width
                                    qTextFieldSize = coordinates.size.toSize()
                                },
                            label = { Text("$qSelectedText") },
                            trailingIcon = {
                                Icon(icon, "contentDescription",
                                    Modifier.clickable { mExpanded = !mExpanded })
                            }
                        )

                        // Create a drop-down menu with list of cities,
                        // when clicked, set the Text Field text as the city selected
                        DropdownMenu(
                            expanded = mExpanded,
                            onDismissRequest = { mExpanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current) { qTextFieldSize.width.toDp() })
                        ) {
                            qList.forEach { label ->
                                DropdownMenuItem(onClick = {
                                    qSelectedText = label
                                    mExpanded = false
                                }) {
                                    Text(text = "$label")
                                }
                            }
                        }
                    }
                }
                if (currentAccount.accountType.equals("admin",ignoreCase = true)) {
                    Button(
                        onClick = {
                            //productViewModel.setCurrentProduct(currentProduct)
                            navController.navigate(route = Screens.EditProductAdmin.route)},
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF7B95F)),
                        modifier = Modifier.padding(end = 15.dp)
                    )
                    {
                        Text(
                            text = "Edit Product",
                            color = Color.White,
                        )
                    }
                    Button(
                        onClick = {
                            productViewModel.deleteProduct(currentProduct)
                            navController.navigate(route = Screens.Home.route)},
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE13646))
                    )
                    {
                        Text(
                            text = "Delete product",
                            color = Color.White,

                        )
                    }
                } else {
                    Button(
                        modifier = Modifier.fillMaxWidth().padding(top = 50.dp, bottom = 50.dp),
                        onClick = {
                            cartItems.forEach() { c ->
                                if (c.size == size && c.color == color && c.product == currentProduct.productId && c.account == currentAccount.accountId) {
                                    currentCartItem = c
                                }
                            }
                            if(currentCartItem!=null) {
                                    currentCartItem!!.quantity+=qSelectedText
                                    cartItemViewModel.updateCartItem(currentCartItem!!)
//                                    cartItemViewModel.getCartItemProduct(100,100,"Yellow",size)
                            }
                            else{
                                    cartItemViewModel.addCartItem(
                                        CartItem(
                                            "",
                                            qSelectedText,
                                            color,
                                            size,
                                            false,
                                            currentProduct.price,
                                            currentProduct.productId,
                                            currentAccount.accountId,
                                            ""
                                        )
                                    )
                                }
                            navController.navigateUp()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE13646))
                    ) {
                        Text(
                            text = "ADD TO CART",
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(
                                horizontal = 90.dp,
                            ).padding(top = 10.dp, bottom = 10.dp)
                        )
                    }
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                backgroundColor = Color.White,
                contentColor = Color.White
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = { }) {
                        Icon(
                            modifier = Modifier
                                .size(40.dp),
                            imageVector = Icons.Outlined.Favorite,
                            tint = Color(0xFFE13646),
                            contentDescription = "heart icon",
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    )
}


