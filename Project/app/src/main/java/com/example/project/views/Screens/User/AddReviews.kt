package com.example.project.views.Screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.project.model.room.entity.Account
import com.example.project.model.room.entity.Review
import com.example.project.model.room.entity.Product
import com.example.project.viewModels.AccountViewModel
import com.example.project.viewModels.ProductViewModel
import com.example.project.viewModels.ReviewViewModel
import com.example.project.views.Navigation.Screens
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt

@Composable
fun AddReview(navController: NavController) {
    AddReviewPage(navController)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddReviewPage(navController: NavController) {
    val reviewViewModel =
        viewModel<ReviewViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val productViewModel =
        viewModel<ProductViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val accountViewModel =
        viewModel<AccountViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)



    var toastContext = LocalContext.current

    val currentAccount: Account by accountViewModel.account.collectAsState()
    val currentProduct: Product by productViewModel.product.collectAsState()

    val editReview: Review by reviewViewModel.review.collectAsState()

    reviewViewModel.getAccountProductReview(currentProduct.productId, currentAccount.accountId)

    var selectedStarEdit by remember { mutableStateOf(editReview.rating.toString()) }

    var selectedStar by remember { mutableStateOf("") }

    val today = Clock.System.todayAt(TimeZone.currentSystemDefault())

    var reviewText by remember { mutableStateOf("") }

    var reviewTextEdit by remember { mutableStateOf(editReview.reviewText) }


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
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 10.dp),
                //horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = if(editReview.reviewId =="0"){"Add Review"}
                    else{ " Edit Review"},
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(currentProduct.image),
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = currentProduct.productName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Divider()
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Your Rating of this product",
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Your star Rating:", color = Color(0xFFF44336))

                    var expanded by remember { mutableStateOf(false) }
                    val suggestions = listOf("1", "2", "3", "4","5")
                    var textfieldSize by remember { mutableStateOf(Size.Zero) }



                    val icon = if (expanded)
                        Icons.Filled.KeyboardArrowUp
                    else
                        Icons.Filled.KeyboardArrowDown


                    Column() {
                        OutlinedTextField(
                            value =
                            if(editReview.reviewId!= "") {
                            selectedStarEdit}
                            else {
                                selectedStar
                            },
                            onValueChange =
                            {if(editReview.reviewId!= "") {
                                selectedStarEdit}
                            else {
                                selectedStar
                            }},
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    //This value is used to assign to the DropDown the same width
                                    textfieldSize = coordinates.size.toSize()
                                },
                            label = { Text("Select your ratting") },
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
                                    if(editReview.reviewId!= "") {
                                        selectedStarEdit = label}
                                    else {
                                        selectedStar = label
                                    }
                                        expanded = false
                                }) {
                                    Text(text = label)
                                }
                            }
                        }
                    }

//
                    Divider()
                }
                Spacer(modifier = Modifier.height(15.dp))
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "What do you like or dislike about this product? ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value =
                        if(editReview.reviewId!= "") {
                            reviewTextEdit}
                        else {
                            reviewText
                        },
                        onValueChange = {
                            if(editReview.reviewId!= "") {
                                reviewTextEdit = it}
                            else {
                                reviewText = it }
                           },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        maxLines = 10,
                        placeholder = { Text(text = "Write your review here") }
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                  if(currentAccount.accountType.equals("user")){
                    Button(
                    onClick = {

                        if(editReview.reviewId!= "") {
                            val finalReviewEdit = Review(
                                reviewId = editReview.reviewId,
                                datePosted= today.toString(),
                                reviewText = reviewTextEdit,
                                rating = selectedStarEdit.toInt(),
                                account = currentAccount.accountId,
                                product = currentProduct.productId
                            )
//                            reviewViewModel.updateReview(reviewTextEdit,selectedStarEditInt)
//                            val editReview: Review by reviewViewModel.review.collectAsState()
                            reviewViewModel.updateReview(finalReviewEdit)
                            Toast.makeText(toastContext,"Your review has been edited!", Toast.LENGTH_SHORT).show();
                            navController.navigate(Screens.ProductInfo.route)}
                        else {
                            if(selectedStar.equals("")){
                                Toast.makeText(toastContext,"Please enter your rating", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                val selectedStarInt = selectedStar.toInt()
                                val finalReview = Review(
                                    "random",
                                    today.toString(),
                                    reviewText,
                                    selectedStarInt,
                                    currentAccount.accountId,
                                    currentProduct.productId
                                )
                                reviewViewModel.addReview(finalReview)
                                Toast.makeText(toastContext,"Your review has been added!", Toast.LENGTH_SHORT).show();
                                navController.navigate(Screens.ProductInfo.route)
                            }
                        }
                   },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE13646))
                    ) {
                        Text(
                            text = "SUBMIT REVIEW",
                            color = Color.White,
                            modifier = Modifier.padding(
                                horizontal = 60.dp
                            )
                        )
                    }
                }}
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
            }
        }
    )



}
