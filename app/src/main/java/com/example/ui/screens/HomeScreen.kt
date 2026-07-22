package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.entity.ComboPackage
import com.example.data.entity.StaffMember
import com.example.data.entity.UniformItem
import com.example.ui.CateringViewModel
import com.example.ui.theme.CateringGoldAccent
import com.example.ui.theme.CateringNavyPrimary
import com.example.ui.theme.CateringSuccessGreen

@Composable
fun HomeScreen(
    viewModel: CateringViewModel,
    onNavigateToStaff: () -> Unit,
    onNavigateToUniforms: () -> Unit,
    onNavigateToEstimator: () -> Unit,
    onNavigateToBookings: () -> Unit,
    onOpenBookingDialog: (staffRole: String, waiterCount: Int, uniformCategory: String, uniformCount: Int) -> Unit
) {
    val staffList by viewModel.staffList.collectAsState()
    val uniformList by viewModel.uniformList.collectAsState()
    val packageList by viewModel.packageList.collectAsState()
    val bookingList by viewModel.bookingList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .testTag("home_screen")
    ) {
        // Hero Banner Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_catering_hero),
                contentDescription = "Catering Team",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.3f),
                                CateringNavyPrimary.copy(alpha = 0.95f)
                            )
                        )
                    )
            )

            // Hero Text
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.festforge_logo_1784716833104),
                        contentDescription = "FestForge Logo",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        color = CateringGoldAccent,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "★ FestForge Waiters & Uniform Rental",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
                Text(
                    text = "Professional Event Staffing",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    text = "Hire trained waiters & rent sanitized uniforms for weddings & banquets",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Stats Strip
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuickStatCard(
                icon = Icons.Default.Groups,
                count = "${staffList.count { it.isAvailable }} Active",
                label = "Waiters Ready",
                color = CateringNavyPrimary,
                modifier = Modifier.weight(1f).clickable { onNavigateToStaff() }.testTag("stat_waiters_card")
            )
            QuickStatCard(
                icon = Icons.Default.Checkroom,
                count = "${uniformList.sumOf { it.availableStock }} Sets",
                label = "Uniform Stock",
                color = CateringGoldAccent,
                modifier = Modifier.weight(1f).clickable { onNavigateToUniforms() }.testTag("stat_uniforms_card")
            )
            QuickStatCard(
                icon = Icons.Default.Inventory,
                count = "${bookingList.size} Total",
                label = "My Orders",
                color = CateringSuccessGreen,
                modifier = Modifier.weight(1f).clickable { onNavigateToBookings() }.testTag("stat_bookings_card")
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 3 Primary FestForge Service Options
        Text(
            text = "3 Main Service Options",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = CateringNavyPrimary
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Large Option 1 Card: Waiters & Staffing
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { onNavigateToStaff() }
                .testTag("option_1_waiters_card")
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Restaurant, contentDescription = null, tint = Color.White)
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = CateringNavyPrimary
                    ) {
                        Text(
                            text = "Option 1 • Staffing",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "1. Waiters & Catering Staffing",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
                Text(
                    text = "Trained, vetted & uniform-ready waiters, captains, bartenders and banquet managers for events.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { onNavigateToStaff() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Explore Waiters →", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Grid for Option 2 and Option 3
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Option 2 Card: Uniforms
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigateToUniforms() }
                    .testTag("option_2_uniforms_card")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = CateringGoldAccent.copy(alpha = 0.2f),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Checkroom, contentDescription = null, tint = CateringNavyPrimary)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = CateringGoldAccent,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Text(
                            text = "Option 2",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Text(
                        text = "2. Uniform Rentals",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = CateringNavyPrimary
                    )
                    Text(
                        text = "Sanitized tuxedos, waistcoats & traditional suits",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }

            // Option 3 Card: Logistics & Equipment
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onOpenBookingDialog("Staff & Logistics Transport", 5, "Formal Suits", 5)
                    }
                    .testTag("option_3_logistics_card")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = CateringSuccessGreen.copy(alpha = 0.2f),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.LocalShipping, contentDescription = null, tint = CateringSuccessGreen)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = CateringSuccessGreen,
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Text(
                            text = "Option 3",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Text(
                        text = "3. Logistics & Van",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = CateringNavyPrimary
                    )
                    Text(
                        text = "Staff transport vans & banquet equipment setup",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Quick Tools Row (Estimator & Package Deals)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ServiceActionTile(
                title = "Instant Cost Estimator",
                subtitle = "Calculate guest staffing budget",
                icon = Icons.Default.Calculate,
                accentColor = CateringSuccessGreen,
                modifier = Modifier.weight(1f).clickable { onNavigateToEstimator() }.testTag("action_instant_quote_tile")
            )
            ServiceActionTile(
                title = "Combo Package Deals",
                subtitle = "Staff + Uniform All-in-One",
                icon = Icons.Default.LocalOffer,
                accentColor = Color(0xFF8B5CF6),
                modifier = Modifier.weight(1f).clickable {
                    onOpenBookingDialog("Combo Package", 5, "Formal Suits", 5)
                }.testTag("action_package_deals_tile")
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Featured Waiter Staff Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Available Event Staff",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = CateringNavyPrimary
                )
            )
            Text(
                text = "See All (${staffList.size}) →",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = CateringGoldAccent,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.clickable { onNavigateToStaff() }.testTag("see_all_staff_link")
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(staffList.take(5)) { staff ->
                HomeStaffCard(
                    staff = staff,
                    onBookClick = {
                        onOpenBookingDialog(staff.role, 1, "Formal Suits", 1)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Popular Uniform Sets Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Popular Uniform Rentals",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = CateringNavyPrimary
                )
            )
            Text(
                text = "Catalog (${uniformList.size}) →",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = CateringGoldAccent,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.clickable { onNavigateToUniforms() }.testTag("see_all_uniforms_link")
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uniformList.take(5)) { uniform ->
                HomeUniformCard(
                    uniform = uniform,
                    onRentClick = {
                        onOpenBookingDialog("Server Waiter", 2, uniform.category, 2)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Combo Packages Section
        Text(
            text = "Featured Catering Combo Packs",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = CateringNavyPrimary
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            packageList.forEach { pkg ->
                ComboPackageCard(
                    comboPackage = pkg,
                    onBookNow = {
                        onOpenBookingDialog("Combo Package", pkg.waiterCount, "Formal Suits", pkg.waiterCount)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun QuickStatCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    count: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.08f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(6.dp))
            Text(count, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = color))
            Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
    }
}

@Composable
fun ServiceActionTile(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = accentColor.copy(alpha = 0.12f),
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(24.dp))
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = CateringNavyPrimary))
                Text(subtitle, style = MaterialTheme.typography.labelSmall, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Composable
fun HomeStaffCard(
    staff: StaffMember,
    onBookClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .width(200.dp)
            .testTag("home_staff_card_${staff.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = CateringNavyPrimary,
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = staff.name.take(1),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = CateringGoldAccent.copy(alpha = 0.15f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = CateringGoldAccent, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text("${staff.rating}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CateringNavyPrimary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(staff.name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), maxLines = 1)
            Text(staff.role, style = MaterialTheme.typography.labelSmall, color = CateringGoldAccent, fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(6.dp))

            Text("${staff.experienceYears} yrs exp • ${staff.languages}", style = MaterialTheme.typography.labelSmall, color = Color.Gray, maxLines = 1)

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("₹${staff.dailyRate.toInt()}/day", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CateringNavyPrimary)
                Button(
                    onClick = onBookClick,
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier
                        .height(30.dp)
                        .testTag("book_staff_btn_${staff.id}"),
                    colors = ButtonDefaults.buttonColors(containerColor = CateringNavyPrimary)
                ) {
                    Text("Hire", fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
fun HomeUniformCard(
    uniform: UniformItem,
    onRentClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .width(220.dp)
            .testTag("home_uniform_card_${uniform.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = CateringGoldAccent.copy(alpha = 0.12f),
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Text(
                    text = uniform.category,
                    color = CateringGoldAccent,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }

            Text(uniform.title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("Sizes: ${uniform.sizesAvailable}", style = MaterialTheme.typography.labelSmall, color = Color.Gray)

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = CateringSuccessGreen, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Sanitized & Ironed • In Stock (${uniform.availableStock})", fontSize = 10.sp, color = CateringSuccessGreen)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("₹${uniform.dailyRentalPrice.toInt()}/day", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CateringNavyPrimary)
                Button(
                    onClick = onRentClick,
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier
                        .height(30.dp)
                        .testTag("rent_uniform_btn_${uniform.id}"),
                    colors = ButtonDefaults.buttonColors(containerColor = CateringGoldAccent)
                ) {
                    Text("Rent", fontSize = 11.sp, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ComboPackageCard(
    comboPackage: ComboPackage,
    onBookNow: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("combo_pkg_card_${comboPackage.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(comboPackage.packageName, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = CateringNavyPrimary))
                    Text(comboPackage.tagLine, style = MaterialTheme.typography.bodySmall, color = CateringGoldAccent)
                }
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = CateringGoldAccent.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "Save ₹${(comboPackage.originalPrice - comboPackage.dailyPrice).toInt()}",
                        color = CateringGoldAccent,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text("👥 Staff Included: ${comboPackage.waiterCount} Waiters + ${comboPackage.captainCount} Captain", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
            Text("👔 Uniforms: ${comboPackage.uniformSetIncluded}", style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
            Text("✨ ${comboPackage.highlights}", style = MaterialTheme.typography.labelSmall, color = Color.Gray)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text("₹${comboPackage.dailyPrice.toInt()}", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = CateringNavyPrimary))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("₹${comboPackage.originalPrice.toInt()}", style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray), textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough)
                }
                Button(
                    onClick = onBookNow,
                    colors = ButtonDefaults.buttonColors(containerColor = CateringNavyPrimary),
                    modifier = Modifier.testTag("book_pkg_btn_${comboPackage.id}")
                ) {
                    Text("Book Pack", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
