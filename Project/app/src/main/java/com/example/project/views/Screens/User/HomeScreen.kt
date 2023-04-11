package com.example.project.views.Screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.project.R
import com.example.project.model.room.entity.Account
import com.example.project.model.room.entity.Product
import com.example.project.viewModels.AccountViewModel
import com.example.project.viewModels.ProductViewModel
import com.example.project.views.Navigation.Screens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeScreen(navController: NavController) {
    HomeScreenApp(navController)
}


@Composable
fun HomeScreenApp(navController: NavController) {
    val productViewModel =
        viewModel<ProductViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val accountViewModel =
        viewModel<AccountViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    productViewModel.getAllProducts()
    val products: List<Product> by productViewModel.products.collectAsState()
    val currentAccount = accountViewModel.getCurrentAccount()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {

        Searchbar(navController)

        Text(
            "Welcome " + currentAccount.username + "!",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE13646)),
            color = Color.White,
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )

        Image(
            contentDescription = null,
            painter = painterResource(R.drawable.homebanner),
        )

        NewIn(navController, products)
        BestSellers(navController, products)

        Image(
            contentDescription = null,
            painter = painterResource(R.drawable.coupon),
        )
        BasedOnFavs(navController, products)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )

    }


}


@Composable
fun NewIn(
    navController: NavController,
    products: List<Product>,
    productViewModel: ProductViewModel = viewModel(LocalContext.current as ComponentActivity)
    ) {
    val list = (1..10).map { it.toString() }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(235.dp)
            .background(Color(0xFFFFEDDF))
    )
    {
        Text(
            "NEW IN",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color(0xFFBE4721)
        )
        if (products.isEmpty()) { Text("products list is empty")
        } else {
            LazyRow(){
                items(products) { p ->
                    ProductCard(navController, p)
                }
            }
        }
    }
}

@Composable
fun ProductCard(
    navController: NavController,
    product: Product,
    productViewModel: ProductViewModel = viewModel(LocalContext.current as ComponentActivity)
) {

    Card(
        modifier = Modifier
            .width(150.dp)
            .height(220.dp)
            .padding(top = 40.dp, start = 30.dp)
            .clickable(onClick = {
                productViewModel.setCurrentProduct(product)
                navController.navigate(Screens.ProductInfo.route) }
            ),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    )
    {
        Column(
            modifier = Modifier.background(Color.White).padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
            )

            Text(
                text = product.productName,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
            )

            Spacer(modifier = Modifier.height(4.dp))
            var discountedPrice = product.price * (1-product.discount)
            if(product.discount > 0){
                Text(
                    text = String.format("%.2f", product.price) + " QAR",
                    fontSize = 12.sp,
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Text(
                    text = String.format("%.2f", discountedPrice) + " QAR",
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }else{
                Text(
                    text = String.format("%.2f", product.price) + " QAR",
                    fontSize = 12.sp,
                )
            }

        }
    }
}

@Composable
fun BasedOnFavs(  navController: NavController,
                  products: List<Product>,
                  productViewModel: ProductViewModel = viewModel(LocalContext.current as ComponentActivity)) {
    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(235.dp)
                .background(Color(0xFFFFE5E3))
        )

        Text(
            "BASED ON YOUR FAVOURITES",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE13646)
        )
        if (products.isEmpty()) { Text("products list is empty")
        } else {
            LazyRow(){
                items(products) { p ->
                    ProductCard(navController, p)
                }
            }
        }
    }
}


@Composable
fun BestSellers(  navController: NavController,
                  products: List<Product>,
                  productViewModel: ProductViewModel = viewModel(LocalContext.current as ComponentActivity)) {
    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        )

        Text(
            "BEST SELLERS",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color(0xFFE13646)
        )

        if (products.isEmpty()) { Text("products list is empty")
        } else {
            LazyRow(){
                items(products) { p ->
                    ProductCard(navController, p)
                }
            }
        }
    }
}

@Composable
fun Searchbar(navController: NavController) {

    val accountViewModel =
        viewModel<AccountViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val currentAccount: Account by accountViewModel.account.collectAsState()


    Row(
    ) {
        Image(
            modifier = Modifier
                .size(80.dp),
            //.padding(end=80.dp),
            contentDescription = null,
            painter = painterResource(R.drawable.logo),
        )
        Row(
            modifier = Modifier
                .padding(top = 20.dp, start = 230.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            if (currentAccount.accountType == "admin"){
            IconButton(onClick = { navController.navigate(Screens.AddProductAdmin.route) }) {
                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    imageVector = Icons.Outlined.Add,
                    tint = Color(0xFFE13646),
                    contentDescription = "Add icon"
                )
            }
//            } else{
//                IconButton(onClick = { navController.navigate(Screens.Favorite.route) }) {
//                    Icon(
//                        modifier = Modifier
//                            .size(30.dp),
//                        imageVector = Icons.Outlined.Favorite,
//                        tint = Color(0xFFE13646),
//                        contentDescription = "heart icon"
//                    )
//                }


            }
            IconButton(onClick = { navController.navigate(Screens.Search.route) }) {
                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    imageVector = Icons.Filled.Search,
                    tint = Color(0xFFE13646),
                    contentDescription = "search icon"
                )


            }
        }
    }
}




