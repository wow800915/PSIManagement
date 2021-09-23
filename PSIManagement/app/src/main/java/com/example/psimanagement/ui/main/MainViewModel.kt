package com.example.psimanagement.ui.main

import android.text.Editable
import androidx.lifecycle.*
import androidx.room.ColumnInfo
import com.example.psimanagement.data.*
import com.example.psimanagement.network.MarsApi
import com.example.psimanagement.network.MarsPhoto
import kotlinx.coroutines.launch

//20210906這邊room開始有改
class MainViewModel(private val inventoryDao: InventoryDao,private val salesDao: SalesDao,private val purchaseDao: PurchaseDao,private val scrapDao: ScrapDao) : ViewModel() {

    fun addNewInventoryItem() {
        val newItem = getNewInventoryEntry(1, 40, "sds",32.2,231,123123,"dsad")
        insertInventory(newItem)
    }

    private fun insertInventory(inventory: Inventory) {
        viewModelScope.launch {
            inventoryDao.insert(inventory)
        }
    }

    private fun getNewInventoryEntry(inventoryItemId: Int, inventoryItemBarcode: Int, inventoryItemName: String, inventoryItemPrice: Double, inventoryItemQuantityInStock: Int, inventoryItemTime: Long, inventoryItemOther: String): Inventory {
        return Inventory(
            inventoryItemId = inventoryItemId,
            inventoryItemBarcode = inventoryItemBarcode,
            inventoryItemName = inventoryItemName,
            inventoryItemPrice = inventoryItemPrice,
            inventoryItemQuantityInStock = inventoryItemQuantityInStock,
            inventoryItemTime = inventoryItemTime,
            inventoryItemOther = inventoryItemOther
        )
    }

    fun addNewSalesItem() {
        val newItem = getNewSalesEntry(1, 40, "sds",32.2,231,123123,"dsad")
        insertSales(newItem)
    }

    private fun insertSales(sales: Sales) {
        viewModelScope.launch {
            salesDao.insert(sales)
        }
    }

    private fun getNewSalesEntry(salesItemId: Int, salesItemBarcode: Int, salesItemName: String, salesItemPrice: Double, salesItemQuantityInStock: Int, salesItemTime: Long, salesItemOther: String): Sales {
        return Sales(
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
        val newItem = getNewPurchaseEntry(1, 40, "sds",32.2,231,123123,"dsad")
        insertPurchase(newItem)
    }

    private fun insertPurchase(purchase: Purchase) {
        viewModelScope.launch {
            purchaseDao.insert(purchase)
        }
    }

    private fun getNewPurchaseEntry(purchaseItemId: Int, purchaseItemBarcode: Int, purchaseItemName: String, purchaseItemPrice: Double, purchaseItemQuantityInStock: Int, purchaseItemTime: Long, purchaseItemOther: String): Purchase {
        return Purchase(
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
        val newItem = getNewScrapEntry(1, 40, "sds",32.2,231,123123,"dsad")
        insertScrap(newItem)
    }

    private fun insertScrap(scrap: Scrap) {
        viewModelScope.launch {
            scrapDao.insert(scrap)
        }
    }

    private fun getNewScrapEntry(scrapItemId: Int, scrapItemBarcode: Int, scrapItemName: String, scrapItemPrice: Double, scrapItemQuantityInStock: Int, scrapItemTime: Long, scrapItemOther: String): Scrap {
        return Scrap(
            scrapItemId = scrapItemId,
            scrapItemBarcode = scrapItemBarcode,
            scrapItemName = scrapItemName,
            scrapItemPrice = scrapItemPrice,
            scrapItemQuantityInStock = scrapItemQuantityInStock,
            scrapItemTime = scrapItemTime,
            scrapItemOther = scrapItemOther
        )
    }
}
//20210906這邊room開始有改
class MainViewModelFactory(private val inventoryDao: InventoryDao,private val salesDao: SalesDao,private val purchaseDao: PurchaseDao,private val scrapDao: ScrapDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(inventoryDao,salesDao,purchaseDao,scrapDao) as T
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