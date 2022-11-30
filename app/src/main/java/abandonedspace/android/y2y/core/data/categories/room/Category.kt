package abandonedspace.android.y2y.core.data.categories.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",
    indices = [Index("id")]
)
data class Category(

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "color_hex")
    val colorHex: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
