package com.zgsbrgr.demos.solarsystem.ui.planet

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ThumbUp
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.zgsbrgr.demos.solarsystem.R
import com.zgsbrgr.demos.solarsystem.di.AppContainer
import com.zgsbrgr.demos.solarsystem.domain.model.Planet
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.util.*
import kotlin.math.absoluteValue
import com.zgsbrgr.demos.solarsystem.ui.utils.*
import kotlinx.coroutines.launch




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


private val FabSize = 56.dp
private const val ExpandedSheetAlpha = 0.96f

private enum class State {
    Open,
    Closed
}

@Composable
private fun PlanetItemChooser(uiState: PlanetListUiState, openFraction: Float, width: Float, height: Float, onPlanetSelectChange: (Int) -> Unit, updateState: (State) -> Unit) {

    if(uiState.planets.isEmpty())
        return

    val color = uiState.planets[uiState.selected].color

    val fabSize = with(LocalDensity.current) { FabSize.toPx() }
    val fabSheetHeight = fabSize + LocalWindowInsets.current.systemBars.bottom
    val offsetX =
        lerp(width - fabSize, 0f, 0f, 0.15f, openFraction)
    val offsetY =
        lerp(height - fabSheetHeight, 0f, openFraction)

//    val offsetX =
//        lerp(width - fabSize, 0f, 0f, 0.15f, openFraction)
//    val offsetY =
//        lerp(LocalWindowInsets.current.systemBars.top.toFloat(), 0f, openFraction)
    val tlCorner = lerp(fabSize, 0f, 0f, 0.15f, openFraction)
    val surfaceColor = lerp(
        startColor = colorResource(id = R.color.s_black),
        endColor = color.copy(alpha = ExpandedSheetAlpha),
        startFraction = 0f,
        endFraction = 0.3f,
        fraction = openFraction
    )
    Surface(
        color = surfaceColor,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primarySurface),
        shape = RoundedCornerShape(topStart = 10f),
        modifier = Modifier.graphicsLayer {
            translationX = offsetX
            translationY = offsetY
        }
    ) {
//        Box(
//            modifier = Modifier.size(50.dp, 50.dp).background(Color.Yellow)
//        )
        PlanetChooser(uiState, openFraction, onPlanetSelectChange, updateState)
    }

}

@Composable
private fun PlanetChooser(
    uiState: PlanetListUiState,
    openFraction: Float,
    onPlanetSelectChange: (Int) -> Unit,
    updateState: (State) -> Unit
) {

    val color = uiState.planets[uiState.selected].color
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        val planetAlpha = lerp(0f, 1f, 0.2f, 0.8f, openFraction)

        Column(modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { alpha = planetAlpha }
            .statusBarsPadding()) {

        }

        val fabAlpha = lerp(1f, 0f, 0f, 0.15f, openFraction)
        Box(
            modifier = Modifier
                .size(FabSize)
                //.padding(start = 16.dp, top = 8.dp)
                .graphicsLayer { alpha = fabAlpha }
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.Center),
                onClick = { updateState(State.Open) }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ThumbUp,
                    tint = color,
                    contentDescription = stringResource(id = R.string.label_expand_planet_list)
                )
            }
        }

    }

}


@OptIn(ExperimentalMaterialApi::class)
@ExperimentalAnimationApi
@InternalCoroutinesApi
@ExperimentalPagerApi
@Composable
fun PlanetDetailScreen(planetListViewModel: PlanetListViewModel) {

    val uiState by planetListViewModel.uiState.collectAsState()
    val onPlanetSelectChange: (Int) -> Unit = {
        planetListViewModel.changeSelection(it)
    }

    BoxWithConstraints {
        val state = rememberSwipeableState(initialValue = State.Closed)
        val fabSize = with(LocalDensity.current) { FabSize.toPx() }
        val dragRange = constraints.maxHeight - fabSize
        val scope = rememberCoroutineScope()

        Box(
            Modifier.swipeable(
                state = state,
                anchors = mapOf(
                    0f to State.Closed,
                    -dragRange to State.Open
                ),
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Vertical
            )
        ) {
            val openFraction = if (state.offset.value.isNaN()) {
                0f
            } else {
                -state.offset.value / dragRange
            }.coerceIn(0f, 1f)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.s_black))
            ) {
                PlanetView(uiState, onPlanetSelectChange)
            }
            PlanetItemChooser(
                uiState = uiState,
                openFraction,
                this@BoxWithConstraints.constraints.maxWidth.toFloat(),
                this@BoxWithConstraints.constraints.maxHeight.toFloat(),
                onPlanetSelectChange
            ) { _state ->
                scope.launch {
                    state.animateTo(_state)
                }
            }
        }
    }




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