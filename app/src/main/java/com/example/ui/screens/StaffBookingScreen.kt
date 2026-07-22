package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.entity.StaffMember
import com.example.ui.CateringViewModel
import com.example.ui.theme.CateringGoldAccent
import com.example.ui.theme.CateringNavyPrimary
import com.example.ui.theme.CateringSuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaffBookingScreen(
    viewModel: CateringViewModel,
    onOpenBookingDialog: (staffRole: String, waiterCount: Int) -> Unit
) {
    val staffList by viewModel.staffList.collectAsState()
    val activeFilter by viewModel.selectedRoleFilter.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current

    val rolesList = listOf("All", "Captain", "Waiter", "Bartender", "Hostess", "Buffet", "Helper")

    val filteredStaff = staffList.filter {
        if (searchQuery.isBlank()) true
        else it.name.contains(searchQuery, ignoreCase = true) ||
             it.specialSkill.contains(searchQuery, ignoreCase = true) ||
             it.role.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("staff_booking_screen")
    ) {
        // Search & Filter Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(CateringNavyPrimary)
                .padding(16.dp)
        ) {
            Text(
                text = "FestForge Waiters & Staff Roster",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Text(
                text = "Trained, vetted & uniform-ready staff for your event",
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search staff by name, role or skill...", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = CateringGoldAccent) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = CateringGoldAccent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("staff_search_input")
            )
        }

        // Role Filter Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(rolesList) { role ->
                val isSelected = activeFilter == role
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) CateringGoldAccent else MaterialTheme.colorScheme.surface,
                    shadowElevation = if (isSelected) 4.dp else 1.dp,
                    modifier = Modifier
                        .clickable { viewModel.selectedRoleFilter.value = role }
                        .testTag("filter_role_chip_$role")
                ) {
                    Text(
                        text = role,
                        color = if (isSelected) Color.White else CateringNavyPrimary,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }
        }

        // Staff List
        if (filteredStaff.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("No staff members found for this filter.", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredStaff) { staff ->
                    StaffDetailCard(
                        staff = staff,
                        onHireClick = {
                            onOpenBookingDialog(staff.role, 1)
                        },
                        onCallClick = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${staff.phone}"))
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun StaffDetailCard(
    staff: StaffMember,
    onHireClick: () -> Unit,
    onCallClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("staff_detail_card_${staff.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = CateringNavyPrimary,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = staff.name.take(1),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(staff.name, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = CateringNavyPrimary))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = CateringGoldAccent.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = staff.role,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CateringGoldAccent,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("• ${staff.experienceYears} Yrs Exp", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = CateringSuccessGreen.copy(alpha = 0.12f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = CateringSuccessGreen, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Available", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CateringSuccessGreen)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text("💡 Skill: ${staff.specialSkill}", style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
            Text("🗣 Languages: ${staff.languages}", style = MaterialTheme.typography.labelSmall, color = Color.Gray)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("₹${staff.dailyRate.toInt()} / Day", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = CateringNavyPrimary)
                    Text("(₹${staff.hourlyRate.toInt()}/hr for short events)", fontSize = 10.sp, color = Color.Gray)
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = onCallClick,
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("call_staff_btn_${staff.id}")
                    ) {
                        Icon(Icons.Default.Call, contentDescription = null, tint = CateringNavyPrimary, modifier = Modifier.size(16.dp))
                    }

                    Button(
                        onClick = onHireClick,
                        colors = ButtonDefaults.buttonColors(containerColor = CateringNavyPrimary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("hire_staff_btn_${staff.id}")
                    ) {
                        Text("Hire Now", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
