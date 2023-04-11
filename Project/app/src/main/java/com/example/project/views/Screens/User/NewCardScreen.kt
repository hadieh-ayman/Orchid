package com.example.project.views.Screens

import android.widget.Toast
import androidx.activity.ComponentActivity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.model.room.entity.Account
import com.example.project.model.room.entity.CreditCard
import com.example.project.viewModels.AccountViewModel
import com.example.project.viewModels.CardViewModel
import kotlinx.coroutines.launch

@Composable
fun NewCard(navController: NavController) {
    NewCardApp(navController)
}

@Composable
fun NewCardApp(navController: NavController) {
    val cardViewModel =
        viewModel<CardViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val accountViewModel =
        viewModel<AccountViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val currentAccount: Account by accountViewModel.account.collectAsState()
    var cardNo by remember {
        mutableStateOf("")
    }
    var expDate by remember {
        mutableStateOf("")
    }
    var ccv by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    var toastContext = LocalContext.current
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
            text = "Card Information",
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
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Card Number",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = cardNo,
                    label = {
                        Text("e.g 4444 5555 0000 8975")
                    },
                    onValueChange = {
                        cardNo = it
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
                    value = expDate,
                    label = {
                        Text("MM/YY")
                    },
                    onValueChange = {
                        expDate = it
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
                    value = ccv,
                    label = {
                        Text("3 or 4 digits on the back of the card")
                    },
                    onValueChange = {
                        ccv = it
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
                    value = name,
                    label = {
                        Text("e.g Maha Ahmed")
                    },
                    onValueChange = {
                        name = it
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .width(150.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFE13646)),
            shape = RoundedCornerShape(30.dp),
            onClick = {
                val expList = expDate.split("/").toTypedArray()
                if ((expList[0].toInt() > 12 || expList[0].toInt() < 1) || expList[1].toInt() < 22) {
                    Toast.makeText(
                        toastContext,
                        "Invalid Expiry Date!",
                        Toast.LENGTH_SHORT
                    ).show();
                } else if (cardNo != "" || expDate != "" || name != "" || ccv != "") {
                    val newCard = CreditCard(
                        "1",
                        cardNo,
                        expDate,
                        ccv,
                        name,
                        currentAccount.accountId
                    )
                    cardViewModel.addCard(newCard)
//                    cardViewModel.getCard(newCard.cardId)
                    navController.navigateUp()
                } else {
                    Toast.makeText(
                        toastContext,
                        "Enter all required information!",
                        Toast.LENGTH_SHORT
                    ).show();
                }
            }) {
            Text("Submit", color = Color.White, fontSize = 18.sp)
        }
    }
}