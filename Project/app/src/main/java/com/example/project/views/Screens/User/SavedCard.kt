package com.example.project.views.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SavedCard(navController: NavController) {
    SavedCardApp(navController)
}

@Composable
fun SavedCardApp(navController: NavController) {
    var textFieldState by remember {
        mutableStateOf("")
    }
    var textFieldState1 by remember {
        mutableStateOf("")
    }
    var textFieldState2 by remember {
        mutableStateOf("")
    }
    var textFieldState3 by remember {
        mutableStateOf("")
    }
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
            text = "Saved Card Information",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
        )
        Card(
            modifier = Modifier
                .width(450.dp)
                .height(550.dp)
                .padding(30.dp, 30.dp, 0.dp, 0.dp),
            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp
        )
        {
            Column(
                modifier = Modifier.background(color = Color(0xFFE9B9B6)),
                verticalArrangement = Arrangement.Center,
                //horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Card Number",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = textFieldState,
                    label = {
                        Text("e.g 4444 5555 0000 8975")
                    },
                    onValueChange = {
                        textFieldState = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Expiration Date",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = textFieldState1,
                    label = {
                        Text("MM/YY")
                    },
                    onValueChange = {
                        textFieldState1 = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "CVV",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = textFieldState2,
                    label = {
                        Text("3 or 4 digits on the back of the card")
                    },
                    onValueChange = {
                        textFieldState2 = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Name on Card",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = textFieldState3,
                    label = {
                        Text("e.g Maha Ahmed")
                    },
                    onValueChange = {
                        textFieldState3 = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(modifier = Modifier
            .width(150.dp)
            .height(40.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFE13646)),
            shape = RoundedCornerShape(30.dp),
            onClick = {/*TODO*/
                navController.navigateUp()
            }) {
            Text("Edit", color = Color.White, fontSize = 18.sp)
        }
    }
}