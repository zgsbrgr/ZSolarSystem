package com.zgsbrgr.demos.solarsystem.domain.model

import androidx.compose.ui.graphics.Color

data class Planet(
    val name: String,
    val title: String,
    val color: Color,
    val imageResourceId: Int,
    val radius: String,
    val distanceFromSun: String,
    val moons: List<String>,
    val gravity: String,
    val tiltOfAxis: String,
    val lengthOfYear: String,
    val lengthOfDay: String,
    val temperature: String
    )
