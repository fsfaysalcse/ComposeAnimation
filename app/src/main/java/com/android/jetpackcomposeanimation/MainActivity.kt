package com.android.jetpackcomposeanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.jetpackcomposeanimation.ui.theme.JetpackComposeAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAnimationTheme {
               // ColorAnimationSample()
               // SizeAnimationSample()
               // RotateImageAnimation()
                InfiniteAnimation()
            }
        }
    }
}

@Composable
private fun ColorAnimationSample() {
    var isAnimated by remember { mutableStateOf(false) }

    val color = remember { Animatable(Color.DarkGray) }

    // animate to green/red based on `button click`
    LaunchedEffect(isAnimated) {

        val colorTo = if (isAnimated){
            Color.Red
        }else{
            Color.Blue
        }

        color.animateTo(colorTo, animationSpec = tween(3000))
    }

    Column() {

        Image(
            painter = painterResource(R.drawable.ic_humanimage),
            contentDescription = "Circle Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(300.dp)
                .clip(CircleShape)
                .border(5.dp, color.value, CircleShape)
        )

        Text(text = "Hello Thomas", color = color.value, fontSize = 30.sp)
        Text(text = "Hello Woodfin", color = color.value)
        Text(text = "Hello Mark Woodfin", color = color.value)

        Button(
            onClick = { isAnimated = !isAnimated },
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Text(text = "Animate Color")
        }
    }




}

@Composable
fun SizeAnimationSample() {
    val isNeedExpansion = remember{ mutableStateOf(false)}

    val animatedSizeDp: Dp by animateDpAsState(targetValue = if (isNeedExpansion.value) 350.dp else 100.dp)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.ic_humanimage),
            contentDescription = "Circle Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(animatedSizeDp)
                .clip(CircleShape)
                .border(5.dp, Color.Blue, CircleShape)
        )
        Button(
            onClick = {
                isNeedExpansion.value = !isNeedExpansion.value
            },
            modifier = Modifier
                .padding(top = 50.dp)
                .width(300.dp)
        ) {
            Text(text = "animateDpAsState")
        }
    }

}


@Composable
fun RotateImageAnimation() {
    var isRotated by rememberSaveable { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (isRotated) 360F else 0f,
        animationSpec = tween(durationMillis = 100)
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.ic_humanimage),
            contentDescription = "fan",
            modifier = Modifier
                .rotate(rotationAngle)
                .size(150.dp)
                .clip(CircleShape)
                .border(5.dp, Color.Blue, CircleShape)
        )

        Button(
            onClick = { isRotated = !isRotated },
            modifier = Modifier
                .padding(top = 50.dp)
                .width(200.dp)
        ) {
            Text(text = "Rotate Fan")
        }
    }
}


@Composable
fun InfiniteAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    val heartSize by infiniteTransition.animateFloat(
        initialValue = 100.0f,
        targetValue = 250.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, delayMillis = 100,easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_humanimage),
            contentDescription = "fan",
            modifier = Modifier
                .size(heartSize.dp)
                .size(150.dp)
                .clip(CircleShape)
                .border(5.dp, Color.Blue, CircleShape)
        )
    }



}


