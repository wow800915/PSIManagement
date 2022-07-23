package com.weiyou.psimanagement.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

/**
 * Database access object to access the Inventory database
 */
@Dao
interface PurchaseItemDao {
    //20210906這邊room開始有改
//這邊的NAME不知道要不要改成inventoryItemName
    @Query("SELECT * from purchaseItem ORDER BY name ASC")
    fun getPurchaseItems(): Flow<List<PurchaseItem>>

//    @Query("SELECT * from purchaseItem WHERE date = :currentDate ORDER BY name ASC")
//    fun getTodayPurchaseItems(currentDate: String): Flow<List<PurchaseItem>>

//    @Query("SELECT * from purchaseItem WHERE date = :currentDate ORDER BY name ASC")
//    fun getTodayPurchaseItems(currentDate: Date): Flow<List<PurchaseItem>>

    @Query("SELECT * FROM purchaseItem WHERE date BETWEEN :from AND :to")
    fun getCustomPurchaseItems(from: Date, to: Date): Flow<List<PurchaseItem>>

//    @Query("SELECT * from purchaseItem WHERE date BETWEEN '20080205 00:00:00.000'and '20080205 23:59:59.999' ORDER BY name ASC")
//    fun getWeekPurchaseItems(currentDate: String): Flow<List<PurchaseItem>>

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