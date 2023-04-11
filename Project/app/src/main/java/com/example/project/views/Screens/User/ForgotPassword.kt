package com.example.project.views.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project.R

@Composable
fun ForgotPassword(navController: NavController) {
    ForgotPasswordApp(navController)
}

@Composable
fun ForgotPasswordApp(navController: NavController) {
    var EmailFieldState by remember {
        mutableStateOf("")
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
    ) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(

                modifier = Modifier
                    .size(30.dp)
                    .align(alignment = Start),
                imageVector = Icons.Filled.ArrowBack,
                tint = Color(0xFFE13646),
                contentDescription = "Arrow icon"
            )
        }
        Image(
            modifier = Modifier
                .size(100.dp),
            //.padding(end=80.dp),
            contentDescription = null,
            painter = painterResource(R.drawable.logo),
        )

        Text(
            "Enter your email. The link to change your password will be sent to your email",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        OutlinedTextField(
            value = EmailFieldState,
            label = {
                Text("Enter Email")
            },
            onValueChange = {
                EmailFieldState = it
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(modifier = Modifier
            .width(3250.dp)
            .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFE13646)),
            onClick = {

            }) {
            Text("Send email", color = Color.White)
        }

    }
}
