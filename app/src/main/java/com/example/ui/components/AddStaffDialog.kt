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
fun AddStaffDialog(
    onDismiss: () -> Unit,
    onSubmit: (name: String, role: String, expYears: Int, dailyRate: Double, phone: String, languages: String, skill: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("Server Waiter") }
    var expYears by remember { mutableStateOf("3") }
    var dailyRate by remember { mutableStateOf("1500") }
    var phone by remember { mutableStateOf("") }
    var languages by remember { mutableStateOf("Hindi, English") }
    var specialSkill by remember { mutableStateOf("Formal Banquet Serving") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(10.dp).testTag("add_staff_dialog")
        ) {
            Column(
                modifier = Modifier.padding(20.dp).verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Add New Waiter / Staff", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = CateringNavyPrimary))
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Staff Full Name") },
                    modifier = Modifier.fillMaxWidth().testTag("add_staff_name_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    label = { Text("Role (Head Captain / Waiter / Bartender / Helper)") },
                    modifier = Modifier.fillMaxWidth().testTag("add_staff_role_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = expYears,
                        onValueChange = { expYears = it },
                        label = { Text("Experience (Years)") },
                        modifier = Modifier.weight(1f).testTag("add_staff_exp_input"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = dailyRate,
                        onValueChange = { dailyRate = it },
                        label = { Text("Daily Rate (₹)") },
                        modifier = Modifier.weight(1f).testTag("add_staff_rate_input"),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth().testTag("add_staff_phone_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = languages,
                    onValueChange = { languages = it },
                    label = { Text("Languages Spoken") },
                    modifier = Modifier.fillMaxWidth().testTag("add_staff_languages_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = specialSkill,
                    onValueChange = { specialSkill = it },
                    label = { Text("Specialization / Skill") },
                    modifier = Modifier.fillMaxWidth().testTag("add_staff_skill_input")
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            onSubmit(
                                name,
                                role,
                                expYears.toIntOrNull() ?: 2,
                                dailyRate.toDoubleOrNull() ?: 1500.0,
                                phone,
                                languages,
                                specialSkill
                            )
                            onDismiss()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().testTag("add_staff_submit_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = CateringNavyPrimary)
                ) {
                    Text("Add Staff to Roster", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
