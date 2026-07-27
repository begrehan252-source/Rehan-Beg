package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.R
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun LoginScreen(
onLoginSuccess: () -> Unit
) {
var phone by remember { mutableStateOf("") }

Box(modifier = Modifier.fillMaxSize()) {

Card(
modifier = Modifier
.fillMaxWidth()
.height(320.dp)
.padding(horizontal = 16.dp)
.align(Alignment.Center),
shape = RoundedCornerShape(24.dp),
colors = CardDefaults.cardColors(
containerColor = Color.Black.copy(alpha = 0.55f)
)
) {

Column(
modifier = Modifier
.fillMaxSize()
.padding(
start = 24.dp,
end = 24.dp,
bottom = 180.dp),
horizontalAlignment = Alignment.CenterHorizontally,
verticalArrangement = Arrangement.Bottom
) {

OutlinedTextField(  
        value = phone,  
        onValueChange = { phone = it },  
        prefix = {  
Text(  
    text = "+91 ",  
    color = Color.White  
)

},
placeholder = {
Text(
text = "Enter 10-digit mobile",
color = Color.LightGray
)
},
singleLine = true,
keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
modifier = Modifier.fillMaxWidth(),

colors = OutlinedTextFieldDefaults.colors(
focusedTextColor = Color.White,
unfocusedTextColor = Color.White,
focusedPlaceholderColor = Color.LightGray,
unfocusedPlaceholderColor = Color.LightGray,
focusedBorderColor = Color(0xFFFFD700),
unfocusedBorderColor = Color(0xFFFFD700)
)
)

Spacer(modifier = Modifier.height(24.dp))

Button(
onClick = {
onLoginSuccess()
},
modifier = Modifier
.fillMaxWidth()
.height(56.dp)
.padding(horizontal = 8.dp),
shape = RoundedCornerShape(16.dp),

colors = ButtonDefaults.buttonColors(
containerColor = Color(0xFFFFD700),
contentColor = Color.Black
)
) {
Text(
text = "Continue",
style = MaterialTheme.typography.titleMedium
)
}

}  
}

}
}