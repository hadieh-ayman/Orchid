package com.example.project.views.Screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.project.R
import com.example.project.model.room.entity.Product
import com.example.project.viewModels.ProductViewModel
import com.example.project.views.Navigation.Screens

@Composable
fun ResultsPage(navController: NavController) {
    ShowResultScreen(navController)
}

@Composable
fun ShowResultScreen(navController: NavController) {
    val productsViewModel =
        viewModel<ProductViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val allProducts: List<Product> by productsViewModel.products.collectAsState()

    val searchWord = productsViewModel.search
    var products : MutableList<Product> = mutableListOf<Product>()
    allProducts.forEach(){p->
        if (p.productName.contains(searchWord, ignoreCase = true)){
            products.add(p)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        //scaffoldState = scaffoldState
    ) {
        Row(
            //modifier = Modifier.fillMaxSize(),
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    imageVector = Icons.Filled.ArrowBack,
                    tint = Color(0xFFE13646),
                    contentDescription = "back icon"
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 10.dp, start = 250.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
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
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
//        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                    //.weight(1f),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { navController.navigate(Screens.FilterProduct.route) }) {
                    Icon(
                        modifier = Modifier
                            .size(30.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_filter_alt_24),
                        tint = Color(0xFFE13646),
                        contentDescription = "Filter icon"
                    )
                }
                Text(
                    "Filter",
                    color = Color(0xFFE13646),
                    modifier = Modifier
                        .size(20.dp)
                        .padding(top=5.dp)
                )
            }
//        }

        LazyVerticalGrid(
            modifier = Modifier.height(620.dp),
            columns = GridCells.Fixed(2),

            // content padding
            contentPadding = PaddingValues(
                start = 0.dp,
                top = 16.dp,
                end = 0.dp,
                bottom = 2.dp
            ),
            content = {
                items(products) { p ->
                        ProductCard(p, navController)
                }
            }
        )
    }
}

@Composable
fun ProductCard(product: Product, navController: NavController) {
    val productViewModel =
        viewModel<ProductViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    Card(
        backgroundColor = Color(0xFFFFDBC1).copy(alpha = 0.6f),
        modifier = Modifier
            .width(140.dp)
            .padding(top = 20.dp, start = 8.dp)
            .clickable(onClick = {//set product= product in controller
                productViewModel.setCurrentProduct(product)
                navController.navigate(Screens.ProductInfo.route)
            }
            ),
        shape = RoundedCornerShape(20.dp),
        elevation = 5.dp

    )
    {
        Column(
            modifier = Modifier.background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
//            Icon(
//                modifier = Modifier
//                    .size(40.dp)
//                    .align(Alignment.End)
//                    .padding(top = 10.dp, end = 10.dp)
//                    .clickable(onClick = {}
//                    ),
//                painter = painterResource(id = R.drawable.ic_baseline_favorite_border_24),
//                tint = Color(0xFFE13646),
//                contentDescription = "heart icon"
//
//            )
            Image(
                //painter = rememberAsyncImagePainter("https://media.geeksforgeeks.org/wp-content/uploads/20210101144014/gfglogo.png"),
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = null,
                modifier = Modifier
                .size(80.dp)
            )

            Text(
                text = product.productName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.price.toString(),
                fontSize = 10.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Spacer(modifier = Modifier
                .height(4.dp)
                .padding(top = 20.dp))

        }
    }
}