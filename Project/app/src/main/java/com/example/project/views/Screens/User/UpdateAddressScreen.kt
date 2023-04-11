package com.example.project.views.Screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.project.views.Navigation.Screens


@Composable
fun UpdateAddressScreen(navController: NavController) {
    UpdateAddressApp(navController)
}


@Composable
fun UpdateAddressApp(navController: NavController) {
    val scaffoldState = rememberScaffoldState()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) { padding ->
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            tint = Color(0xFFE13646),
            contentDescription = "lock icon",
            modifier = Modifier
                .padding(padding)
                .clickable(onClick = {
                    navController.navigate(Screens.SignUp.route)
                })
        )

        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))
            Text("Update Address", fontSize = 35.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Update the address information", color = Color(0xFFE13646))
            Spacer(modifier = Modifier.height(40.dp))

            AddressLineTextField()

            CityTextField()

            CountriesOptions()

            ZipTextField()


            val scope = rememberCoroutineScope()

            Button(modifier = Modifier
                .width(3250.dp)
                .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFE13646)),
                onClick = {
                    navController.navigate(Screens.Login.route)
                }) {
                Text("Update", color = Color.White)
            }

        }
    }

}