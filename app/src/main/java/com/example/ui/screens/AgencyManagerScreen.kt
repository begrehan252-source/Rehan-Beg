package com.example.ui.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.sp
import com.example.data.entity.BookingOrder
import com.example.data.entity.StaffMember
import com.example.data.entity.UniformItem
import com.example.ui.CateringViewModel
import com.example.ui.components.AddStaffDialog
import com.example.ui.components.AddUniformDialog
import com.example.ui.theme.CateringGoldAccent
import com.example.ui.theme.CateringNavyPrimary
import com.example.ui.theme.CateringSuccessGreen

@Composable
fun AgencyManagerScreen(
    viewModel: CateringViewModel
) {
    val staffList by viewModel.staffList.collectAsState()
    val uniformList by viewModel.uniformList.collectAsState()
    val bookings by viewModel.bookingList.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }
    var showAddStaffDialog by remember { mutableStateOf(false) }
    var showAddUniformDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("agency_manager_screen")
    ) {
        // Manager Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(CateringNavyPrimary)
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = CateringGoldAccent, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Agency Provider Manager",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "Manage waiter staff roster, uniform inventory & deployment orders",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.LightGray
                    )
                }
            }
        }

        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = CateringNavyPrimary,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = CateringGoldAccent,
                    height = 3.dp
                )
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Staff Roster (${staffList.size})", fontWeight = FontWeight.Bold) },
                modifier = Modifier.testTag("tab_staff_roster")
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Uniform Stock", fontWeight = FontWeight.Bold) },
                modifier = Modifier.testTag("tab_uniform_stock")
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = { Text("Bookings (${bookings.size})", fontWeight = FontWeight.Bold) },
                modifier = Modifier.testTag("tab_incoming_bookings")
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTab) {
                0 -> StaffRosterManagerTab(
                    staffList = staffList,
                    onToggleAvailability = { viewModel.toggleStaffAvailability(it) },
                    onAddStaffClick = { showAddStaffDialog = true }
                )
                1 -> UniformStockManagerTab(
                    uniformList = uniformList,
                    onAddUniformClick = { showAddUniformDialog = true }
                )
                2 -> BookingsManagerTab(
                    bookings = bookings,
                    onUpdateStatus = { id, status -> viewModel.updateBookingStatus(id, status) },
                    onAssignStaff = { id, names -> viewModel.assignStaffToBooking(id, names) }
                )
            }
        }
    }

    if (showAddStaffDialog) {
        AddStaffDialog(
            onDismiss = { showAddStaffDialog = false },
            onSubmit = { name, role, exp, rate, phone, langs, skill ->
                viewModel.addNewStaff(name, role, exp, rate, phone, langs, skill)
            }
        )
    }

    if (showAddUniformDialog) {
        AddUniformDialog(
            onDismiss = { showAddUniformDialog = false },
            onSubmit = { title, cat, sizes, rent, deposit, stock, desc, includes ->
                viewModel.addNewUniform(title, cat, sizes, rent, deposit, stock, desc, includes)
            }
        )
    }
}

@Composable
fun StaffRosterManagerTab(
    staffList: List<StaffMember>,
    onToggleAvailability: (StaffMember) -> Unit,
    onAddStaffClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(staffList) { staff ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth().testTag("manager_staff_item_${staff.id}")
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(staff.name, fontWeight = FontWeight.Bold, color = CateringNavyPrimary)
                            Text("${staff.role} • ${staff.phone}", fontSize = 12.sp, color = Color.Gray)
                            Text("Rate: ₹${staff.dailyRate.toInt()}/day", fontSize = 12.sp, color = CateringGoldAccent, fontWeight = FontWeight.SemiBold)
                        }

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (staff.isAvailable) CateringSuccessGreen else Color.Gray,
                            modifier = Modifier
                                .clickable { onToggleAvailability(staff) }
                                .testTag("toggle_staff_avail_${staff.id}")
                        ) {
                            Text(
                                text = if (staff.isAvailable) "Available" else "On Duty",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onAddStaffClick,
            containerColor = CateringGoldAccent,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .testTag("add_staff_fab")
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Staff")
        }
    }
}

@Composable
fun UniformStockManagerTab(
    uniformList: List<UniformItem>,
    onAddUniformClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(uniformList) { uniform ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth().testTag("manager_uniform_item_${uniform.id}")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(uniform.title, fontWeight = FontWeight.Bold, color = CateringNavyPrimary)
                        Text("Category: ${uniform.category} • Rent: ₹${uniform.dailyRentalPrice.toInt()}/day", fontSize = 12.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Total Stock: ${uniform.totalStock} Sets", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            Text("Available: ${uniform.availableStock} Sets", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CateringSuccessGreen)
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onAddUniformClick,
            containerColor = CateringNavyPrimary,
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .testTag("add_uniform_fab")
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Uniform")
        }
    }
}

@Composable
fun BookingsManagerTab(
    bookings: List<BookingOrder>,
    onUpdateStatus: (Int, String) -> Unit,
    onAssignStaff: (Int, String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(bookings) { booking ->
            var staffInput by remember { mutableStateOf(booking.assignedStaffNames) }

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth().testTag("manager_booking_item_${booking.id}")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Order #${booking.id} - ${booking.eventType}", fontWeight = FontWeight.Bold, color = CateringNavyPrimary)
                            Text("${booking.clientName} (${booking.clientPhone})", fontSize = 12.sp, color = Color.Gray)
                            Text("Venue: ${booking.venueAddress}", fontSize = 12.sp, color = Color.DarkGray)
                        }
                        Text("₹${booking.totalAmount.toInt()}", fontWeight = FontWeight.Bold, color = CateringGoldAccent)
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Needs: ${booking.waiterCount} Waiters • ${booking.uniformCount} Uniforms (${booking.selectedUniformCategory})", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = staffInput,
                        onValueChange = { staffInput = it },
                        label = { Text("Assign Staff Names") },
                        modifier = Modifier.fillMaxWidth().testTag("assign_staff_input_${booking.id}"),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onAssignStaff(booking.id, staffInput) },
                            colors = ButtonDefaults.buttonColors(containerColor = CateringNavyPrimary),
                            modifier = Modifier.weight(1f).testTag("save_staff_assign_btn_${booking.id}")
                        ) {
                            Text("Assign Staff", fontSize = 12.sp)
                        }

                        Button(
                            onClick = { onUpdateStatus(booking.id, "Completed") },
                            colors = ButtonDefaults.buttonColors(containerColor = CateringSuccessGreen),
                            modifier = Modifier.weight(1f).testTag("mark_completed_btn_${booking.id}")
                        ) {
                            Text("Complete", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
