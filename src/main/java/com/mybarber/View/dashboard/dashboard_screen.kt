@file:OptIn(ExperimentalMaterial3Api::class)

package com.mybarber.View.dashboard

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
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
import com.mybarber.model.Barber
import com.mybarber.View.getMockBarber
import com.mybarber.navigation.NavHostBottom
import com.mybarber.navigation.Screen
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import java.util.Map

@Composable
fun MainScreen(
    onBarberClick: (Barber) -> Unit,
    rootNavController: NavHostController,
    onLogout: () -> Unit = {}
) {
    val navController = rememberNavController()

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
            NavHostBottom(
                navController = navController,
                onBarberClick = onBarberClick,
                rootNavController = rootNavController,
                onLogout = onLogout
            )
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


@Composable
fun SearchContent(modifier: Modifier = Modifier, onBarberClick: (Barber) -> Unit = {}) {
    var isSearchActive by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        if (isSearchActive) {
            ActiveSearchScreen(
                onBack = { isSearchActive = false },
                onBarberClick = onBarberClick
            )
        } else {
            DashboardContent(
                onSearchClick = { isSearchActive = true },
                onBarberClick = onBarberClick
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DashboardContent(onSearchClick: () -> Unit, onBarberClick: (Barber) -> Unit) {
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
        getMockBarber().copy(id = "1", name = "The Barber Shop", rating = 4.8f, distance = "1.2km", profileImage = R.drawable.barber_1),
        getMockBarber().copy(id = "2", name = "Sharp Cuts", rating = 4.5f, distance = "2.5km", profileImage = R.drawable.barber_2),
        getMockBarber().copy(id = "3", name = "Style Masters", rating = 4.9f, distance = "0.8km", profileImage = R.drawable.barber_3),
        getMockBarber().copy(id = "4", name = "Grooming Lounge", rating = 4.7f, distance = "1.5km", profileImage = R.drawable.barber_4),
        getMockBarber().copy(id = "5", name = "The Barber Shop", rating = 4.8f, distance = "1.2km", profileImage = R.drawable.barber_1),
        getMockBarber().copy(id = "6", name = "Sharp Cuts", rating = 4.5f, distance = "2.5km", profileImage = R.drawable.barber_2),
        getMockBarber().copy(id = "7", name = "Style Masters", rating = 4.9f, distance = "0.8km", profileImage = R.drawable.barber_3),
        getMockBarber().copy(id = "8", name = "Grooming Lounge", rating = 4.7f, distance = "1.5km", profileImage = R.drawable.barber_4),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        ) {
            Text(
                stringResource(R.string.dashboard_title),
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
                .onFocusChanged {
                    if (it.isFocused) onSearchClick()
                }
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
                    BarberCard(shop, onClick = { onBarberClick(shop) })
                }
            }
        )
    }
}

@Composable
fun BarberCard(shop: Barber, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = shop.profileImage),
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
fun ActiveSearchScreen(onBack: () -> Unit, onBarberClick: (Barber) -> Unit) {
    var query by remember { mutableStateOf("") }
    var selectedView by remember { mutableStateOf("Combine") }

    BackHandler { onBack() }

    val categories = listOf(
        stringResource(R.string.hair_cut),
        stringResource(R.string.beard_trim),
        stringResource(R.string.hair_color)
    )

    val barbers = listOf(
        getMockBarber().copy(id = "1", name = "The Barber Shop", rating = 4.8f, distance = "1.2km", profileImage = R.drawable.barber_1),
        getMockBarber().copy(id = "2", name = "Sharp Cuts", rating = 4.5f, distance = "2.5km", profileImage = R.drawable.barber_2),
    )

    val salons = listOf(
        getMockBarber().copy(id = "3", name = "Style Masters", rating = 4.9f, distance = "0.8km", profileImage = R.drawable.barber_3),
        getMockBarber().copy(id = "4", name = "Grooming Lounge", rating = 4.7f, distance = "1.5km", profileImage = R.drawable.barber_4),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // --- Header ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Text(
                text = stringResource(R.string.search_title),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Surface(
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 2.dp
            ) {
                IconButton(onClick = { /* TODO: Open Map */ }) {
                    Icon(
                        imageVector = Icons.Default.Map,
                        contentDescription = "Map",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            // --- Search Bar ---
            item {
                TextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    placeholder = {
                        Text(
                            stringResource(R.string.search_for_barbers),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    singleLine = true
                )
            }

            // --- Categories ---
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    categories.forEach { category ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = category,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            // --- View Toggle ---
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.view_label),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    ViewToggle(
                        selectedView = selectedView,
                        onViewSelected = { selectedView = it }
                    )
                }
            }

            // --- Barbers Section ---
            if (selectedView == "Combine" || selectedView == "Barbers") {
                item {
                    SectionHeader(
                        title = stringResource(R.string.barbers),
                        icon = Icons.Default.ContentCut
                    )
                }
                items(barbers) { barber ->
                    BarberHorizontalCard(
                        barber = barber,
                        onClick = { onBarberClick(barber) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // --- Salons Section ---
            if (selectedView == "Combine" || selectedView == "Salons") {
                item {
                    SectionHeader(
                        title = stringResource(R.string.salons),
                        icon = Icons.Default.Storefront
                    )
                }
                item {
                    // Grid for salons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        salons.forEach { salon ->
                            SalonVerticalCard(
                                salon = salon,
                                modifier = Modifier.weight(1f),
                                onClick = { onBarberClick(salon) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ViewToggle(selectedView: String, onViewSelected: (String) -> Unit) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.height(36.dp)
    ) {
        Row(
            modifier = Modifier.padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ToggleItem("Combine", selectedView == "Combine", onClick = { onViewSelected("Combine") })
            ToggleItem("Barbers", selectedView == "Barbers", icon = Icons.Default.ContentCut, onClick = { onViewSelected("Barbers") })
            ToggleItem("Salons", selectedView == "Salons", icon = Icons.Default.Storefront, onClick = { onViewSelected("Salons") })
        }
    }
}

@Composable
fun ToggleItem(text: String, isSelected: Boolean, icon: androidx.compose.ui.graphics.vector.ImageVector? = null, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SectionHeader(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun BarberHorizontalCard(barber: Barber, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left indicator bar
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                painter = painterResource(id = barber.profileImage),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = barber.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${barber.rating}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = " • ${barber.distance}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun SalonVerticalCard(salon: Barber, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = salon.profileImage),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = salon.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${salon.rating}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = " • ${salon.distance}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navhostController: NavHostController) {
//    Get Current navigation back stack Map.entry.**
    val navBackStackEntry by navhostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val items = listOf(
        Screen.Search,
        Screen.Appointments,
        Screen.Profile
    )
    NavigationBar {
        items.forEach { screen ->
            val label = when (screen) {
                Screen.Search -> stringResource(R.string.search_hint)
                Screen.Appointments -> stringResource(R.string.appointments_label)
                Screen.Profile -> stringResource(R.string.profile_label)
                else -> ""
            }
            NavigationBarItem(
                icon = {
                    when (screen) {
                        Screen.Search -> Icon(Icons.Default.Search, contentDescription = label)
                        Screen.Appointments -> Icon(Icons.Default.CalendarToday, contentDescription = label)
                        Screen.Profile -> Icon(Icons.Default.Person, contentDescription = label)
                        else -> Icon(Icons.Default.Search, contentDescription = label)
                    }
                },
                label = { Text(label) },
//                Determine if the item is selected by comparing routes
                selected = currentDestination?.hierarchy?.any { it.route?.contains(screen.javaClass.simpleName, ignoreCase = true) == true } == true,
                onClick = { navhostController.navigate(screen){
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
