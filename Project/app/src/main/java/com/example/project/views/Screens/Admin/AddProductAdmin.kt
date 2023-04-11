package com.example.project.views.Screens

import android.widget.Toast
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.project.model.room.entity.Category
import com.example.project.model.room.entity.Product
import com.example.project.viewModels.CategoryViewModel
import com.example.project.viewModels.ProductViewModel
import com.example.project.views.Navigation.Screens


@Composable
fun AddProductApp(navController: NavController) {
    val scaffoldState = rememberScaffoldState()

    val productViewModel =
        viewModel<ProductViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val newProduct: Product by productViewModel.product.collectAsState()

    val scope = rememberCoroutineScope()
    var toastContext = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) { padding ->
        Spacer(
            modifier = Modifier
                .padding(padding)
                .height(40.dp)
        )
        IconButton(onClick = { navController.navigate(Screens.Home.route) }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                tint = Color(0xFFE13646),
                contentDescription = "Arrow icon",
                modifier = Modifier.clickable(onClick = {
                    navController.navigate(Screens.Home.route)
                })
            )
        }


        Column(//horizontalAlignment = Alignment.CenterHorizontally
            verticalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            Spacer(modifier = Modifier.height(21.dp))
            Text(
                text = "Add New Product",
                fontSize = 35.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            var categoryOption = CategoryOption()
            var imagelink = AddImage()
            var name = AddName()
            var priceInput = AddPrice()
            var quantity = AddQuantity()
            var discount = AddDiscount()

            Button(modifier = Modifier
                .width(3250.dp)
                .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFE13646)),
                onClick = {
                    var priceInputDouble = priceInput.toDouble()
                    var quantityInt = quantity.toInt()
                    var discountDouble = discount.toDouble()
                    //if (newProduct.productId == "") {
                        productViewModel.addProduct(
                            Product(
                                name,
                                priceInputDouble,
                                imagelink,
                                0.0,
                                0,
                                discountDouble,
                                quantityInt,
                                categoryOption
                            )
                        )
                        navController.navigate(route = Screens.Home.route)
                   // }
                }) {
                Text(
                    text = "Add Product",
                    color = Color.White
                )
            }
        }
    }
}


@Composable
fun CategoryOption(): String {
    Spacer(modifier = Modifier.height(16.dp))
    val productViewModel =
        viewModel<ProductViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val editProduct: Product by productViewModel.product.collectAsState()
    val categoryViewModel =
        viewModel<CategoryViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val categories : List<Category> by categoryViewModel.categories.collectAsState()

    Text("Select shop  category", fontWeight = FontWeight.Bold)

    var expanded by remember { mutableStateOf(false) }
    var suggestions = mutableListOf<String>()
    categories.forEach(){c ->
        suggestions.add(c.categoryName)
    }
//    val suggestions = listOf("Men's Clothing", "Jewelry", "Electronics", "Women's Clothing")
    var selectedText by remember { mutableStateOf("") }
    //var selectedTextEdit by remember { mutableStateOf(editProduct.category.toString()) }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column() {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {
                selectedText = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            label = { Text("Select category") },
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

    Spacer(modifier = Modifier.height(10.dp))
    var categoryOptions = "1"

    categories.forEach(){c ->
        if(selectedText == c.categoryName)
            categoryOptions = c.categoryId
    }
//    var categoryOptions = "1"
//    if (selectedText == "Men's Clothing") {
//        categoryOptions = "1"
//    } else if (selectedText == "Jewelry") {
//        categoryOptions = "2"
//    } else if (selectedText == "Electronics") {
//        categoryOptions = "3"
//    } else {
//        categoryOptions = "4"
//    }
    return categoryOptions
}


@Composable
fun AddImage(): String {
    var ImageURLFieldState by remember {
        mutableStateOf("")
    }
    Text("Add URL of Product image", fontWeight = FontWeight.Bold)
    OutlinedTextField(
        value =
        ImageURLFieldState,
        onValueChange = {
            ImageURLFieldState = it
        },

        label = {
            Text("Enter url here")
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(10.dp))
    return ImageURLFieldState
}


@Composable
fun AddName(): String {
    var NameFieldState by remember {
        mutableStateOf("")
    }

    Text("Name of Product", fontWeight = FontWeight.Bold)
    OutlinedTextField(
        value = NameFieldState,
        onValueChange = {
            NameFieldState = it
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text("Enter name here")
        }
    )
    Spacer(modifier = Modifier.height(10.dp))
    return NameFieldState
}


@Composable
fun AddPrice(): String {
    var toastContext = LocalContext.current
    var PriceFieldState by remember {
        mutableStateOf("")
    }
    Text("Price of product", fontWeight = FontWeight.Bold)
    OutlinedTextField(
        value = PriceFieldState,

        onValueChange = {
            val integerChars = '0'..'9'

            fun isNumber(input: String): Boolean {
                var dotOccurred = 0
                return input.all { it in integerChars || it == '.' && dotOccurred++ < 1 }
            }

            if(isNumber(it))
                PriceFieldState = it
            else{
                Toast.makeText(
                    toastContext,
                    "Invalid price amount!",
                    Toast.LENGTH_SHORT
                ).show();
            }
        },
        label = {
            Text("Enter price here")
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(10.dp))
    return PriceFieldState
}


@Composable
fun AddQuantity(): String {
    var QuantityFieldState by remember {
        mutableStateOf("")
    }

    Text("Quantity available of product", fontWeight = FontWeight.Bold)
    OutlinedTextField(
        value = QuantityFieldState,

        onValueChange = {
            QuantityFieldState = it
        },
        label = {
            Text("Enter Quantity here")
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(10.dp))

    return QuantityFieldState
}

@Composable
fun AddDiscount(): String {
    var toastContext = LocalContext.current
    var discountFieldState by remember {
        mutableStateOf("")
    }

    Text("Discount for product", fontWeight = FontWeight.Bold)
    OutlinedTextField(
        value = discountFieldState,
        onValueChange = {
            if(it.toDouble() in 0.0..1.0)
                discountFieldState = it
            else{
                Toast.makeText(
                    toastContext,
                    "Invalid discount amount!",
                    Toast.LENGTH_SHORT
                ).show();
            }
        },
        label = {
            Text("Enter Discount here (0.0 to 1.0)")
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(10.dp))

    return discountFieldState
}
// @Composable
/*fun ColorsOptions() {
    Spacer(modifier = Modifier.height(3.dp))

    Text("Colors of product (multiple choices are possible)", fontWeight = FontWeight.Bold)

    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Black", "White", "Pink", "Blue", "Red")
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
            label = { Text("Select colors") },
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
    Spacer(modifier = Modifier.height(10.dp))

}
*/

/*
@Composable
fun SizeSelect() {

    val SizesList = listOf("XS", "S", "M", "L", "XL")
    Text("Sizes of product (multiple select is possible)", fontWeight = FontWeight.Bold)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SizesList.forEach { size ->
            var selected by remember { mutableStateOf(false) }
            val color = if (selected) Color.LightGray else Color.White
            Button(
                modifier = Modifier
                    .width(60.dp)
                    .height(40.dp)
                    .padding(4.dp),
                onClick = { selected = !selected },
                colors = ButtonDefaults.buttonColors(backgroundColor = color)
            ) {
                Text(size, fontSize = 10.sp)
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}
*/



