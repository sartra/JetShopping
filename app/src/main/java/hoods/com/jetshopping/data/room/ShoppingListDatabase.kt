package hoods.com.jetshopping.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hoods.com.jetshopping.data.room.converters.DateConverter
import hoods.com.jetshopping.data.room.model.Item
import hoods.com.jetshopping.data.room.model.ShoppingList
import hoods.com.jetshopping.data.room.model.Store

@Database(
    entities = [Item::class, ShoppingList::class, Store::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(DateConverter::class)
abstract class ShoppingListDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun listDao(): ListDao
    abstract fun storeDao(): StoreDao
}