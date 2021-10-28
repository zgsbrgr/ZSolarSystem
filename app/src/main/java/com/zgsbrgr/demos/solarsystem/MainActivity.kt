package com.zgsbrgr.demos.solarsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zgsbrgr.demos.solarsystem.data.orderedPlanetsList
import com.zgsbrgr.demos.solarsystem.ui.planet.PlanetView
import com.zgsbrgr.demos.solarsystem.ui.theme.AppBlack
import com.zgsbrgr.demos.solarsystem.ui.theme.SolarSystemTheme

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = MaterialTheme.colors.isLight
            val position: MutableState<Int> = mutableStateOf(0)

            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = useDarkIcons)
                systemUiController.setNavigationBarColor(AppBlack, darkIcons = useDarkIcons)

            }
            SolarSystemTheme {
                // A surface container using the 'background' color from the theme
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.s_black))) {
                    PlanetView(position.value)
                    Button(onClick = {
                        if(position.value < orderedPlanetsList.size - 1)
                            position.value++
                        else
                            position.value = 0
                    }) {

                    }
                }

            }
        }
    }
}

