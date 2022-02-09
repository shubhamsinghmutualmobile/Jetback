package com.example.jetback

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import com.example.jetback.ui.screens.landingScreen.LandingScreen
import com.example.jetback.ui.screens.landingScreen.components.TopNavigationBar
import com.example.jetback.ui.screens.landingScreen.components.TopNavigationBarType
import com.example.jetback.ui.screens.landingScreen.components.UserAccountPicture
import com.example.jetback.ui.theme.JetbackTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetbackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TopNavigationBar(
                        type = TopNavigationBarType.Centered(
                            startItem = { Text("Shubham's TV") }
                        ),
                        totalItems = 5,
                        endItem = {
                            UserAccountPicture()
                        },
                        content = {
                            LandingScreen()
                        }
                    )
                }
            }
        }
    }
}
