package com.example.project.views.Screens

import androidx.activity.ComponentActivity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.model.room.entity.Account
import com.example.project.viewModels.AccountViewModel
import com.example.project.views.Navigation.Screens

@Composable
fun UpdatePersonalScreen(navController: NavController) {
    UpdatePersonalApp(navController)
}

@Composable
fun UpdatePersonalApp(navController: NavController) {
    val accountViewModel =
        viewModel<AccountViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val currentAccount: Account by accountViewModel.account.collectAsState()
    var updatedInfo = emptyList<String>()

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

    }
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Text(
            "Update Account",
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp, 80.dp, 0.dp, 10.dp),
            color = Color.Black,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
        )

        updatedInfo = InformationBox(currentAccount)
        Spacer(modifier = Modifier.height(10.dp))
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(230.dp, 530.dp, 10.dp, 10.dp)
    ) {
        Button(modifier = Modifier
            .width(150.dp)
            .height(30.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFE13646)),
            onClick = {
                navController.navigate(Screens.UpdatePassword.route)

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
            .padding(20.dp, 700.dp, 20.dp, 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(modifier = Modifier
            .width(350.dp)
            .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFE13646)),
            onClick = {
                accountViewModel.updateCurrentAccount(updatedInfo[0],updatedInfo[1],updatedInfo[2])
                accountViewModel.updateAccount(currentAccount)
                navController.navigate(Screens.UpdateSaved.route)
            }) {
            Text("Update", color = Color.White)
        }
    }
}

@Composable
fun InformationBox(account: Account): List<String> {

    var PhoneFieldState by remember {
        mutableStateOf(account.phone)
    }

    var EmailFieldState by remember {
        mutableStateOf(account.email)
    }
    var UsernameFieldState by remember {
        mutableStateOf(account.username)
    }

    Card(
        modifier = Modifier
            .width(400.dp)
            .height(400.dp)
            .padding(30.dp, 10.dp, 0.dp, 0.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    )
    {
        Column(
            modifier = Modifier.background(color = Color(0xFFE9B9B6)),

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
                    Text(account.username)
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
                    Text(account.email)
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
                    EmailFieldState = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))


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
                    Text(account.phone)
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
                    PhoneFieldState = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    return listOf<String>(UsernameFieldState, EmailFieldState, PhoneFieldState)
}

