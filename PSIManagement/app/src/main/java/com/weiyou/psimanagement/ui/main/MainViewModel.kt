package com.weiyou.psimanagement.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.weiyou.psimanagement.data.*
import kotlinx.coroutines.launch
import java.util.*

//20210906這邊room開始有改
class MainViewModel(private val inventoryItemDao: InventoryItemDao, private val salesItemDao: SalesItemDao, private val purchaseItemDao: PurchaseItemDao, private val scrapItemDao: ScrapItemDao) : ViewModel() {
    val calenderTime =Calendar.getInstance().getTime()
//    lateinit var inventoryItem: InventoryItem

    val allInventoryItems: LiveData<List<InventoryItem>> = inventoryItemDao.getInventoryItems().asLiveData()

    val allPurchaseItems: LiveData<List<PurchaseItem>> = purchaseItemDao.getPurchaseItems().asLiveData()
//    val todayPurchaseItems: LiveData<List<PurchaseItem>> = purchaseItemDao.getTodayPurchaseItems(Date(calenderTime.year,calenderTime.month,calenderTime.date)).asLiveData()
    val CustomPurchaseItems: LiveData<List<PurchaseItem>> = purchaseItemDao.getCustomPurchaseItems(Date(calenderTime.year,calenderTime.month,calenderTime.date-7),Date(calenderTime.year,calenderTime.month,calenderTime.date)).asLiveData()
    fun todayPurchaseItems() : LiveData<List<PurchaseItem>>{
        return purchaseItems(Date(calenderTime.year,calenderTime.month,calenderTime.date),Date(calenderTime.year,calenderTime.month,calenderTime.date))
    }

    fun purchaseItems(from:Date,to:Date): LiveData<List<PurchaseItem>> {
        val purchaseItems: LiveData<List<PurchaseItem>> = purchaseItemDao.getCustomPurchaseItems(from,to).asLiveData()
        Log.d("IANIAN","MainViewModel36 calenderTime.year: "+calenderTime.year +" calenderTime.month:"+calenderTime.month + " calenderTime.date:"+calenderTime.date);
        return purchaseItems
    }

    fun salesItems(from:Date,to:Date): LiveData<List<SalesItem>> {
//        val salesItems: LiveData<List<SalesItem>> = salesItemDao.getSalesItems().asLiveData()
        val salesItems: LiveData<List<SalesItem>> = salesItemDao.getCustomSalesItems(from,to).asLiveData()
        Log.d("IANIAN","MainViewModel36 calenderTime.year: "+calenderTime.year +" calenderTime.month:"+calenderTime.month + " calenderTime.date:"+calenderTime.date);
        return salesItems
    }

    fun scrapItems(from:Date,to:Date): LiveData<List<ScrapItem>> {
//        val salesItems: LiveData<List<SalesItem>> = salesItemDao.getSalesItems().asLiveData()
        val scrapItems: LiveData<List<ScrapItem>> = scrapItemDao.getCustomScrapItems(from,to).asLiveData()
        Log.d("IANIAN","MainViewModel36 calenderTime.year: "+calenderTime.year +" calenderTime.month:"+calenderTime.month + " calenderTime.date:"+calenderTime.date);
        return scrapItems
    }

    fun retrieveItem(id: Int): LiveData<InventoryItem> {
        return inventoryItemDao.getInventoryItem(id).asLiveData()
    }





    private fun getNewInventoryItemEntry(inventoryItemOrder: String, inventoryItemBarcode: String, inventoryItemName: String, inventoryItemCurrency: String, inventoryItemPrice: Double, inventoryItemQuantityInStock: Int,inventoryItemDate:String , inventoryItemTime: String, inventoryItemOther: String): InventoryItem {
        return InventoryItem(
//            inventoryItemId = inventoryItemId,
                inventoryItemOrder = inventoryItemOrder,
                inventoryItemBarcode = inventoryItemBarcode,
                inventoryItemName = inventoryItemName,
                inventoryItemCurrency = inventoryItemCurrency,
                inventoryItemPrice = inventoryItemPrice,
                inventoryItemQuantityInStock = inventoryItemQuantityInStock,
                inventoryItemDate = inventoryItemDate,
                inventoryItemTime = inventoryItemTime,
                inventoryItemOther = inventoryItemOther
        )
    }


    private fun insertInventoryItem(inventoryItem: InventoryItem) {
        viewModelScope.launch {
            inventoryItemDao.insert(inventoryItem)
        }
    }

    private fun updateInventoryItem(inventoryItem: InventoryItem) {
        viewModelScope.launch {
            inventoryItemDao.update(inventoryItem)
        }
    }

    fun updateInventoryItem(
            inventoryItemId: Int,
            inventoryItemName: String,
            inventoryItemPrice: Double,
            inventoryItemQuantityInStock: Int
    ) {
        val updatedInventoryItem = getUpdatedInventoryItemEntry(inventoryItemId,"0", "0", inventoryItemName,"NTD", inventoryItemPrice, inventoryItemQuantityInStock,"20210101", "12:02:03", "其他")
        updateInventoryItem(updatedInventoryItem)
    }

    private fun getUpdatedInventoryItemEntry(inventoryItemId: Int,inventoryItemOrder: String, inventoryItemBarcode: String, inventoryItemName: String, inventoryItemCurrency: String, inventoryItemPrice: Double, inventoryItemQuantityInStock: Int,inventoryItemDate: String, inventoryItemTime: String, inventoryItemOther: String): InventoryItem {
        return InventoryItem(
                inventoryItemId = inventoryItemId,
                inventoryItemOrder = inventoryItemOrder,
                inventoryItemBarcode = inventoryItemBarcode,
                inventoryItemName = inventoryItemName,
                inventoryItemCurrency = inventoryItemCurrency,
                inventoryItemPrice = inventoryItemPrice,
                inventoryItemQuantityInStock = inventoryItemQuantityInStock,
                inventoryItemDate = inventoryItemDate,
                inventoryItemTime = inventoryItemTime,
                inventoryItemOther = inventoryItemOther
        )
    }

    fun purchaseItem(inventoryItemName: String, inventoryItemPrice: Double, inventoryItemQuantityInStock: Int){
        val newItem = getNewInventoryItemEntry("0", "0", inventoryItemName,"NTD", inventoryItemPrice, inventoryItemQuantityInStock, "20210203","11:01:01", "IanInventoryItemOther")
        insertInventoryItem(newItem)
        val newPurchaseItem =getNewPurchaseItemEntry("0", "0", inventoryItemName,"NTD", inventoryItemPrice, inventoryItemQuantityInStock, Date(calenderTime.year,calenderTime.month,calenderTime.date),"11:01:01", "IanInventoryItemOther")
        insertPurchaseItem(newPurchaseItem)
    }

    fun sell1Item(inventoryItem: InventoryItem) {
        if (inventoryItem.inventoryItemQuantityInStock > 0) {
            // Decrease the quantity by 1
            val newInventoryItem = inventoryItem.copy(inventoryItemQuantityInStock = inventoryItem.inventoryItemQuantityInStock - 1)
            updateInventoryItem(newInventoryItem)
            val newItem = getNewSalesItemEntry("0", "0",inventoryItem.inventoryItemName, "NTD", inventoryItem.inventoryItemPrice,1, Date(calenderTime.year,calenderTime.month,calenderTime.date),"12:23:22", "IanSalesItemOther")
            insertSalesItem(newItem)
        }
    }

    fun sellMoreItem(inventoryItem: InventoryItem,amountToSale : Int) {
        if (inventoryItem.inventoryItemQuantityInStock > 0) {
            // Decrease the quantity by 1
            val newInventoryItem = inventoryItem.copy(inventoryItemQuantityInStock = inventoryItem.inventoryItemQuantityInStock - amountToSale)
            updateInventoryItem(newInventoryItem)
            val newItem = getNewSalesItemEntry("0", "0",inventoryItem.inventoryItemName, "NTD", inventoryItem.inventoryItemPrice,amountToSale, Date(calenderTime.year,calenderTime.month,calenderTime.date),"12:23:22", "IanSalesItemOther")
            insertSalesItem(newItem)
        }
    }

    fun scrapMoreItem(inventoryItem: InventoryItem,amountToScrap : Int){
        if (inventoryItem.inventoryItemQuantityInStock > 0) {
            // Decrease the quantity by 1
            val newInventoryItem = inventoryItem.copy(inventoryItemQuantityInStock = inventoryItem.inventoryItemQuantityInStock - amountToScrap)
            updateInventoryItem(newInventoryItem)
            val newItem = getNewScrapItemEntry("0", "0",inventoryItem.inventoryItemName, "NTD", inventoryItem.inventoryItemPrice,amountToScrap, Date(calenderTime.year,calenderTime.month,calenderTime.date),"12:23:22", "IanSalesItemOther")
           viewModelScope.launch {
                scrapItemDao.insert(newItem)
            }

        }
    }

    fun deleteItem(inventoryItem: InventoryItem){
        deleteInventoryItem(inventoryItem)
//        insertScrapItem(inventoryItem)
        addNewScrapItem(inventoryItem)
    }

    fun deleteInventoryItem(inventoryItem: InventoryItem) {
        viewModelScope.launch {
            inventoryItemDao.delete(inventoryItem)
        }
    }


//    fun getInventoryData(): Flow<List<Inventory>>? {
//        viewModelScope.launch {
//           Log.d("IANIAN",inventoryDao.getInventorys().toString());
//        }
//        return null
//    }

    private fun insertSalesItem(salesItem: SalesItem) {
        viewModelScope.launch {
            salesItemDao.insert(salesItem)
        }
    }

    private fun getNewSalesItemEntry(salesItemOrder: String, salesItemBarcode: String, salesItemName: String,salesItemCurrency: String, salesItemPrice: Double, salesItemQuantityInStock: Int,salesItemDate: Date,  salesItemTime: String, salesItemOther: String): SalesItem {
        return SalesItem(
//                salesItemId = salesItemId,
                salesItemOrder = salesItemOrder,
                salesItemBarcode = salesItemBarcode,
                salesItemName = salesItemName,
                salesItemCurrency = salesItemCurrency,
                salesItemPrice = salesItemPrice,
                salesItemQuantityInStock = salesItemQuantityInStock,
                salesItemDate = salesItemDate,
                salesItemTime = salesItemTime,
                salesItemOther = salesItemOther
        )
    }

    private fun insertPurchaseItem(purchaseItem: PurchaseItem) {
        viewModelScope.launch {
            purchaseItemDao.insert(purchaseItem)
        }
    }

    private fun getNewPurchaseItemEntry(
            purchaseItemOrder: String, purchaseItemBarcode: String, purchaseItemName: String, purchaseItemCurrency: String, purchaseItemPrice: Double, purchaseItemQuantityInStock: Int,
            purchaseItemDate: Date, purchaseItemTime: String, purchaseItemOther: String): PurchaseItem {
        return PurchaseItem(
//                purchaseItemId = purchaseItemId,
                purchaseItemOrder = purchaseItemOrder,
                purchaseItemBarcode = purchaseItemBarcode,
                purchaseItemName = purchaseItemName,
                purchaseItemCurrency = purchaseItemCurrency,
                purchaseItemPrice = purchaseItemPrice,
                purchaseItemQuantityInStock = purchaseItemQuantityInStock,
                purchaseItemDate = purchaseItemDate,
                purchaseItemTime = purchaseItemTime,
                purchaseItemOther = purchaseItemOther
        )
    }

    fun addNewScrapItem(inventoryItem: InventoryItem) {
        val newItem = getNewScrapItemEntry("0", "0", inventoryItem.inventoryItemName, "NTD", inventoryItem.inventoryItemPrice, inventoryItem.inventoryItemQuantityInStock, Date(calenderTime.year,calenderTime.month,calenderTime.date),"12:23:32","IanScrapItemOther")
        viewModelScope.launch {
            scrapItemDao.insert(newItem)
        }
    }

//    private fun insertScrapItem(scrapItem: ScrapItem) {
//        viewModelScope.launch {
//            scrapItemDao.insert(scrapItem)
//        }
//    }

    private fun getNewScrapItemEntry(scrapItemOrder: String, scrapItemBarcode: String, scrapItemName: String,scrapItemCurrency:String, scrapItemPrice: Double, scrapItemQuantityInStock: Int,scrapItemDate: Date, scrapItemTime: String, scrapItemOther: String): ScrapItem {
        return ScrapItem(
//                scrapItemId = scrapItemId,
                scrapItemOrder =scrapItemOrder,
                scrapItemBarcode = scrapItemBarcode,
                scrapItemName = scrapItemName,
                scrapItemCurrency = scrapItemCurrency,
                scrapItemPrice = scrapItemPrice,
                scrapItemQuantityInStock = scrapItemQuantityInStock,
                scrapItemDate = scrapItemDate,
                scrapItemTime = scrapItemTime,
                scrapItemOther = scrapItemOther
        )
    }

    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
//        if (itemName.isBlank(()) || itemPrice.isBlank() || itemCount.isBlank()) {
//            return false
//        }
        return true
    }

}


//20210906這邊room開始有改
class MainViewModelFactory(private val inventoryItemDao: InventoryItemDao, private val salesItemDao: SalesItemDao, private val purchaseItemDao: PurchaseItemDao, private val scrapItemDao: ScrapItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(inventoryItemDao, salesItemDao, purchaseItemDao, scrapItemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

