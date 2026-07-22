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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.entity.UniformItem
import com.example.ui.CateringViewModel
import com.example.ui.theme.CateringGoldAccent
import com.example.ui.theme.CateringNavyPrimary
import com.example.ui.theme.CateringSuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniformCatalogScreen(
    viewModel: CateringViewModel,
    onOpenBookingDialog: (uniformCategory: String, uniformCount: Int) -> Unit
) {
    val uniformList by viewModel.uniformList.collectAsState()
    val activeCategory by viewModel.selectedUniformCategoryFilter.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val categories = listOf("All", "Formal Suits", "Indian Traditional", "Waistcoat Sets", "Aprons & Caps", "Female Uniforms")

    val filteredUniforms = uniformList.filter {
        if (searchQuery.isBlank()) true
        else it.title.contains(searchQuery, ignoreCase = true) ||
             it.description.contains(searchQuery, ignoreCase = true) ||
             it.category.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("uniform_catalog_screen")
    ) {
        // Search & Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(CateringGoldAccent)
                .padding(16.dp)
        ) {
            Text(
                text = "FestForge Uniform Rentals",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Text(
                text = "100% Sanitized, Dry-Cleaned & Crisp Uniform Sets for Staff",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search tuxedos, waistcoats, aprons...", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = CateringNavyPrimary) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = CateringNavyPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("uniform_search_input")
            )
        }

        // Category Filter Pills
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                val isSelected = activeCategory == category
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) CateringNavyPrimary else MaterialTheme.colorScheme.surface,
                    shadowElevation = if (isSelected) 4.dp else 1.dp,
                    modifier = Modifier
                        .clickable { viewModel.selectedUniformCategoryFilter.value = category }
                        .testTag("filter_uniform_cat_$category")
                ) {
                    Text(
                        text = category,
                        color = if (isSelected) Color.White else CateringNavyPrimary,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }
        }

        // Sanitized Guarantee Badge Strip
        Surface(
            color = CateringNavyPrimary.copy(alpha = 0.05f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.LocalLaundryService, contentDescription = null, tint = CateringGoldAccent)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Laundering Guarantee: All rental uniforms are hot-steam ironed & delivered in plastic garment covers.",
                    fontSize = 11.sp,
                    color = CateringNavyPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Uniforms List
        if (filteredUniforms.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Checkroom, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("No uniform sets match this category.", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredUniforms) { uniform ->
                    UniformDetailCard(
                        uniform = uniform,
                        onRentClick = {
                            onOpenBookingDialog(uniform.category, 5)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun UniformDetailCard(
    uniform: UniformItem,
    onRentClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("uniform_detail_card_${uniform.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = CateringGoldAccent.copy(alpha = 0.15f),
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Text(
                            text = uniform.category,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = CateringGoldAccent,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        text = uniform.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = CateringNavyPrimary)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = CateringSuccessGreen.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = "${uniform.availableStock} Available",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = CateringSuccessGreen,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(uniform.description, style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(4.dp))
            Text("📦 Includes: ${uniform.includes}", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = FontWeight.SemiBold)
            Text("📏 Sizes Available: ${uniform.sizesAvailable}", style = MaterialTheme.typography.labelSmall, color = CateringNavyPrimary)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text("₹${uniform.dailyRentalPrice.toInt()}", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = CateringNavyPrimary))
                        Text(" / Set / Day", fontSize = 11.sp, color = Color.Gray)
                    }
                    Text("+ ₹${uniform.securityDepositPerItem.toInt()} Deposit (Refundable)", fontSize = 10.sp, color = CateringGoldAccent)
                }

                Button(
                    onClick = onRentClick,
                    colors = ButtonDefaults.buttonColors(containerColor = CateringGoldAccent),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("rent_uniform_item_btn_${uniform.id}")
                ) {
                    Text("Rent Set", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
