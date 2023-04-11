package com.example.project.views.Screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.model.room.entity.Address
import com.example.project.viewModels.AccountViewModel
import com.example.project.viewModels.AddressViewModel
import com.example.project.views.Navigation.Screens
import kotlinx.coroutines.launch


@Composable
fun AddAddressScreen(navController: NavController) {
    AddAddressApp(navController)
}

@Composable
fun AddAddressApp(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val accountViewModel = viewModel<AccountViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val addressViewModel = viewModel<AddressViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    var currentAccount = accountViewModel.getCurrentAccount()
    val scope = rememberCoroutineScope()

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
                    navController.navigateUp()
                })
        )

        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {

            Spacer(modifier = Modifier.height(40.dp))
            Text("Address", fontSize = 35.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Enter the address information", color = Color(0xFFE13646))
            Spacer(modifier = Modifier.height(40.dp))

            var addressLineField = AddressLineTextField()
            var cityField = CityTextField()
            var countryField = CountriesOptions()
            var zipField  = ZipTextField()

            Button(modifier = Modifier
                .width(3250.dp)
                .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFE13646)),
                onClick = {
                    if (addressLineField == "") {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Please Enter Address")
                        }
                    }else if (cityField == "") {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Please Enter City")
                        }
                    }else if (countryField == "") {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Please Enter Country")
                        }
                    }else if (zipField == "") {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Please Enter Zip")
                        }
                    }
                    else {
                        var zipFieldInt = zipField.toInt()

                        addressViewModel.addAddress(
                            Address(
                                "1",
                                addressLineField,
                                cityField,
                                countryField,
                                zipFieldInt,
                                currentAccount.accountId
                            )
                        )
                        navController.navigate(Screens.Payment.route)
                    }
                }) {
                Text("Add Address", color = Color.White)
            }

        }
    }

}
