package com.weiyou.psimanagement.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

/**
 * Database access object to access the Inventory database
 */
@Dao
interface SalesItemDao {
    //20210906這邊room開始有改
    //這邊的NAME不知道要不要改成inventoryItemName
    @Query("SELECT * from salesItem ORDER BY name ASC")
    fun getSalesItems(): Flow<List<SalesItem>>

    @Query("SELECT * FROM salesItem WHERE date BETWEEN :from AND :to")
    fun getCustomSalesItems(from: Date, to: Date): Flow<List<SalesItem>>

    //inventoryItemId不知道要不要改成ID
    @Query("SELECT * from salesItem WHERE salesItemId = :salesItemId")
    fun getSales(salesItemId: Int): Flow<SalesItem>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(salesItem: SalesItem)

    @Update
    suspend fun update(salesItem: SalesItem)

    @Delete
    suspend fun delete(salesItem: SalesItem)
}