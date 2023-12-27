package com.example.evvolitm.ui.screens

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.evvolitm.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen() {
    println(AppCompatDelegate.getApplicationLocales())
    println("AppCompatDelegate.getApplicationLocales()[0]?.language " +
            "= ${AppCompatDelegate.getApplicationLocales()[0]?.language}")
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(stringResource(R.string.about_us)) })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CompanyLogo()
            Spacer(modifier = Modifier.height(16.dp))
            CompanyDescription()
            Spacer(modifier = Modifier.height(16.dp))
            ContactInformation()
        }
    }
}

@Composable
fun CompanyLogo() {
    Image(
        painter = painterResource(id = R.drawable.evvoli_logo_b1),
        contentDescription = "Company Logo",
        modifier = Modifier.size(100.dp)
    )
}

@Composable
fun CompanyDescription() {
    Text(
        text = "Evvoli Technology is a leading provider of innovative tech solutions. " +
                "Our mission is to enhance the digital experience with cutting-edge products and services.",
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun ContactInformation() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ContactDetail(Icons.Default.Place, "123 Tech Street, Innovation City")
            Spacer(modifier = Modifier.height(8.dp))
            ContactDetail(Icons.Default.Phone, "+123 456 7890")
            Spacer(modifier = Modifier.height(8.dp))
            ContactDetail(Icons.Default.Email, "contact@evvoli.com")
        }
    }
}

@Composable
fun ContactDetail(icon: ImageVector, detail: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(detail, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AboutApp() {
    AboutScreen()
}