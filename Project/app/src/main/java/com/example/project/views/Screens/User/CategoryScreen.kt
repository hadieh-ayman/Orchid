package com.example.project.views.Screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.project.R
import com.example.project.model.room.entity.Category
import com.example.project.viewModels.CategoryViewModel
import com.example.project.viewModels.ProductViewModel
import com.example.project.views.Navigation.Screens
import kotlinx.coroutines.flow.toList

//import coil.compose.rememberAsyncImagePainter

@Composable
fun CategoryScreen(navController: NavController) {
    ShowCatScreen(navController)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowCatScreen(navController: NavController) {
    val categoryViewModel =
        viewModel<CategoryViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    categoryViewModel.getAllCategories()
    val categories: List<Category> by categoryViewModel.categories.collectAsState()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            //scaffoldState = scaffoldState
        ) {
            Row(
                //modifier = Modifier.fillMaxSize(),
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
                        .padding(top = 20.dp, start = 170.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = { }) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp)
                                .clickable(onClick = { navController.navigate(Screens.Favorite.route) }
                                ),
                            imageVector = Icons.Outlined.Favorite,
                            tint = Color(0xFFE13646),
                            contentDescription = "heart icon"
                        )
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
            Text(
                "Categories",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                // modifier = Modifier,
                color = Color.Black,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
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
                    items(categories.size) { index ->
                        CategoryCards(categories[index], navController)
                    }
                }
            )
            //render bottom bar
        }
        //  }
    }
}

//@Preview
@Composable
fun CategoryCards(
    category: Category,
    navController: NavController
) {
    val categoryViewModel =
        viewModel<CategoryViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    Card(
        shape = RoundedCornerShape(10.dp),

        backgroundColor = Color(
            0xFFFA9C7F
        ),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable(onClick = {
                categoryViewModel.setCategory(category)
                navController.navigate(Screens.ResultsCatScreen.route)
            }
            ),
        elevation = 8.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 10.dp),
                painter = rememberAsyncImagePainter("${category.categoryImg}"),
                contentDescription = "gfg image",
            )
            Text(
                "${category.categoryName}",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}