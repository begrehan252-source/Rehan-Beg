package com.festforge.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.festforge.app.R
import com.festforge.app.ui.navigation.Screen

@Composable
fun LoginScreen(navController: NavController) {
    var mobileNumber by remember { mutableStateOf("") }
    val isValid = mobileNumber.length == 10

    Box(modifier = Modifier.fillMaxSize()) {
        // Full screen background
        Image(
            painter = painterResource(id = R.drawable.luxury_bg), // Ensure you add this resource
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Black gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f)),
                        startY = 300f
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .imePadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Logo and Title
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    text = "FestForge",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(0xFFFFD700),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp
                )
            }

            // Input Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Login with Mobile Number",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = mobileNumber,
                    onValueChange = { if (it.length <= 10) mobileNumber = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Enter 10 digit number", color = Color.Gray) },
                    leadingIcon = { Text("+91", color = Color.White, modifier = Modifier.padding(start = 12.dp)) },
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFFD700),
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { navController.navigate("${Screen.OTP.route}/$mobileNumber") },
                    enabled = isValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFD700),
                        disabledContainerColor = Color.Gray.copy(alpha = 0.5f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("SEND OTP", fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }
        }
    }
}