package com.mybarber.View.dashboard.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mybarber.R

data class BarberSpecialty(
    val name: String,
    val specialties: String,
    val imageRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectBarberScreen(
    onBackClick: () -> Unit,
    onBarberSelected: (String) -> Unit
) {
    val barbers = listOf(
        BarberSpecialty("Rohan Sharma", "Hair Styling, Beard Trim", R.drawable.ic_barber_name_1),
        BarberSpecialty("Vikram Singh", "Fades, Shaving", R.drawable.ic_barber_name_2),
        BarberSpecialty("Arjun Verma", "Coloring, Hair Spa", R.drawable.ic_barber_name_3),
        BarberSpecialty("Karan Kapoor", "Classic Cuts, Grooming", R.drawable.ic_barber_name_4),
        BarberSpecialty("Aryan Malhotra", "Trendy Styles, Facial", R.drawable.ic_barber_name_1)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.select_your_barber),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {
            items(barbers) { barber ->
                BarberItem(barber = barber, onClick = { onBarberSelected(barber.name) })
            }
        }
    }
}

@Composable
fun BarberItem(barber: BarberSpecialty, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = barber.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = barber.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = barber.specialties,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
