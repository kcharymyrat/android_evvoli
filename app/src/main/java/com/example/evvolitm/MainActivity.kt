package com.example.evvolitm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.evvolitm.ui.theme.EvvoliTmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EvvoliTmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductsApp()
                }
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EvvoliTmPreview() {
    EvvoliTmTheme(darkTheme = false) {
        ProductsApp()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EvvoliTmDarkThemePreview() {
    EvvoliTmTheme(darkTheme = true) {
        ProductsApp()
    }
}


@Composable
fun Sample(name: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(16.dp)) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Hello $name!",
                fontSize = 58.sp,
                lineHeight = 62.sp,
                letterSpacing = 0.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Hello $name!",
                style = MaterialTheme.typography.displayLarge,
            )
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Submit")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(
                    text = "Submit",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}