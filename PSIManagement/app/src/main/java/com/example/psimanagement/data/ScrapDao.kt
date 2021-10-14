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
interface ScrapDao {
    //20210906這邊room開始有改
//這邊的NAME不知道要不要改成inventoryItemName
    @Query("SELECT * from scrap ORDER BY name ASC")
    fun getScraps(): Flow<List<Scrap>>
    //inventoryItemId不知道要不要改成ID
    @Query("SELECT * from scrap WHERE scrapItemId = :scrapItemId")
    fun getScrap(scrapItemId: Int): Flow<Scrap>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(scrap: Scrap)

    @Update
    suspend fun update(scrap: Scrap)

    @Delete
    suspend fun delete(scrap: Scrap)
}