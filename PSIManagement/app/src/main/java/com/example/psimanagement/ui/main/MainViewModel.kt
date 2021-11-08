package com.example.psimanagement.ui.main

import androidx.lifecycle.*
import com.example.psimanagement.data.*
import kotlinx.coroutines.launch

//20210906這邊room開始有改
class MainViewModel(private val inventoryItemDao: InventoryItemDao, private val salesItemDao: SalesItemDao, private val purchaseItemDao: PurchaseItemDao, private val scrapItemDao: ScrapItemDao) : ViewModel() {

//    lateinit var inventoryItem: InventoryItem

    val allInventoryItems: LiveData<List<InventoryItem>> = inventoryItemDao.getInventoryItems().asLiveData()
    val allPurchaseItems: LiveData<List<PurchaseItem>> = purchaseItemDao.getPurchaseItems().asLiveData()

    fun retrieveItem(id: Int): LiveData<InventoryItem> {
        return inventoryItemDao.getInventoryItem(id).asLiveData()
    }

    fun purchaseItem(inventoryItemName: String, inventoryItemPrice: Double, inventoryItemQuantityInStock: Int){
        val newItem = getNewInventoryItemEntry("0", "0", inventoryItemName,"NTD", inventoryItemPrice, inventoryItemQuantityInStock, "20210203","11:01:01", "IanInventoryItemOther")
        insertInventoryItem(newItem)
        val newPurchaseItem =getNewPurchaseItemEntry("0", "0", inventoryItemName,"NTD", inventoryItemPrice, inventoryItemQuantityInStock, "20210203","11:01:01", "IanInventoryItemOther")
        insertPurchaseItem(newPurchaseItem)
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

    fun sellItem(inventoryItem: InventoryItem) {
        if (inventoryItem.inventoryItemQuantityInStock > 0) {
            // Decrease the quantity by 1
            val newInventoryItem = inventoryItem.copy(inventoryItemQuantityInStock = inventoryItem.inventoryItemQuantityInStock - 1)
            updateInventoryItem(newInventoryItem)
            add1NewSalesItem()
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

    fun add1NewSalesItem() {
        val newItem = getNewSalesItemEntry("0", "0","IanSaleItemName", "NTD", 1.0,1, "20200102","12:23:22", "IanSalesItemOther")
        insertSalesItem(newItem)
    }

    private fun insertSalesItem(salesItem: SalesItem) {
        viewModelScope.launch {
            salesItemDao.insert(salesItem)
        }
    }

    private fun getNewSalesItemEntry(salesItemOrder: String, salesItemBarcode: String, salesItemName: String,salesItemCurrency: String, salesItemPrice: Double, salesItemQuantityInStock: Int,salesItemDate: String,  salesItemTime: String, salesItemOther: String): SalesItem {
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

    private fun getNewPurchaseItemEntry(purchaseItemOrder: String, purchaseItemBarcode: String, purchaseItemName: String, purchaseItemCurrency: String, purchaseItemPrice: Double, purchaseItemQuantityInStock: Int,purchaseItemDate: String,  purchaseItemTime: String, purchaseItemOther: String): PurchaseItem {
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

    fun addNewScrapItem() {
        val newItem = getNewScrapItemEntry("0", "0", "IanScrapItemName", "NTD", 1.0, 1, "20210102","12:23:32","IanScrapItemOther")
        insertScrapItem(newItem)
    }

    private fun insertScrapItem(scrapItem: ScrapItem) {
        viewModelScope.launch {
            scrapItemDao.insert(scrapItem)
        }
    }

    private fun getNewScrapItemEntry(scrapItemOrder: String, scrapItemBarcode: String, scrapItemName: String,scrapItemCurrency:String, scrapItemPrice: Double, scrapItemQuantityInStock: Int,scrapItemDate: String, scrapItemTime: String, scrapItemOther: String): ScrapItem {
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