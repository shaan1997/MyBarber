package com.mybarber.View

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mybarber.R
import com.mybarber.model.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarberProfileScreen(
    barber: Barber = getMockBarber(),
    onBackClick: () -> Unit,
    onBookClick: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.barber_profile_title), fontWeight = FontWeight.Bold) },
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
                    .shadow(8.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Button(
                    onClick = { onBookClick(barber.name) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(54.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5D5FEF)
                    )
                ) {
                    Text(
                        text = stringResource(R.string.book_now),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = barber.profileImage),
                    contentDescription = barber.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = barber.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "${barber.rating} (${barber.reviewCount} reviews)",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Text(text = barber.address, color = Color.Gray, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Contact Section
            SectionTitle(stringResource(R.string.contact_label))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF5F5F5),
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        Icons.Default.Call,
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp),
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = barber.contactNumber, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Services Section
            SectionTitle(stringResource(R.string.services_label))
            barber.services.forEach { service ->
                ServiceItem(service)
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Hours Section
            SectionTitle(stringResource(R.string.hours_label))
            barber.hours.forEach { hour ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
                    Icon(Icons.Default.AccessTime, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "${hour.days}: ${hour.time}", fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Reviews Section
            SectionTitle(stringResource(R.string.reviews_label))
            RatingOverview(barber)
            Spacer(modifier = Modifier.height(24.dp))
            barber.reviews.forEach { review ->
                ReviewItem(review)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Gallery Section
            SectionTitle(stringResource(R.string.gallery_label))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(barber.gallery) { imageRes ->
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
fun ServiceItem(service: BarberService) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFF5F5F5),
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                painter = painterResource(id = service.icon ?: R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.padding(12.dp),
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = service.name, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(text = service.price, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
fun RatingOverview(barber: Barber) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = barber.rating.toString(), fontSize = 48.sp, fontWeight = FontWeight.Bold)
            RatingStars(rating = barber.rating)
            Text(text = "${barber.reviewCount} reviews", fontSize = 14.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.width(32.dp))
        Column(modifier = Modifier.weight(1f)) {
            for (i in 5 downTo 1) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = i.toString(), modifier = Modifier.width(12.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    LinearProgressIndicator(
                        progress = { barber.ratingDistribution[i] ?: 0f },
                        modifier = Modifier
                            .weight(1f)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Color.Black,
                        trackColor = Color(0xFFE0E0E0)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${((barber.ratingDistribution[i] ?: 0f) * 100).toInt()}%",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.width(30.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun RatingStars(rating: Float) {
    Row {
        repeat(5) { index ->
            val starIndex = index + 1
            val icon = when {
                rating >= starIndex -> Icons.Default.Star
                rating >= starIndex - 0.5f -> Icons.AutoMirrored.Filled.StarHalf
                else -> Icons.Default.StarOutline
            }
            Icon(icon, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun ReviewItem(review: BarberReview) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = review.authorAvatar),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = review.authorName, fontWeight = FontWeight.Bold)
                Text(text = review.date, fontSize = 12.sp, color = Color.Gray)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        RatingStars(rating = review.rating.toFloat())
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = review.comment, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.ThumbUp, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = review.likes.toString(), fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Outlined.ThumbDown, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = review.dislikes.toString(), fontSize = 12.sp, color = Color.Gray)
        }
    }
}

fun getMockBarber() = Barber(
    id = "1",
    name = "The Sharp Edge",
    rating = 4.8f,
    reviewCount = 120,
    address = "123 Main St, Anytown",
    distance = "1.2km",
    contactNumber = "(555) 123-4567",
    profileImage = R.drawable.ic_barber_name_1,
    services = listOf(
        BarberService("Haircut", "$25", R.drawable.ic_scissor),
        BarberService("Beard Trim", "$15", R.drawable.ic_trim),
        BarberService("Haircut & Beard Trim", "$40", R.drawable.ic_scissor)
    ),
    hours = listOf(
        OperatingHours("Mon-Fri", "9am - 7pm"),
        OperatingHours("Sat", "10am - 5pm"),
        OperatingHours("Sun", "Closed")
    ),
    reviews = listOf(
        BarberReview(
            "Ethan Carter", "2 weeks ago", 5,
            "Best haircut I've had in years! The barber was skilled and attentive to detail. Highly recommend.",
            R.drawable.ic_barber_name_1, 10, 2
        ),
        BarberReview(
            "Liam Harper", "1 month ago", 4,
            "Good experience overall. The barber was friendly and the haircut was decent, but could have been a bit more precise.",
            R.drawable.ic_barber_name_2, 5, 1
        )
    ),
    ratingDistribution = mapOf(5 to 0.7f, 4 to 0.2f, 3 to 0.05f, 2 to 0.03f, 1 to 0.02f),
    gallery = listOf(R.drawable.barber_1, R.drawable.barber_2, R.drawable.barber_3, R.drawable.barber_4)
)
