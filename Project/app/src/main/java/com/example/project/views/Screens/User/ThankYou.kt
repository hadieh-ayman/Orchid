package com.example.project.views.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun ThankYou(navController: NavController) {
    ThankYouApp(navController)
}

@Composable
fun ThankYouApp(navController: NavController) {
    TopAppBar(
        backgroundColor = Color.White,
        contentColor = Color.White
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { navController.navigate(Screens.Home.route) }) {
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
            .padding(horizontal = 30.dp),
    ) {
        Image(
            modifier = Modifier
                .size(150.dp),
            contentDescription = null,
            painter = painterResource(R.drawable.logo),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "Thank You For Shopping with Orcid!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            "Your Order has been Placed.",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(modifier = Modifier
            .width(150.dp)
            .height(35.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFE13646)),
            shape = RoundedCornerShape(30.dp),
            onClick = {
                navController.navigate(Screens.OrderScreen.route)
            }) {
            Text("Track Order", color = Color.White)
        }
    }
}