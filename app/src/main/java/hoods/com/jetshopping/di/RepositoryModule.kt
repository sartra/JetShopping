package hoods.com.jetshopping.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hoods.com.jetshopping.data.repository.ShoppingRepository
import hoods.com.jetshopping.data.repository.ShoppingRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindShoppingRepository(
        shoppingRepositoryImpl: ShoppingRepositoryImpl
    ): ShoppingRepository
}

