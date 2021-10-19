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
interface PurchaseItemDao {
    //20210906這邊room開始有改
//這邊的NAME不知道要不要改成inventoryItemName
    @Query("SELECT * from purchaseItem ORDER BY name ASC")
    fun getPurchaseItems(): Flow<List<PurchaseItem>>
    //inventoryItemId不知道要不要改成ID
    @Query("SELECT * from purchaseItem WHERE purchaseItemId = :purchaseItemId")
    fun getPurchaseItem(purchaseItemId: Int): Flow<PurchaseItem>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(purchaseItem: PurchaseItem)

    @Update
    suspend fun update(purchaseItem: PurchaseItem)

    @Delete
    suspend fun delete(purchaseItem: PurchaseItem)
}