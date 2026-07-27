package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.R
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.foundation.layout.imePadding

@Composable
fun LoginScreen(
onLoginSuccess: () -> Unit
) {
var phone by remember { mutableStateOf("") }

Box(modifier = Modifier.fillMaxSize()) {  

    Image(  
        painter = painterResource(id = R.drawable.login_background),  
        contentDescription = null,  
        modifier = Modifier.fillMaxSize(),  
        contentScale = ContentScale.Crop  
    )  

    Card(  
        modifier = Modifier  
            .fillMaxWidth()  
            .padding(horizontal = 16.dp)  
            .align(Alignment.Center),  
        shape = RoundedCornerShape(24.dp),  
        colors = CardDefaults.cardColors(  
            containerColor = Color.Black.copy(alpha = 0.55f)  
        )  
    ) {  

        Column(  
            modifier = Modifier  
                .fillMaxWidth()  
                .imePadding()
                .padding(24.dp),  
            horizontalAlignment = Alignment.CenterHorizontally  
        ) {  
OutlinedTextField(
    value = phone,
    onValueChange = { phone = it },
    prefix = {
        Text("+91 ", color = Color.White)
    },
    placeholder = {
        Text(
            "Enter 10-digit mobile",
            color = Color.LightGray
        )
    },
    singleLine = true,
    keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Phone
    ),
    modifier = Modifier.fillMaxWidth(),
    colors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White,
        focusedPlaceholderColor = Color.LightGray,
        unfocusedPlaceholderColor = Color.LightGray,
        focusedBorderColor = Color(0xFFFFD700),
        unfocusedBorderColor = Color(0xFFFFD700),
        focusedContainerColor = Color.Black.copy(alpha = 0.6f),
        unfocusedContainerColor = Color.Black.copy(alpha = 0.6f)
    )
)
           Spacer(modifier = Modifier.height(24.dp))  

            Button(
    onClick = { onLoginSuccess() },
    enabled = phone.length == 10,
    modifier = Modifier
        .fillMaxWidth()
        .height(56.dp),
    shape = RoundedCornerShape(16.dp),
    colors = ButtonDefaults.buttonColors(
        containerColor = Color(0xFFFFD700),
        contentColor = Color.Black
    )
)
            ) {  
                Text("Continue")  
            }  
        }  
    }  
}

}