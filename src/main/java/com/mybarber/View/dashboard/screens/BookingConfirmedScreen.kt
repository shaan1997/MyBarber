package com.mybarber.View.dashboard.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mybarber.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingConfirmedScreen(
    barberName: String = "Rajeev Sharma",
    serviceName: String = "Classic Haircut",
    date: String = "July 20, 2024",
    time: String = "2:00 PM",
    price: String = "$25",
    onBackClick: () -> Unit,
    onViewAppointmentClick: () -> Unit,
    onCancelBookingClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.booking_confirmed_title),
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
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Confirmation Image
            Image(
                painter = painterResource(id = R.drawable.barber_1), // Placeholder for the illustration
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = stringResource(R.string.you_are_all_set),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.booking_confirmation_msg, barberName, serviceName),
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = stringResource(R.string.appointment_details_label),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Details List
                DetailRow(
                    icon = null,
                    imageRes = R.drawable.ic_barber_name_1,
                    label = stringResource(R.string.barber_label),
                    value = barberName
                )
                DetailRow(
                    icon = painterResource(id = R.drawable.ic_scissor),
                    label = stringResource(R.string.service_label),
                    value = serviceName
                )
                DetailRow(
                    iconVector = Icons.Default.CalendarToday,
                    label = stringResource(R.string.date_label),
                    value = date
                )
                DetailRow(
                    iconVector = Icons.Default.AccessTime,
                    label = stringResource(R.string.time_label),
                    value = time
                )
                DetailRow(
                    iconVector = Icons.Default.AttachMoney,
                    label = stringResource(R.string.price_label),
                    value = price
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Buttons
                Button(
                    onClick = onViewAppointmentClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBDD1E6), contentColor = Color(0xFF1E3A5F))
                ) {
                    Text(text = stringResource(R.string.view_appointment), fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onCancelBookingClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F2F5), contentColor = Color.Black)
                ) {
                    Text(text = stringResource(R.string.cancel_booking), fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun DetailRow(
    icon: androidx.compose.ui.graphics.painter.Painter? = null,
    iconVector: ImageVector? = null,
    imageRes: Int? = null,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF0F2F5)),
            contentAlignment = Alignment.Center
        ) {
            when {
                imageRes != null -> {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().clip(CircleShape)
                    )
                }
                icon != null -> {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                }
                iconVector != null -> {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color.Black
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(text = value, fontSize = 14.sp, color = Color.Gray)
        }
    }
}
