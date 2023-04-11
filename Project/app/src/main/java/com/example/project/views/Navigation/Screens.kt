package com.example.project.views.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : Screens(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object AddProductAdmin : Screen(
        route = "addProductAdmin",
        title = "addProductAdmin",
    )
    object EditProductAdmin : Screen(
        route = "editProductAdmin",
        title = "editProductAdmin",
    )

    object Catagory : Screens(
        route = "catagory",
        title = "Catagory",
        icon = Icons.Default.List
    )

    object Cart : Screens(
        route = "cart",
        title = "Cart",
        icon = Icons.Default.ShoppingCart
    )

    object Account : Screens(
        route = "account",
        title = "Account",
        icon = Icons.Default.Person
    )

    object ForgotPassword : Screen(
        route = "forgotpassword",
        title = "Forgot Password",
    )

    object ProductInfo : Screen(
        route = "productinfo",
        title = "Item Info",
    )

    object Login : Screen(
        route = "login",
        title = "Login",
    )

    object Address : Screen(
        route = "address",
        title = "address",
    )

    object AddAddress : Screen(
        route = "addaddress",
        title = "addaddress",
    )
    object AddReview : Screen(
        route = "addreview",
        title = "addreview",
    )

    object AdminInfo : Screen(
        route = "admininfo",
        title = "admininfo",
    )

    object Favorite : Screen(
        route = "favorite",
        title = "favorite",
    )

    object NewCard : Screen(
        route = "newcard",
        title = "newcard",
    )

    object Order : Screen(
        route = "order",
        title = "order",
    )

    object Payment : Screen(
        route = "payment",
        title = "payment",
    )

    object Results : Screen(
        route = "results",
        title = "results",
    )

    object Reviews : Screen(
        route = "reviews",
        title = "reviews",
    )

    object UpdateAddress : Screen(
        route = "updateaddress",
        title = "updateaddress",
    )
    object UpdatePersonal : Screen(
        route = "updatepersonal",
        title = "updatepersonal",
    )

    object UpdatePassword : Screen(
        route = "updatepassword",
        title = "updatepassword",
    )
    object SavedCard : Screen(
        route = "savedcard",
        title = "savedcard",
    )

    object ViewAllOrders : Screen(
        route = "viewallorders",
        title = "viewallorders",
    )
    object Search : Screen(
        route = "search",
        title = "search",
    )

    object ShoppingCart : Screen(
        route = "shoppingCart",
        title = "shoppingCart",
    )

    object SignUp : Screen(
        route = "signUp",
        title = "signUp",
    )

    object ThankYou : Screen(
        route = "thankYou",
        title = "thankYou",
    )

    object Tracking : Screen(
        route = "tracking",
        title = "tracking",
    )

    object OrderScreen : Screen(
        route = "orderScreen",
        title = "orderScreen",
    )

    object UpdateSaved : Screen(
        route = "updateSaved",
        title = "updateSaved",
    )

    object UpdateAdmin : Screen(
        route = "updateAdmin",
        title = "updateAdmin",
    )

    object OrderReport : Screen(
        route = "orderReport",
        title = "orderReport",
    )

    object FilterProduct : Screen(
        route = "filterProduct",
        title = "filterProduct",
    )

    object IntroSplashScreen : Screen(
        route = "introSplashScreen",
        title = "introSplashScreen",
    )

    object ResultsCatScreen : Screen(
        route = "ResultsCatScreen",
        title = "ResultsCatScreen",
    )

    object ResultsFilterScreen : Screen(
        route = "ResultsFilterScreen",
        title = "ResultsFilterScreen",
    )

    object FilterStatus: Screen(
        route = "FilterStatusScreen",
        title = "FilterStatusScreen",
    )

    object MainScreen : Screen(
        route = "mainScreen",
        title = "MainScreen",
    )
}