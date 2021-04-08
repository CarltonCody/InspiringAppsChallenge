package com.example.inspiringappschallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.inspiringappschallenge.ui.theme.InspiringAppsChallengeTheme

/*
* I decided to use jetpack compose libraries as I have been building UIs in code
* and find it easier to build nicer interfaces.
* */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = DataService().presentationData
        setContent {
            InspiringAppsChallengeTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){
                    if (data.isNullOrEmpty()){
                        CircularProgressIndicator()
                        Text(text = "Please wait getting data.",
                            modifier = Modifier.wrapContentWidth(Alignment.Start),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h5)
                    }else{
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                        ){
                            items(items = data){ item ->
                                LogCard(displayItem = item)
                            }
                        }
                    }
                }
            }
        }
    }
}