package com.example.project.views.Screens.User

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.R
import com.example.project.model.room.entity.Category
import com.example.project.model.room.entity.Product
import com.example.project.viewModels.CategoryViewModel
import com.example.project.viewModels.ProductViewModel
import com.example.project.views.Navigation.Screens
import com.example.project.views.Screens.ProductCard

@Composable
fun ResultsCatScreen(navController: NavController) {
    ResultCatApp(navController)
}

@Composable
fun ResultCatApp(navController: NavController) {
    val productsViewModel =
        viewModel<ProductViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val allProducts: List<Product> by productsViewModel.products.collectAsState()
    var products : MutableList<Product> = mutableListOf<Product>()
    val categoryViewModel =
        viewModel<CategoryViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val currentCategory: Category by categoryViewModel.category.collectAsState()
    allProducts.forEach(){p->
            if (p.category==currentCategory.categoryId) {
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
                    .padding(top = 20.dp, start = 170.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    imageVector = Icons.Outlined.Favorite,
                    tint = Color(0xFFE13646),
                    contentDescription = "heart icon"
                )
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = Modifier
                            .size(30.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                        tint = Color(0xFFE13646),
                        contentDescription = "sort icon"
                    )
                }
                Text(
                    "Sort",
                    color = Color(0xFFE13646),
                    modifier = Modifier
                        .size(20.dp)
                        .width(5.dp),
                )
            }
            Spacer(modifier = Modifier
                .width(5.dp)
                .padding(end = 20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { navController.navigate(Screens.FilterProduct.route) }) {
                    Icon(
                        modifier = Modifier
                            .size(30.dp)
                            .padding(start = 10.dp),
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
                        .weight(1f)
                )
            }
        }

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
//                    if(currentCategory.categoryName=="All")
//                        ProductCard(p, navController)
                }
            }
        )
    }
}