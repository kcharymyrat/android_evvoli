package com.example.evvolitm.ui.screens

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.example.evvolitm.R

@Composable
fun LanguageSelectionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.select_language),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        LanguageOption(
            "English",
            "en",
            Icons.Default.Language
        ){
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
        }
        LanguageOption(
            "Русский",
            "ru",
            Icons.Default.Language
        ) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ru"))
        }
        LanguageOption(
            "Türkmen",
            "tk",
            Icons.Default.Language
        ) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("tk"))
        }
    }
}

@Composable
fun LanguageOption(
    languageName: String,
    languageCode: String,
    icon: ImageVector,
    onLanguageSelected: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onLanguageSelected() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (AppCompatDelegate.getApplicationLocales()[0]?.language == languageCode) {
                MaterialTheme.colorScheme.primary
            } else {
                Color.White
            },
            contentColor = if (AppCompatDelegate.getApplicationLocales()[0]?.language == languageCode) {
                Color.White
            } else {
                MaterialTheme.colorScheme.primary
            },
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(icon, contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = languageName, style = MaterialTheme.typography.bodyLarge)
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LangApp() {
    LanguageSelectionScreen()
}

