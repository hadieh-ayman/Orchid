package com.example.project.views.Screens

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.model.room.entity.Category
import com.example.project.model.room.entity.Product
import com.example.project.viewModels.ProductViewModel
import com.example.project.views.Navigation.Screens
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(navController: NavController) {
    ShowSearchScreen(navController)
}

@Composable
fun ShowSearchScreen(navController: NavController) {
    // Scaffold {
    var toastContext = LocalContext.current

    val productViewModel =
        viewModel<ProductViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val products: List<Product> by productViewModel.products.collectAsState()

    var textFieldState by remember {
        mutableStateOf("")
    }
    val list = (1..6).map { it.toString() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        //scaffoldState = scaffoldState
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable(
                        onClick = { navController.navigateUp() })
                    .size(30.dp)
                    .weight(1f),
                tint = Color(0xFFE13646),
            )
            OutlinedTextField(
                value = textFieldState,
                onValueChange = {
                    textFieldState = it
                },
                singleLine = true,
                modifier = Modifier
                    .weight(6f)
                    .fillMaxWidth(),
                placeholder = { Text("What are you looking for?") }
            )
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                modifier = Modifier
                    .clickable(onClick = {
                        if (textFieldState.isNotBlank()) {
                            products.forEach { p ->
                                if (p.productName.contains(textFieldState, ignoreCase = true)) {
                                    productViewModel.setSearchWord(textFieldState)
                                    navController.navigate(Screens.Results.route)
                                }
                            }
                        } else if (textFieldState.isBlank())
                            Toast
                                .makeText(
                                    toastContext,
                                    "Please enter a keyword to search",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                    })
                    .size(30.dp)
                    .weight(1f),
                tint = Color(0xFFE13646),
            )
        }
    }
}