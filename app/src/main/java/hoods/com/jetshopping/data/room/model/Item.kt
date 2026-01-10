package hoods.com.jetshopping.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "items")
data class Item(
    @PrimaryKey
    @ColumnInfo(name = "item_id")
    val id: Int,
    val name: String,
    val price: Double,
    val quantity: Int,
    @ColumnInfo(name = "list_id")
    val listId: Int, // foreign key
    @ColumnInfo(name = "store_id")
    val storeId: Int, // foreign key
    @ColumnInfo(name = "created_at")
    val date: Date,
    val createdAt: Long = System.currentTimeMillis(),
    val isChecked: Boolean,
)
