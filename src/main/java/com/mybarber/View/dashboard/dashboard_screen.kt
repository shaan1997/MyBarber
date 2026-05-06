@file:OptIn(ExperimentalMaterial3Api::class)

package com.mybarber.View.dashboard

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mybarber.R
import com.mybarber.navigation.NavHostBottom
import com.mybarber.navigation.Screen
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import java.util.Map

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var showExitDialog by remember { mutableStateOf(false) }


   /* BackHandler {
    val currentRoute = navController.currentDestination?.route
        if(currentRoute == Screen.Dashboard.toString() || currentRoute == "search"){
            showExitDialog = true
        }
    }*/

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHostBottom(navController)
        }
//        SearchContent(Modifier.padding(innerPadding))
    }

    ExitAppHandler(navController)

   /* if (showExitDialog) {
        val scope = rememberCoroutineScope()
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Exit App") },
            text = { Text("Do you really want to exit?") },
            confirmButton = {
                TextButton(onClick = {
                    showExitDialog = false
                    // Close or minimize the app
                    LaunchedEffect(Unit) {
                        // Wait one frame
                        yield()
                        (context as? Activity)?.finishAffinity()
                    }

                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text("No")
                }
            }
        )
    }*/
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchContent(modifier: Modifier = Modifier) {
    var query by remember { mutableStateOf("") }
    val services = listOf(
        stringResource(R.string.hair_cut),
        stringResource(R.string.beard_trim),
        stringResource(R.string.hair_color)
    )
    val sortFilters = listOf(
        stringResource(R.string.rating),
        stringResource(R.string.distance)
    )
    val barbers = listOf(
        BarberShop("The Barber Shop", "4.8", "1.2km", R.drawable.barber_1),
        BarberShop("Sharp Cuts", "4.5", "2.5km", R.drawable.barber_2),
        BarberShop("Style Masters", "4.9", "0.8km", R.drawable.barber_3),
        BarberShop("Grooming Lounge", "4.7", "1.5km", R.drawable.barber_4),
        BarberShop("The Barber Shop", "4.8", "1.2km", R.drawable.barber_1),
        BarberShop("Sharp Cuts", "4.5", "2.5km", R.drawable.barber_2),
        BarberShop("Style Masters", "4.9", "0.8km", R.drawable.barber_3),
        BarberShop("Grooming Lounge", "4.7", "1.5km", R.drawable.barber_4),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .align(alignment = Alignment.CenterHorizontally)
                .padding(bottom = 12.dp)

        ){
            Text(stringResource(R.string.dashboard_title), modifier = Modifier
                .fillMaxWidth(),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
                )
        }
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            placeholder = { Text(stringResource(R.string.search_hint)) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        // Filter Chips Row
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            services.forEach { item ->
                FilterChip(selected = false, onClick = { /*TODO*/ }, label = { Text(item) })
            }
            sortFilters.forEach { filter ->
                var expanded by remember { mutableStateOf(false) }
                Box {
                    FilterChip(
                        selected = false,
                        onClick = { expanded = true },
                        label = { Text(filter) },
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) }
                    )
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(text = { Text(stringResource(R.string.high_to_low)) }, onClick = { expanded = false })
                        DropdownMenuItem(text = { Text(stringResource(R.string.low_to_high)) }, onClick = { expanded = false })
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Barber Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            content = {
                items(barbers) { shop ->
                    BarberCard(shop)
                }
            }
        )
    }
}

data class BarberShop(
    val name: String,
    val rating: String,
    val distance: String,
    val imageRes: Int
)

@Composable
fun BarberCard(shop: BarberShop) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { }
    ) {
        Image(
            painter = painterResource(id = shop.imageRes),
            contentDescription = shop.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(140.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        )
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = shop.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${shop.rating} • ${shop.distance}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navhostController: NavHostController) {
//    Get Current navigation back stack Map.entry.**
    val navBackStackEntry by navhostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val items = listOf(
        stringResource(R.string.search_hint),
        stringResource(R.string.appointments_label),
        stringResource(R.string.profile_label)
    )
    NavigationBar {
        items.forEach { label ->
            NavigationBarItem(
                icon = {
                    when (label) {
                        stringResource(R.string.search_hint) -> Icon(Icons.Default.Search, contentDescription = label)
                        stringResource(R.string.appointments_label) -> Icon(Icons.Default.CalendarToday, contentDescription = label)
                        stringResource(R.string.profile_label) -> Icon(Icons.Default.Person, contentDescription = label)
                    }
                },
                label = { Text(label) },
//                Determine if the item is selected by comparing routes
                selected = currentDestination?.hierarchy?.any { it.route == label } == true,
                onClick = { navhostController.navigate(label){
                    // Pop up to the start of the graph to avoid building up a large stack of the same destinations
                    popUpTo(navhostController.graph.startDestinationId) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                } }
            )
        }
    }
}

@Composable
fun ExitAppHandler(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showExitDialog by remember { mutableStateOf(false) }

    // Handle system back button
    BackHandler {
        val currentRoute = navController.currentDestination?.route
        if(currentRoute == Screen.Dashboard.toString() || currentRoute == "search"){
            showExitDialog = true
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text(stringResource(R.string.exit_app_title)) },
            text = { Text(stringResource(R.string.exit_app_message)) },
            confirmButton = {
                TextButton(onClick = {
                    // 1️⃣ Close dialog first
                    showExitDialog = false

                    // 2️⃣ Launch side-effect safely
                    scope.launch {
                        (context as? Activity)?.finishAffinity()
                    }
                }) {
                    Text(stringResource(R.string.yes))
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text(stringResource(R.string.no))
                }
            }
        )
    }
}
