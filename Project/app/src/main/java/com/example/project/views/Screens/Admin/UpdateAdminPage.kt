package com.example.project.views.Screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun UpdateAdminScreen(navController: NavController) {
    UpdateAdminApp(navController)
}

@Composable
fun UpdateAdminApp(navController: NavController) {
    Row(
        modifier = Modifier
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.Start,
    ) {
        IconButton(onClick = { }) {
            Icon(

                modifier = Modifier
                    .size(30.dp),
                imageVector = Icons.Filled.ArrowBack,
                tint = Color(0xFFE13646),
                contentDescription = "Arrow icon"
            )
        }

    }
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Text(
            "Update Account",
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp, 50.dp, 0.dp, 10.dp),
            color = Color.Black,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
        )

        InfoBox()
        Spacer(modifier = Modifier.height(10.dp))
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(200.dp, 510.dp, 10.dp, 10.dp)
    ) {
        Button(modifier = Modifier
            .width(150.dp)
            .height(30.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFE13646)),
            onClick = {

            }) {
            Text(
                "Update Password",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 770.dp, 20.dp, 10.dp)
    ) {
        Button(modifier = Modifier
            .width(350.dp)
            .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFE13646)),
            onClick = {

            }) {
            Text("Update", color = Color.White)
        }
    }
}

@Composable
fun InfoBox() {

    Card(
        modifier = Modifier
            .width(350.dp)
            .height(400.dp)
            .padding(30.dp, 10.dp, 0.dp, 0.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    )
    {
        Column(
            modifier = Modifier.background(color = Color(0xFFE9B9B6)),
            //horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 0.dp, 5.dp),
                text = "Edit your personal information",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))

            var UsernameFieldState by remember {
                mutableStateOf("")
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 0.dp, 5.dp),
                text = "Username",
                color = Color(0xFFE13646),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )

            OutlinedTextField(
                value = UsernameFieldState,
                label = {
                    Text("Haya")
                },
                leadingIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            tint = Color(0xFFE13646),
                            contentDescription = "User icon"
                        )
                    }
                },
                onValueChange = {
                    UsernameFieldState = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            var EmailFieldState by remember {
                mutableStateOf("")
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 0.dp, 5.dp),
                text = "Email",
                color = Color(0xFFE13646),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
            OutlinedTextField(
                value = EmailFieldState,
                label = {
                    Text("haya@qu.edu.qa")
                },
                leadingIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            tint = Color(0xFFE13646),
                            contentDescription = "Email icon"
                        )
                    }
                },
                onValueChange = {
                    UsernameFieldState = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            var PhoneFieldState by remember {
                mutableStateOf("")
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 10.dp, 0.dp, 5.dp),
                text = "Phone",
                color = Color(0xFFE13646),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
            OutlinedTextField(
                value = PhoneFieldState,
                label = {
                    Text("77223344")
                },
                leadingIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Phone,
                            tint = Color(0xFFE13646),
                            contentDescription = "Phone icon"
                        )
                    }
                },
                onValueChange = {
                    UsernameFieldState = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

