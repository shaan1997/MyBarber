@file:OptIn(ExperimentalMaterial3Api::class)

package com.mybarber.View.dashboard

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mybarber.R

@Composable
fun MainScreen() {
    Scaffold(
        topBar = { SearchTopBar() },
        bottomBar = { BottomNavigationBar() }
    ) { innerPadding ->
        SearchContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun SearchTopBar() {
    CenterAlignedTopAppBar(
        title = { Text("Dashboard", fontSize = 22.sp, fontWeight = FontWeight.Bold) }
//        actions = {
//            IconButton(onClick = { /* open map */ }) {
//                Icon(Icons.Default, contentDescription = "Map")
//            }
//        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchContent(modifier: Modifier = Modifier) {
    var query by remember { mutableStateOf("") }
    val services = listOf("Hair cut", "Beard Trim", "Hair Color")
    val sortFilters = listOf("Rating", "Distance")
    val barbers = listOf(
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
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            placeholder = { Text("Search") },
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
                        DropdownMenuItem(text = { Text("High to Low") }, onClick = { expanded = false })
                        DropdownMenuItem(text = { Text("Low to High") }, onClick = { expanded = false })
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
fun BottomNavigationBar() {
    val items = listOf("Search", "Appointments", "Profile")
    NavigationBar {
        items.forEach { label ->
            NavigationBarItem(
                icon = {
                    when (label) {
                        "Search" -> Icon(Icons.Default.Search, contentDescription = label)
                        "Appointments" -> Icon(Icons.Default.CalendarToday, contentDescription = label)
                        "Profile" -> Icon(Icons.Default.Person, contentDescription = label)
                    }
                },
                label = { Text(label) },
                selected = false,
                onClick = { /* handle navigation */ }
            )
        }
    }
}
