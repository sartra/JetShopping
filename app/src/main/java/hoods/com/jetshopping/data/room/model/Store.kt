package hoods.com.jetshopping.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stores")
data class Store(
    @ColumnInfo(name = "store_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val listId: Int, // foreign key
)
