package com.example.inspiringappschallenge

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun LogCard(
    displayItem: DisplayItem,
){
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(all = 4.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 4.dp)
            ) {
                Text(text = "UserIP: ${displayItem.userIP}",
                    modifier = Modifier.wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h5)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 4.dp)
            ) {
                Text(text = "Endpoint: ${displayItem.endPoint}",
                modifier = Modifier.wrapContentWidth(Alignment.Start),
                style = MaterialTheme.typography.h5)

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 4.dp)
            ) {
                Text(text = "Frequency visited: ${displayItem.freqRequests}",
                    modifier = Modifier.wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h5)

            }
        }

    }
}
