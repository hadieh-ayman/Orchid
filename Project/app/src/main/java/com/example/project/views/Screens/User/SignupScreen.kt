package com.example.project.views.Screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.R
import com.example.project.model.room.entity.Account
import com.example.project.model.room.entity.Product
import com.example.project.viewModels.AccountViewModel
import com.example.project.viewModels.ProductViewModel
import com.example.project.views.Navigation.Screens
import com.example.projecttracker.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(navController: NavController) {
    SignupApp(navController)
}

@Composable
fun SignupApp(navController: NavController) {

    val accountViewModel = viewModel<AccountViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) { padding ->

        Spacer(modifier = Modifier.height(40.dp))
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            tint = Color(0xFFE13646),
            contentDescription = "lock icon",
            modifier = Modifier.clickable(onClick = {
                navController.navigate(Screens.Login.route)
            })
        )


        Column(//horizontalAlignment = Alignment.CenterHorizontally
            verticalArrangement = Arrangement.Center, modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            Spacer(modifier = Modifier.height(21.dp))

            Text(
                "Create an account",
                fontSize = 35.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Enter the required information to sign up", color = Color(0xFFE13646))
            Spacer(modifier = Modifier.height(20.dp))


            val accountName = UsernameTextField()
            val accountEmail = EmailTextField()
            val accountPhone = PhoneTextField()
            val accountPass = PasswordsTextField()
            val authViewModel =
                viewModel<AuthViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
            Button(modifier = Modifier
                .width(3250.dp)
                .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFE13646)),
                onClick = {
                    if(accountPass==""){
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Passwords have to be the same")
                        }
                    }else if(accountName=="") {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Please Enter a User")
                        }
                    }else if(accountEmail=="") {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Please Enter an Email")
                        }
                    }else if(accountPhone=="") {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Please Enter a Phone Number")
                        }
                    }else {
                        val accountFinal = Account(
                            "1",
                            "user",
                            accountName,
                            accountEmail,
                            accountPhone,
                            accountPass
                        )
                        accountViewModel.addAccount(accountFinal)
                        authViewModel.signup(accountFinal.email, accountFinal.password)
                        accountViewModel.getAccount(accountName)
                        navController.navigate(Screens.Address.route)
                    }
                }) {
                Text("Next", color = Color.White)
            }
        }
    }
}

@Composable
fun UsernameTextField(): String {
    var UsernameFieldState by remember {
        mutableStateOf("")
    }
    Text("Username", color = Color(0xFFF44336))
    OutlinedTextField(
        value = UsernameFieldState,
        label = {
            Text("Enter Username")
        },
        leadingIcon = {
            IconButton(onClick = {

            }) {
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
    Spacer(modifier = Modifier.height(16.dp))
    return UsernameFieldState
}

@Composable
fun EmailTextField(): String  {
    var EmailFieldState by remember {
        mutableStateOf("")
    }
    Text("Email", color = Color(0xFFF44336))
    OutlinedTextField(
        value = EmailFieldState,
        label = {
            Text("Enter Email")
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
    Spacer(modifier = Modifier.height(16.dp))
    return EmailFieldState
}

@Composable
fun PhoneTextField(): String {
    var PhoneFieldState by remember {
        mutableStateOf("")
    }
    Text("Phone", color = Color(0xFFF44336))
    OutlinedTextField(
        value = PhoneFieldState,
        label = {
            Text("Enter Phone number")
        },
        leadingIcon = {
            IconButton(onClick = {}) {
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
    Spacer(modifier = Modifier.height(16.dp))
    return PhoneFieldState
}

@Composable
fun PasswordsTextField(): String {

    var password by rememberSaveable() { mutableStateOf("") }
    var passDivisibility by remember { mutableStateOf(false) }
    val eyeicon = if (passDivisibility)
        painterResource(id = R.drawable.eye)
    else
        painterResource(id = R.drawable.eyeno)

    var passConfirmFieldState by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Text("Password", color = Color(0xFFF44336))
    OutlinedTextField(
        value = pass,
        label = { Text("Enter Password") },
        onValueChange = { pass = it },
        leadingIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    tint = Color(0xFFE13646),
                    contentDescription = "lock icon"
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = {
                passDivisibility = !passDivisibility
            }) {
                Icon(
                    painter = eyeicon,
                    modifier = Modifier.size(25.dp),
                    tint = Color(0xFFE13646),
                    contentDescription = "eye icon"
                )
            }
        },
        visualTransformation = if (passDivisibility) VisualTransformation.None
        else PasswordVisualTransformation(),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))

    Text("Confirm Password", color = Color(0xFFF44336))
    OutlinedTextField(
        value = passConfirmFieldState,
        label = {
            Text("Confirm Password")
        },
        onValueChange = {
            passConfirmFieldState = it
        },
        leadingIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    tint = Color(0xFFE13646),
                    contentDescription = "lock icon"
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = {
                passDivisibility = !passDivisibility
            }) {
                Icon(
                    painter = eyeicon,
                    modifier = Modifier.size(25.dp),
                    tint = Color(0xFFE13646),
                    contentDescription = "eye icon"
                )
            }
        },
        visualTransformation = if (passDivisibility) VisualTransformation.None
        else PasswordVisualTransformation(),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(60.dp))

    return if(pass==passConfirmFieldState){
        pass
    }else{
        ""
    }

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