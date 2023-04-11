package com.example.project.views.Screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.model.room.entity.Address
import com.example.project.viewModels.AccountViewModel
import com.example.project.viewModels.AddressViewModel
import com.example.project.views.Navigation.Screens
import kotlinx.coroutines.launch


@Composable
fun AddressScreen(navController: NavController) {
    AddressApp(navController)
}

@Composable
fun AddressApp(navController: NavController) {
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
            var check = TermsConditions()

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
                    }else if (check == false) {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Check Terms and Conditions to Continue")
                        }
                    } else {
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
                    navController.navigate(Screens.Login.route)
                }
                    }) {
                Text("Sign up", color = Color.White)
            }

        }
    }

}

@Composable
fun TermsConditions(): Boolean {
    val checkedState = remember {
        mutableStateOf(false)
    }
    Row() {
        val check: Boolean
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it },
            modifier = Modifier.padding(3.dp)
        )
        Text(
            text = "By creating an account, you agree to our\n" +
                    "Term & Conditions", Modifier.padding(3.dp), fontSize = 14.sp
        )
    }

    Spacer(modifier = Modifier.height(32.dp))

    return checkedState.value
}

@Composable
fun ZipTextField(): String {
    var ZipFieldState by remember {
        mutableStateOf("")
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text("Zip", color = Color(0xFFF44336))

    OutlinedTextField(
        value = ZipFieldState,
        label = {
            Text("Enter Zip")
        },
        onValueChange = {
            ZipFieldState = it
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))

    return ZipFieldState
}

@Composable
fun CityTextField(): String {
    var cityFieldState by remember {
        mutableStateOf("")
    }
    Text("City", color = Color(0xFFF44336))
    OutlinedTextField(
        value = cityFieldState,
        label = {
            Text("Enter city")
        },
        onValueChange = {
            cityFieldState = it
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))

    return cityFieldState
}


@Composable
fun AddressLineTextField(): String {
    var lineFieldState by remember {
        mutableStateOf("")
    }

    Text("Address Line", color = Color(0xFFF44336))
    OutlinedTextField(
        value = lineFieldState,
        label = {
            Text("Enter Address line")
        },
        onValueChange = {
            lineFieldState = it
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))

    return lineFieldState
}


@Composable
fun CountriesOptions(): String {
    Spacer(modifier = Modifier.height(16.dp))

    Text("Country", color = Color(0xFFF44336))

    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("USA", "UK", "Australia", "India")
    var selectedText by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column() {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            label = { Text("Select country") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label
                    expanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
    return selectedText
}


//
//@Composable
//fun textfun(textFieldState: String, name:String) {
//    Text(
//        "$name",
//        color = Color(0xFFF44336),
//        textAlign = TextAlign.Start,
//    )
//
//    OutlinedTextField(
//        value = textFieldState,
//        label = {
//            Text("Enter $name")
//        },
//        leadingIcon = {
//            IconButton(onClick = { }) {
//                Icon(
//                    imageVector = Icons.Filled.Person,
//                    tint = Color(0xFFE13646),
//                    contentDescription = "$name icon"
//                )
//            }
//        },
//        onValueChange = {
//           //textFieldState = it
//        },
//        singleLine = true,
//        modifier = Modifier.fillMaxWidth()
//    )
//    Spacer(modifier = Modifier.height(16.dp))
//
//}