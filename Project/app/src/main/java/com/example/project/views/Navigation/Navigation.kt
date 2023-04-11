package com.example.project.views.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.project.views.Screens.*
import com.example.project.views.Screens.User.FilterProductApp
import com.example.project.views.Screens.User.IntroSplashApp
import com.example.project.views.Screens.User.ResultCatApp
import com.example.project.views.Screens.User.ResultFilterApp

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.IntroSplashScreen.route
    ) {
        composable(route = Screens.Login.route) {
            LoginScreen(navController)
        }
        composable(route = Screens.ForgotPassword.route) {
            ForgotPassword(navController)
        }
        composable(route = Screens.Home.route){
            HomeScreen(navController)
        }
        composable(route = Screens.AddProductAdmin.route) {
            AddProductApp(navController)
        }
        composable(route = Screens.ProductInfo.route) {
            ProductInfo(navController)
        }
        composable(route = Screens.Catagory.route) {
            CategoryScreen(navController)
        }
        composable(route = Screens.Cart.route) {
            ShoppingCartScreen(navController)
        }
        composable(route = Screens.Account.route) {
            AccountInfoApp(navController)
        }
        composable(route = Screens.Address.route) {
            AddressScreen(navController)
        }
        composable(route = Screens.AddAddress.route) {
            AddAddressScreen(navController)
        }
        composable(route = Screens.AddReview.route) {
            AddReview(navController)
        }
        composable(route = Screens.AdminInfo.route) {
            AdminInfo(navController)
        }
        composable(route = Screens.Favorite.route) {
            FavoriteApp(navController)
        }
        composable(route = Screens.NewCard.route) {
            NewCardApp(navController)
        }
//        composable(route = Screens.Order.route) {
//            OrderApp(navController)
//        }
        composable(route = Screens.Payment.route) {
            PaymentApp(navController)
        }
        composable(route = Screens.Results.route) {
            ResultsPage(navController)
        }
        composable(route = Screens.Reviews.route) {
            Reviews(navController)
        }
        composable(route = Screens.SavedCard.route) {
            SavedCard(navController)
        }
        composable(route = Screens.Search.route) {
            SearchScreen(navController)
        }
        composable(route = Screens.ShoppingCart.route) {
            ShoppingCartScreen(navController)
        }
        composable(route = Screens.SignUp.route) {
            SignupApp(navController)
        }
        composable(route = Screens.ThankYou.route) {
            ThankYouApp(navController)
        }
        composable(route = Screens.Tracking.route) {
            TrackApp(navController)
        }
        composable(route = Screens.UpdateSaved.route) {
            UpdateSavedApp(navController)
        }
        composable(route = Screens.UpdatePersonal.route) {
            UpdatePersonalApp(navController)
        }
        composable(route = Screens.ViewAllOrders.route) {
            ViewOrdersApp(navController)
        }
        composable(route = Screens.UpdatePassword.route) {
            UpdatePasswordApp(navController)
        }
        composable(route = Screens.UpdateAddress.route) {
            UpdateAddressApp(navController)
        }
        composable(route = Screens.UpdateSaved.route) {
            UpdateSavedApp(navController)
        }
        composable(route = Screens.UpdateAdmin.route) {
            UpdateAdminApp(navController)
        }
        composable(route = Screens.OrderReport.route) {
            OrderReportApp(navController)
        }
        composable(route = Screens.FilterProduct.route) {
            FilterProductApp(navController)
        }
        composable(route = Screens.IntroSplashScreen.route) {
            IntroSplashApp(navController)
        }
        composable(route = Screens.ResultsCatScreen.route) {
            ResultCatApp(navController)
        }
        composable(route = Screens.ResultsFilterScreen.route) {
            ResultFilterApp(navController)
        }
        composable(route = Screens.EditProductAdmin.route) {
            EditProductApp(navController)
        }
        composable(route = Screens.OrderScreen.route) {
            ViewOrdersScreen(navController)
        }
//        composable(route = Screens.FilterStatus.route) {
//            FilterStatusApp(navController)
//        }
    }
}
//package com.example.project.views.Navigation.BottomBar
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable

//import com.example.project.Navigation.Screens
//import com.example.project.Navigation.Screen
//import com.example.project.views.Navigation.BottomBarScreen
//import com.example.project.views.Navigation.Screen
//import com.example.project.views.Navigation.BottomBarScreen
//import com.example.project.views.Navigation.Screen
//import com.example.project.views.*
//
//@Composable
//fun BottomNavGraph(navController: NavHostController){
//    NavHost(navController = navController,
//        startDestination = Screen.Login.route)
//    {
//        composable(route = Screens.Home.route){
//            HomeScreen()
//        }
//        composable(route = Screens.Catagory.route){
//            CategoryScreen()
//        }
//        composable(route = Screens.Cart.route){
//            ShoppingCartScreen()
//        }
//        composable(route = Screens.Account.route){
//        composable(route = BottomBarScreen.Home.route){
//            HomeScreen()
//        }
//        composable(route = BottomBarScreen.Catagory.route){
//            CategoryScreen()
//        }
//        composable(route = BottomBarScreen.Cart.route){
//            ShoppingCartScreen()
//        }
//        composable(route = BottomBarScreen.Account.route){
//            AccountInfo()
//        }
//    }
//}