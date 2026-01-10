package hoods.com.jetshopping.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoods.com.jetshopping.data.repository.ShoppingRepository
import hoods.com.jetshopping.data.room.ItemsWithStoreAndList
import hoods.com.jetshopping.data.room.model.Item
import hoods.com.jetshopping.ui.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val shoppingRepository: ShoppingRepository
) : ViewModel() {

    private val selectedCategoryId = MutableStateFlow(10001) // Default: all items
    private val category = MutableStateFlow<Category>(Category())

    // Single flow that switches based on selected category
    private val itemsFlow = selectedCategoryId.flatMapLatest { listId ->
        if (listId == 10001) {
            shoppingRepository.getItemsWithStoreAndList
        } else {
            shoppingRepository.getItemsWithStoreAndListByListId(listId)
        }
    }

    // Combine items with UI state to create the complete state
    val uiState: StateFlow<HomeState> = combine(
        itemsFlow,
        category
    ) { items, currentCategory ->
        HomeState.Content(
            items = items,
            category = currentCategory,
            loadingItems = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeState.Loading
    )

    fun onCategoryChange(newCategory: Category) {
        category.value = newCategory
        selectedCategoryId.value = newCategory.id
    }

    fun filterBy(listId: Int) {
        selectedCategoryId.value = listId
    }

    fun onItemChecked(item: Item) {
        viewModelScope.launch {
            val updatedItem = item.copy(isChecked = !item.isChecked)
            shoppingRepository.updateItem(updatedItem)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            shoppingRepository.deleteItem(item)
        }
    }
}

sealed class HomeState {
    data class Content(
        val items: List<ItemsWithStoreAndList> = emptyList(),
        val category: Category = Category(),
        val itemChecked: Boolean = false,
        val loadingItems: Boolean = false
    ): HomeState()

    object Loading : HomeState()
    object Error : HomeState()
}

