package com.debduttapanda.arrowfollower

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.debduttapanda.arrowfollower.ui.theme.ArrowFollowerTheme

class MainActivity : ComponentActivity() {
    private val list = MutableList(20){
        it
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArrowFollowerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        val arrowFollower = ArrowFollower.rememberArrowFollower(Unit)
                        var currentSelectedIndex by remember {
                            mutableStateOf(-1)
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 65.dp),
                                horizontalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                itemsIndexed(
                                    list,
                                    key = {index,item->
                                        index
                                    }
                                ) { index, it ->
                                    Box(
                                        modifier = Modifier
                                            .size(84.dp)
                                            .clip(RoundedCornerShape(24.dp))
                                            .background(
                                                if (currentSelectedIndex == index)
                                                    Color(0xfff54272)
                                                else
                                                    Color.White
                                            )
                                            .border(
                                                width = 2.dp,
                                                color = Color(0xfff54272),
                                                shape = RoundedCornerShape(24.dp)
                                            )
                                            .clickable {
                                                currentSelectedIndex = index
                                            }
                                            .then(arrowFollower.arrowFollowed(active = currentSelectedIndex == index)),
                                        contentAlignment = Alignment.Center
                                    ){
                                        Text(
                                            "$it",
                                            color = if(currentSelectedIndex!=index)
                                                Color(0xfff54272)
                                            else
                                                Color.White,
                                            fontSize = if(currentSelectedIndex==index) 48.sp else 24.sp
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            arrowFollower.Arrow(
                                color = Color(0xfff54272),
                                shape = CircleShape,
                                size = DpSize(20.dp,40.dp),
                                paddingValues = PaddingValues(top = 0.dp)
                            )
                            Divider(
                                color = Color(0xfff54272),
                                thickness = 2.dp
                            )
                        }
                    }
                }
            }
        }
    }
}
