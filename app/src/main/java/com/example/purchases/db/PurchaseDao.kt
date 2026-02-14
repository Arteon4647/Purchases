package com.example.purchases.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchaseDao {
//    @Insert
//    fun insertPurchase()
//
//    @Update
//    fun updatePurchase()

    @Upsert
    suspend fun upsertPurchase()

    @Delete
    suspend fun deletePurchase()

    @Query("SELECT * FROM Purchase ORDER BY date ASC")
    fun getPurchasesOrderedByDateAsc(): Flow<List<Purchase>>

    @Query("SELECT * FROM Purchase ORDER BY date DESC")
    fun getPurchasesOrderedByDateDesc(): Flow<List<Purchase>>
}