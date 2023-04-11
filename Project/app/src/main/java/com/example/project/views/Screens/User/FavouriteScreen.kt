package com.example.project.views.Screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project.R
import com.example.project.views.Navigation.Screens

@Composable
fun FavouriteScreen(navController: NavController) {
    FavoriteApp(navController)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteApp(navController: NavController) {
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
        Row(
            modifier = Modifier
                //.padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top,
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 0.dp, start = 170.dp),
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
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            "Your Favorites",
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp),
            color = Color.Black,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )

        val list = (1..4).map { it.toString() }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),

            contentPadding = PaddingValues(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
            content = {
                items(list.size) { index ->
                    ItemCard(navController)
                }
            })
    }

}


@Composable
fun ItemCard(navController: NavController) {

    Card(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .padding(20.dp, 20.dp, 20.dp, 20.dp)
            .fillMaxSize()
            .clickable(onClick = { navController.navigate(Screens.ProductInfo.route) }
            ),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    )

    {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        modifier = Modifier
                            .size(20.dp),
                        imageVector = Icons.Filled.Favorite,
                        tint = Color(0xFFE13646),
                        contentDescription = "heart icon",
                    )
                }
            }
            Image(
                painter = painterResource(R.drawable.shirt),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
            )

            Text(
                text = "Name",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Price QAR",
                fontSize = 10.sp,
            )
        }
    }
}