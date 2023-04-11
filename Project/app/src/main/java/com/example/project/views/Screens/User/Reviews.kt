package com.example.project.views.Screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
import java.lang.Math.ceil
import java.lang.Math.floor
import kotlin.math.roundToInt


@Composable
fun Reviews(navController: NavController) {
    showReviews(navController)
}

@Composable
fun showReviews(navController: NavController) {
    val reviewViewModel =
        viewModel<ReviewViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val productViewModel =
        viewModel<ProductViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val cartItemViewModel =
        viewModel<CartItemViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)



    val accountViewModel =
        viewModel<AccountViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val currentProduct: Product by productViewModel.product.collectAsState()

    val currentAccount: Account by accountViewModel.account.collectAsState()

    cartItemViewModel.getAllBoughtItems(currentAccount.accountId)

    val cartItems: List<CartItem> by cartItemViewModel.cartItems.collectAsState()

    reviewViewModel.getAccountProductReview(currentProduct.productId, currentAccount.accountId)

    //val ProductAccountReview: Review by reviewViewModel.ProductAccountReview.collectAsState()

    val ProductAccountReview: List<Review> by reviewViewModel.ProductAccountReview.collectAsState()

    val productReview : List<Review> by reviewViewModel.ProductReviews.collectAsState()

    var total = 0.0
    for(review in productReview){
        total = total + review.rating
    }
    var average = 0.0
    if(productReview.size != 0){
     average = total/productReview.size}

    val averageRounded = (average * 100.0).roundToInt() / 100.0


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
                    IconButton(onClick = {navController.navigateUp()}) {
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
        content = {  padding ->

            Column(
                modifier = Modifier
                    .padding(padding)
                    //.verticalScroll(rememberScrollState())
                    .background(Color.White)
                    .padding(padding),
                //horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = "Reviews",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
                //review summary
                Row() {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = " ${averageRounded}/5",
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp
                        )
                        Text(
                            text = "Based on ${productReview.size} reviews",
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Row() {
                            if (ceil(average) == floor(average)) {
                                repeat(average.toInt()) {
                                    Icon(
                                        modifier = Modifier
                                            .size(30.dp),
                                        imageVector = Icons.Filled.Star,
                                        tint = Color(0xFFFFA230),
                                        contentDescription = "full star icon"
                                    )}
                                repeat((5-average).toInt()){
                                    Icon(
                                        modifier = Modifier
                                            .size(30.dp),
                                        //imageVector = Icons.Filled.Star,
                                        painter = painterResource(id = R.drawable.star_outline),
                                        tint = Color(0xFFFFA230),
                                        contentDescription = "star icon"
                                    )}

                            } else {
                                repeat(average.toInt()) {
                                    Icon(
                                        modifier = Modifier
                                            .size(30.dp),
                                        imageVector = Icons.Filled.Star,
                                        tint = Color(0xFFFFA230),
                                        contentDescription = "full star icon"
                                    )}
                                    Icon(
                                        modifier = Modifier
                                            .size(30.dp),
                                        painter = painterResource(id = R.drawable.star_half),
                                        tint = Color(0xFFFFA230),
                                        contentDescription = "half star icon"
                                    )
                                repeat((5-(average)).toInt()){
                                    Icon(
                                        modifier = Modifier
                                            .size(30.dp),
                                        //imageVector = Icons.Filled.Star,
                                        painter = painterResource(id = R.drawable.star_outline),
                                        tint = Color(0xFFFFA230),
                                        contentDescription = "star icon"
                                    )}
                            }


                        }
                    }


                }
                Spacer(modifier = Modifier.height(10.dp))

                Divider()

                Column {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        cartItems.forEach{c ->
            if(c.product == currentProduct.productId && c.checkedOut){
                if (ProductAccountReview == null) {
                    IconButton(onClick = { navController.navigate(Screens.AddReview.route) }) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp),
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add icon"
                        )
                    }
                    Text(
                        text = "Add Review",
                        modifier = Modifier
                            .padding(start = 10.dp, end = 160.dp)
                            .clickable { navController.navigate(Screens.AddReview.route) },
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    IconButton(onClick = { navController.navigate(Screens.AddReview.route) }) {
                        Icon(
                            modifier = Modifier
                                .size(35.dp),
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = "forward arrow icon"
                        )
                    }
                    Divider()
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Divider()
                        Text("Your Review:")
                        val PAReview: List<Review> = ProductAccountReview
                        reviewCard(navController = navController, reviews = PAReview)
                        Row() {
                            Button(
                                onClick = {
                                    reviewViewModel.setCurrentReview(ProductAccountReview[0])
                                    navController.navigate(Screens.AddReview.route)
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF7B95F)),
                                modifier = Modifier.padding(end = 15.dp)
                            )
                            {
                                Text(
                                    text = "Edit review",
                                    color = Color.White,
                                )
                            }
                            Button(
                                onClick = {
                                    reviewViewModel.deleteReview(ProductAccountReview[0])
                                    navController.navigate(Screens.Reviews.route)
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE13646))
                            )
                            {
                                Text(
                                    text = "Delete review",
                                    color = Color.White,
//                            )
                                )
                            }
                        }
                        Divider()
                    }
                }
            }
        }

    }

}
            Spacer(modifier = Modifier.height(10.dp))
            Text("All Reviews:")
            reviewCard(navController, productReview)
            }
        }
    )
}


@Composable
fun reviewCard(
    navController: NavController,
    reviews: List<Review>,
    ){

    val accountViewModel =
        viewModel<AccountViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val accountReview: Account by accountViewModel.reviewAccount.collectAsState()
    val accounts: List<Account> by accountViewModel.accounts.collectAsState()
    var currentAccount = Account("", "", "","", "", "")


    if(reviews.size != 0){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        content = {
                    items(reviews) { r ->
                        var stars = r.rating
                        accounts.forEach(){
                                a -> if(a.accountId.equals(r.account)){currentAccount =a}
                        }
                        //accountViewModel.getAccountById(r.account)
                       // val accountReview: Account by accountViewModel.reviewAccount.collectAsState()

                        Column(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFF3F6F8)
                                )
                                .fillMaxWidth()
                                .padding(5.dp)
                        ) {
                            Row() {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = currentAccount.username,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        text = r.datePosted,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 12.sp
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Row() {
                                    while(stars>0){
                                        Icon(
                                            modifier = Modifier
                                                .size(25.dp),
                                            imageVector = Icons.Filled.Star,
                                            tint = Color(0xFFFFA230),
                                            contentDescription = "full star icon"
                                        )
                                        stars = stars-1
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = r.reviewText,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        }
                    }

        })
}}

//
//@Composable
//fun SortDropDown() {
//    // Declaring a boolean value to store
//    // the expanded state of the Text Field
//    var mExpanded by remember { mutableStateOf(false) }
//
//    // Create a list of cities
//    val mList = listOf("Recent", "Highest", "Lowest", "All")
//
//    // Create a string value to store the selected city
//    var mSelectedText by remember { mutableStateOf("") }
//
//    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
//
//    // Up Icon when expanded and down icon when collapsed
//    val icon = if (mExpanded)
//        Icons.Filled.KeyboardArrowUp
//    else
//        Icons.Filled.KeyboardArrowDown
//
//    Column(Modifier.padding(start = 10.dp)) {
//
//        // Create an Outlined Text Field
//        // with icon and not expanded
//        OutlinedTextField(
//            value = mSelectedText,
//            onValueChange = { mSelectedText = it },
//            modifier = Modifier
//                .fillMaxWidth()
//                .onGloballyPositioned { coordinates ->
//                    // This value is used to assign to
//                    // the DropDown the same width
//                    mTextFieldSize = coordinates.size.toSize()
//                },
//            // label = { Text("Recent") },
//            trailingIcon = {
//                Icon(icon, "contentDescription",
//                    Modifier.clickable { mExpanded = !mExpanded })
//            }
//        )
//
//        // Create a drop-down menu with list of cities,
//        // when clicked, set the Text Field text as the city selected
//        DropdownMenu(
//            expanded = mExpanded,
//            onDismissRequest = { mExpanded = false },
//            modifier = Modifier
//                .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
//        ) {
//            mList.forEach { label ->
//                DropdownMenuItem(onClick = {
//                    mSelectedText = label
//                    mExpanded = false
//                }) {
//                    Text(text = label)
//                }
//            }
//        }
//    }
//}
//


//Column(
//modifier = Modifier.padding(horizontal = 10.dp),
//) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            text = "5 stars",
//            fontWeight = FontWeight.Bold,
//            fontSize = 15.sp
//        )
//        LinearProgressIndicator(
//            progress = 0.8f,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 5.dp, horizontal = 5.dp),
//            color = Color(0xFFFFA230)
//        )
//    }
//    Row(
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            text = "4 stars",
//            fontWeight = FontWeight.Bold,
//            fontSize = 15.sp
//        )
//        LinearProgressIndicator(
//            progress = 0.6f,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 5.dp, horizontal = 5.dp),
//            color = Color(0xFFFFA230)
//        )
//    }
//    Row(
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            text = "3 stars",
//            fontWeight = FontWeight.Bold,
//            fontSize = 15.sp
//        )
//        LinearProgressIndicator(
//            progress = 0.3f,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 5.dp, horizontal = 5.dp),
//            color = Color(0xFFFFA230)
//        )
//    }
//    Row(
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            text = "2 stars",
//            fontWeight = FontWeight.Bold,
//            fontSize = 15.sp
//        )
//        LinearProgressIndicator(
//            progress = 0.1f,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 5.dp, horizontal = 5.dp),
//            color = Color(0xFFFFA230)
//        )
//    }
//    Row(
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            text = "1 stars",
//            fontWeight = FontWeight.Bold,
//            fontSize = 15.sp
//        )
//        LinearProgressIndicator(
//            progress = 0.2f,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 5.dp, horizontal = 5.dp),
//            color = Color(0xFFFFA230)
//        )
//    }
//}
//}
//Spacer(modifier = Modifier.height(10.dp))
//Column {
//    Divider()
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        IconButton(onClick = { navController.navigate(Screens.AddReview.route) }) {
//            Icon(
//                modifier = Modifier
//                    .size(30.dp),
//                imageVector = Icons.Filled.Add,
//                contentDescription = "Add icon"
//            )
//        }
//        Text(
//            text = "Add Review",
//            modifier = Modifier.padding(start = 10.dp, end = 160.dp),
//            fontWeight = FontWeight.Bold,
//            fontSize = 20.sp
//        )
//        IconButton(onClick = { /*TODO*/ navController.navigateUp()}) {
//            Icon(
//                modifier = Modifier
//                    .size(35.dp),
//                imageVector = Icons.Filled.ArrowForward,
//                contentDescription = "forward arrow icon"
//            )
//        }
//    }
//    Divider()
//}
//Spacer(modifier = Modifier.height(10.dp))
//Column(
//modifier = Modifier.padding(horizontal = 10.dp)
//) {
////                    Row {
////                        Text(
////                            text = "User Ratings",
////                            modifier=Modifier.padding(top=20.dp),
////                            fontWeight = FontWeight.Bold,
////                            fontSize = 16.sp
////                        )
////                        SortDropDown()
////                    }
//    Spacer(modifier = Modifier.height(20.dp))