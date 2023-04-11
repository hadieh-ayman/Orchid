package com.example.project.views.Screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.project.views.Navigation.Navigation
import com.example.project.views.Navigation.Screens

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {}
        Navigation(navController)
    }
}


@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        Screens.Home,
        Screens.Catagory,
        Screens.Cart,
        Screens.Account,
    )

    Log.d("test", "test2")
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    if (currentDestination?.route == Screens.Login.route ||
        currentDestination?.route == Screens.SignUp.route ||
        currentDestination?.route == Screens.Address.route ||
        currentDestination?.route == Screens.ForgotPassword.route ||
        currentDestination?.route == Screens.IntroSplashScreen.route ||
        currentDestination?.route == Screens.AddProductAdmin.route
    ) {

    } else {
        BottomNavigation(
            backgroundColor = Color.White,
            contentColor = Color(0xFFE13646)
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: Screens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route)
        }
    )

}