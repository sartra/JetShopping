package hoods.com.jetshopping.data.room

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import hoods.com.jetshopping.data.room.model.Item
import hoods.com.jetshopping.data.room.model.ShoppingList
import hoods.com.jetshopping.data.room.model.Store
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: ShoppingList)

    @Transaction
    @Query("SELECT * FROM items")
    fun getItemsWithStoreAndList(): Flow<List<ItemsWithStoreAndList>>

    @Transaction
    @Query("SELECT * FROM items WHERE list_id = :listId")
    fun getItemsWithStoreAndListFilteredByListId(listId: Int): Flow<List<ItemsWithStoreAndList>>

    @Transaction
    @Query("SELECT * FROM items WHERE item_id = :itemId")
    fun getItemsWithStoreAndListFilteredByItemId(itemId: Int): Flow<ItemsWithStoreAndList>

}

data class ItemsWithStoreAndList(
    @Embedded
    val item: Item,
    @Relation(
        parentColumn = "list_id",
        entityColumn = "list_id"
    )
    val shoppingList: ShoppingList,
    @Relation(
        parentColumn = "store_id",
        entityColumn = "store_id"
    )
    val store: Store,
)
