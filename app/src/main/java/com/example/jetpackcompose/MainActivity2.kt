package com.example.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val position = intent.getIntExtra("message", -1)
        println("geted position $position")
        setContent {
            ShowsUserDetail(position)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ShowsUserDetail(position: Int = 0) {
    var isClicked by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Koko") },
                Modifier.background(Color.LightGray)
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.LightGray)
        ) {
            Column {


                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(Color.LightGray)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    ) {
                        Image(
                            bitmap = ImageBitmap.imageResource(R.drawable.user),
                            modifier = Modifier
                                .size(150.dp)
                                .clickable { isClicked = !isClicked }
                                .background(Color.LightGray),
                            contentDescription = "NoPhotoUserIco"
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Number $position",
                            fontSize = 34.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Surname",
                            fontSize = 34.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        MyButton()
                    }

                }
                Text(
                    fontSize = 24.sp,
                    text = "Статус: ")

            }

            AnimatedVisibility(visible = isClicked, enter = scaleIn(), exit = scaleOut()) {

                Box(
                    modifier = Modifier

                        .background(Color.LightGray.copy(alpha = 0.8f))
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        bitmap = ImageBitmap.imageResource(R.drawable.user),
                        modifier = Modifier
                            .fillMaxSize()
                            ,

//                            .scale(1.5f),
                        contentDescription = "Expanded Photo"
                    )
                }
                IconButton(

                    onClick = { isClicked = false },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun MyButton() {
    var buttonText by remember { mutableStateOf("Добавить в друзья") }
    var isFriend by remember { mutableStateOf(false) }
    val color by animateColorAsState(if (isFriend) Color.Green else Color.DarkGray)
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            buttonText = if (isFriend) "Добавить в друзья" else "Друг \u2713"
            isFriend = !isFriend
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        )
    ) {
        Text(text = buttonText)
    }
}