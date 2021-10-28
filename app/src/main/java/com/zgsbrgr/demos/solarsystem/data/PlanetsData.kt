package com.zgsbrgr.demos.solarsystem.data

import androidx.compose.ui.graphics.Color
import com.zgsbrgr.demos.solarsystem.R
import com.zgsbrgr.demos.solarsystem.domain.model.Planet


val Mars = Planet(
    name = "mars",
    title = "the red planet",
    color = Color(0xFFE5734F),
    imageResourceId = R.drawable.p_mars,
    radius = "3,389.5 km",
    distanceFromSun = "227.9 mln km",
    moons = listOf("Phobos", "Deimos"),
    gravity = "3.711 m/s²",
    tiltOfAxis = "25°",
    lengthOfYear = "687 earth days",
    lengthOfDay = "24 h 37 min",
    temperature = "Average -62.78°C"
)

val Earth = Planet(
    name = "earth",
    title = "the Blue Planet",
    color = Color(0xFF8cb1de),
    imageResourceId = R.drawable.p_earth,
    radius = "6,378.1 km",
    distanceFromSun = "148.66 mln km",
    moons = listOf("Moon"),
    gravity = "9.807 m/s²",
    tiltOfAxis = "23.5°",
    lengthOfYear = "365 days",
    lengthOfDay = "24 h 00 min",
    temperature = "Average 15°C"
)

val Jupiter = Planet(
    name = "jupiter",
    title = "the Gas Giant",
    color = Color(0xFFe36e4b),
    imageResourceId = R.drawable.p_jupiter,
    radius = "69,911 km",
    distanceFromSun = "749.48 mln km",
    moons = listOf("Io","Europa","Ganymede", "Callisto", "..."),
    gravity = "24.79 m/s²",
    tiltOfAxis = "3.13°",
    lengthOfYear = "12 earth years",
    lengthOfDay = "09 h 56 min",
    temperature = "Average -145°C"
)

val Saturn = Planet(
    name = "saturn",
    title = "Ringed Planet",
    color = Color(0xFFceb8b8),
    imageResourceId = R.drawable.p_saturn,
    radius = "58,232 km",
    distanceFromSun = "1.4835 bln km",
    moons = listOf("Mimas","Enceladus","Tethys", "Dione", "..."),
    gravity = "10.44 m/s²",
    tiltOfAxis = "26.73°",
    lengthOfYear = "29 earth years",
    lengthOfDay = "10 h 42 min",
    temperature = "Average 178°C"
)

val Neptune = Planet(
    name = "neptune",
    title = "The Windy Planet",
    imageResourceId = R.drawable.p_neptune,
    color = Color(0xFF4b70DD),
    radius = "24,622 km",
    distanceFromSun = "4.475 bln km",
    moons = listOf("Naiad","Thalassa","Despina", "Galatea", "..."),
    gravity = "11.15 m/s²",
    tiltOfAxis = "28°",
    lengthOfYear = "165 earth years",
    lengthOfDay = "16 h 06 min",
    temperature = "Average -200°C"
)

val Uranus = Planet(
    name = "uranus",
    title = "the bulls-eye planet",
    color = Color(0xFF4FD0E7),
    imageResourceId = R.drawable.p_uranus,
    radius = "25,362 km",
    distanceFromSun = "2.9512 bln km",
    moons = listOf("Puck","Miranda","Ariel", "Umbriel", "..."),
    gravity = "8.87 m/s²",
    tiltOfAxis = "23°",
    lengthOfYear = "84 earth years",
    lengthOfDay = "17 h 14 min",
    temperature = "Average -195°C"
)

val Venus = Planet(
    name = "venus",
    title = "Evening Star",
    color = Color(0xFFeecb8b),
    imageResourceId = R.drawable.p_venus,
    radius = "6,051.8 km",
    distanceFromSun = "108.78 mln km",
    moons = emptyList(),
    gravity = "8.87 m/s²",
    tiltOfAxis = "177.3°",
    lengthOfYear = "225 earth days",
    lengthOfDay = "116 d 18 h 00 min",
    temperature = "Average 462°C"
)

val Mercury = Planet(
    name = "mercury",
    title = "the Swift Planet",
    color = Color(0xFFB1ADAD),
    imageResourceId = R.drawable.p_mercury,
    radius = "2,439.7 km",
    distanceFromSun = "48.585 mln km",
    moons = emptyList(),
    gravity = "3.7 m/s²",
    tiltOfAxis = "0.034°",
    lengthOfYear = "88 earth days",
    lengthOfDay = "58 d 15 h 30 min",
    temperature = "Average 178°C"
)

val orderedPlanetsList = listOf<Planet>(
    Mercury,
    Venus,
    Earth,
    Mars,
    Jupiter,
    Saturn,
    Uranus,
    Neptune
)