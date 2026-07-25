package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.CateringViewModel
import com.example.ui.components.BookingDialog
import com.example.ui.screens.AgencyManagerScreen
import com.example.ui.screens.BookingsScreen
import com.example.ui.screens.CostEstimatorScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.StaffBookingScreen
import com.example.ui.screens.UniformCatalogScreen
import com.example.ui.theme.CateringGoldAccent
import com.example.ui.theme.CateringNavyPrimary
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainCateringApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCateringApp(
    viewModel: CateringViewModel = viewModel()
) {
    var selectedScreen by remember { mutableIntStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val userMessage by viewModel.userMessage.collectAsState()
    val isManagerMode by viewModel.isManagerMode.collectAsState()

    // Dialog state
    var showBookingDialog by remember { mutableStateOf(false) }
    var dialogStaffRole by remember { mutableStateOf("Server Waiter") }
    var dialogWaiterCount by remember { mutableIntStateOf(2) }
    var dialogUniformCategory by remember { mutableStateOf("Formal Suits") }
    var dialogUniformCount by remember { mutableIntStateOf(2) }

    LaunchedEffect(userMessage) {
        userMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearUserMessage()
        }
    }

    val openBookingModal = { staffRole: String, waiterCount: Int, uniformCategory: String, uniformCount: Int ->
        dialogStaffRole = staffRole
        dialogWaiterCount = waiterCount
        dialogUniformCategory = uniformCategory
        dialogUniformCount = uniformCount
        showBookingDialog = true
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isManagerMode) "FestForge Agency Admin" else "FestForge Staffing",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.isManagerMode.value = !isManagerMode
                        },
                        modifier = Modifier.testTag("toggle_manager_mode_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = "Toggle Manager Mode",
                            tint = if (isManagerMode) CateringGoldAccent else Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CateringNavyPrimary
                )
            )
        },
        bottomBar = {
            if (!isManagerMode) {
                NavigationBar(
                    containerColor = CateringNavyPrimary,
                    contentColor = Color.White
                ) {
                    val navItems = listOf(
                        Triple("Home", Icons.Default.Home, "nav_home"),
                        Triple("Waiters", Icons.Default.Groups, "nav_waiters"),
                        Triple("Uniforms", Icons.Default.Checkroom, "nav_uniforms"),
                        Triple("Estimator", Icons.Default.Calculate, "nav_estimator"),
                        Triple("Orders", Icons.Default.Receipt, "nav_orders")
                    )

                    navItems.forEachIndexed { index, (label, icon, tag) ->
                        NavigationBarItem(
                            selected = selectedScreen == index,
                            onClick = { selectedScreen = index },
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = CateringNavyPrimary,
                                selectedTextColor = CateringGoldAccent,
                                indicatorColor = CateringGoldAccent,
                                unselectedIconColor = Color.LightGray,
                                unselectedTextColor = Color.LightGray
                            ),
                            modifier = Modifier.testTag(tag)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isManagerMode) {
                AgencyManagerScreen(viewModel = viewModel)
            } else {
                when (selectedScreen) {
                    0 -> HomeScreen(
                        viewModel = viewModel,
                        onNavigateToStaff = { selectedScreen = 1 },
                        onNavigateToUniforms = { selectedScreen = 2 },
                        onNavigateToEstimator = { selectedScreen = 3 },
                        onNavigateToBookings = { selectedScreen = 4 },
                        onOpenBookingDialog = { role, waiters, uCat, uCount ->
                            openBookingModal(role, waiters, uCat, uCount)
                        }
                    )
                    1 -> StaffBookingScreen(
                        viewModel = viewModel,
                        onOpenBookingDialog = { role, waiters ->
                            openBookingModal(role, waiters, "Formal Suits", waiters)
                        }
                    )
                    2 -> UniformCatalogScreen(
                        viewModel = viewModel,
                        onOpenBookingDialog = { cat, count ->
                            openBookingModal("Server Waiter", count, cat, count)
                        }
                    )
                    3 -> CostEstimatorScreen(
                        viewModel = viewModel,
                        onBookCalculatedOrder = { waiters, uCat, uCount, total ->
                            openBookingModal("Event Staff", waiters, uCat, uCount)
                        }
                    )
                    4 -> BookingsScreen(
                        viewModel = viewModel,
                        onNavigateToStaff = { selectedScreen = 1 }
                    )
                }
            }
        }
    }

    if (showBookingDialog) {
        BookingDialog(
            initialStaffRole = dialogStaffRole,
            initialWaiterCount = dialogWaiterCount,
            initialUniformCategory = dialogUniformCategory,
            initialUniformCount = dialogUniformCount,
            onDismiss = { showBookingDialog = false },
            onSubmit = { clientName, phone, eventType, date, time, duration, address, waiters, uCat, uniforms, sizes, total, deposit ->
                viewModel.createBooking(
                    clientName,
                    phone,
                    eventType,
                    date,
                    time,
                    duration,
                    address,
                    waiters,
                    uCat,
                    uniforms,
                    sizes,
                    total,
                    deposit
                )
            }
        )
    }
}
