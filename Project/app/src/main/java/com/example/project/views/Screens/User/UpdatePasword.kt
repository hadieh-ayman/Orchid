package com.example.project.views.Screens



import androidx.navigation.NavController
import com.example.project.views.Navigation.Screens
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project.R

@Composable
fun UpdatePasswordScreen(navController: NavController) {
    UpdatePasswordApp(navController)
}

@Composable
fun UpdatePasswordApp(navController: NavController) {
    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) { padding ->

        Spacer(modifier = Modifier.height(40.dp))
        IconButton(onClick = { navController.navigateUp() }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            tint = Color(0xFFE13646),
            contentDescription = "Arrow icon",
           )}


        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            Spacer(modifier = Modifier.height(3.dp))

            Text(
                "Update Password",
                fontSize = 35.sp,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,

                )
            Spacer(modifier = Modifier.height(15.dp))

            OldPasswordTextField()
            PasswordsTextField()

            Button(modifier = Modifier
                .width(3240.dp)
                .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFE13646)),
                onClick = {
                    navController.navigate(Screens.UpdateSaved.route)

                }) {
                Text("Update", color = Color.White)
            }
        }
    }


    @Composable
    fun PasswordsTextField() {
        var password by rememberSaveable() {
            mutableStateOf("")
        }
        var passDivisibility by remember {
            mutableStateOf(false)
        }
        val eyeicon = if (passDivisibility)
            painterResource(id = R.drawable.eye)
        else
            painterResource(id = R.drawable.eyeno)


        var passConfirmFieldState by remember {
            mutableStateOf("")
        }
        var passFieldState by remember {
            mutableStateOf("")
        }


        Text("New Password", color = Color(0xFFF44336))
        OutlinedTextField(
            value = passFieldState,
            label = {
                Text("Enter New Password")
            },
            onValueChange = {
                passFieldState = it
            },
            leadingIcon = {

                    Icon(
                        imageVector = Icons.Filled.Lock,
                        tint = Color(0xFFE13646),
                        contentDescription = "lock icon"
                    )

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

        Text("Confirm New Password", color = Color(0xFFF44336))
        OutlinedTextField(
            value = passConfirmFieldState,
            label = {
                Text("Confirm New Password")
            },
            onValueChange = {
                passConfirmFieldState = it
            },
            leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        tint = Color(0xFFE13646),
                        contentDescription = "lock icon"
                    )

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

    }


}

@Composable
fun OldPasswordTextField() {
    var password by rememberSaveable() {
        mutableStateOf("")
    }
    var passDivisibility by remember {
        mutableStateOf(false)
    }
    val eyeicon = if (passDivisibility)
        painterResource(id = R.drawable.eye)
    else
        painterResource(id = R.drawable.eyeno)


    var oldFieldState by remember {
        mutableStateOf("")
    }


    Text("Old Password", color = Color(0xFFF44336))
    OutlinedTextField(
        value = oldFieldState,
        label = {
            Text("Enter Old Password")
        },
        onValueChange = {
            oldFieldState = it
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
    Spacer(modifier = Modifier.height(16.dp))

}

