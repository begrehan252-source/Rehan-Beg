package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.CateringGoldAccent
import com.example.ui.theme.CateringNavyPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingDialog(
    initialStaffRole: String = "Server Waiter",
    initialWaiterCount: Int = 2,
    initialUniformCategory: String = "Formal Suits",
    initialUniformCount: Int = 2,
    estimatedTotal: Double = 3500.0,
    onDismiss: () -> Unit,
    onSubmit: (
        clientName: String,
        clientPhone: String,
        eventType: String,
        eventDate: String,
        eventTime: String,
        durationHours: Int,
        address: String,
        waiters: Int,
        uniformCategory: String,
        uniforms: Int,
        uniformSizes: String,
        totalAmount: Double,
        deposit: Double
    ) -> Unit
) {
    var clientName by remember { mutableStateOf("") }
    var clientPhone by remember { mutableStateOf("") }
    var eventType by remember { mutableStateOf("Wedding / Reception") }
    var eventDate by remember { mutableStateOf("2026-08-20") }
    var eventTime by remember { mutableStateOf("07:00 PM") }
    var durationHours by remember { mutableIntStateOf(6) }
    var venueAddress by remember { mutableStateOf("") }
    var waitersCount by remember { mutableIntStateOf(initialWaiterCount) }
    var uniformsCount by remember { mutableIntStateOf(initialUniformCount) }
    var selectedUniformCat by remember { mutableStateOf(initialUniformCategory) }
    var uniformSizes by remember { mutableStateOf("L & M Standard") }

    val calculatedStaffFee = waitersCount * 1500.0
    val calculatedUniformFee = uniformsCount * 300.0
    val calculatedDeposit = uniformsCount * 100.0
    val grandTotal = calculatedStaffFee + calculatedUniformFee

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("booking_dialog")
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Book Waiters & Uniforms",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = CateringNavyPrimary
                            )
                        )
                        Text(
                            text = "Instant Catering Staff Reservation",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("dialog_close_button")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Host Details
                OutlinedTextField(
                    value = clientName,
                    onValueChange = { clientName = it },
                    label = { Text("Your Name / Catering Firm") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("booking_client_name_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = clientPhone,
                    onValueChange = { clientPhone = it },
                    label = { Text("Mobile Phone Number") },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("booking_client_phone_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = eventType,
                    onValueChange = { eventType = it },
                    label = { Text("Event Type (e.g. Wedding, Birthday, Corporate)") },
                    leadingIcon = { Icon(Icons.Default.Event, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("booking_event_type_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = eventDate,
                        onValueChange = { eventDate = it },
                        label = { Text("Event Date") },
                        leadingIcon = { Icon(Icons.Default.CalendarMonth, contentDescription = null) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("booking_event_date_input"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = eventTime,
                        onValueChange = { eventTime = it },
                        label = { Text("Start Time") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("booking_event_time_input"),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = venueAddress,
                    onValueChange = { venueAddress = it },
                    label = { Text("Venue Address / Banquet Name") },
                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("booking_venue_address_input")
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Quantity Selectors
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Catering Waiters Count:", fontWeight = FontWeight.SemiBold)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                TextButton(
                                    onClick = { if (waitersCount > 1) waitersCount-- },
                                    modifier = Modifier.testTag("waiter_minus_btn")
                                ) {
                                    Text("-", style = MaterialTheme.typography.titleLarge)
                                }
                                Text("$waitersCount Staff", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp))
                                TextButton(
                                    onClick = { waitersCount++ },
                                    modifier = Modifier.testTag("waiter_plus_btn")
                                ) {
                                    Text("+", style = MaterialTheme.typography.titleLarge)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Uniform Rental Sets:", fontWeight = FontWeight.SemiBold)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                TextButton(
                                    onClick = { if (uniformsCount > 0) uniformsCount-- },
                                    modifier = Modifier.testTag("uniform_minus_btn")
                                ) {
                                    Text("-", style = MaterialTheme.typography.titleLarge)
                                }
                                Text("$uniformsCount Sets", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp))
                                TextButton(
                                    onClick = { uniformsCount++ },
                                    modifier = Modifier.testTag("uniform_plus_btn")
                                ) {
                                    Text("+", style = MaterialTheme.typography.titleLarge)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = uniformSizes,
                            onValueChange = { uniformSizes = it },
                            label = { Text("Uniform Sizes Required (e.g. 2x M, 3x L)") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("booking_uniform_sizes_input"),
                            singleLine = true
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Price Calculation Breakdown
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CateringNavyPrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Staff Fee ($waitersCount Waiters):", color = Color.LightGray)
                            Text("₹${calculatedStaffFee.toInt()}", color = Color.White)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Uniform Rent ($uniformsCount Sets):", color = Color.LightGray)
                            Text("₹${calculatedUniformFee.toInt()}", color = Color.White)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Refundable Security Deposit:", color = Color.LightGray)
                            Text("₹${calculatedDeposit.toInt()}", color = CateringGoldAccent)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Total Estimated Payable:", fontWeight = FontWeight.Bold, color = Color.White)
                            Text(
                                "₹${grandTotal.toInt()}",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = CateringGoldAccent
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        onSubmit(
                            clientName,
                            clientPhone,
                            eventType,
                            eventDate,
                            eventTime,
                            durationHours,
                            venueAddress,
                            waitersCount,
                            selectedUniformCat,
                            uniformsCount,
                            uniformSizes,
                            grandTotal,
                            calculatedDeposit
                        )
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("submit_booking_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = CateringGoldAccent)
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Confirm & Reserve Booking", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}
