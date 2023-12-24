package com.example.evvolitm.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.evvolitm.R
import com.example.evvolitm.presentation.CartScreenState
import com.example.evvolitm.util.Screen


@Composable
fun EvvoliTopBar(
    navController: NavHostController,
    cartScreenState: CartScreenState,
    modifier: Modifier = Modifier,
) {
    var showMenu by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp) // Standard app bar height
            .padding(4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                painter = painterResource(R.drawable.evvoli_logo_b1),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(dimensionResource(R.dimen.logo_size))
                    .padding(vertical = dimensionResource(R.dimen.padding_small))
            )

//            // Search section
//            Box(modifier = Modifier.weight(1f)) {
//                BasicTextField(
//                    value = searchText,
//                    onValueChange = { searchText = it },
//                    decorationBox = { innerTextField ->
//                        if (searchText.isEmpty()) {
//                            Text("Search", color = Color.Gray)
//                        }
//                        innerTextField()
//                    }
//                )
//            }
        }
    }
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun TopBarPreview() {
//    EvvoliTopBar()
//}
//@Composable
//fun MyAppBar(navController: NavController) {
//    var showMenu by remember { mutableStateOf(false) }
//    var searchText by remember { mutableStateOf("") }
//
//    // Custom app bar layout
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(56.dp) // Standard app bar height
//            .padding(4.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        Row(
//            modifier = Modifier.fillMaxSize(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Logo
//            Image(
//                painter = painterResource(id = R.drawable.logo),
//                contentDescription = "App Logo",
//                modifier = Modifier.weight(1f)
//            )
//            // Dropdown menu for categories
//            Box(modifier = Modifier.weight(2f)) {
//                DropdownMenu(
//                    expanded = showMenu,
//                    onDismissRequest = { showMenu = false }
//                ) {
//                    DropdownMenuItem(onClick = { /* Handle click */ }) {
//                        Text("Category 1")
//                    }
//                    DropdownMenuItem(onClick = { /* Handle click */ }) {
//                        Text("Category 2")
//                    }
//                    // Add more categories as needed
//                }
//                Text("Categories", modifier = Modifier.clickable { showMenu = true })
//            }
//            // Search section
//            Box(modifier = Modifier.weight(3f)) {
//                BasicTextField(
//                    value = searchText,
//                    onValueChange = { searchText = it },
//                    decorationBox = { innerTextField ->
//                        if (searchText.isEmpty()) {
//                            Text("Search", color = Color.Gray)
//                        }
//                        innerTextField()
//                    }
//                )
//            }
//            // Cart section
//            IconButton(
//                onClick = { /* Handle cart click */ },
//                modifier = Modifier.weight(1f)
//            ) {
//                Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart")
//            }
//        }
//    }
//}

//@Composable
//fun MainScreen() {
//    val navController = rememberNavController()
//    Scaffold(
//        topBar = { MyAppBar(navController) }
//    ) {
//        // Content of your main screen goes here
//    }
//}
//
//object MainDestinations {
//    const val MAIN_ROUTE = "main"
//    const val CART_ROUTE = "cart"
//    // Define other routes as needed
//}
//
//@Composable
//fun AppNavHost(navController: NavController) {
//    NavHost(navController, startDestination = MainDestinations.MAIN_ROUTE) {
//        composable(MainDestinations.MAIN_ROUTE) {
//            MainScreen(navController)
//        }
//        composable(MainDestinations.CART_ROUTE) {
//            CartScreen() // Replace with your actual cart screen composable
//        }
//        // Add more destinations as needed
//    }
//}
//
//
//@Composable
//fun MainScreen(navController: NavController) {
//    Scaffold(
//        topBar = { MyAppBar(navController) }
//    ) {
//        AppNavHost(navController)
//    }
//}
//
//
//@Composable
//fun MyAppBar(navController: NavController) {
//    // ... existing code ...
//
//    // Cart section
//    IconButton(
//        onClick = { navController.navigate(MainDestinations.CART_ROUTE) },
//        modifier = Modifier.weight(1f)
//    ) {
//        Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart")
//    }
//}
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            val navController = rememberNavController()
//            MainScreen(navController)
//        }
//    }
//}
//
//dependencies {
//    implementation "androidx.navigation:navigation-compose:x.x.x" // Use the latest version
//    // Other dependencies...
//}
