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
interface SalesDao {
    //20210906這邊room開始有改
//這邊的NAME不知道要不要改成inventoryItemName
    @Query("SELECT * from sales ORDER BY name ASC")
    fun getSaleses(): Flow<List<Sales>>
    //inventoryItemId不知道要不要改成ID
    @Query("SELECT * from sales WHERE salesItemId = :salesItemId")
    fun getSales(salesItemId: Int): Flow<Sales>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sales: Sales)

    @Update
    suspend fun update(sales: Sales)

    @Delete
    suspend fun delete(sales: Sales)
}