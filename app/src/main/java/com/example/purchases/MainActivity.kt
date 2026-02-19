package com.example.purchases

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.purchases.db.PurchasesDatabase
import com.example.purchases.ui.AddPurchaseScreen
import com.example.purchases.ui.PurchasesScreen
import com.example.purchases.ui.theme.PurchasesTheme

class MainActivity : ComponentActivity() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            PurchasesDatabase::class.java,
            "purchases.db"
        ).build()
    }
    private val viewModel by viewModels<PurchasesViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T: ViewModel> create(modelClass: Class<T>) : T {
                    return PurchasesViewModel(database.dao) as T
                }
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PurchasesTheme {
                val state by viewModel.state.collectAsState()
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "PurchasesScreen") {
                    composable(route = "PurchasesScreen") {
                        PurchasesScreen(
                            state = state,
                            navController = navController,
                            onEvent = viewModel::onEvent
                        )
                    }
                    composable(route = "AddPurchaseScreen") {
                        AddPurchaseScreen(
                            state = state,
                            navController = navController,
                            onEvent = viewModel::onEvent
                        )
                    }
                }
            }
        }
    }
}
