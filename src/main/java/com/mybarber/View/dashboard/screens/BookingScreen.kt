package com.mybarber.View.dashboard.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mybarber.R
import com.mybarber.model.BarberService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    barberName: String = "Alex",
    onBackClick: () -> Unit,
    onBookClick: () -> Unit
) {
    var selectedDate by remember { mutableStateOf(5) }
    var selectedTime by remember { mutableStateOf("9:30 AM") }
    val selectedServices = remember { mutableStateListOf<String>() }

    // Calendar state
    var calendarDate by remember { mutableStateOf(java.util.Calendar.getInstance().apply { set(2024, 4, 1) }) } // Start with May 2024 as in screenshot
    val monthName = java.text.SimpleDateFormat("MMMM yyyy", java.util.Locale.getDefault()).format(calendarDate.time)
    
    val daysInMonth = calendarDate.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)
    val startDayOfWeek = calendarDate.get(java.util.Calendar.DAY_OF_WEEK) - 1 // 0 = Sunday, 1 = Monday...

    val timeSlots = listOf("9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM")
    val services = listOf(
        BarberService("Haircut", "$25"),
        BarberService("Beard Trim", "$15"),
        BarberService("Hair Wash", "$10")
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.book_with, barberName),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .navigationBarsPadding(),
                shadowElevation = 8.dp
            ) {
                Button(
                    onClick = onBookClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
                ) {
                    Text(text = stringResource(R.string.book), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Select Date Section
            Text(
                text = stringResource(R.string.select_date),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Simple Calendar
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.ChevronLeft,
                        contentDescription = "Previous Month",
                        tint = Color.Gray,
                        modifier = Modifier.clickable {
                            val newCal = java.util.Calendar.getInstance().apply {
                                time = calendarDate.time
                                add(java.util.Calendar.MONTH, -1)
                            }
                            calendarDate = newCal
                        }
                    )
                    Spacer(modifier = Modifier.width(32.dp))
                    Text(text = monthName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(32.dp))
                    Icon(
                        Icons.Default.ChevronRight,
                        contentDescription = "Next Month",
                        tint = Color.Gray,
                        modifier = Modifier.clickable {
                            val newCal = java.util.Calendar.getInstance().apply {
                                time = calendarDate.time
                                add(java.util.Calendar.MONTH, 1)
                            }
                            calendarDate = newCal
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                val daysOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    daysOfWeek.forEach { day ->
                        Text(text = day, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.width(32.dp), textAlign = TextAlign.Center)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Days grid
                Column {
                    for (week in 0..5) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                            for (dayOffset in 0..6) {
                                val dayIndex = week * 7 + dayOffset - startDayOfWeek + 1
                                if (dayIndex in 1..daysInMonth) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(if (selectedDate == dayIndex) Color(0xFF1E88E5) else Color.Transparent)
                                            .clickable { selectedDate = dayIndex },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = dayIndex.toString(),
                                            color = if (selectedDate == dayIndex) Color.White else Color.Black,
                                            fontSize = 14.sp
                                        )
                                    }
                                } else {
                                    Spacer(modifier = Modifier.size(40.dp))
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Select Time Section
            Text(
                text = stringResource(R.string.select_time),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Time Slots Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(110.dp)
            ) {
                items(timeSlots) { time ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (selectedTime == time) Color(0xFF1E88E5) else Color(0xFFF0F2F5),
                        modifier = Modifier
                            .height(48.dp)
                            .clickable { selectedTime = time }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = time,
                                color = if (selectedTime == time) Color.White else Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Select Services Section
            Text(
                text = stringResource(R.string.select_services),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            services.forEach { service ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            if (selectedServices.contains(service.name)) {
                                selectedServices.remove(service.name)
                            } else {
                                selectedServices.add(service.name)
                            }
                        }
                ) {
                    Text(
                        text = "${service.name} - ${service.price}",
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp
                    )
                    Checkbox(
                        checked = selectedServices.contains(service.name),
                        onCheckedChange = {
                            if (it) selectedServices.add(service.name) else selectedServices.remove(service.name)
                        },
                        colors = CheckboxDefaults.colors(checkedColor = Color(0xFF1E88E5))
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
