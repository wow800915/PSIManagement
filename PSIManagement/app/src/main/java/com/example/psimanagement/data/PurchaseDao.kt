package com.example.psimanagement.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the Inventory database
 */
@Dao
interface PurchaseDao {
    //20210906這邊room開始有改
//這邊的NAME不知道要不要改成inventoryItemName
    @Query("SELECT * from purchase ORDER BY name ASC")
    fun getPurchases(): Flow<List<Purchase>>
    //inventoryItemId不知道要不要改成ID
    @Query("SELECT * from purchase WHERE purchaseItemId = :purchaseItemId")
    fun getPurchase(purchaseItemId: Int): Flow<Purchase>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(purchase: Purchase)

    @Update
    suspend fun update(purchase: Purchase)

    @Delete
    suspend fun delete(purchase: Purchase)
}