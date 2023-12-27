package com.example.evvolitm

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.evvolitm.presentation.MainViewModel
import com.example.evvolitm.ui.EvvoliTmScreenContainer
import com.example.evvolitm.ui.theme.EvvoliTmTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EvvoliTmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mainViewModel = hiltViewModel<MainViewModel>()
                    val navController: NavHostController = rememberNavController()
                    EvvoliTmScreenContainer(
                        mainViewModel = mainViewModel, navController = navController
                    )
                }
            }
        }
    }
}