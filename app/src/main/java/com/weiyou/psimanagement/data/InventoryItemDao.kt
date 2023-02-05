package com.weiyou.psimanagement.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the Inventory database
 */
@Dao
interface InventoryItemDao {
    //20210906這邊room開始有改
    //這邊的NAME不知道要不要改成inventoryItemName
    @Query("SELECT * from inventoryItem ORDER BY name ASC")
    fun getInventoryItems(): Flow<List<InventoryItem>>

    //inventoryItemId不知道要不要改成ID
    @Query("SELECT * from inventoryItem WHERE inventoryItemId = :inventoryItemId")
    fun getInventoryItem(inventoryItemId: Int): Flow<InventoryItem>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(inventoryItem: InventoryItem)

    @Update
    suspend fun update(inventoryItem: InventoryItem)

    @Delete
    suspend fun delete(inventoryItem: InventoryItem)
}