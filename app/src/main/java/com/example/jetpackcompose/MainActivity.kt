package com.example.jetpackcompose
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LazyColumnSample()        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Предварительный просмотр LazyColumn", showBackground = true)
@Composable
fun LazyColumnSample() {
    var selectedPosition by remember { mutableStateOf(-1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Koko") },  // Исправленная строка
                Modifier.background(MaterialTheme.colorScheme.background)
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
//            // Затемнение фона при выборе элемента
//            if (selectedPosition != -1) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.LightGray)
//                )
//            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(3) { position ->
                    val isVisible = position == selectedPosition || selectedPosition == -1
                    MyListItem(
                        position = position,
                        isSelected = (position == selectedPosition),
                        isVisible = isVisible,
                        onClick = {
                            selectedPosition = if (selectedPosition == position) -1 else position
                        }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}


@Composable
fun MyListItem(
    position: Int,
    isSelected: Boolean,
    isVisible: Boolean,
    onClick: () -> Unit
) {

    val RatioAspectSize by animateFloatAsState(if (isSelected) 2.0f else 1.0f)

    val backgroundColor by animateColorAsState(if (isSelected) Color.LightGray else Color.Transparent)
    val numberFontSize by animateFloatAsState(if (isSelected) 34f else 20f)
    val photoSize by animateFloatAsState(if (isSelected) 180f else 90f)
    val surnameFontSize by animateFloatAsState(if (isSelected) 28f else 13f)
    val rowHeight by animateDpAsState(if (isSelected) 180.dp else 42.dp)
    val mContext = LocalContext.current
    val density = LocalDensity.current

    AnimatedVisibility(visible = isVisible,

        enter = slideInVertically {
            with(density) { 40.dp.roundToPx() }
        } + expandVertically(
            tween(durationMillis = 2000),
            expandFrom = Alignment.Top
        ) + fadeIn(
            initialAlpha = 0.3f

        ),
        exit = slideOutVertically() +
                shrinkVertically() +
                fadeOut()
    ) {
        Box(

            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                    println(position)
//                val navigate = Intent(mContext, MainActivity2::class.java)
//                navigate.putExtra("number", position)
//                mContext.startActivity(navigate)
                }
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .height(rowHeight)
                        .background(backgroundColor)
                ) {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1.0f)
                            .clip(CircleShape)
                            .background(backgroundColor)
                    ) {
                        Image(

                            bitmap = ImageBitmap.imageResource(R.drawable.user),
                            modifier = Modifier.size(photoSize.dp),
                            contentDescription = "NoPhotoUserIco"
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Column(
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = "Number $position",
                                fontSize = numberFontSize.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(1.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .clip(shape = RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = "Surname",
                                fontSize = surnameFontSize.sp
                            )
                        }

                    }
                }
                AnimatedVisibility(visible = isSelected) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Button(
                            onClick = {  }) {
                            Text(text = "Добавить в друзья")
                        }
                        Button(
                            onClick = {
                                val message = position
                                println(position)
                                val intent = Intent(mContext, MainActivity2::class.java).apply {
                                    putExtra("message", message)

                                }
                                mContext.startActivity(intent)
                            }) {
                            Text(text = "Посмотреть профиль")
                        }


                    }
                }
            }

        }

    }

}