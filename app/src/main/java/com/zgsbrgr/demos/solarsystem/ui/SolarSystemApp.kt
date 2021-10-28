package com.zgsbrgr.demos.solarsystem.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zgsbrgr.demos.solarsystem.R
import com.zgsbrgr.demos.solarsystem.data.orderedPlanetsList
import com.zgsbrgr.demos.solarsystem.di.AppContainer
import com.zgsbrgr.demos.solarsystem.ui.planet.PlanetDetail
import com.zgsbrgr.demos.solarsystem.ui.planet.PlanetView
import com.zgsbrgr.demos.solarsystem.ui.theme.AppBlack
import com.zgsbrgr.demos.solarsystem.ui.theme.SolarSystemTheme

@ExperimentalAnimationApi
@Composable
fun SolarSystemApp(appContainer: AppContainer) {
    SolarSystemTheme {
        ProvideWindowInsets {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = MaterialTheme.colors.isLight


            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = useDarkIcons)
                systemUiController.setNavigationBarColor(AppBlack, darkIcons = useDarkIcons)

            }
            PlanetDetail(appContainer = appContainer)

        }

    }
}