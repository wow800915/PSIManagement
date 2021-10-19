package com.example.psimanagement.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.psimanagement.data.*
import kotlinx.coroutines.launch

//20210906這邊room開始有改
class MainViewModel(private val inventoryItemDao: InventoryItemDao,private val salesItemDao: SalesItemDao,private val purchaseItemDao: PurchaseItemDao,private val scrapItemDao: ScrapItemDao) : ViewModel() {

    lateinit var inventoryItem: InventoryItem

    val allInventoryItems: LiveData<List<InventoryItem>> = inventoryItemDao.getInventoryItems().asLiveData()

    fun retrieveItem(id: Int): LiveData<InventoryItem> {
        return inventoryItemDao.getInventoryItem(id).asLiveData()
    }

    fun addNewInventoryItem(inventoryItemName: String, inventoryItemPrice: Double, inventoryItemQuantityInStock: Int) {
        val newItem = getNewInventoryEntry(2, 40, inventoryItemName,inventoryItemPrice,inventoryItemQuantityInStock,123123,"dsad")
        insertInventory(newItem)
    }

    private fun getNewInventoryEntry(inventoryItemId: Int, inventoryItemBarcode: Int, inventoryItemName: String, inventoryItemPrice: Double, inventoryItemQuantityInStock: Int, inventoryItemTime: Long, inventoryItemOther: String): InventoryItem {
        return InventoryItem(
//            inventoryItemId = inventoryItemId,
                inventoryItemBarcode = inventoryItemBarcode,
                inventoryItemName = inventoryItemName,
                inventoryItemPrice = inventoryItemPrice,
                inventoryItemQuantityInStock = inventoryItemQuantityInStock,
                inventoryItemTime = inventoryItemTime,
                inventoryItemOther = inventoryItemOther
        )
    }


    private fun insertInventory(inventoryItem: InventoryItem) {
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
        val updatedInventoryItem = getUpdatedInventoryItemEntry(inventoryItemId,0,inventoryItemName,inventoryItemPrice, inventoryItemQuantityInStock, 12311, "其他")
        updateInventoryItem(updatedInventoryItem)
    }

    private fun getUpdatedInventoryItemEntry(inventoryItemId: Int, inventoryItemBarcode: Int, inventoryItemName: String, inventoryItemPrice: Double, inventoryItemQuantityInStock: Int, inventoryItemTime: Long, inventoryItemOther: String): InventoryItem {
        return InventoryItem(
                inventoryItemId = inventoryItemId,
                inventoryItemBarcode = inventoryItemBarcode,
                inventoryItemName = inventoryItemName,
                inventoryItemPrice = inventoryItemPrice,
                inventoryItemQuantityInStock = inventoryItemQuantityInStock,
                inventoryItemTime = inventoryItemTime,
                inventoryItemOther = inventoryItemOther
        )
    }

    fun sellItem(inventoryItem: InventoryItem) {
        if (inventoryItem.inventoryItemQuantityInStock > 0) {
            // Decrease the quantity by 1
            val newInventoryItem = inventoryItem.copy(inventoryItemQuantityInStock = inventoryItem.inventoryItemQuantityInStock - 1)
            updateInventoryItem(newInventoryItem)
        }
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

    fun addNewSalesItem() {
        val newItem = getNewSalesItemEntry(1, 40, "sds",32.2,231,123123,"dsad")
        insertSalesItem(newItem)
    }

    private fun insertSalesItem(salesItem: SalesItem) {
        viewModelScope.launch {
            salesItemDao.insert(salesItem)
        }
    }

    private fun getNewSalesItemEntry(salesItemId: Int, salesItemBarcode: Int, salesItemName: String, salesItemPrice: Double, salesItemQuantityInStock: Int, salesItemTime: Long, salesItemOther: String): SalesItem {
        return SalesItem(
            salesItemId = salesItemId,
            salesItemBarcode = salesItemBarcode,
            salesItemName = salesItemName,
            salesItemPrice = salesItemPrice,
            salesItemQuantityInStock = salesItemQuantityInStock,
            salesItemTime = salesItemTime,
            salesItemOther = salesItemOther
        )
    }

    fun addNewPurchaseItem() {
        val newItem = getNewPurchaseItemEntry(1, 40, "sds",32.2,231,123123,"dsad")
        insertPurchaseItem(newItem)
    }

    private fun insertPurchaseItem(purchaseItem: PurchaseItem) {
        viewModelScope.launch {
            purchaseItemDao.insert(purchaseItem)
        }
    }

    private fun getNewPurchaseItemEntry(purchaseItemId: Int, purchaseItemBarcode: Int, purchaseItemName: String, purchaseItemPrice: Double, purchaseItemQuantityInStock: Int, purchaseItemTime: Long, purchaseItemOther: String): PurchaseItem {
        return PurchaseItem(
            purchaseItemId = purchaseItemId,
            purchaseItemBarcode = purchaseItemBarcode,
            purchaseItemName = purchaseItemName,
            purchaseItemPrice = purchaseItemPrice,
            purchaseItemQuantityInStock = purchaseItemQuantityInStock,
            purchaseItemTime = purchaseItemTime,
            purchaseItemOther = purchaseItemOther
        )
    }

    fun addNewScrapItem() {
        val newItem = getNewScrapItemEntry(1, 40, "sds",32.2,231,123123,"dsad")
        insertScrapItem(newItem)
    }

    private fun insertScrapItem(scrapItem: ScrapItem) {
        viewModelScope.launch {
            scrapItemDao.insert(scrapItem)
        }
    }

    private fun getNewScrapItemEntry(scrapItemId: Int, scrapItemBarcode: Int, scrapItemName: String, scrapItemPrice: Double, scrapItemQuantityInStock: Int, scrapItemTime: Long, scrapItemOther: String): ScrapItem {
        return ScrapItem(
            scrapItemId = scrapItemId,
            scrapItemBarcode = scrapItemBarcode,
            scrapItemName = scrapItemName,
            scrapItemPrice = scrapItemPrice,
            scrapItemQuantityInStock = scrapItemQuantityInStock,
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
class MainViewModelFactory(private val inventoryItemDao: InventoryItemDao,private val salesItemDao: SalesItemDao,private val purchaseItemDao: PurchaseItemDao,private val scrapItemDao: ScrapItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(inventoryItemDao,salesItemDao,purchaseItemDao,scrapItemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



enum class MarsApiStatus { LOADING, ERROR, DONE }
//class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
//    private var user: String? = "nullUSER"
//
//    fun setUser(aa: String) {
//        user = aa
//    }
//
//    fun getUser(): String? {
//        return user
//    }
//
//    private var _backingPropertyTest = "backingPropertyTest"
//    val backingPropertyTest: String
//        get() = _backingPropertyTest
//
//    private val _mutableLiveDataTest = MutableLiveData<String>()
//    val mutableLiveDataTest: LiveData<String>
//        get() = _mutableLiveDataTest
//
//
//    private val _quantity = MutableLiveData<Int>(0)
//    val quantity: LiveData<Int> = _quantity
//
//    fun setQuantity(numberCupcakes: Int) {
//        _quantity.value = numberCupcakes
//    }

    //{拿火星照片,測試網路
//    private val _marStatusTest = MutableLiveData<MarsApiStatus>()
//    val marStatusTest: LiveData<MarsApiStatus> = _marStatusTest
//        get() = _marStatusTest
//    private val _status = MutableLiveData<MarsApiStatus>()
//
//    val status: LiveData<MarsApiStatus> = _status
//    private val _photos = MutableLiveData<MarsPhoto>()
//    val photos: LiveData<MarsPhoto> = _photos
//    init {
//        getMarsPhotos()
//    }
//    private fun getMarsPhotos() {
//        viewModelScope.launch {
//            _marStatusTest.value = MarsApiStatus.LOADING
//            try {
////                val listResult = MarsApi.retrofitService.getPhotos()
////                _marStatusTest.value = "Success: ${listResult.size} Mars photos retrieved"
////                如果要收字串
////                _marStatusTest.value = listResult
//
//                _photos.value = MarsApi.retrofitService.getPhotos()[0]
////                _marStatusTest.value = "   First Mars image URL : ${_photos.value!!.imgSrcUrl}"
//                _marStatusTest.value = MarsApiStatus.DONE
//            } catch (e: Exception) {
//                _marStatusTest.value = MarsApiStatus.ERROR
////                _marStatusTest.value = "Failure: ${e.message}"
//            }
//        }
//    }
//    拿火星照片,測試網路}
//
//}