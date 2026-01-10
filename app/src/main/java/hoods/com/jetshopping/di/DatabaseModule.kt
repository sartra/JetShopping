package hoods.com.jetshopping.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hoods.com.jetshopping.data.room.ItemDao
import hoods.com.jetshopping.data.room.ListDao
import hoods.com.jetshopping.data.room.ShoppingListDatabase
import hoods.com.jetshopping.data.room.StoreDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideShoppingListDatabase(
        @ApplicationContext context: Context
    ): ShoppingListDatabase {
        return Room.databaseBuilder(
            context,
            ShoppingListDatabase::class.java,
            "shopping_list_database"
        ).build()
    }

    @Provides
    fun provideItemDao(database: ShoppingListDatabase): ItemDao {
        return database.itemDao()
    }

    @Provides
    fun provideListDao(database: ShoppingListDatabase): ListDao {
        return database.listDao()
    }

    @Provides
    fun provideStoreDao(database: ShoppingListDatabase): StoreDao {
        return database.storeDao()
    }
}

