package com.example.purchases.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.purchases.PurchaseState
import com.example.purchases.PurchasesEvent
import com.example.purchases.R
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PurchasesScreen(
    state: PurchaseState,
    navController: NavController,
    onEvent: (PurchasesEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(R.string.app_name),
                    modifier = Modifier.weight(1f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                IconButton(onClick = {
                    onEvent(PurchasesEvent.SortPurchases)
                }) {
                    Icon(
                        painterResource(R.drawable.outline_sort_24),
                        contentDescription = "Sort",
                        modifier = Modifier.size(35.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    state.description.value = ""
                    state.amount.value = 0.0f
                    navController.navigate("AddPurchaseScreen")
                }) {
                Icon(
                    modifier = Modifier.size(35.dp),
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add purchase",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.purchases.size) {index ->
                PurchaseItem(state = state, index = index, onEvent = onEvent)
            }
        }

    }
}

@Composable
fun PurchaseItem(
    state: PurchaseState,
    index: Int,
    onEvent: (PurchasesEvent) -> Unit
) {
    val date = state.purchases[index].date
    val ourFormat = SimpleDateFormat("dd/MMM/yyyy HH:mm", Locale.getDefault())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = ourFormat.format(date),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                state.purchases[index].description,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Text(
            state.purchases[index].amount.toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        IconButton(onClick = {
            onEvent(PurchasesEvent.DeletePurchase(state.purchases[index]))
        }) {
            Icon(
                modifier = Modifier.size(35.dp),
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete purchase",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}
