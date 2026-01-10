package hoods.com.jetshopping.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import hoods.com.jetshopping.data.room.model.Item
import hoods.com.jetshopping.data.room.model.Store
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStore(store: Store)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateStore(store: Store)

    @Delete
    suspend fun deleteStore(store: Store)

    @Query("SELECT * FROM stores")
    fun getAllStores(): Flow<List<Store>>

    @Query("SELECT * FROM stores WHERE store_id = :id")
    fun getStoreById(id: Int): Flow<Store?>

}