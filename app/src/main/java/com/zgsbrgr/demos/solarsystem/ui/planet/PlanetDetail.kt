package com.zgsbrgr.demos.solarsystem.ui.planet

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.zgsbrgr.demos.solarsystem.R
import com.zgsbrgr.demos.solarsystem.data.orderedPlanetsList
import com.zgsbrgr.demos.solarsystem.di.AppContainer
import com.zgsbrgr.demos.solarsystem.domain.model.Planet
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.util.*
import kotlin.math.absoluteValue


@ExperimentalAnimationApi
@Composable
fun PlanetDetailTop(shape: Shape, uiState: PlanetListUiState) {
    if(uiState.planets.isNotEmpty()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            val visible by remember { mutableStateOf(true) }
            val planet = uiState.planets[uiState.selected]
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.6f)
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

}

@ExperimentalPagerApi
@InternalCoroutinesApi
@Composable
fun PlanetDetailBottom(uiState: PlanetListUiState, modifier: Modifier, onPlanetSelectChange: (Int) -> Unit) {

    if(uiState.planets.isNotEmpty()) {
        val planet = uiState.planets[uiState.selected]

        ConstraintLayout(modifier = modifier) {
            val (label, textBox, pager) = createRefs()
            Text(
                text = stringResource(id = R.string.list_title).uppercase(),
                style = MaterialTheme.typography.subtitle1.copy(
                    shadow = Shadow(
                        color = colorResource(id = R.color.s_black),
                        offset = Offset(0f,4f),
                        blurRadius = 40f
                    )
                ),
                color = planet.color,
                modifier = Modifier
                    .padding(end = 16.dp, top = 16.dp)
                    .constrainAs(label) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    },


            )
            PlanetDetailsTextColumn(
                planet = planet,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .fillMaxWidth(.7f)
//                    .background(Color.Yellow)
                    .constrainAs(textBox) {
                        start.linkTo(parent.start)
                        top.linkTo(label.bottom)
                        //centerVerticallyTo(parent)

                    }
            )
            PlanetDetailsPager(
                planets = uiState.planets,
                modifier = Modifier
                    .fillMaxWidth(.3f)
                    .padding(top = 40.dp)
                    .height(200.dp)
                    .constrainAs(pager) {
                        end.linkTo(label.end)
                        start.linkTo(label.start)
                        top.linkTo(label.bottom)
                        bottom.linkTo(textBox.bottom)

                    }
                ,
                onPlanetSelectChange = onPlanetSelectChange
            )

        }

    }

    
}

@InternalCoroutinesApi
@ExperimentalPagerApi
@Composable
fun PlanetDetailsPager(planets: List<Planet>, modifier: Modifier, onPlanetSelectChange: (Int) -> Unit) {


    val pagerState = rememberPagerState(pageCount = planets.size, initialPage = 3)

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onPlanetSelectChange(page)
        }
    }
    VerticalPager(state = pagerState, itemSpacing = 15.dp, modifier = modifier
        ) { page ->
        Image(
            painterResource(id = planets[page].imageResourceId),
            contentDescription = planets[page].name,
            modifier = Modifier
                .size(80.dp)
                .alpha(1f)
                .padding(end = 8.dp)
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                    alpha = lerp(
                        start = 0.2f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }

        )
    }


}

@Composable
fun PlanetDetailsTextColumn(planet: Planet, modifier: Modifier) {
    Column(modifier = modifier) {

        val labels = arrayOf(
            stringResource(id = R.string.label1),
            stringResource(id = R.string.label2),
            stringResource(id = R.string.label3),
            stringResource(id = R.string.label4),
            stringResource(id = R.string.label5),
            stringResource(id = R.string.label6),
            stringResource(id = R.string.label7),
            stringResource(id = R.string.label8)
        )

        val planetValues = arrayOf(
            planet.radius,
            planet.distanceFromSun,
            planet.moons.joinToString(separator = ","),
            planet.gravity,
            planet.tiltOfAxis,
            planet.lengthOfYear,
            planet.lengthOfDay,
            planet.temperature
        )

        for((index, value) in labels.iterator().withIndex()) {
            PlanetDetailTextRow(label = value, planetValue = planetValues[index], color = planet.color)
        }


    }
}

@Composable
fun PlanetDetailTextRow(label: String, planetValue: String, color: Color ) {
    Row(
        modifier = Modifier.padding(top = 2.dp)
    ) {
        Text(
            text = label,
            style =  MaterialTheme.typography.subtitle2,
            color = colorResource( id = R.color.s_text_color ),
            modifier = Modifier.padding(end = 4.dp),
            fontSize = 13.sp
        )
        Text(
            text = planetValue,
            style =  MaterialTheme.typography.subtitle2,
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp

        )
    }
}


@InternalCoroutinesApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun PlanetView(uiState: PlanetListUiState, onPlanetSelectChange: (Int) -> Unit) {
    PlanetDetailTop(shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 200.dp, bottomEnd = 200.dp), uiState = uiState)
    PlanetDetailBottom(uiState = uiState, modifier = Modifier.fillMaxSize(), onPlanetSelectChange = onPlanetSelectChange)
}




//@ExperimentalAnimationApi
//@Composable
//fun PlanetDetailScreen(planetDetailViewModel: PlanetDetailViewModel) {
//
//    val uiState by planetDetailViewModel.uiState.collectAsState()
//
//    if (uiState.planet != null) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(colorResource(id = R.color.s_black))
//        ) {
//            PlanetView(uiState.planet!!)
//        }
//
//    }
//}




@ExperimentalAnimationApi
@InternalCoroutinesApi
@ExperimentalPagerApi
@Composable
fun PlanetDetailScreen(planetListViewModel: PlanetListViewModel) {

    val uiState by planetListViewModel.uiState.collectAsState()
    val onPlanetSelectChange: (Int) -> Unit = {
        planetListViewModel.changeSelection(it)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.s_black))
    ) {
        PlanetView(uiState, onPlanetSelectChange)
    }
//        PlanetDetailsPager(planets = uiState.planets, modifier = Modifier.fillMaxSize(), onPlanetSelectChange = onPlanetSelectChange)

}

@InternalCoroutinesApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun PlanetDetail(appContainer: AppContainer) {
    val planetDetailViewModel: PlanetDetailViewModel = viewModel(
        factory = PlanetDetailViewModel.provideFactory(appContainer.planetsRepository)
    )
    val planetListViewModel: PlanetListViewModel = viewModel(
        factory = PlanetListViewModel.provideFactory(appContainer.planetsRepository)
    )

    val onPanelSelectChange: (Int) -> Unit = {
        planetDetailViewModel.loadSinglePlanetByPosition(it)
    }

    //PlanetDetailScreen(planetDetailViewModel)
    PlanetDetailScreen(planetListViewModel = planetListViewModel)

}



@ExperimentalAnimationApi
@Preview
@Composable
fun PlanetTopPreview() {
    //PlanetDetailTop(shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 200.dp, bottomEnd = 200.dp), orderedPlanetsList[0])
}

@ExperimentalAnimationApi
@Preview
@Composable
fun PlanetBottomPreview() {
    //PlanetDetailBottom(planet = orderedPlanetsList[0], modifier = Modifier.fillMaxSize())
}