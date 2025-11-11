// AppointmentsScreen.kt

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Appointment(
    val title: String,
    val subtitle: String,
    val time: String
)

/**
 * Primary composable to display the Appointments screen.
 *
 * - appointments: list to render (defaults to 3 sample items)
 * - onViewDetails: optional click callback for the "View Details" pill
 * - modifier: host modifier
 *
 * Note: Bottom nav is visual only (no navigation logic).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentsScreen(
    appointments: List<Appointment> = listOf(
        Appointment("Barber: Rohan", "Haircut", "10:00 AM"),
        Appointment("Barber: Arjun Verma", "Haircut", "11:30 AM"),
        Appointment("Barber: Vikram", "Haircut", "12:00 PM"),
    ),
    onViewDetails: (Appointment) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(18.dp))

        // Title
        Text(
            text = "Upcoming Appointments",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF111827),
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Section header

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(appointments) { appt ->
                AppointmentRow(appointment = appt, onViewDetails = onViewDetails)
                Spacer(modifier = Modifier.height(12.dp))
            }
            // Keep content above bottom bar
            item { Spacer(modifier = Modifier.height(120.dp)) }
        }
    }
}

@Composable
private fun AppointmentRow(
    appointment: Appointment,
    onViewDetails: (Appointment) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent, RoundedCornerShape(12.dp))
            .shadow(0.dp, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // avatar placeholder
        Box(
            modifier = Modifier
                .size(52.dp)
                .background(Color(0xFFFFE8C6), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = Color(0xFF5B5B5B),
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = appointment.title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color(0xFF111827)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${appointment.subtitle} · ${appointment.time}",
                color = Color(0xFF6B7280),
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Surface(
            shape = RoundedCornerShape(20.dp),
            tonalElevation = 0.dp,
            color = Color(0xFFEBEDF2),
            modifier = Modifier
                .height(36.dp)
                .wrapContentWidth()
                .clickable { onViewDetails(appointment) }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Text(
                    text = "View Details",
                    fontSize = 13.sp,
                    color = Color(0xFF374151)
                )
            }
        }
    }
}


