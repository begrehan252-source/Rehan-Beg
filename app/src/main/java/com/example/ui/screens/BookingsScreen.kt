package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.entity.BookingOrder
import com.example.ui.CateringViewModel
import com.example.ui.theme.CateringGoldAccent
import com.example.ui.theme.CateringNavyPrimary
import com.example.ui.theme.CateringSuccessGreen

@Composable
fun BookingsScreen(
    viewModel: CateringViewModel,
    onNavigateToStaff: () -> Unit
) {
    val bookings by viewModel.bookingList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("bookings_screen")
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(CateringNavyPrimary)
                .padding(20.dp)
        ) {
            Text(
                text = "My FestForge Staffing Orders",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Text(
                text = "Track status of requested waiters & uniform deployments",
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray
            )
        }

        if (bookings.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Inventory, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("No catering staffing bookings yet.", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onNavigateToStaff,
                        colors = ButtonDefaults.buttonColors(containerColor = CateringNavyPrimary),
                        modifier = Modifier.testTag("empty_bookings_hire_btn")
                    ) {
                        Text("Browse & Book Waiters Now", color = Color.White)
                    }
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(bookings) { booking ->
                    BookingItemCard(
                        booking = booking,
                        onCancelClick = {
                            viewModel.cancelBooking(booking.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BookingItemCard(
    booking: BookingOrder,
    onCancelClick: () -> Unit
) {
    val statusColor = when (booking.status) {
        "Confirmed", "Staff Assigned" -> CateringSuccessGreen
        "Pending" -> CateringGoldAccent
        "Completed" -> CateringNavyPrimary
        else -> Color.Red
    }

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("booking_order_card_${booking.id}")
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Booking #${booking.id} • ${booking.eventType}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = CateringNavyPrimary)
                    )
                    Text("Client: ${booking.clientName} (${booking.clientPhone})", fontSize = 11.sp, color = Color.Gray)
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = statusColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = booking.status,
                        color = statusColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Details Box
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = CateringGoldAccent, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Date & Time: ${booking.eventDate} at ${booking.eventTime} (${booking.eventDurationHours} hrs)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = CateringGoldAccent, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Venue: ${booking.venueAddress}", fontSize = 12.sp, color = Color.DarkGray)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("👥 Waiter Staff: ${booking.waiterCount} Persons Required", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = CateringNavyPrimary)
                    if (booking.uniformCount > 0) {
                        Text("👔 Uniform Sets: ${booking.uniformCount} Sets (${booking.selectedUniformCategory} - ${booking.uniformSizes})", fontSize = 12.sp, color = Color.DarkGray)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("🙋 Assigned Staff: ${booking.assignedStaffNames}", fontSize = 12.sp, color = CateringNavyPrimary, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Total Amount Payable:", fontSize = 11.sp, color = Color.Gray)
                    Text("₹${booking.totalAmount.toInt()}", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = CateringNavyPrimary))
                }

                if (booking.status == "Pending" || booking.status == "Confirmed") {
                    OutlinedButton(
                        onClick = onCancelClick,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                        modifier = Modifier.testTag("cancel_booking_btn_${booking.id}")
                    ) {
                        Text("Cancel Request", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
