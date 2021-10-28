package com.zgsbrgr.demos.solarsystem.ui.planet

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zgsbrgr.demos.solarsystem.data.orderedPlanetsList
import java.util.*

@ExperimentalAnimationApi
@Composable
fun PlanetDetailTop(shape: Shape, itemToLoad: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val visible by remember { mutableStateOf(true) }
        val planet = orderedPlanetsList[itemToLoad]
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.65f)
                    .clip(shape = shape)
                    .background(planet.color)
                    .alpha(0.8f)
                    .animateEnterExit(
                        enter = slideInVertically(),
                        exit = slideOutVertically()
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,


                ) {
                Text(text = planet.name.uppercase(),
                    modifier = Modifier
                        .padding(top = 50.dp),
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = planet.title.uppercase(Locale.getDefault()),
                    style = MaterialTheme.typography.h4
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 20.dp),

                    ) {
                    Image(
                        painterResource(id = planet.imageResourceId),
                        contentDescription = "mars planet",
                        modifier = if(planet.name != "saturn") {
                            Modifier
                                .size(300.dp)
                                .padding(20.dp)
                                .shadow(
                                    elevation = 20.dp,
                                    shape = CircleShape,
                                    clip = true
                                )
                                .align(
                                    Alignment.BottomCenter
                                )
                                .alpha(1f)
                        } else {
                            Modifier
                                .size(300.dp)
                                .padding(20.dp)
                                .align(
                                    Alignment.BottomCenter
                                )
                        }

                    )
                }

            }
        }

    }
}

@ExperimentalAnimationApi
@Composable
fun PlanetView(itemToLoad: Int) {
    PlanetDetailTop(shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 200.dp, bottomEnd = 200.dp), itemToLoad = itemToLoad)
}



@ExperimentalAnimationApi
@Preview
@Composable
fun PlanetTopPreview() {
    PlanetDetailTop(shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 200.dp, bottomEnd = 200.dp), 0)
}