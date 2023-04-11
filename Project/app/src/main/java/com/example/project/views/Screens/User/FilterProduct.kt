package com.example.project.views.Screens.User

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.project.viewModels.ProductViewModel
import com.example.project.views.Navigation.Screens

@Composable
fun FilterProduct(navController: NavController) {
    FilterProductApp(navController)
}

@Composable
fun FilterProductApp(navController: NavController) {
    var minimum by remember {
        mutableStateOf("")
    }
    var maximum by remember {
        mutableStateOf("")
    }
    val productViewModel =
        viewModel<ProductViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

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
            .padding(horizontal = 30.dp),
    ) {
        Text(
            text = "Choose the price range of filtered products",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Minimum price value:",
            fontSize = 25.sp,
        )
        OutlinedTextField(
            value = minimum,
            label = {
                Text("e.g. 20.0")
            },
            onValueChange = {
                minimum = it
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if(minimum!="")
            productViewModel.setMinValue(minimum.toDouble())
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Maximum price value:",
            fontSize = 25.sp,
        )
        OutlinedTextField(
            value = maximum,
            label = {
                Text("e.g. 100.0")
            },
            onValueChange = {
                maximum = it
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if(maximum!="")
            productViewModel.setMaxValue(maximum.toDouble())
        Spacer(modifier = Modifier.height(30.dp))
        Button(modifier = Modifier
            .width(100.dp)
            .height(45.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFE13646)),
            shape = RoundedCornerShape(30.dp),
            onClick = {
                navController.navigate(Screens.ResultsFilterScreen.route)
            }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_filter_alt_24),
                contentDescription = "Filter",
                modifier = Modifier
                    .size(30.dp)
                    .weight(1f),
                tint = Color.White,
            )
            Text("Filter", color = Color.White)
        }
    }
}