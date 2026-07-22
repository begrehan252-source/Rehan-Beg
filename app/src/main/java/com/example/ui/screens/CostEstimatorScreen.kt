package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.CateringViewModel
import com.example.ui.theme.CateringGoldAccent
import com.example.ui.theme.CateringNavyPrimary
import com.example.ui.theme.CateringSuccessGreen
import kotlin.math.roundToInt

@Composable
fun CostEstimatorScreen(
    viewModel: CateringViewModel,
    onBookCalculatedOrder: (waiterCount: Int, uniformCategory: String, uniformCount: Int, totalCost: Double) -> Unit
) {
    var guestCount by remember { mutableFloatStateOf(120f) }
    var eventHours by remember { mutableFloatStateOf(6f) }
    var includeCaptain by remember { mutableStateOf(true) }
    var includeUniforms by remember { mutableStateOf(true) }
    var selectedUniformType by remember { mutableStateOf("Formal Suits") }

    val guests = guestCount.roundToInt()
    val hours = eventHours.roundToInt()

    // Formula calculation:
    // 1 waiter per 20 guests
    val waitersNeeded = (guests / 20.0).coerceAtLeast(2.0).roundToInt()
    val captainsNeeded = if (includeCaptain) (guests / 100.0).coerceAtLeast(1.0).roundToInt() else 0

    val waiterRate = 1500.0 // per waiter per day
    val captainRate = 2200.0 // per captain per day
    val uniformRate = if (selectedUniformType == "Indian Traditional") 350.0 else 300.0
    val uniformDeposit = 100.0

    val totalStaffCost = (waitersNeeded * waiterRate) + (captainsNeeded * captainRate)
    val totalUniformCost = if (includeUniforms) (waitersNeeded + captainsNeeded) * uniformRate else 0.0
    val totalDeposit = if (includeUniforms) (waitersNeeded + captainsNeeded) * uniformDeposit else 0.0
    val grandTotal = totalStaffCost + totalUniformCost

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .testTag("cost_estimator_screen")
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(CateringNavyPrimary)
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Calculate, contentDescription = null, tint = CateringGoldAccent, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Instant Event Staff Calculator",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "Calculate optimal waiter count & uniform rental cost for your guest count",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.LightGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            // Guest Count Slider Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Expected Guest Count:", fontWeight = FontWeight.SemiBold, color = CateringNavyPrimary)
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = CateringNavyPrimary
                        ) {
                            Text(
                                text = "$guests Guests",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Slider(
                        value = guestCount,
                        onValueChange = { guestCount = it },
                        valueRange = 20f..500f,
                        steps = 47,
                        colors = SliderDefaults.colors(
                            thumbColor = CateringGoldAccent,
                            activeTrackColor = CateringNavyPrimary
                        ),
                        modifier = Modifier.testTag("guest_count_slider")
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("20 Guests", fontSize = 10.sp, color = Color.Gray)
                        Text("500 Guests", fontSize = 10.sp, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Duration Slider Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Event Duration:", fontWeight = FontWeight.SemiBold, color = CateringNavyPrimary)
                        Text("$hours Hours Service", fontWeight = FontWeight.Bold, color = CateringGoldAccent)
                    }

                    Slider(
                        value = eventHours,
                        onValueChange = { eventHours = it },
                        valueRange = 2f..12f,
                        steps = 9,
                        colors = SliderDefaults.colors(
                            thumbColor = CateringGoldAccent,
                            activeTrackColor = CateringNavyPrimary
                        ),
                        modifier = Modifier.testTag("duration_slider")
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Options Toggles Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Head Captain / Supervisor", fontWeight = FontWeight.SemiBold)
                            Text("Supervises buffet, refills & guest service", fontSize = 11.sp, color = Color.Gray)
                        }
                        Switch(
                            checked = includeCaptain,
                            onCheckedChange = { includeCaptain = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = CateringGoldAccent),
                            modifier = Modifier.testTag("toggle_captain_switch")
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Include Uniform Rentals", fontWeight = FontWeight.SemiBold)
                            Text("Crisp sanitized matching uniforms for all staff", fontSize = 11.sp, color = Color.Gray)
                        }
                        Switch(
                            checked = includeUniforms,
                            onCheckedChange = { includeUniforms = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = CateringGoldAccent),
                            modifier = Modifier.testTag("toggle_uniforms_switch")
                        )
                    }

                    if (includeUniforms) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Select Uniform Style:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = CateringNavyPrimary)
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("Formal Suits", "Indian Traditional", "Waistcoat Sets").forEach { style ->
                                val selected = selectedUniformType == style
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (selected) CateringGoldAccent else Color.LightGray.copy(alpha = 0.2f),
                                    modifier = Modifier.testTag("estimator_style_$style")
                                ) {
                                    Text(
                                        text = style,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (selected) Color.White else Color.DarkGray,
                                        modifier = Modifier
                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                            .clickable { selectedUniformType = style }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Calculated Instant Quotation Receipt
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CateringNavyPrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quotation_receipt_card")
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Receipt, contentDescription = null, tint = CateringGoldAccent)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Recommended Staffing Receipt", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = Color.White))
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("• Waiter Servers ($waitersNeeded staff):", color = Color.LightGray, fontSize = 13.sp)
                        Text("₹${(waitersNeeded * waiterRate).toInt()}", color = Color.White, fontWeight = FontWeight.SemiBold)
                    }

                    if (captainsNeeded > 0) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("• Head Captains ($captainsNeeded staff):", color = Color.LightGray, fontSize = 13.sp)
                            Text("₹${(captainsNeeded * captainRate).toInt()}", color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    if (includeUniforms) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("• ${selectedUniformType} (${waitersNeeded + captainsNeeded} sets):", color = Color.LightGray, fontSize = 13.sp)
                            Text("₹${totalUniformCost.toInt()}", color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("• Refundable Security Deposit:", color = Color.LightGray, fontSize = 13.sp)
                            Text("₹${totalDeposit.toInt()}", color = CateringGoldAccent, fontSize = 13.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Gray.copy(alpha = 0.5f)))
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Estimated Total Payable:", color = Color.LightGray, fontSize = 12.sp)
                            Text("₹${grandTotal.toInt()}", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = CateringGoldAccent))
                        }

                        Button(
                            onClick = {
                                onBookCalculatedOrder(
                                    waitersNeeded + captainsNeeded,
                                    selectedUniformType,
                                    if (includeUniforms) waitersNeeded + captainsNeeded else 0,
                                    grandTotal
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CateringGoldAccent),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("convert_quote_to_booking_btn")
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Book Quote", fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
