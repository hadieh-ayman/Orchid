package com.example.project.views.Screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.project.R
import com.example.project.model.room.entity.Account
import com.example.project.viewModels.AccountViewModel
import com.example.project.views.Navigation.Screen
import com.example.project.views.Navigation.Screens
import com.example.projecttracker.viewmodel.AuthViewModel
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navController: NavController) {
    LoginApp(navController)
}


@Composable
fun LoginApp(navController: NavController) {
    val accountViewModel = viewModel<AccountViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val accounts: List<Account> by accountViewModel.accounts.collectAsState()

    val authViewModel: AuthViewModel =
        viewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity)
//
    if (authViewModel.user?.email != null){
        accountViewModel.getAccountByEmail(authViewModel.user?.email!!)
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        if (currentDestination?.route == Screens.Login.route){
            navController.navigate(Screens.Home.route)}
    }

    val scaffoldState = rememberScaffoldState()
    var passDivisibility by remember { mutableStateOf(false) }
    val eyeicon = if (passDivisibility)
        painterResource(id = R.drawable.eye)
    else
        painterResource(id = R.drawable.eyeno)

    var usernameTextField by remember { mutableStateOf("") }
    var passFieldState by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Gray,
                        )
                    ) {
                        append("Don't have an account?")
                    }
                    append("Sign up! ")
                },
                color = Color.Red,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable(onClick = {
                    navController.navigate(Screens.SignUp.route)
                })
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Text(
                "Login to your account",
                fontSize = 35.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Welcome back you have been missed!", color = Color(0xFFE13646))
        }



        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {

            Text(
                "Email",
                color = Color(0xFFF44336),
                textAlign = TextAlign.Start,
                modifier = Modifier.align(alignment = Alignment.Start)
            )

            OutlinedTextField(
                value = usernameTextField,
                label = {
                    Text("Enter email")
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
                    usernameTextField = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Password",
                color = Color(0xFFF44336),
                textAlign = TextAlign.Start,
                modifier = Modifier.align(alignment = Alignment.Start)
            )
            OutlinedTextField(
                value = passFieldState,
                label = {
                    Text("Enter Password")
                },
                onValueChange = {
                    passFieldState = it
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
            Spacer(modifier = Modifier.height(19.dp))
            ClickableText(AnnotatedString("Forgot Password?"),
                style = TextStyle(
                    color = Color(0xFFE91E63)
                ),
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 10.dp),
                onClick = { navController.navigate(route = Screens.ForgotPassword.route) }
            )

            Spacer(modifier = Modifier.height(60.dp))

            Button(modifier = Modifier
                .width(3250.dp)
                .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFE13646)),
                onClick = {
//                    if(usernameTextField=="Admin" && passFieldState=="pass123"){
//
//                        navController.navigate(Screens.Home.route)
//                    }else {
                        if (usernameTextField.isNotBlank() && passFieldState.isNotBlank()) {
                                    if (accounts.size == 0) {
                                        scope.launch {
                                            scaffoldState.snackbarHostState.showSnackbar("There is no accounts in the database")
                                        }
                                    }
                            accounts.forEach { a ->
                                if (usernameTextField.equals(a.email) && passFieldState.equals(a.password)) {
                                    accountViewModel.setCurrentAccount(a)
                                    authViewModel.signInWithEmailAndPassWord(usernameTextField,passFieldState )
                                   // accountViewModel.setCurrentAccount(a)
                                    navController.navigate(Screens.Home.route)
                                } else {
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar("Incorrect Username or Password")
                                    }
                                }
                            }
                        } else {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Please enter Username and Password")
                            }
                        }
                   // }
                })
            {
                Text("Login", color = Color.White)
            }
        }
    }
}