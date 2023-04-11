package com.example.project.views.Screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.project.model.room.entity.CartItem
import com.example.project.model.room.entity.Order
import com.example.project.model.room.entity.Product
import com.example.project.viewModels.CartItemViewModel
import com.example.project.viewModels.OrderViewModel
import com.example.project.viewModels.ProductViewModel
import com.example.project.views.Navigation.Screens
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun OrderReport(navController: NavController) {
    OrderReportApp(navController)
}


@Composable
fun OrderReportApp(navController: NavController) {
    val orderViewModel =
        viewModel<OrderViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val cartItemViewModel =
        viewModel<CartItemViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    orderViewModel.getAllOrders()
    cartItemViewModel.getAllCartItemsEver()
    val orders: List<Order> by orderViewModel.allOrders.collectAsState()
    val cartItems : List<CartItem> by cartItemViewModel.allCartItems.collectAsState()

    var selectedOptionText by remember { mutableStateOf("All") }
    var mDateTo by remember { mutableStateOf("2022-12-31") }
    var mDateFrom by remember { mutableStateOf("2022-01-01") }

    var cartItemList = mutableListOf<CartItem>()
    var orderList = mutableListOf<Order>()
    var totalMoney=0.0
    orders.forEach() { o ->
        if((compareDate(o.datePlaced,mDateFrom) >= 0) && (compareDate(o.datePlaced,mDateTo) <= 0)) {
            if (selectedOptionText == "All") {
                orderList.add(o)
                totalMoney+=o.total
            } else if (o.status.equals(selectedOptionText)) {
                orderList.add(o)
                totalMoney+=o.total
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
    ) {
        IconButton(onClick = {navController.navigateUp() }) {
            Icon(
                modifier = Modifier
                    .size(30.dp),
                imageVector = Icons.Filled.ArrowBack,
                tint = Color(0xFFE13646),
                contentDescription = "Arrow icon"
            )
        }
        Text(
            "All Ordered Products",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start=25.dp),
            color = Color(0xFFE13646),
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
    }
    Row(modifier = Modifier.padding(start = 100.dp, top = 110.dp))
    {
        var dateList : List<String> = MyDatePicker()
        mDateFrom = dateList[0]
        mDateTo = dateList[1]
    }
    Row(
        modifier = Modifier.padding(start = 110.dp, top = 205.dp)
    ) {
        selectedOptionText = ShowStatus()

    }

    Text(
        modifier = Modifier.padding(start = 10.dp, top = 270.dp),
        text = "Total Amount of orders: " + orderList.size
    )
    Text(
        modifier = Modifier.padding(start = 10.dp, top = 290.dp),
        text = "Total $: " + totalMoney
    )
    LazyColumn(modifier = Modifier
        .padding(top = 325.dp)
        .height(440.dp)
        .fillMaxWidth(),
        // content padding
        contentPadding = PaddingValues(
            start = 0.dp,
            top = 16.dp,
            end = 0.dp,
            bottom = 2.dp
        ),
        content = {
                items(orderList) { p ->
                    OrderCard(navController, p)
                }
                cartItemList = mutableListOf()
            }
             )
        }


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyDatePicker(): List<String> {
    var expanded by remember {
        mutableStateOf(true)
    }
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()
    
    var mDateTo by remember { mutableStateOf("") }
    var mDateFrom by remember { mutableStateOf("") }

    val mDatePickerDialogTo = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDateTo = "$mYear-${mMonth + 1}-$mDayOfMonth"
        }, mYear, mMonth, mDay
    )
    val mDatePickerDialogFrom = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDateFrom = "$mYear-${mMonth + 1}-$mDayOfMonth"
        }, mYear, mMonth, mDay
    )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            Column {
                DropdownMenuItem(onClick = {
                    mDatePickerDialogFrom.show()
                }) {
                    Text(
                        text = "From Date: ${mDateFrom}",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.size(40.dp))
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Select From Date")

                }

                DropdownMenuItem(onClick = {
                    mDatePickerDialogTo.show()
                }) {
                    Text(
                        text = "To Date: ${mDateTo}",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.size(40.dp))
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Select To Date")
                }
            }
        }
    var dateArray = listOf(mDateFrom,mDateTo)
    return dateArray
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowStatus(): String {
    val options = listOf("All", "Processed", "Shipped", "Delivered")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            modifier = Modifier
                .width(150.dp)
                .height(50.dp),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text("Status", fontSize = 12.sp) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(Color(0xFF000000))
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
    return selectedOptionText
}


@Composable
fun OrderCard(navController: NavController, order: Order) {
    val productViewModel =
        viewModel<ProductViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val orderViewModel =
        viewModel<OrderViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    //orderViewModel.setCurrentOrder(order)
    val products: List<Product> by productViewModel.products.collectAsState()
    var product = Product("", 0.0, "", 0.0, 0, 0.0, 0, "", "")
//    products.forEach() { p ->
//        if (p.productId.equals(cartItem.product)) {
//            product = p
//        }
//    }
//
//    var total = cartItem.price*(1-product.discount)*cartItem.quantity

    Card(
        border = BorderStroke(2.dp,Color(0xFFE13646)),
        backgroundColor = Color(0xFFE2B4B1),
        modifier = Modifier
            .padding(top = 20.dp, start = 8.dp,end=30.dp).clickable(onClick = {
                orderViewModel.setCurrentOrder(order)
                navController.navigate(Screens.Tracking.route)})
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp)
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .width(10.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.TopStart),
                    text = "Order #${order.orderId}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            fontSize = 15.sp,
                            modifier = Modifier
                                .padding(10.dp),
                            text = "Order Date: ${order.datePlaced}",
                            color = Color(0xFFE13646),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            fontSize = 15.sp,
                            modifier = Modifier
                                .padding(10.dp),
                            text = "ETA: ${order.estDelivery}",
                            color = Color(0xFFE13646),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp),
                        text = " ",
                        fontSize = 15.sp,
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp),
                        text = "Status: ${order.status}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                    )
                    Text(
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(top=10.dp),
                        text = "Total: ${String.format("%.2f", order.total)}",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    var expanded by remember { mutableStateOf(false) }
                    val suggestions = listOf("Processed", "Shipped", "Delivered")

                    var textfieldSize by remember { mutableStateOf(Size.Zero) }

                    val icon = if (expanded)
                        Icons.Filled.KeyboardArrowUp
                    else
                        Icons.Filled.KeyboardArrowDown
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

fun compareDate(orderDate:String, dateReport: String): Int {

    val sdf = SimpleDateFormat("yyyy-MM-dd")
    var cmp = 0
    try {
        val firstDate: Date = sdf.parse(orderDate)
        val secondDate: Date = sdf.parse(dateReport)
        cmp = firstDate.compareTo(secondDate)
    }catch (e : Exception){}
    return cmp
}
