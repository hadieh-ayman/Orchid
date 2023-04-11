package com.example.project.views.Screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.R
import com.example.project.model.room.entity.Product
import com.example.project.viewModels.AccountViewModel
import com.example.project.viewModels.ProductViewModel
import com.example.project.views.Navigation.Screens
import com.example.projecttracker.viewmodel.AuthViewModel


@Composable
fun AccountInfo(navController: NavController) {
    (navController)
}


@Composable
fun AccountInfoApp(navController: NavController) {
     val accountViewModel =
        viewModel<AccountViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val currentAccount = accountViewModel.getCurrentAccount()
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
                Image(
                    modifier = Modifier
                        .size(80.dp),
                    //.padding(end=80.dp),
                    contentDescription = null,
                    painter = painterResource(R.drawable.logo),
                )
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp, 55.dp, 0.dp, 0.dp),
        ) {
            Icon(
                modifier = Modifier
                    .size(30.dp),
                imageVector = Icons.Filled.Person,
                tint = Color(0xFFE13646),
                contentDescription = "Account icon"
            )
            if (currentAccount.username != "Admin") {
            Text(
                "Account Information",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp, 0.dp, 0.dp, 0.dp),
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
            )
            }else{
                Text(
                    "Admin Dashboard",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp, 0.dp, 0.dp, 0.dp),
                    color = Color.Black,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        val authViewModel: AuthViewModel =
            viewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 30.dp, 0.dp, 30.dp)
        ) {
            Divider(thickness = 1.dp, color = Color.Black)
            if (currentAccount.username != "Admin") {
            Row(modifier = Modifier.background(color = Color(0xFFFFFFFF))) {
                    IconButton(onClick = { navController.navigate(route = Screens.UpdatePersonal.route) }) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp),
                            imageVector = Icons.Filled.Info,
                            tint = Color(0xFFE13646),
                            contentDescription = "Info icon"
                        )
                    }
                    Text(
                        "Personal Information",
                        modifier = Modifier
                            .clickable { navController.navigate(route = Screens.UpdatePersonal.route) }
                            .fillMaxWidth()
                            .padding(15.dp),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF9800),
                    )
                }
                Divider(thickness = 1.dp, color = Color.Black)
                Row(modifier = Modifier.background(color = Color(0xFFFFFFFF))) {
                    IconButton(onClick = { navController.navigate(route = Screens.UpdateAddress.route) }) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp),
                            imageVector = Icons.Filled.LocationOn,
                            tint = Color(0xFFE13646),
                            contentDescription = "Location icon"
                        )
                    }
                    Text(
                        "Saved Address",
                        modifier = Modifier
                            .clickable { navController.navigate(route = Screens.UpdateAddress.route) }
                            .fillMaxWidth()
                            .padding(15.dp),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF9800),
                    )
                }
                Divider(thickness = 1.dp, color = Color.Black)
                Row(modifier = Modifier.background(color = Color(0xFFFFFFFF))) {
                    IconButton(onClick = { navController.navigate(route = Screens.OrderScreen.route) }) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp),
                            imageVector = Icons.Filled.Email,
                            tint = Color(0xFFE13646),
                            contentDescription = "Email icon"
                        )
                    }
                    Text(
                        "Orders Information",
                        modifier = Modifier
                            .clickable { navController.navigate(route = Screens.OrderScreen.route) }
                            .fillMaxWidth()
                            .padding(15.dp),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF9800),
                    )
                }
                Divider(thickness = 1.dp, color = Color.Black)
                Divider(thickness = 1.dp, color = Color.Black)
                Row(modifier = Modifier.background(color = Color(0xFFFFFFFF))) {
                    IconButton(onClick = { navController.navigate(route = Screens.SavedCard.route) }) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp),
                            imageVector = Icons.Filled.Lock,
                            tint = Color(0xFFE13646),
                            contentDescription = "lock icon"
                        )
                    }
                    Text(
                        "Card Information",
                        modifier = Modifier
                            .clickable { navController.navigate(route = Screens.SavedCard.route) }
                            .fillMaxWidth()
                            .padding(15.dp),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF9800),
                    )
                }
            }
            else {
            Row(modifier = Modifier.background(color = Color(0xFFFFFFFF))) {
                IconButton(onClick = { navController.navigate(route = Screens.OrderReport.route) }) {
                    Icon(
                        modifier = Modifier
                            .size(30.dp),
                        imageVector = Icons.Filled.Menu,
                        tint = Color(0xFFE13646),
                        contentDescription = "menu icon"
                    )
                }
                Text(
                    "View All Orders ",
                    modifier = Modifier
                        .clickable { navController.navigate(route = Screens.ViewAllOrders.route) }
                        .fillMaxWidth()
                        .padding(15.dp),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF9800),
                )
            }
            Divider(thickness = 1.dp, color = Color.Black)
                Row(modifier = Modifier.background(color = Color(0xFFFFFFFF))) {
                    IconButton(onClick = { navController.navigate(route = Screens.ViewAllOrders.route) }) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp),
                            imageVector = Icons.Filled.Info,
                            tint = Color(0xFFE13646),
                            contentDescription = "info icon"
                        )
                    }
                    Text(
                        "Admin Report",
                        modifier = Modifier
                            .clickable { navController.navigate(route = Screens.OrderReport.route) }
                            .fillMaxWidth()
                            .padding(15.dp),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF9800),
                    )
                }
                Divider(thickness = 1.dp, color = Color.Black)
        }
            Divider(thickness = 1.dp, color = Color.Black)
            Row(modifier = Modifier.background(color = Color(0xFFFFFFFF))) {
                IconButton(onClick = { navController.navigate(route = Screens.Login.route) }) {
                    Icon(
                        modifier = Modifier
                            .size(30.dp),
                        imageVector = Icons.Filled.Person,
                        tint = Color(0xFFE13646),
                        contentDescription = "Account icon"
                    )
                }
                Text(
                    "Logout",
                    modifier = Modifier
                        .clickable {
                            authViewModel.signOut()
                            navController.navigate(route = Screens.Login.route) }
                        .fillMaxWidth()
                        .padding(15.dp),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF9800),
                )
            }
        }


    }
}


