package com.example.evvolitm.ui.screens

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.core.os.LocaleListCompat

@Composable
fun LanguageChangeScreen() {
    Column {
        Button(onClick = {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags("tk")
            )
        }) {
            Text("Turkmen")
        }
        Button(onClick = {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags("en")
            )
        }) {
            Text("English")
        }
        Button(onClick = {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags("ru")
            )
        }) {
            Text("Русский")
        }
    }
}