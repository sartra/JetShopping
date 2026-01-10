package hoods.com.jetshopping.data.repository

import hoods.com.jetshopping.data.room.ItemDao
import hoods.com.jetshopping.data.room.ItemsWithStoreAndList
import hoods.com.jetshopping.data.room.ListDao
import hoods.com.jetshopping.data.room.StoreDao
import hoods.com.jetshopping.data.room.model.Item
import hoods.com.jetshopping.data.room.model.ShoppingList
import hoods.com.jetshopping.data.room.model.Store
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
    val stores: Flow<List<Store>>
    val items: Flow<List<Item>>
    val itemsWithStoreAndList: Flow<List<ItemsWithStoreAndList>>

    fun itemsWithStoreAndListByListId(listId: Int): Flow<List<ItemsWithStoreAndList>>
    fun itemWithStoreAndListByItemId(itemId: Int): Flow<ItemsWithStoreAndList?>

    suspend fun insertList(list: ShoppingList)
    suspend fun insertItem(item: Item)
    suspend fun updateItem(item: Item)
    suspend fun deleteItem(item: Item)
}

class ShoppingRepositoryImpl @Inject constructor(
    private val storeDao: StoreDao,
    private val itemDao: ItemDao,
    private val listDao: ListDao
) : ShoppingRepository {
    override val stores = storeDao.getAllStores()
    override val items = itemDao.getAllItems()
    override val itemsWithStoreAndList = listDao.getItemsWithStoreAndList()

    override fun itemsWithStoreAndListByListId(listId: Int) =
        listDao.getItemsWithStoreAndListFilteredById(listId)

    override fun itemWithStoreAndListByItemId(itemId: Int) =
        listDao.getItemWithStoreAndListFilteredById(itemId)

    override suspend fun insertList(list: ShoppingList) = listDao.insertList(list)
    override suspend fun insertItem(item: Item) = itemDao.insertItem(item)
    override suspend fun updateItem(item: Item) = itemDao.updateItem(item)
    override suspend fun deleteItem(item: Item) = itemDao.deleteItem(item)
}
