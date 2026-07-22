package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.CateringGoldAccent
import com.example.ui.theme.CateringNavyPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUniformDialog(
    onDismiss: () -> Unit,
    onSubmit: (title: String, category: String, sizes: String, dailyRent: Double, deposit: Double, stock: Int, desc: String, includes: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Formal Suits") }
    var sizes by remember { mutableStateOf("S, M, L, XL") }
    var dailyRent by remember { mutableStateOf("300") }
    var deposit by remember { mutableStateOf("100") }
    var stock by remember { mutableStateOf("25") }
    var desc by remember { mutableStateOf("Clean sanitized catering uniform set") }
    var includes by remember { mutableStateOf("Waistcoat, Pants, Shirt & Bowtie") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(10.dp).testTag("add_uniform_dialog")
        ) {
            Column(
                modifier = Modifier.padding(20.dp).verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Add Uniform Set to Stock", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = CateringNavyPrimary))
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Uniform Set Title") },
                    modifier = Modifier.fillMaxWidth().testTag("add_uniform_title_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category (Formal Suits / Traditional / Waistcoat / Aprons)") },
                    modifier = Modifier.fillMaxWidth().testTag("add_uniform_category_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = dailyRent,
                        onValueChange = { dailyRent = it },
                        label = { Text("Daily Rent (₹)") },
                        modifier = Modifier.weight(1f).testTag("add_uniform_rent_input"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = deposit,
                        onValueChange = { deposit = it },
                        label = { Text("Deposit (₹)") },
                        modifier = Modifier.weight(1f).testTag("add_uniform_deposit_input"),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = sizes,
                        onValueChange = { sizes = it },
                        label = { Text("Sizes Available") },
                        modifier = Modifier.weight(1f).testTag("add_uniform_sizes_input"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = stock,
                        onValueChange = { stock = it },
                        label = { Text("Stock Qty") },
                        modifier = Modifier.weight(1f).testTag("add_uniform_stock_input"),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = includes,
                    onValueChange = { includes = it },
                    label = { Text("Items Included") },
                    modifier = Modifier.fillMaxWidth().testTag("add_uniform_includes_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            onSubmit(
                                title,
                                category,
                                sizes,
                                dailyRent.toDoubleOrNull() ?: 300.0,
                                deposit.toDoubleOrNull() ?: 100.0,
                                stock.toIntOrNull() ?: 20,
                                desc,
                                includes
                            )
                            onDismiss()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().testTag("add_uniform_submit_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = CateringGoldAccent)
                ) {
                    Text("Save Uniform Stock", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
