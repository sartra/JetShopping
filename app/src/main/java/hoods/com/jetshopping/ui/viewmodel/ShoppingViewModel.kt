package hoods.com.jetshopping.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoods.com.jetshopping.data.repository.ShoppingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val shoppingRepository: ShoppingRepository
) : ViewModel() {

    val stores = shoppingRepository.stores.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val items = shoppingRepository.items.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val itemsWithStoreAndList = shoppingRepository.itemsWithStoreAndList.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun itemsWithStoreAndListByListId(listId: Int) =
        shoppingRepository.itemsWithStoreAndListByListId(listId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun itemWithStoreAndListByItemId(itemId: Int) =
        shoppingRepository.itemWithStoreAndListByItemId(itemId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun insertList(list: hoods.com.jetshopping.data.room.model.ShoppingList) {
        viewModelScope.launch {
            shoppingRepository.insertList(list)
        }
    }

    fun insertItem(item: hoods.com.jetshopping.data.room.model.Item) {
        viewModelScope.launch {
            shoppingRepository.insertItem(item)
        }
    }

    fun updateItem(item: hoods.com.jetshopping.data.room.model.Item) {
        viewModelScope.launch {
            shoppingRepository.updateItem(item)
        }
    }

    fun deleteItem(item: hoods.com.jetshopping.data.room.model.Item) {
        viewModelScope.launch {
            shoppingRepository.deleteItem(item)
        }
    }
}

