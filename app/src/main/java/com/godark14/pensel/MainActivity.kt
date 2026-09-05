package com.godark14.pensel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.godark14.pensel.fold.rememberFoldPosture
import com.godark14.pensel.ui.navigation.PenselNavHost
import com.godark14.pensel.ui.theme.PenselTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PenselTheme {
                val foldPosture = rememberFoldPosture()
                PenselNavHost(
                    foldPosture = foldPosture,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}